package net.nebula.api.modder;

/**
 * 一个模组的通用实现接口
 */
interface Initable {
    void onLoad();
    void run();
    void stop();
}
