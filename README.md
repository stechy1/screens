# Screens framework
Jedná se o jednoduchý pomocný framework pro správu oken v JavaFX. Je velmi inspirován aktivitami v Androidu.
## Inicializace
Nejdříve je potřeba vytvořit třídu **ScreenManagerConfiguration**, která bude obsahovat cesty k důležitým souborům a složkám, které framework vyžaduje pro bezproblémový běh.
### ScreenManagerConfiguration
Jedná se o jednoduchou přepravku obsahující cesty k adresářům důležitým pro chod frameworku

    ScreenManagerConfiguration configuration = new ScreenManagerConfiguration.ConfigurationBuilder()
            .baseFxml(Example.class.getClassLoader().getResource("root.fxml"))
            .fxml(Example.class.getClassLoader().getResource("view").getPath())
            .css(Example.class.getClassLoader().getResource("css").getPath())
            .lang(Example.class.getClassLoader().getResource("lang").getPath())
            .audio(Example.class.getClassLoader().getResource("audio").getPath())
            .images(Example.class.getClassLoader().getResource("images").getPath())
            .config(Example.class.getClassLoader().getResource("config").getPath())
            .build();
        manager = new ScreenManager(configuration);

- baseFxml - *[povinný]* Cesta k základnímu FXML dokumentu, který představuje kořenový prvek každého dialogu.
- fxml - *[povinný]* Cesta ke kořenové složce, která obsahuje FXMl dokumenty, může obsahovat i podsložky. Jediné, co se musí zachovat, je, že názvy souborů budou univerzální.
- css - *[nepovinný]* Cesta k souboru s CSS styly.
- lang - *[nepovinný]* Cesta ke kořenovému adresáři, kde se nachází soubory pro překlad aplikace.
- audio -  *[nepovinný]*Cesta ke kořenovému adresáři, kde se nachází audio soubory.
- images - *[nepovinný]* Cesta ke kořenovému adresáři, kde se nachází soubory s obrázky.
- config - *[nepovinný]* Cesta ke kořenovému adresáři, kde se nachází konfigurační soubory.

## ScreenManager
**ScreenManager** je hlavní třída zodpovědná za veškerou funkčnost.
V parametru konstruktoru přijímá třídu **ScreenManagerConfiguration**.

    ScreenManager manager = new ScreenManager(configuration);
Pokud aplikace bude využívat více jazyků, tak nastavíme překlad aplikace

    ResourceBundle resources = ResourceBundle.getBundle("lang.translate", Locale.getDefault());
        manager.setResources(resources);
Kde `lang.translate`představuje složku *lang*, ve které jsou soubory s prefixem **translate**, například: *translate_cs_CZ.properties* pro český překlad.
Je dobré udělat tuto inicializaci ještě před samotným spuštěním JavaFX aplikace, tedy v metodě *main*

    public static void main(String[] args) {
        initScreenManager(); // Inicializace Screen manažeru
        launch(args); // Spuštění JavaFX aplikace
    }
## Spuštění
V metodě *start*, kterou vyžaduje javaFX aplikace využijeme **ScreenManager** a zobrazíme první okno.

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(manager.getScreenManagerConfiguration().baseFxml);
        loader.setResources(manager.getResources());
        Parent parent = loader.load();
        IMainScreen controlledScreen = loader.getController();
        manager.setMainScreen(controlledScreen);
        manager.addScreensToBlacklist("screen4", "screen7");
        manager.loadScreens();
        manager.resize(800, 600);
	    manager.setTitle(manager.getResources().getString("title"));
        manager.showNewDialog(parent, primaryStage);
        manager.showScreen("file1", null);
    }
První dialog (hlavní okno) se musí vytvořit manuálně, ostatní už generuje framework sám.
Vytvoří se nový FXMLLoader, kterému se nastaví resources (překlad aplikace), pokud nějaké jsou. Načte se kořenový node, ve kterém se budou zobrazovat jednotlivé screeny. Z tohoto node se získá reference na hlavní screen reprezentovaný interfacem **IMainScreen**. Tento screen je zodpovědný za dodání reference na kořenový node. Pokud chceme nějaké soubory vynechat značítacího procesu, tak je přidáme do tzv. blacklistu pomocí metody *addScreensToBlacklist*. Je důležité, aby se tato metoda zavolala dříve, než *loadScreens*, protože pak by neměla tato metoda žádný efekt. Dále se zavolá nad managerem *loadScreens*, který vytvoří interní stukturu screenů. Je důležité vědět, že screeny jako takové se ještě nenačtou. Screeny se načtou až tehdy, kdy bude potřeba.
Metodou *resize* se nastaví velikost okna.
Metodou *setTitle* se nastaví titulek okna.
Metodou *showNewDialog* se zobrazí hlavní dialog. Je to jediný případ, kdy je tado metoda použita.
Metodou *showScreen* se zobrazí požadovaný screen. Jako první parametr přijímá název screenu, který odpovídá názvu FXML souboru, který reprezentuje tento screen. Jako druhý parametr je třída **Bundle**, pomocí které se předávají parametry dalším screenům.
## Implementace IMainScreen
Teď se podíváme, jak implementovat rozhraní **IMainScreen**. Nejdříve vytvoříme odpovídající FXML dokument. Nazvěme ho *root.fxml*

    <?xml version="1.0" encoding="UTF-8"?>

    <?import javafx.scene.layout.AnchorPane?>

    <AnchorPane xmlns:fx="http://javafx.com/fxml/1"
      xmlns="http://javafx.com/javafx/8"
      fx:controller="MainController"
      fx:id="container">
    </AnchorPane>
Jedná se o nejjednodušší implementaci. Je potřeba nastavit kontroler pomocí *fx:controller* a pro přístup k tomuto nodu *fx:id*
Konečne implementujeme rozhraní **IMainScreen**.

    public class MainController implements Initializable, IMainScreen {

        @FXML
        private AnchorPane container;
        private ObservableList<Node> containerContent;

        @Override
        public void setChildNode(Node node) {
            containerContent.clear();
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            containerContent.setAll(node);
        }

        @Override
        public Node getContainer() {
            return container;
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            containerContent = container.getChildren();
        }
    }
Třída **MainController** implementuje dvě rozhraní. **Initializable** je z JavaFX pomocí kterého se můou inicializovat jednotlivé kontrolky.
Rozhraní **IMainScreen** definuje metody pro získání kořenového kontejneru - k výměně nodů - a metodu pro nastavení nového nodu.

## Použití
Všechny ostatní kontrolery, které budou spravovány frameworkem musí dědit od třídy **BaseController**, která obsahuje metody pro práci s okny.

    public class Controller1 extends BaseController {
        public void buttonHandle() {
            startScreen("file2"); // Zobrazí screen ve stejném okně
            startNewDialog("file3"); // Zobrazí screen v novém okně
        }
    }