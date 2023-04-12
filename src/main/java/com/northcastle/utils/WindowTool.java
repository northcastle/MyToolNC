package com.northcastle.utils;

import javax.swing.*;
import java.awt.*;

/**
 * @Author: northcastle
 * @CreateTime: 2023-02-03  16:48
 * @Description: 窗口的工具类
 */
public class WindowTool {

    /**
     * 将窗体放到正中间 ：（桌面的宽度-窗体的宽度）/2 ；。。。
     * @param jFrame
     * @return
     */
    public static JFrame center(JFrame jFrame){

        // 1、获取窗体的宽和高
        int widthFrame = jFrame.getWidth();
        int heightFrame = jFrame.getHeight();
        // 2、获取屏幕的宽和高
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = defaultToolkit.getScreenSize();
        double widthScreen = screenSize.getWidth();
        double heightScreen = screenSize.getHeight();
        // 3、如果窗体的尺寸超过了，则直接用屏幕的尺寸
        if (widthFrame > widthScreen){
            widthFrame = (int)widthScreen;
        }
        if (heightFrame > heightScreen){
            heightFrame = (int)heightScreen;
        }
        // 4、设置位置
        int positionX = (int) ((widthScreen - widthFrame)/2);
        int positionY = (int)((heightScreen - heightFrame)/2);

        jFrame.setSize(new Dimension(widthFrame,heightFrame));
        jFrame.setLocation(new Point(positionX,positionY));

        return jFrame;
    }
}
