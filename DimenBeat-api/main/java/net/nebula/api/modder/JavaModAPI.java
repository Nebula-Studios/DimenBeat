package net.nebula.api.modder;

import net.nebula.api.Configuration.Configuration;
import net.nebula.api.Configuration.JSONConfiguration;
import net.nebula.api.GameSystem;
import net.nebula.api.Logger.Logger;
import net.nebula.api.Logger.LoggerManager;
import net.nebula.api.event.EventListener;
import net.nebula.api.util.FileUtils;
import net.nebula.util.JsonWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 模组API
 * <p>
 * 提供了一系列方法来让一个模组正常加载
 */
public abstract class JavaModAPI implements JavaMod {
    /**
     * 模组配置文件
     */
    private Configuration configuration = null;
    /**
     * 模组日志管理器
     */
    private Logger logAPI = null;
    /**
     * 模组名称
     */
    protected static volatile String ModName;

    /**
     * 一个 Mod 的加载阶段,第一句是必须是初始化ModName(设置模组名字)
     * <p>
     * 加载阶段执行完了之后才会执行运行阶段
     */
    @Override
    public void onLoad() {}

    /**
     * 一个 Mod 的运行阶段
     * <p>在此放置你想要执行的代码
     */
    @Override
    public void run() {}

    /**
     * 一个 Mod 的关闭阶段
     * <p>请在此阶段保存你的配置文件(如果有的话)
     */
    @Override
    public void stop() {}

    public JavaModAPI getModAPI(){
        return this;
    }

    /**
     * 获取模组配置文件
     * @return 模组配置文件
     * @exception NullPointerException 如果未指定模组名称(ModName)
     */
    @Override
    final public Configuration getConfig(){
        //如果未初始化配置文件管理器则初始化
        if (configuration == null){
            if (ModName != null){
                configuration = new JSONConfiguration(FileUtils.getDataFolder()+"config\\mod_"+ModName+".json");
                return configuration;
            }else {
                //抛出错误
                throw new RuntimeException(new NullPointerException("Invalid mod loading,Reason: mod name is null"));
            }
        }
        return configuration;
    }

    /**
     * 返回一个日志记录器
     * @return 日志记录器(如果返回null表示没有定义ModName)
     * @exception NullPointerException 如果未指定模组名称(ModName)
     */
    @Override
    final public Logger getLogger(){
        if (logAPI == null){
            if (ModName != null){
                logAPI = new LoggerManager(ModName);
                return logAPI;
            }else {
                //抛出错误
                throw new RuntimeException(new NullPointerException("Invalid mod loading,Reason: mod name is null"));
            }
        }
        return logAPI;
    }

    //获得程序的主路径
    @Override
    public final String getDataFolder(){
        return FileUtils.getDataFolder();
    }

    /**
     * 注册事件监听器
     * @param listener 事件监听器
     */
    @Override
    public final void registerEvents(final EventListener listener){
        GameSystem.eventExecuter.registerEventListener(listener);
    }

    /**
     * 解绑某个事件监听器
     * @param listener 事件监听器
     */
    @Override
    public final void unregisterEvents(final EventListener listener){
        GameSystem.eventExecuter.unregisterEventListener(listener);
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
        File file = new File(FileUtils.getDataFolder()+"config\\mod_"+ModName+".json");
        if (!file.exists()){
            try(Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)){//使用模组的Class加载器来读取包文件
                //获取并格式化json
                String jsonString = JsonWriter.formatJson(FileUtils.readJar("config.json",this.getClass().getClassLoader()));
                writer.write(jsonString);
                writer.flush();
                return true;
            }catch (IOException e){
                net.nebula.client.logger.LoggerManager.getLogger().error(e.getMessage(),e);
            }
        }
        return false;
    }
}
