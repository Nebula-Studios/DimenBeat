package net.nebula.api.modder;

import net.nebula.client.Main;

/**
 * 代表模组API
 */
public abstract class ModAPI {
    /**
     * 游戏版本
     */
    public static final String VERSION = Main.AppVersion;
    /**
     * 游戏详细版本号
     */
    public static final String APPDETAILEDVERSION = Main.AppDetailedVersion;
    /**
     * 游戏数字版本号
     */
    public static final int APPNUMBER = Main.AppNumber;
    /**
     * 模组API版本
     */
    public static final int APIVERSION = 1;
}
