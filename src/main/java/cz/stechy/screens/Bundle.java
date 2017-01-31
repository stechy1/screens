package cz.stechy.screens;

import java.util.HashMap;
import java.util.Map;

/**
 * Třída pro předávání parametrů mezi screeny
 */
public final class Bundle {

    private final Map<String, Object> map = new HashMap<>();

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public void clear() {
        map.clear();
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public void remove(String key) {
        map.remove(key);
    }

    public Object get(String key) {
        return map.get(key);
    }

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public void putBoolean(String key, boolean value) {
        map.put(key, value);
    }

    public void putByte(String key, byte value) {
        map.put(key, value);
    }

    public void putChar(String key, char value) {
        map.put(key, value);
    }

    public void putShort(String key, short value) {
        map.put(key, value);
    }

    public void putInt(String key, int value) {
        map.put(key, value);
    }

    public void putLong(String key, long value) {
        map.put(key, value);
    }

    public void putFloat(String key, float value) {
        map.put(key, value);
    }

    public void putDouble(String key, double value) {
        map.put(key, value);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (boolean) ret;
    }

    public byte getByte(String key) {
        return getByte(key, (byte) 0);
    }

    public byte getByte(String key, byte defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (byte) ret;
    }

    public char getChar(String key) {
        return getChar(key, (char) 0);
    }

    public char getChar(String key, char defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (char) ret;
    }

    public short getShort(String key) {
        return getShort(key, (short) 0);
    }

    public short getShort(String key, short defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (short) ret;
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (int) ret;
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (long) ret;
    }

    public float getFloat(String key) {
        return getFloat(key, 0);
    }

    public float getFloat(String key, float defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (float) ret;
    }

    public double getDouble(String key) {
        return getDouble(key, 0);
    }

    public double getDouble(String key, double defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (double) ret;
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        Object ret = map.get(key);
        return ret == null ? defaultValue : (String) ret;
    }

}
