/*
 *         Copyright 2017 Petr Štechmüller
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *         limitations under the License.
 */

package cz.stechy.screens;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import cz.stechy.screens.Notification.Length;
import cz.stechy.screens.animation.FadeAnimator;
import cz.stechy.screens.animation.IScreenAnimator;
import cz.stechy.screens.base.IMainScreen;
import cz.stechy.screens.base.IScreenLoader;
import cz.stechy.screens.base.IScreenManager;
import cz.stechy.screens.base.WidgetBuilderProvider;
import cz.stechy.screens.loader.SimpleScreenLoader;
import cz.stechy.screens.loader.ZipScreenLoader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.animation.Transition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;

/**
 * Hlavní správce screenů
 */
public final class ScreenManager implements IScreenManager {

    // region Constants

    // Výchozí šířka okna
    public static final double DEFAULT_WIDTH = 800;
    // Výchozí výška okna
    public static final double DEFAULT_HEIGHT = 600;

    // endregion

    // region Variables

    // Handler pro odchycení události zmáčknuté klávesy v aktivním okně
    private static EventHandler<? super KeyEvent> keyPressedHandler;
    // Handler pro odchycení události puštění klávesy v aktivním okne
    private static EventHandler<? super KeyEvent> keyReleasedHandler;
    // Globální nastavení dekorátoru pro všechna okna
    private static boolean undecorateGlobal = false;
    // Rodičovský screen manažer. Pouze, pokud se jedná o dialog
    private final ScreenManager mParentManager;
    // Id akce
    private final int mActionId;
    // Kolekce obsahující screeny
    private final Map<String, ScreenInfo> mScreens = new HashMap<>();
    // Stack s aktivními screeny v aktuálním dialogu
    private final Stack<ActiveScreen> mActiveScreens = new Stack<>();
    // Kolekce s názvy screenů, které se nemají načítat
    private final List<String> mBlackList = new ArrayList<>();
    // Kolekce s okny, které spravuje tento screen manager
    private final List<IScreenManager> mChildScreenManagers = new ArrayList<>();
    // Titulek okna
    private final StringProperty mTitle = new SimpleStringProperty();
    // Konfigurace obsahující cesty k důležitým adresářům
    private ScreenManagerConfiguration mConfiguration;
    // Resources
    private ResourceBundle mResources;
    // Továrna kontrolerů
    private Callback<Class<?>, Object> mFactory;
    // Továrna JavaFX nodů
    private CustomWidgetBuilder mBuilderFactory = new CustomWidgetBuilder();
    // Screen transition
    private IScreenAnimator mAnimator;
    // Screen loader
    private IScreenLoader mScreenLoader;
    // Kontroler hlavního screenu
    private IMainScreen mMainScreen;
    // Handler, který se zavolá po zobrazení dialogu
    private OnDialogShow onDialogShowHandler;
    // Screen s čekacím dialogem
    private ScreenInfo waitingScreen;
    // Šířka okna
    private double mWidth;
    // Výška okna
    private double mHeight;
    private JFXSnackbar snackbar;
    private EventHandler<WindowEvent> windowCloseHandler;

    // endregion

    // region Constructor

    /**
     * Soukromý konstruktor pro vytvoření hierarchické struktury správců oken
     *
     * @param parentManager Rodičovský ScreenManager
     */
    private ScreenManager(final ScreenManager parentManager, final int actionId) {
        this.mParentManager = parentManager;
        this.mActionId = actionId;
        this.mBlackList.addAll(parentManager.mBlackList);
        this.mConfiguration = parentManager.mConfiguration;
        this.mResources = parentManager.mResources;
        this.mAnimator = parentManager.mAnimator;
        this.mFactory = parentManager.mFactory;
        this.mBuilderFactory = parentManager.mBuilderFactory;
        this.mScreenLoader = parentManager.mScreenLoader;
        try {
            loadScreens();
        } catch (IOException e) {
            // Nemělo by nikdy nastat
            e.printStackTrace();
        }
    }

    /**
     * Vytvoří nový správce screenů
     *
     * @param screenManagerConfiguration Konfigurace obsahující cesty k důležitým adresářům
     */
    public ScreenManager(final ScreenManagerConfiguration screenManagerConfiguration) {
        this(screenManagerConfiguration, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Vytvoří nový správce screenů
     *
     * @param screenManagerConfiguration Konfigurace obsahující cesty k důležitým adresářům
     * @param witdh Šířka okna
     * @param heiht Výška okna
     */
    public ScreenManager(final ScreenManagerConfiguration screenManagerConfiguration, final double witdh, final double heiht) {
        if (screenManagerConfiguration == null) {
            throw new IllegalArgumentException();
        }

        this.mConfiguration = screenManagerConfiguration;
        this.mParentManager = null;
        this.mActionId = NO_ACTION;
        this.mWidth = witdh;
        this.mHeight = heiht;
        this.mAnimator = new FadeAnimator();
        String fxmlPath = screenManagerConfiguration.fxml.toExternalForm();
        if (fxmlPath.contains("zip") || fxmlPath.contains("jar")) {
            this.mScreenLoader = new ZipScreenLoader(screenManagerConfiguration.fxml, mBlackList);
        } else {
            this.mScreenLoader = new SimpleScreenLoader(screenManagerConfiguration.fxml, mBlackList);
        }
    }

    // endregion

    // region Public static methody

    /**
     * Nastaví handler pro událost stisknu klávesy pro všechny okna spravovaná správcem
     */
    public static void setKeyPressedHandler(EventHandler<? super KeyEvent> keyPressedHandler) {
        if (ScreenManager.keyPressedHandler != null) {
            return;
        }

        ScreenManager.keyPressedHandler = keyPressedHandler;
    }

    /**
     * Nastaví handler pro událost uvolnění klávesy pro všechny okna spravovaná správcem
     */
    public static void setKeyReleasedHandler(EventHandler<? super KeyEvent> keyReleasedHandler) {
        if (ScreenManager.keyReleasedHandler != null) {
            return;
        }

        ScreenManager.keyReleasedHandler = keyReleasedHandler;
    }

    /**
     * Aktivuje globální undecorator.
     * Všechna okna budou použivat vlastní vzhled
     */
    public static void enableGlobalUndecorator() {
        undecorateGlobal = true;
    }

    // endregion

    // region Private methods

    /**
     * Načte fxml soubor, přidá screen do kolekce
     *
     * @param screenInfo Přepravka obsahující informace o screenu
     */
    private void loadScreen(final ScreenInfo screenInfo) throws IOException {
        if (screenInfo.loaded) {
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(screenInfo.url);
        loader.setResources(mResources);
        loader.setControllerFactory(mFactory);
        loader.setBuilderFactory(mBuilderFactory);
        Parent parent = loader.load();
        BaseController controller = loader.getController();
        controller.setScreenManager(this);
        screenInfo.controller = controller;
        screenInfo.node = parent;
        screenInfo.setLoaded();
    }

    /**
     * @return True, pokud se jedná o správce, který spravuje dialog, jinak false
     */
    private boolean isDialog() {
        return mParentManager != null;
    }

    /**
     * Zavře aktuální dialog
     *
     * @param screen Screen, který je v dialogu určený k zavření
     */
    private void close(ActiveScreen screen) {
        ((Stage) screen.screenInfo.node.getScene().getWindow()).close();
    }

    URL getScreenUrl(String name) {
        final ScreenInfo screenInfo = mScreens.get(name);
        return screenInfo.url;
    }

    // endregion

    // region Public methods

    /**
     * Načte všechny screeny obsažené ve složce pathToViews
     */
    public void loadScreens() throws IOException {
        mScreens.clear();
        mScreens.putAll(mScreenLoader.loadScreens());

    }

    /**
     * Přidá screen do kolekce
     *
     * @param screenInfo Přepravk s {@link ScreenInfo}
     */
    public void addScreen(final ScreenInfo screenInfo) {
        mScreens.put(screenInfo.name, screenInfo);
    }

    /**
     * Odstraní screen z kolekce
     *
     * @param name Screen name
     */
    public void unloadScreen(final String name) {
        mScreens.remove(name);
    }

    /**
     * Přidá screeny do blacklistu, aby se zabránilo jejich načtení
     *
     * @param names Názvy screenů do blacklistu
     */
    public void addScreensToBlacklist(final String... names) {
        mBlackList.addAll(Arrays.asList(names));
    }

    /**
     * Zobrazí nový dialog
     * @param parent Kořenový prvek
     * @param stage Okno, ve kterém se má dialog zobrazit
     * @param undecorate
     */
    public void showNewDialog(AnchorPane parent, Stage stage, boolean undecorate) {
        stage.setOnCloseRequest(mOnCloseHandlerInternal);
        final Scene scene;
        if (undecorate) {
            final JFXDecorator decorator = new JFXDecoratorWithTitle(stage, parent, false, true, true);
            scene = new Scene(decorator);
            stage.initStyle(StageStyle.TRANSPARENT);
        } else {
            scene = new Scene(parent);
        }
        scene.getStylesheets().setAll(mConfiguration.css);
        scene.setFill(null);
        scene.setOnKeyPressed(keyPressedHandler);
        scene.setOnKeyReleased(keyReleasedHandler);
        final Optional<Node> notificationContainerOptional = parent.getChildren().stream()
            .filter(node -> node.getId().equals("notificationContainer")).findFirst();
        if (notificationContainerOptional.isPresent()) {
            Pane notificationContainer = (Pane) notificationContainerOptional.get();
            snackbar = new JFXSnackbar(notificationContainer);
            Group group = (Group) notificationContainer.getChildren().get(0);
            notificationContainer.visibleProperty().bind(group.visibleProperty());
        } else {
            snackbar = new JFXSnackbar(parent);
        }
        stage.setScene(scene);
        stage.setWidth(mWidth);
        stage.setHeight(mHeight);
        stage.titleProperty().bind(mTitle);
        stage.show();
    }

    /**
     * Přidá stavitele widgetu
     *
     * @param widgetBuilderProvider {@link WidgetBuilderProvider}
     */
    public void addWidgetBuilderProvider(WidgetBuilderProvider widgetBuilderProvider) {
        mBuilderFactory.addWidgetBuilderProvider(widgetBuilderProvider);
    }

    /**
     * Odebere stavitele widgetu
     *
     * @param widgetBuilderProvider {@link WidgetBuilderProvider}
     */
    public void removeWidgetBuilderProvider(WidgetBuilderProvider widgetBuilderProvider) {
        mBuilderFactory.removeWidgetBuilderProvider(widgetBuilderProvider);
    }

    // endregion

    // region Setery a Gettery

    /**
     * Nastaví hlavní screen kontroleru
     *
     * @param mainScreen Hlavní screen
     */
    public void setMainScreen(final IMainScreen mainScreen) {
        mMainScreen = mainScreen;
    }

    /**
     * @return Resource bundle
     */
    public ResourceBundle getResources() {
        return mResources;
    }

    /**
     * Nastaví mResources
     *
     * @param resources {@link ResourceBundle}
     */
    public void setResources(final ResourceBundle resources) {
        this.mResources = resources;
    }

    /**
     * @return {@link ScreenManagerConfiguration} obsahující cesty k důleitým adresářům
     */
    public ScreenManagerConfiguration getScreenManagerConfiguration() {
        return mConfiguration;
    }

    /**
     * Přidá nový handler na zobrazení dialogu
     *
     * @param handler {@link OnDialogShow}
     */
    public void setOnDialogShowHandler(OnDialogShow handler) {
        onDialogShowHandler = handler;
    }

    public Callback<Class<?>, Object> getFactory() {
        return mFactory;
    }

    public CustomWidgetBuilder getBuilderFactory() {
        return mBuilderFactory;
    }

    /**
     * Nastaví továrnu na kontrolery
     *
     * @param factory Továrna na kontrolery
     */
    public void setControllerFactory(Callback<Class<?>, Object> factory) {
        this.mFactory = factory;
    }

    /**
     * Nastaví animaci pro přechod mezi screeny
     *
     * @param mAnimator {@link IScreenAnimator}
     */
    public void setmAnimator(IScreenAnimator mAnimator) {
        this.mAnimator = mAnimator;
    }

    // endregion

    @Override
    public void showScreenForResult(String name, int actionId, Bundle bundle) {
        final ScreenInfo screenInfo = mScreens.get(name);
        final Node container = mMainScreen.getContainer();
        assert screenInfo != null;
        try {
            loadScreen(screenInfo);
            if (mActiveScreens.isEmpty()) { // Zobrazuji první screen v dialogu
                ActiveScreen newScreen = new ActiveScreen(NO_ACTION, screenInfo);
                screenInfo.controller.beforeShow();
                mMainScreen.setChildNode(screenInfo.node);
                mActiveScreens.push(newScreen);
                screenInfo.controller.onCreate(bundle);
                screenInfo.controller.onResume();
                final Transition animationIn = mAnimator.getShowAnimation(container);
                animationIn.setOnFinished(event -> {
                    if (onDialogShowHandler != null) {
                        onDialogShowHandler.onShow();
                    }
                });
                animationIn.play();

            } else { // Zobrazuji n-tý screen v dialogu
                final ActiveScreen parentScreen = mActiveScreens.peek();
                // Pokud chci zobrazit znovu stejný screen, tak to nedovolím
                if (Objects.equals(name, parentScreen.screenInfo.name)) {
                    return;
                }

                // Před měnící se animací
                parentScreen.screenInfo.controller.onClose();
                ActiveScreen newScreen = new ActiveScreen(actionId, screenInfo, parentScreen);
                screenInfo.controller.beforeShow();
                // Inicializace animace
                final Transition animationOut = mAnimator.getHideAnimation(container);
                animationOut.setOnFinished(event -> {
                    mMainScreen.setChildNode(screenInfo.node);
                    mActiveScreens.push(newScreen);
                    screenInfo.controller.onCreate(bundle);
                    screenInfo.controller.onResume();
                    final Transition animationIn = mAnimator.getShowAnimation(container);
                    animationIn.setOnFinished(event1 -> {
                        if (onDialogShowHandler != null) {
                            onDialogShowHandler.onShow();
                        }
                    });
                    animationIn.play();
                });
                animationOut.play();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showDialogForResult(String name, int actionId, Bundle bundle) {
        try {
            FXMLLoader loader = new FXMLLoader(mConfiguration.baseFxml);
            loader.setResources(mResources);
            AnchorPane parent = loader.load();
            IMainScreen mainScreen = loader.getController();
            ScreenManager newManager = new ScreenManager(this, actionId);
            mChildScreenManagers.add(newManager);
            newManager.setMainScreen(mainScreen);
            newManager.showNewDialog(parent, new Stage(), undecorateGlobal);
            newManager.showScreen(name, bundle);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void showPopupForResult(String name, int actionId, Bundle bundle, double x, double y) {
        try {
            FXMLLoader loader = new FXMLLoader(mConfiguration.baseFxml);
            loader.setResources(mResources);
            AnchorPane parent = loader.load();
            IMainScreen mainScreen = loader.getController();
            ScreenManager newManager = new ScreenManager(this, actionId);
            mChildScreenManagers.add(newManager);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setX(x);
            stage.setY(y);
            stage.setResizable(false);
            stage.setAlwaysOnTop(true);
            newManager.setMainScreen(mainScreen);
            newManager.showNewDialog(parent, stage, false);
            parent.setStyle("-fx-background: darkgrey");
            newManager.showScreen(name, bundle);
            mMainScreen.getContainer().getScene().getWindow().focusedProperty().addListener((observable, oldValue, hasFocus) -> {
                if (hasFocus) {
                    newManager.finish(new Bundle(), -1);
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<BaseController> showWaitingScreen(String name) {
        if (waitingScreen != null) {
            return Optional.empty();
        }

        final ScreenInfo screenInfo = mScreens.get(name);
        assert screenInfo != null;
        try {
            loadScreen(screenInfo);
            mMainScreen.disableScreen();
            mMainScreen.addChildNode(screenInfo.node);
            waitingScreen = screenInfo;
            return Optional.of(screenInfo.controller);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public void hideWaitingScreen() {
        if (waitingScreen == null) {
            return;
        }

        mMainScreen.removeChildNode(waitingScreen.node);
        mMainScreen.enableScreen();
        waitingScreen = null;
    }

    @Override
    public void back() {
        // Na stacku vždy musí být alespoň jeden screen (ten, který zavolal tuto metodu)
        final ActiveScreen activeScreen = mActiveScreens.peek();
        if (activeScreen.parent == null) { // Pokud aktuální screen nemá rodiče
            if (isDialog()) { // Pokud se jedná o správce okna v dialogu
                activeScreen.screenInfo.controller.onClose();
                close(activeScreen);
            }
        } else { // Pokud aktuální screen má rodiče
            mActiveScreens.pop(); // Můžu popnout pouze tehdy, pokud má nějakého rodiče
            final Node container = mMainScreen.getContainer();
            final ActiveScreen previousScreen = mActiveScreens.peek();
            // Vím, že rodič existuje, protože prošel horní podmínkou
            assert previousScreen != null;
            final Transition animationOut = mAnimator.getHideAnimation(container);
            animationOut.setOnFinished(event -> {
                activeScreen.screenInfo.controller.onClose();
                previousScreen.screenInfo.controller.beforeShow();
                mMainScreen.setChildNode(previousScreen.screenInfo.node);
                previousScreen.screenInfo.controller.onResume();
                mAnimator.getShowAnimation(container).play();
            });
            animationOut.play();
        }
    }

    @Override
    public void finish(Bundle bundle, int statusCode) {
        if (mParentManager == null) {
            return;
        }
        // Na stacku vždy musí být alespoň jeden screen (ten, který zavolal tuto metodu)
        final ActiveScreen activeScreen = mActiveScreens.peek();
        activeScreen.screenInfo.controller.onClose();
        mParentManager.mActiveScreens.peek().screenInfo.controller.onScreenResult(
            statusCode, mActionId, bundle);
        close(activeScreen);
    }

    @Override
    public void resize(double width, double heithg) {
        mWidth = width;
        mHeight = heithg;
        if (mMainScreen != null && mMainScreen.getContainer().getScene() != null) {
            mMainScreen.getContainer().getScene().getWindow().setWidth(width);
            mMainScreen.getContainer().getScene().getWindow().setHeight(heithg);
        }
    }

    @Override
    public void setTitle(String title) {
        mTitle.setValue(title);
    }

    @Override
    public void closeChildScreens() {
        for (IScreenManager mChildScreenManager : mChildScreenManagers) {
            mChildScreenManager.closeChildScreens();
        }
        mChildScreenManagers.clear();

        finish(new Bundle(), BaseController.RESULT_FAIL);
    }

    @Override
    public void showNotification(Notification notification) {
        long timeout = (long) NOTIFICATION_TIMEOUT[notification.length.ordinal()].toMillis();
        snackbar.enqueue(new SnackbarEvent(notification.message, notification.actionText, timeout,
            notification.length == Length.INFINITE, event -> {
            if (notification.actionHandler != null) {
                notification.actionHandler.handle(event);
            }
            snackbar.close();
        }));
    }

    @Override
    public Node getRoot() {
        return mMainScreen.getContainer();
    }

    @Override
    public ScreenPartManager getPartManager() {
        return new ScreenPartManager(this);
    }

    public void setOnCloseWindowHandler(EventHandler<WindowEvent> windowCloseHandler) {
        this.windowCloseHandler = windowCloseHandler;
    }

    public interface OnDialogShow {
        void onShow();
    }

    private static final Duration[] NOTIFICATION_TIMEOUT = new Duration[] {
        Duration.seconds(1),
        Duration.millis(2500),
        Duration.INDEFINITE
    };

    private final EventHandler<WindowEvent> mOnCloseHandlerInternal = event -> {
        mActiveScreens.peek().screenInfo.controller.onClose();
        if (this.windowCloseHandler != null) {
            windowCloseHandler.handle(event);
        }
    };
}
