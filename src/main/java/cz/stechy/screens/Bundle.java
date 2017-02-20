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

import java.util.HashMap;
import java.util.Map;

/**
 * Třída pro předávání parametrů mezi screeny
 */
public final class Bundle {

    // Kolekce obsahující všechny parametry
    private final Map<String, Object> map = new HashMap<>();

    /**
     * @return Počet parametrů v {@link Bundle}
     */
    public int size() {
        return map.size();
    }

    /**
     * @return True, pokud {@link Bundle} obsahuje nějaké parametry, jinak false
     */
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Vymaže všechny parametry
     */
    public void clear() {
        map.clear();
    }

    /**
     * @param key Klíč, který má {@link Bundle} obsahovat
     * @return True, pokud {@link Bundle} klíč obsahuje, jinak false
     */
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    /**
     * Odstraní záznam podle zadaného klíče
     *
     * @param key Klíč záznamu, který se má smazat
     */
    public void remove(String key) {
        map.remove(key);
    }

    /**
     * @param key Klíč objektu
     * @return Objekt podle klíče
     */
    public Object get(String key) {
        return map.get(key);
    }

    /**
     * Vloží hodnotu na zadaný klíč
     *
     * @param key Klíč
     * @param value Hodnota
     * @return {@link Bundle}
     */
    public Bundle put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    /**
     * Vloží {@link Boolean} na pozici klíče
     *
     * @param key Klíč
     * @param value Hodnota, která se má vložit
     * @return {@link Bundle}
     */
    public Bundle putBoolean(String key, boolean value) {
        map.put(key, value);
        return this;
    }

    /**
     * Vloží {@link Byte} na pozici klíče
     *
     * @param key Klíč
     * @param value Hodnota, která se má vložit
     * @return {@link Bundle}
     */
    public Bundle putByte(String key, byte value) {
        map.put(key, value);
        return this;
    }

    /**
     * Vloží {@link Character} na pozici klíče
     *
     * @param key Klíč
     * @param value Hodnota, která se má vložit
     * @return {@link Bundle}
     */
    public Bundle putChar(String key, char value) {
        map.put(key, value);
        return this;
    }

    /**
     * Vloží {@link Short} na pozici klíče
     *
     * @param key Klíč
     * @param value Hodnota, která se má vložit
     * @return {@link Bundle}
     */
    public Bundle putShort(String key, short value) {
        map.put(key, value);
        return this;
    }

    /**
     * Vloží {@link Integer} na pozici klíče
     *
     * @param key Klíč
     * @param value Hodnota, která se má vložit
     * @return {@link Bundle}
     */
    public Bundle putInt(String key, int value) {
        map.put(key, value);
        return this;
    }

    /**
     * Vloží {@link Long} na pozici klíče
     *
     * @param key Klíč
     * @param value Hodnota, která se má vložit
     * @return {@link Bundle}
     */
    public Bundle putLong(String key, long value) {
        map.put(key, value);
        return this;
    }

    /**
     * Vloží {@link Float} na pozici klíče
     *
     * @param key Klíč
     * @param value Hodnota, která se má vložit
     * @return {@link Bundle}
     */
    public Bundle putFloat(String key, float value) {
        map.put(key, value);
        return this;
    }

    /**
     * Vloží {@link Double} na pozici klíče
     *
     * @param key Klíč
     * @param value Hodnota, která se má vložit
     * @return {@link Bundle}
     */
    public Bundle putDouble(String key, double value) {
        map.put(key, value);
        return this;
    }

    /**
     * Vloží {@link String} na pozici klíče
     *
     * @param key Klíč
     * @param value Hodnota, která se má vložit
     * @return {@link Bundle}
     */
    public Bundle putString(String key, String value) {
        map.put(key, value);
        return this;
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo (boolean) 0, pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @return boolean hodnotu
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo výchozí hodnotu, pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @param defaultValue Hodnota, která se vrátí, pokud klíč neexistuje
     * @return boolean hodnotu
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (boolean) ret;
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo (byte) 0, pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @return byte hodnotu
     */
    public byte getByte(String key) {
        return getByte(key, (byte) 0);
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo výchozí hodnotu, pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @param defaultValue Hodnota, která se vrátí, pokud klíč neexistuje
     * @return byte hodnotu
     */
    public byte getByte(String key, byte defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (byte) ret;
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo (char) 0, pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @return char hodnotu
     */
    public char getChar(String key) {
        return getChar(key, (char) 0);
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo výchozí hodnotu, pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @param defaultValue Hodnota, která se vrátí, pokud klíč neexistuje
     * @return char hodnotu
     */
    public char getChar(String key, char defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (char) ret;
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo (short) 0, pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @return short hodnotu
     */
    public short getShort(String key) {
        return getShort(key, (short) 0);
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo výchozí hodnotu, pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @param defaultValue Hodnota, která se vrátí, pokud klíč neexistuje
     * @return short hodnotu
     */
    public short getShort(String key, short defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (short) ret;
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo (int) 0, pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @return int hodnotu
     */
    public int getInt(String key) {
        return getInt(key, 0);
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo výchozí hodnotu, pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @param defaultValue Hodnota, která se vrátí, pokud klíč neexistuje
     * @return int hodnotu
     */
    public int getInt(String key, int defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (int) ret;
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo (long) 0, pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @return long hodnotu
     */
    public long getLong(String key) {
        return getLong(key, 0);
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo výchozí hodnotu, pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @param defaultValue Hodnota, která se vrátí, pokud klíč neexistuje
     * @return long hodnotu
     */
    public long getLong(String key, long defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (long) ret;
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo (float) 0, pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @return float hodnotu
     */
    public float getFloat(String key) {
        return getFloat(key, 0);
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo výchozí hodnotu, pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @param defaultValue Hodnota, která se vrátí, pokud klíč neexistuje
     * @return float hodnotu
     */
    public float getFloat(String key, float defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (float) ret;
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo (double) 0, pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @return double hodnotu
     */
    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo výchozí hodnotu, pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @param defaultValue Hodnota, která se vrátí, pokud klíč neexistuje
     * @return double hodnotu
     */
    public double getDouble(String key, double defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (double) ret;
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo (String) "", pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @return String hodnotu
     */
    public String getString(String key) {
        return getString(key, "");
    }

    /**
     * Vrátí hodnotu spojenou se zadaným klíčem,
     * nebo výchozí hodnotu, pokud neexistuje žádné spojení s klíčem
     *
     * @param key Klíč
     * @param defaultValue Hodnota, která se vrátí, pokud klíč neexistuje
     * @return String hodnotu
     */
    public String getString(String key, String defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (String) ret;
    }

}
