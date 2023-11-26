package net.nebula.api.Logger;

import net.nebula.client.logger.LoggerManager;

/**
 * 系统日志管理器
 */
public class LogManager {

    /**
     * 返回日志记录器
     * @return 日志记录器
     */
    public static org.apache.logging.log4j.Logger getLogger(){
        return LoggerManager.getLogger();
    }
}
