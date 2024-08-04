package net.GingkoTreeX.Inject;

import net.GingkoTreeX.totem.Totem;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.jar.JarFile;


public class ModLoader {
    public static void agentmain(String agentArgs, Instrumentation inst) {//利用agent将mod注入到minecraft线程中
        System.setProperty("java.awt.headless", "false");//这行一定不能删 否则任何GUI(包括JOptionPane)都将无法运行 导致抛出初始化失败的异常
        try {
            Thread[] threads = new Thread[Thread.activeCount()];
            Thread.enumerate(threads);
            threadInject(threads,inst);
        } catch (Exception e) {
            e.printStackTrace();
           JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public static void threadInject(Thread[] threads,Instrumentation inst) {
        for (Thread thread : threads) {
                if(thread != null && thread.getName().equals("Render thread")) {//这个是要注入的线程
                    try {
                        inst.appendToSystemClassLoaderSearch(new JarFile(getJarPath(ModLoader.class)));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {

                      //  ClassLoader classLoader = thread.getContextClassLoader();
                        if (thread.getContextClassLoader() != null) {
                            URL url=getJarUrlFromPath(getJarPath(ModLoader.class));
                            JOptionPane.showMessageDialog(null, "Inject succeed");
                            return;
                        }
                        }catch(Exception e){
                        JOptionPane.showMessageDialog(null,e.getClass().getName());
                        JOptionPane.showMessageDialog(null,e.getStackTrace());
                    }
                }
            }
        JOptionPane.showMessageDialog(null, "Client thread not found");
    }

    //这个暂时没用上
    private static Thread findThread(long threadId) {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        while (group != null) {
            Thread[] threads = new Thread[(int) (group.activeCount() * 1.2)];
            int count = group.enumerate(threads, true);
            for (int i = 0; i < count; i++) {
                if (threadId == threads[i].getId()) {
                    return threads[i];
                }
            }
            group = group.getParent();
        }
        return null;
    }

    public static String getJarPath(Class<?> clazz) {
        URL resource = clazz.getProtectionDomain().getCodeSource().getLocation();
        try {
            return Paths.get(resource.toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public static URL getJarUrlFromPath(String jarFilePath) {
        try {
            // 将文件路径转换为URL
            return new File(jarFilePath).toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error converting file path to URL: " + jarFilePath, e);
        }
    }

        }

