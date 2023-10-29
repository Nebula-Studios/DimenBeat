package net.nebula.api.modder;

import net.nebula.api.Configuration.JSONConfiguration;
import net.nebula.api.util.FileUtils;
import net.nebula.client.Main;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 负责加载指定文件夹下所有模组
 * <p>
 * 包含模组类加载器功能
 */
public class JavaModClassLoader {

    private final String path;
    /**
     * 存储着所有模组的stop()方法(如果有)的Map
     */
    private static final Map<Method,Object> overriddenStopMethods = new HashMap<>();
    /**
     * 存储着所有模组的名字的Map
     * <p>
     * 用以保存时的提示
     */
    private static final Map<Method,Object> overriddenNameMethods = new HashMap<>();
    /**
     * 存储着每个已经加载的模组的信息的List
     */
    private static final List<List<Object>> ModInformation = new ArrayList<>();

    /**
     * 默认构造方法
     * @param path 要加载的模组文件夹
     */
    public JavaModClassLoader(String path){
        this.path = path;
    }

    /**
     * 关闭所有模组
     */
    public void stopAllMod(){
        //方法列表不为空才执行
        if (!overriddenStopMethods.isEmpty()){
            try {
                //循环遍历方法列表
                for (Map.Entry<Method, Object> entry : overriddenStopMethods.entrySet()) {
                    Method method = entry.getKey();
                    Class<?> Class = entry.getValue().getClass();
                    //执行mod的关闭方法(stop)
                    Main.logger.info("Uninstalling mod: "+overriddenNameMethods.get(method));
                    method.invoke(Class.getDeclaredConstructor().newInstance());
                }
            } catch (Exception e) {
                //输出报错信息
                Main.logger.error(e.getMessage(),e);
            }
        }
    }

    /**
     * 加载所有模组
     */
    public void loadModClass() {
        //获得模组列表
        List<String> list = FileUtils.getJarFilePaths(path);
        //如果模组列表不为空就加载列表内的所有模组
        if (!list.isEmpty()){
            for (String s : list) {
                loadMod(s);
            }
        }
    }


    /**
     * 加载一个模组
     * @param file 模组的绝对路径
     */
    public void loadMod(String file){
        try {
            // 创建URLClassLoader加载器加载jar包
            URL jarUrl = new File(file).toURI().toURL();
            try(URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl})){
                //尝试获取mod包内的mod.json文件(存放着必要的信息)
                FileUtils fileUtils = new FileUtils();
                JSONConfiguration jsonConfiguration = new JSONConfiguration(null,null);
                jsonConfiguration.setConfig(new JSONObject(fileUtils.readJar("mod.json",classLoader)));
                //获取执行类路径(存放于mod的mod.json中，该文件必须在JAR包的根目录下)
                String mainClassPath = jsonConfiguration.getString("main");//尝试获取模组指定的执行类路径
                String modName = jsonConfiguration.getString("name");//尝试获取模组名字
                String modVersion = jsonConfiguration.getString("version");//尝试获取模组版本
                int modAPIVersion = jsonConfiguration.getInt("api-version");//尝试获取模组构建时使用的API版本
                if (modAPIVersion != 0) {//如果模组有指定构建时使用的API版本
                    if (modAPIVersion != ModAPI.APIVERSION){
                        Main.logger.warn(modName+"<- Detected mismatch between current API version and mod’s API version. There may be compatibility issues.");
                        Main.logger.warn("The mod API version of the game is: "+ModAPI.APIVERSION+",But the API version used during mod construction is: "+modAPIVersion);
                    }
                }
                if (mainClassPath != null){
                    //加载mod的执行类
                    Class<?> runClass = classLoader.loadClass(mainClassPath);
                    // 检查是否继承了JavaModAPI
                    if (!JavaModAPI.class.isAssignableFrom(runClass)) {
                        throw new NullPointerException(modName+"<- Failed to load properly: No valid execution class found.");
                    }

                    // 尝试获取onLoad()方法
                    Method loadMethod = runClass.getMethod("onLoad");

                    // 检查是否重写了onLoad()方法，如果有就执行
                    if (loadMethod.getDeclaringClass() != JavaModAPI.class) {
                        Main.logger.info("Loading mod: "+modName);
                        loadMethod.invoke(runClass.getDeclaredConstructor().newInstance());
                    }

                    // 获取run方法并执行
                    Method runMethod = runClass.getMethod("run");

                    // 检查是否重写了run()方法
                    if (runMethod.getDeclaringClass() != JavaModAPI.class) {
                        runMethod.invoke(runClass.getDeclaredConstructor().newInstance());
                    } else {
                        throw new NullPointerException(modName+"<- Failed to load properly: No valid execution class found.");
                    }

                    // 获取stop()方法
                    Method stopMethod = runClass.getMethod("stop");

                    // 将重写的方法(如果有)调用保存到一个Map中
                    if (stopMethod.getDeclaringClass() != JavaModAPI.class) {
                        overriddenStopMethods.put(stopMethod,runClass.getDeclaredConstructor().newInstance());
                        overriddenNameMethods.put(stopMethod,modName);
                        //把模组的信息保存到一个List中
                        List<Object> list = new ArrayList<>();
                        list.add(modName);
                        list.add(modVersion);
                        ModInformation.add(list);
                    }
                }else {
                    throw new NullPointerException(modName+"<- Failed to load properly: Cannot find a valid mod metadata file.");
                }

            }
        } catch (Exception e) {
            //致命错误
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    /**
     * 返回存储每个模组的信息列表的列表
     * @return 模组信息列表
     */
    public List<List<Object>> getAllModInformation(){
        return ModInformation;
    }
}
