package com.northcastle.ui.component;

import com.northcastle.basebeans.BaseBean;
import com.northcastle.ui.staticdata.WindowSize;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

/**
 * @Author: northcastle
 * @CreateTime: 2023-04-04  10:40
 * @Description: 自定义带背景图片的 JPanel 对象
 */
public class BackgroundJPanel extends JPanel implements BaseBean {

    /**
     * 背景图片的路径
     */
    private String backgroundImagePath;

    public BackgroundJPanel() {
    }

    public BackgroundJPanel(String backgroundImagePath) {
        this.backgroundImagePath = backgroundImagePath;
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    public void setBackgroundImagePath(String backgroundImagePath) {
        this.backgroundImagePath = backgroundImagePath;
    }

    /**
     * 重写画图的逻辑
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            ClassLoader classLoader = BackgroundJPanel.class.getClassLoader();
            InputStream imageInputStream = classLoader.getResourceAsStream(backgroundImagePath);
            BufferedImage backgroundImage =  ImageIO.read(imageInputStream);

            // 修改一下图片的大小
            Image scaledImage = backgroundImage.getScaledInstance(WindowSize.CONTENT_PANEL_WIDTH, WindowSize.CONTENT_PANEL_HEIGHT, BufferedImage.SCALE_DEFAULT);
            g.drawImage(scaledImage, 0,0,WindowSize.CONTENT_PANEL_WIDTH,WindowSize.CONTENT_PANEL_HEIGHT,null);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
