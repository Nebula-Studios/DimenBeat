package com.github.egg;

import net.nebula.api.modder.JavaModAPI;
import net.nebula.api.util.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

@SuppressWarnings({"ResultOfMethodCallIgnored", "SpellCheckingInspection"})
public class Egg extends JavaModAPI {

    public static JavaModAPI api;

    @Override
    public void onLoad(){
        api = this;
        api.registerEvents(new EggEvent());
    }

    @Override
    public void run(){
        api.getLogger().info("很好，我来搞事了！");
        eggs_init();
    }

    @Override
    public void stop(){
        api.getLogger().info("不！为什么关掉了！");
    }

    public static void eggs_init(){
        api.getLogger().info("创建彩蛋ing...");
        if (!new File(FileUtils.getDataFolder()+"\\eggs").exists()){//如果彩蛋目录不存在
            new File(FileUtils.getDataFolder()+"\\eggs").mkdirs();//创建目录
        }
        //用户GA的彩蛋
        createEgg("GA","""
                        GA是个大聪明
                        GA is quite the genius, isn’t he/she?
                        
                        GA真是个聪明绝顶的存在，无与伦比的智商让我佩服得五体投地。它就像是个活生生的计算器，逻辑思维简直令人窒息.
                        GA is such a brilliant being, its unparalleled intellect leaves me in awe. It’s like a living calculator, its logical thinking is simply suffocating.
                        
                        GA以为自己聪明似乎是个天大的笑话.
                        It’s a colossal joke how GA thinks it’s clever.
                        
                        GA自诩为智者，可惜只是空洞的自负.
                        GA arrogantly claims to be wise, but it’s nothing more than hollow conceit.

                        GA的所谓聪明只是无知的伪装.
                        GA’s so-called cleverness is just a facade of ignorance.

                        GA大概认为自己是地球上最聪明的存在，实际上令人发指.
                        GA probably considers itself the smartest being on Earth, which is outrageously laughable.
                        """);
        //用户b_yy的彩蛋
        createEgg("b_yy","""
                        yy什么时候女装awa
                        yy的声音真好听！(bushi)
                        
                        yy:全员砍头！
                        
                        蚌埠住的性格扭曲脆弱爱哭病娇小恶魔系笨蛋猫娘喷水蒸汽姬狡辩傲娇抖m残念系爱狡辩绝壁杂鱼雌小鬼yy
                        
                        爱炫富的yy让人又爱又恨
                        """);
        //用户Remilia_marisa1的彩蛋
        createEgg("Remilia_marisa1", """
                84什么时候女装awa
                傲娇84和傲娇yy
                
                84在上,yy在下,Ahas在偷看~
                """);
        api.getLogger().info("创建完成！awa");
    }

    private static void createEgg(String name,String egg){
        Thread thread = new Thread("Egg-"+name){
            @Override
            public void run(){
                String path = FileUtils.getDataFolder()+"eggs\\"+name+".txt";
                File file = new File(path);
                if (file.exists()){//保证文件内容不变 :)
                    file.delete();
                }
                try {
                    //创建文件
                    file.createNewFile();
                    //写入"彩蛋"内容
                    Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
                    writer.write(egg);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    file.delete();//取消创建,因为出错
                    api.getLogger().error(e.getMessage(),e);
                }
            }
        };
        thread.start();
    }
}