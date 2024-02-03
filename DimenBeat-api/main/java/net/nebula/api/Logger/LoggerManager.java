package net.nebula.api.Logger;

/**
 * 日志API
 */
public class LoggerManager implements Logger {

    private final String LogName;//日志记录器标记名

    public LoggerManager(String name){
        LogName = name;
    }

    /**
     * 返回日志记录器对应的Mod的名字
     * @return 日志记录器对应的Mod的名字(如果有)
     */
    public String getName(){
        return LogName;
    }

    //包装API

    @Override
    public void info(Object message){
        net.nebula.client.logger.LoggerManager.getLogger().info("["+LogName+"] "+message);
    }

    @Override
    public void warn(Object message){
        net.nebula.client.logger.LoggerManager.getLogger().warn("["+LogName+"] "+message);
    }

    @Override
    public void warn(Object message,Throwable t){
        net.nebula.client.logger.LoggerManager.getLogger().warn("["+LogName+"] "+message,t);
    }

    @Override
    public void error(Object message){
        net.nebula.client.logger.LoggerManager.getLogger().error("["+LogName+"] "+message);
    }

    @Override
    public void error(Object message,Throwable t){
        net.nebula.client.logger.LoggerManager.getLogger().error("["+LogName+"] "+message,t);
    }

    @Override
    public void error(Throwable t){
        net.nebula.client.logger.LoggerManager.getLogger().error(t);
    }

    @Override
    public void error(String message, Object p0, Object p1) {
        net.nebula.client.logger.LoggerManager.getLogger().error(message,p0,p1);
    }
}
