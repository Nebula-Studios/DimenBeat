package net.nebula.api.modder.exception;

/**
 * 代表由事件引发的异常
 */
public class EventException extends Exception{

    private final Throwable cause;

    /**
     * 构造函数
     * @param throwable 引发本异常的异常
     */
    public EventException(Throwable throwable){
        this.cause = throwable;
    }

    /**
     * 构造函数
     * @param throwable 引发本异常的异常
     * @param message 引发异常的原因
     */
    public EventException(Throwable throwable,String message){
        super(message);
        this.cause = throwable;
    }

    /**
     * 构造函数
     * @param message 引发异常的原因
     */
    public EventException(String message){
        super(message);
        this.cause = null;
    }

    /**
     * 返回引发本异常的异常
     * @return 引发本异常的异常
     */
    public Throwable getCause(){
        return this.cause;
    }
}
