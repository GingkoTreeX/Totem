package net.GingkoTreeX.Inject;


import com.sun.tools.attach.VirtualMachine;

import javax.swing.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class InjectMain {

    public InjectMain() {
    }

    public static void main(String[] args) throws Exception {
        String pid;
        pid = JOptionPane.showInputDialog("请输入要注入进程的PID");
        String agent = getJarPath(InjectMain.class);
        System.out.println(agent);
        VirtualMachine vm = null;

        try {
            vm = VirtualMachine.attach(pid);
            vm.loadAgent(agent, (String)null);
        } catch (Exception var9) {
            var9.printStackTrace();
        } finally {
            if (vm != null) {
                vm.detach();
            }
        }

    }

    public static String getJarPath(Class<?> clazz) {
        URL resource = clazz.getProtectionDomain().getCodeSource().getLocation();
        try {
            return Paths.get(resource.toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}

