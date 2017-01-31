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

import java.net.URL;

/**
 * Přepravka obsahující cesty k důleitým adresářům
 */
@SuppressWarnings("WeakerAccess")
public final class ScreenManagerConfiguration {

    public static final URL DEFAULT_FXML_BASE_FILE = null;
    public static final String DEFAULT_FXML = "";
    public static final String DEFAULT_CSS = "";
    public static final String DEFAULT_IMG = "";
    public static final String DEFAULT_AUD = "";
    public static final String DEFAULT_CONFIG = "";
    public static final String DEFAULT_LANG = "";

    public final URL baseFxml;
    // Cesta ke složce, kde se nachází FXML soubory
    public final String fxml;
    // Cesta ke složce, kde se nachází CSS soubory
    public final String css;
    // Cesta ke složce, kde se nachází soubory s obrázky
    public final String images;
    // Cesta ke složce, kde se nachází soubory s audio obsahem
    public final String audio;
    // Cesta ke složce, kde se nachází konfigurační soubory
    public final String config;
    // Cesta ke složce, kde se nachází soubory s překladem
    public final String lang;

    /**
     * Vytvoří novou konfiguraci s parametry
     *
     * @param baseFxml
     * @param fxml Cesta ke složce, kde se nachází FXML soubory
     * @param css Cesta ke složce, kde se nachází CSS soubory
     * @param images Cesta ke složce, kde se nachází soubory s obrázky
     * @param audio Cesta ke složce, kde se nachází soubory s audio obsahem
     * @param config Cesta ke složce, kde se nachází konfigurační soubory
     * @param lang Cesta ke složce, kde se nachází soubory s překladem
     */
    private ScreenManagerConfiguration(URL baseFxml, String fxml, String css, String images,
        String audio,
        String config, String lang) {
        this.baseFxml = baseFxml;
        this.fxml = fxml;
        this.css = css;
        this.images = images;
        this.audio = audio;
        this.config = config;
        this.lang = lang;
    }

    public static final class ConfigurationBuilder {

        private URL baseFxml = DEFAULT_FXML_BASE_FILE;
        private String fxml = DEFAULT_FXML;
        private String css = DEFAULT_CSS;
        private String images = DEFAULT_IMG;
        private String audio = DEFAULT_AUD;
        private String config = DEFAULT_CONFIG;
        private String lang = DEFAULT_LANG;

        /**
         * Nastaví cestu ke všem fxml dokumentům
         *
         * @param baseFxml Cesta k základnímu fxml souboru
         * @return {@link ConfigurationBuilder}
         */
        public ConfigurationBuilder baseFxml(URL baseFxml) {
            this.baseFxml = baseFxml;
            return this;
        }

        /**
         * Nastaví cestu ke všem fxml dokumentům
         *
         * @param fxml Cesta ke kořenovému adresáři, kde se nachází všechny fxml dokumenty
         * @return {@link ConfigurationBuilder}
         */
        public ConfigurationBuilder fxml(String fxml) {
            this.fxml = fxml;
            return this;
        }

        /**
         * Nastaví cestu k css stylům
         *
         * @param css Cesta ke kořenovému adresáři, kde se nachází všechny css styly
         * @return {@link ConfigurationBuilder}
         */
        public ConfigurationBuilder css(String css) {
            this.css = css;
            return this;
        }

        /**
         * Nastaví cestu k obrázkům aplikace
         *
         * @param images Cesta ke kořenovému adresáři, kde se nachází všechny obrázky
         * @return {@link ConfigurationBuilder}
         */
        public ConfigurationBuilder images(String images) {
            this.images = images;
            return this;
        }

        /**
         * Nastaví cestu ke zvukům aplikace
         *
         * @param audio Cesta ke kořenovému adresáři, kde se nachází všechny zvuky
         * @return {@link ConfigurationBuilder}
         */
        public ConfigurationBuilder audio(String audio) {
            this.audio = audio;
            return this;
        }

        /**
         * Nastaví cestu ke konfiguračním souborům
         *
         * @param config Cesta ke kořenovému adresáři, kde se nachází všechny konfigurační soubory
         * @return {@link ConfigurationBuilder}
         */
        public ConfigurationBuilder config(String config) {
            this.config = config;
            return this;
        }

        /**
         * Nastaví cestu k souborům pro překlad
         *
         * @param lang Cesta ke kořenovému adresáři, kde se nachází soubory pro překlad
         * @return {@link ConfigurationBuilder}
         */
        public ConfigurationBuilder lang(String lang) {
            this.lang = lang;
            return this;
        }

        public ScreenManagerConfiguration build() {
            return new ScreenManagerConfiguration(baseFxml, fxml, css, images, audio, config, lang);
        }
    }
}
