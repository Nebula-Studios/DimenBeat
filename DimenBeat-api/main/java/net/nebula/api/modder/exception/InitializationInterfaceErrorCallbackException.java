package net.nebula.api.modder.exception;

/**
 *
 */
final public class InitializationInterfaceErrorCallbackException extends RuntimeException{

    /**
     * 构建一个新的线程运行时异常(未初始化原因)
     */
    public InitializationInterfaceErrorCallbackException(){
        super();
    }

    /**
     * 使用指定的信息创建新的线程运行时异常
     * @param message 详细信息
     */
    public InitializationInterfaceErrorCallbackException(String message) {
        super(message);
    }

    /**
     * 使用指定的信息和原因创建新的线程运行时异常
     * @param message 详细信息
     */
    public InitializationInterfaceErrorCallbackException(String message, Throwable cause) {
        super(message,cause);
    }

    /** 创建一个新的线程运行时异常
     *  具有指定的原因和详细信息(通常包含出现问题的类和其他详细信息)
     *  @param cause 原因(允许null,表示未知原因)
     */
    public InitializationInterfaceErrorCallbackException(Throwable cause){
        super(cause);
    }
}
