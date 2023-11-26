package net.nebula.api.modder;

import net.nebula.client.logger.Logger;

/**
 * 模组日志API
 */
public class LogModder extends Logger {

    private final String LogName;//日志记录器标记名

    public LogModder(String name){
        LogName = name;
    }

    /**
     * 返回日志记录器对应的Mod的名字
     * @return 日志记录器对应的Mod的名字(如果有)
     */
    public String getLogName(){
        return LogName;
    }

    //包装API

    @Override
    public void info(Object message){
        super.info("["+LogName+"] "+message);
    }

    @Override
    public void warn(Object message){
        super.warn("["+LogName+"] "+message);
    }

    @Override
    public void warn(Object message,Throwable t){
        super.warn("["+LogName+"] "+message,t);
    }

    @Override
    public void error(Object message){
        super.error("["+LogName+"] "+message);
    }

    @Override
    public void error(Object message,Throwable t){
        super.error("["+LogName+"] "+message,t);
    }

    @Override
    public void error(Throwable t){
        super.error(t);
    }
}
