package net.nebula.api.Runnable;

import net.nebula.api.GameSystem;

/**
 * 代表API线程接口
 */
public abstract class ThreadRunnable implements Runnable{

    @Override
    public void run(){

    }

    /**
     * 任务名称
     */
    private String name;

    public void runTask(String name){
        this.name = name;
        //初始化线程并修改名字
        Thread thread = new Thread(this);
        thread.setName("AsyncTask-"+name);
        //执行线程
        GameSystem.ThreadExcutor.execute(thread);
    }

    public String getTaskName(){
        return name;
    }
}
