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

import cz.stechy.screens.animation.FadeAnimator;
import cz.stechy.screens.animation.IScreenAnimator;
import cz.stechy.screens.base.IMainScreen;
import cz.stechy.screens.base.IScreenLoader;
import cz.stechy.screens.base.IScreenManager;
import cz.stechy.screens.base.IScreenTransition;
import cz.stechy.screens.loader.SimpleScreenLoader;
import cz.stechy.screens.loader.ZipScreenLoader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.Stack;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    // Kolekce s notifikacemi, které se zobrazují na screenu
    private final Queue<Notification> notifications = new ArrayDeque<>();
    private final BooleanProperty notifiing = new SimpleBooleanProperty();
    // Titulek okna
    private final StringProperty mTitle = new SimpleStringProperty();
    // Konfigurace obsahující cesty k důležitým adresářům
    private ScreenManagerConfiguration mConfiguration;
    // Resources
    private ResourceBundle mResources;
    // Továrna kontrolerů
    private Callback<Class<?>, Object> mFactory;
    // Screen transition
    private IScreenAnimator mAnimator;
    // Screen loader
    private IScreenLoader mScreenLoader;
    // Kontroler hlavního screenu
    private IMainScreen mMainScreen;
    // Handler, který se zavolá po zobrazení dialogu
    private OnDialogShow onDialogShowHandler;
    // Šířka okna
    private double mWidth;
    // Výška okna
    private double mHeight;

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
        //this.mScreens.putAll(parentManager.mScreens);
        this.mBlackList.addAll(parentManager.mBlackList);
        this.mConfiguration = parentManager.mConfiguration;
        this.mResources = parentManager.mResources;
        this.mAnimator = parentManager.mAnimator;
        this.mFactory = parentManager.mFactory;
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

    // region Private methods

    /**
     * Načte všechny screeny
     *
     * @param directory Složka s FXML soubory
     */
    private void loadScreens(final File directory) throws IOException {
        final File[] views = directory.listFiles();
        assert views != null;
        for (File entry : views) {
            if (entry.isDirectory()) {
                loadScreens(entry);
            } else {
                final String name = entry.getName();
                final int dotIndex = name.indexOf(".");
                final String clearName = name
                    .substring(0, dotIndex > -1 ? dotIndex : name.length());
                if (mBlackList.contains(clearName)) {
                    continue;
                }
                mScreens.put(clearName, new ScreenInfo(clearName, entry.toURI().toURL()));
            }
        }
    }

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

    /**
     * Zobrazí notifikaci z fronty pouze, pokud není žádná notifikace zobrazena
     */
    private synchronized void nextNotification() {
        if (notifiing.get() || notifications.isEmpty()) {
            return;
        }

        final Notification notification = notifications.poll();
        final Timeline timeline = new Timeline(new KeyFrame(
            notification.duration, event -> {
                mMainScreen.hideNotification();
                notifiing.set(false);
                nextNotification();
            }
        ));

        mMainScreen.showNotification(notification.text);
        notifiing.set(true);
        timeline.setCycleCount(1);
        timeline.play();
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
     *  @param parent Kořenový prvek
     * @param stage Okno, ve kterém se má dialog zobrazit
     */
    public void showNewDialog(Parent parent, Stage stage) {
        Scene scene = new Scene(parent);
        scene.getStylesheets().setAll(mConfiguration.css.toString());
        stage.setScene(scene);
        stage.setWidth(mWidth);
        stage.setHeight(mHeight);
        stage.titleProperty().bind(mTitle);
        stage.show();
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
     * @param mAnimator {@link IScreenTransition}
     */
    public void setmAnimator(IScreenAnimator mAnimator) {
        this.mAnimator = mAnimator;
    }

    // endregion

    @Override
    public void showScreenForResult(String name, int actionId, Bundle bundle) {
        final ScreenInfo screenInfo = mScreens.get(name);
        final Node container = mMainScreen.getContainer();
        //final DoubleProperty opacity = mMainScreen.getContainer().opacityProperty();
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
            Parent parent = loader.load();
            IMainScreen mainScreen = loader.getController();
            ScreenManager newManager = new ScreenManager(this, actionId);
            mChildScreenManagers.add(newManager);
            newManager.setMainScreen(mainScreen);
            newManager.showNewDialog(parent, new Stage());
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
            Parent parent = loader.load();
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
            newManager.showNewDialog(parent, stage);
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
            // TODO přepnout screeny v okně
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
    public void showNotification(String text, Duration duration) {
        notifications.add(new Notification(text, duration));
        nextNotification();
    }

    public interface OnDialogShow {
        void onShow();
    }
}
