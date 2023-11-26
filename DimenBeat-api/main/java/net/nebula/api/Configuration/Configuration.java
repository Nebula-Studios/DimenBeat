package net.nebula.api.Configuration;

import net.nebula.api.util.FileUtils;
import net.nebula.client.logger.LoggerManager;

import java.io.*;
import java.nio.charset.StandardCharsets;


/**
 * 模组配置文件管理器
 * @author 3cxc
 * @since Phosphorus 1.0.2.5 Dev 0.0.2
 */
final public class Configuration extends JSONConfiguration{

    //模组名字
    private final String modName;

    /**
     * 初始化一个新的配置文件管理器
     * @param path 配置文件路径
     * @param modName 模组名字
     */
    public Configuration(String path, String modName){
        super(path);
        this.modName = modName;
    }


    /**
     * 创建默认配置文件
     * <p>
     * 将MOD内置的配置文件输出
     * <p>
     * 如果MOD包内根目录下没有config.json，将无法输出！
     *
     * @return 为true表示创建成功
     */
    @Override
    public boolean createDefaultConfig(){
        try {
            File file = new File(FileUtils.getDataFolder()+"config\\mod_"+modName+".json");
            if (!file.exists()){
                Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
                writer.write(FileUtils.readJar("config.json",this.getClass().getClassLoader()));
                return true;
            }
            return false;
        } catch (IOException e) {
            LoggerManager.getLogger().error(e.getMessage(),e);
            return false;
        }
    }
}

