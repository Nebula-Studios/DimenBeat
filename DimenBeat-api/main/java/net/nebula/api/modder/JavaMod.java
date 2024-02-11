package net.nebula.api.modder;

import net.nebula.api.Configuration.Configuration;
import net.nebula.api.Logger.Logger;
import net.nebula.api.event.EventListener;
import net.nebula.client.Main;

/**
 * 一个模组的通用实现接口
 */
interface JavaMod {
    void onLoad();
    void run();
    void stop();
    Configuration getConfig();
    Logger getLogger();
    String getDataFolder();
    void registerEvents(final EventListener listener);
    void unregisterEvents(final EventListener listener);
    boolean createDefaultConfig();

    /**
     * 游戏版本
     */
    public static final String VERSION = Main.AppVersion;
    /**
     * 游戏数字版本号
     */
    public static final int APPNUMBER = Main.AppNumber;
    /**
     * API正式版本号
     */
    public static final int APIVERSION = 12;

    /**
     * API预览版本号
     * <p>
     * 如果该变量为0表示API为正式版，不为0则是预览版
     */
    public static final int API_DEV_VERSION = 2;
}
