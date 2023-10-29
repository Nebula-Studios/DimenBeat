package net.nebula.api.modder;

import net.nebula.api.Configuration.JSONConfiguration;
import net.nebula.api.Logger.LogAPI;
import net.nebula.api.event.EventListener;
import net.nebula.api.util.FileUtils;

/**
 * 模组API
 * <p>
 * 提供了一系列方法来让一个模组正常加载
 */
public class JavaModAPI extends ModAPI implements Initable {
    //配置文件(Mod或Plugin)
    private JSONConfiguration configuration = null;
    //日志API
    private LogAPI logAPI = null;
    //mod名字
    public String ModName;

    /**
     * 一个 Mod 的加载阶段,第一句是必须是初始化ModName(设置模组名字)
     * <p>加载阶段执行完了之后才会执行运行阶段
     * <p>且 Mod 的事件注册必须在Load阶段完成
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

    //获得配置文件
    public final JSONConfiguration getConfig(){
        //如果未初始化配置文件管理器则初始化
        if (configuration == null){
            if (ModName != null){
                configuration = new JSONConfiguration(FileUtils.getDataFolder()+"config\\mod_"+ModName+".json",ModName);
                return configuration;
            }else {
                //抛出错误
                throw new RuntimeException(new NullPointerException("Invalid mod loading"));
            }
        }
        return configuration;
    }

    /**
     * 返回一个日志记录器
     * @return 日志记录器(如果返回null表示没有定义ModName)
     */
    public final LogAPI getLogger(){
        if (logAPI == null){
            if (ModName != null){
                logAPI = new LogAPI(ModName);
                return logAPI;
            }else {
                //抛出错误
                throw new RuntimeException(new NullPointerException("Invalid mod loading"));
            }
        }
        return logAPI;
    }

    //获得程序的主路径
    public final String getDataFolder(){
        return FileUtils.getDataFolder();
    }

    /**
     * 注册事件监听器
     * @param listener 事件监听器
     */
    public final void registerEvents(final EventListener listener){
        EventAPI.eventExecuter.registerEventListener(listener);
    }

    /**
     * 解绑某个事件监听器
     * @param listener 事件监听器
     */
    public final void unregisterEvents(final EventListener listener){
        EventAPI.eventExecuter.unregisterEventListener(listener);
    }
}
