package net.nebula.api.Configuration;

import net.nebula.api.util.FileUtils;
import net.nebula.client.Main;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 内部配置文件管理器的API实现
 * @author 3cxc
 * @since Phosphorus 1.0.2.5 Dev 0.0.2
 */
@SuppressWarnings("unused")
final public class JSONConfiguration {

    //配置文件管理器
    private final net.nebula.util.Configuration.JSONConfiguration configuration;
    private final String modName;

    /**
     * 初始化一个新的配置文件管理器
     * @param path 配置文件路径
     * @param modName 模组名字
     */
    public JSONConfiguration(String path,String modName){
        configuration = new net.nebula.util.Configuration.JSONConfiguration(path);
        this.modName = modName;
    }

    /**
     * 覆盖(内存中的)配置文件内容
     * @param json 新的配置文件内容
     */
    public void setConfig(JSONObject json){
        configuration.setConfig(json);
    }

    /**
     * 覆盖(内存中的)配置文件内容
     * @param map 新的配置文件内容
     */
    public void setConfig(HashMap<String,Object> map){
        configuration.setConfig(map);
    }

    /**
     * 覆盖(内存中的)配置文件内容
     * @param map 新的配置文件内容
     */
    public void setConfig(Map<String,Object> map){
        configuration.setConfig(map);
    }

    /**
     * 获取配置文件路径
     * @return 返回配置文件的路径
     */
    public String getPath(){
        return configuration.getPath();
    }

    /**
     * 重载配置文件
     */
    public void reloadConfig(){
        configuration.reloadConfig();
    }


    /**
     * 获取配置项的值，如果不存在则返回默认值(String)
     * @param key JSON项
     * @param defaultValue 默认值
     * @return 返回获取到的值(JSON对象的)或者默认值
     */
    public String getString(String key, String defaultValue) {
        return configuration.getString(key,defaultValue);
    }

    /**
     * 获取配置项的值，如果不存在则返回null
     * @param key JSON项
     * @return 返回获取到的值(JSON对象的)或者null
     */
    public String getString(String key){
        return configuration.getString(key);
    }

    /**
     * 获取配置项的值，如果不存在则返回默认值(int)
     * @param key JSON项
     * @param defaultValue 默认值
     * @return 返回获取到的值(JSON对象的)或者默认值
     */
    public int getInt(String key, int defaultValue) {
        return configuration.getInt(key,defaultValue);
    }

    /**
     * 获取配置项的值，如果不存在则返回0
     * @param key JSON项
     * @return 返回获取到的值(JSON对象的)或者0
     */
    public int getInt(String key){
        return configuration.getInt(key);
    }

    /**
     * 获取配置项的值，如果不存在则返回默认值(Boolean)
     * @param key JSON项
     * @param defaultValue 默认值
     * @return 返回获取到的值(JSON对象的)或者默认值
     */
    public boolean getBoolean(String key, Boolean defaultValue) {
        return configuration.getBoolean(key,defaultValue);
    }

    /**
     * 获取配置项的值，如果不存在则返回false
     * @param key JSON项
     * @return 返回获取到的值(JSON对象的)或者false
     */
    public boolean getBoolean(String key){
        return configuration.getBoolean(key);
    }

    /**
     * 获取配置项的值，如果不存在则返回null
     * @param key JSON项
     * @return 返回获取到的值(JSON对象的)或者bull
     */
    public List<String> getStringList(String key){
        return configuration.getStringList(key);
    }

    /**
     * 获取配置项的值，如果不存在则返回null
     * @param key JSON项
     * @return 返回获取到的值(JSON对象的)或者bull
     */
    public List<Integer> getIntList(String key){
        return configuration.getIntList(key);
    }

    /**
     * 获取配置项的值，如果不存在则返回null
     * @param key JSON项
     * @return 返回获取到的值(JSON对象的)或者bull
     */
    public List<Double> getDoubleList(String key){
        return configuration.getDoubleList(key);
    }

    /**
     * 创建默认配置文件
     * <p>
     * 将MOD内置的配置文件输出
     * <p>
     * 如果MOD包内根目录下没有config.json，将无法输出！
     */
    public void createDefaultConfig(){
        try {
            File file = new File(FileUtils.getDataFolder()+"config\\mod_"+modName+".json");
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            if (!file.exists()){
                writer.write(new FileUtils().readJar("config.json"));
            }
        } catch (IOException e) {
            Main.logger.error(e.getMessage(),e);
        }
    }

    /**
     * 保存配置文件
     */
    public void saveConfig(){
        configuration.saveConfig();
    }

    /**
     * 检查配置文件是否已创建
     * @return 为true表示文件已存在，为false表示文件不存在或路径为null
     */
    public boolean exists(){
        return configuration.exists();
    }

    /**
     * 添加一个Object对象到配置文件中
     * @param key 键
     * @param value 对应的值
     */
    public void put(String key, Object value){
       configuration.put(key,value);
    }

    /**
     * 添加一个boolean到配置文件中
     * @param key 键
     * @param value 对应的值
     */
    public void put(String key, boolean value){
        configuration.put(key,value);
    }

    /**
     * 添加一个double到配置文件中
     * @param key 键
     * @param value 对应的值
     */
    public void put(String key, double value){
        configuration.put(key,value);
    }

    /**
     * 添加一个float到配置文件中
     * @param key 键
     * @param value 对应的值
     */
    public void put(String key, float value){
        configuration.put(key,value);
    }

    /**
     * 添加一个int到配置文件中
     * @param key 键
     * @param value 对应的值
     */
    public void put(String key, int value) {
        configuration.put(key,value);
    }

    /**
     * 添加一个long到配置文件中
     * @param key 键
     * @param value 对应的值
     */
    public void put(String key, long value) {
        configuration.put(key,value);
    }

    /**
     * 添加一个Map到配置文件中
     * @param key 键
     * @param value 对应的值
     */
    public void put(String key, Map<?, ?> value) {
        configuration.put(key,value);
    }


    /**
     * 从配置文件中删除一个键及其对应的值
     * @param key 键
     */
    public void remove(String key){
        configuration.remove(key);
    }
}

