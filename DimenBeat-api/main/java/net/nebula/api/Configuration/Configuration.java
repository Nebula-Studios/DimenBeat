package net.nebula.api.Configuration;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Configuration {
    void setConfig(JSONObject json);
    void setConfig(HashMap<String,Object> map);
    void setConfig(Map<String,Object> map);
    String getPath();
    void reloadConfig();
    String getString(String key, String defaultValue);
    @Nullable String getString(String key);
    int getInt(String key, int defaultValue);
    int getInt(String key);
    boolean getBoolean(String key, Boolean defaultValue);
    boolean getBoolean(String key);
    @Nullable List<String> getStringList(String key);
    @Nullable List<Integer> getIntList(String key);
    @Nullable List<Double> getDoubleList(String key);
    void saveConfig();
    boolean exists();
    void put(String key,Object value);
    void put(String key, boolean value);
    void put(String key, double value);
    void put(String key, float value);
    void put(String key, int value);
    void put(String key, long value);
    void put(String key, Map<?, ?> value);
    void remove(String key);
}
