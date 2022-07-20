package com.example.springbootdraw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by shaosen on 2022/7/20
 */
@SpringBootTest(classes = DrawTest.class,webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner.class)
public class DrawTest {


    @Test
    public void test2 () throws IOException {
        //底图
        ClassPathResource redResource = new ClassPathResource("back_print.png");
        BufferedImage red = ImageIO.read(redResource.getInputStream());


        //二维码
        File file = new File("D:/source2.png");
        FileInputStream inputStream = new FileInputStream(file);
        BufferedImage qrCodeImage = ImageIO.read(inputStream);

        // --- 画图 ---

        //底层空白 bufferedImage
        BufferedImage baseImage = new BufferedImage(red.getWidth(), red.getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE);

        //画上图片
        drawImgInImg(baseImage, red, 0, 0, red.getWidth(), red.getHeight());

        //画上二维码
        drawImgInImg(baseImage, qrCodeImage, 113, 117, 453, 453);

        //画上文字信息
        drawTextInImg(baseImage, "Label1: xxxxx", 614, 182, new Color(0, 0, 0), new Font("Microsoft YaHei", Font.PLAIN, 50));
        drawTextInImg(baseImage, "Label2: xxxxx", 614, 282, new Color(0, 0, 0), new Font("Microsoft YaHei", Font.PLAIN, 50));
        drawTextInImg(baseImage, "Label3: xxxxx", 614, 382, new Color(0, 0, 0), new Font("Microsoft YaHei", Font.PLAIN, 50));
        drawTextInImg(baseImage, "Label4: xxxxx", 614, 482, new Color(0, 0, 0), new Font("Microsoft YaHei", Font.PLAIN, 50));
        drawTextInImg(baseImage, "Label5: xxxxx", 614, 582, new Color(0, 0, 0), new Font("Microsoft YaHei", Font.PLAIN, 50));

        //转jpg
        BufferedImage result = new BufferedImage(baseImage.getWidth(), baseImage
                .getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        result.getGraphics().drawImage(baseImage, 0, 0, null);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageIO.write(result, "jpg", bs);
        byte[] bytes = bs.toByteArray();

        FileOutputStream out = new FileOutputStream("D://test2.jpg");
        out.write(bytes);
        out.close();

    }


    private void drawTextInImgCenter(BufferedImage baseImage, String textToWrite, int y) {
        Graphics2D g2D = (Graphics2D) baseImage.getGraphics();
        g2D.setColor(new Color(167, 136, 69));

        String fontName = "Microsoft YaHei";

        Font f = new Font(fontName, Font.PLAIN, 28);
        g2D.setFont(f);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 计算文字长度，计算居中的x点坐标
        FontMetrics fm = g2D.getFontMetrics(f);
        int textWidth = fm.stringWidth(textToWrite);
        int widthX = (baseImage.getWidth() - textWidth) / 2;
        // 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。

        g2D.drawString(textToWrite, widthX, y);
        // 释放对象
        g2D.dispose();
    }

    private void drawTextInImg(BufferedImage baseImage, String textToWrite, int x, int y, Color color, Font font) {
        Graphics2D g2D = (Graphics2D) baseImage.getGraphics();
        g2D.setColor(color);

        //TODO 注意，这里的字体必须安装在服务器上
        g2D.setFont(font);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.drawString(textToWrite, x, y);
        g2D.dispose();
    }

    private void drawImgInImg(BufferedImage baseImage, BufferedImage imageToWrite, int x, int y, int width, int heigth) {
        Graphics2D g2D = (Graphics2D) baseImage.getGraphics();
//        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
        g2D.drawImage(imageToWrite.getScaledInstance(width, heigth, Image.SCALE_SMOOTH), x, y, null);
        // 在图形和图像中实现混合和透明效果
        g2D.dispose();
    }


}
