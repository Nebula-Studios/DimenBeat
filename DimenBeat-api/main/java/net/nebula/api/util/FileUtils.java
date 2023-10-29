package net.nebula.api.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    /**
     * 返回程序所在的目录路径
     * @return 程序目录路径
     */
    public static @Nullable String getDataFolder(){//提供程序所在路径
        return net.nebula.util.FileUtils.getDataFolder();
    }

    /**
     * 从MOD的JAR包内读取文件
     * @param resourcePath MOD的JAR包内相对路径
     * @return 文件内容
     * @throws IOException 如果读取发生错误
     */
    public String readJar(String resourcePath) throws IOException{
        return readJar(resourcePath,this.getClass().getClassLoader());
    }

    /**
     * 从指定的类加载器指向的包内读取文件
     * @param resourcePath JAR包内相对路径
     * @param classLoader 指定的类加载器
     * @return 文件内容
     * @throws IOException 如果读取发生错误
     */
    public String readJar(String resourcePath, @NotNull ClassLoader classLoader) throws IOException{
        // 使用ClassLoader的getResourceAsStream方法获取资源的输入流
        InputStream inputStream = classLoader.getResourceAsStream(resourcePath);

        // 使用InputStreamReader和BufferedReader读取输入流中的内容
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        // 读取文件内容
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String tmp = stringBuilder.toString();
        // 关闭流
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        //返回文件内容
        return tmp;
    }

    /**
     * 获取指定文件夹内所有JAR包的绝对路径
     * @param folderPath 文件夹路径
     * @return 包含所有JAR包的绝对路径的列表
     */
    public static @NotNull List<String> getJarFilePaths(String folderPath) {
        // 创建存储.jar文件绝对路径的列表
        List<String> jarFilePaths = new ArrayList<>();

        // 创建文件对象
        File folder = new File(folderPath);

        // 检查文件夹是否存在,不存在直接返回空列表
        if (!folder.isDirectory()) {
            return jarFilePaths;
        }

        // 获取文件夹内的所有文件
        File[] files = folder.listFiles();

        // 遍历文件列表
        if (files != null) {
            for (File file : files) {
                // 判断文件是否为.jar文件
                if (file.isFile() && file.getName().endsWith(".jar")) {
                    // 将.jar文件的绝对路径添加到列表中
                    jarFilePaths.add(file.getAbsolutePath());
                }
            }
        }

        // 返回包含所有.jar文件绝对路径的列表
        return jarFilePaths;
    }
}
