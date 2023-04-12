package com.northcastle.ui;

import com.apple.eawt.Application;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.northcastle.basebeans.BaseBean;
import com.northcastle.ui.event.menu.MenuEventHandler;
import com.northcastle.ui.module.ContentPanel;
import com.northcastle.ui.module.SiderBar;
import com.northcastle.ui.staticdata.WindowSize;
import com.northcastle.utils.WindowTool;

import javax.swing.*;
import java.awt.*;

/**
 * @Author: northcastle
 * @CreateTime: 2023-02-17  17:02
 * @Description: 主窗体对象 ： 所有的对象都采用绝对布局的方式
 */
public class MainFrame implements BaseBean {
    /**
     * 主窗体对象
     * 绝对布局
     * 窗体不可变动
     */
    private static  JFrame mainFrame;

    /**
     * 存放内容的 容器对象
     */
    private static JPanel rootPanel;

    /**
     * 左侧的工具窗口
     */
    private static SiderBar siderBar;

    /**
     * 右侧的容器对象
     */
    private static ContentPanel contentPanel;

    /**
     * 菜单事件的处理对象
     */
    private static MenuEventHandler menuEventHandler = MenuEventHandler.getInstance();

    public static void init(){


        // 1、设置主窗体的标题和大小
        mainFrame = new JFrame("NorthCastle的工具箱");
        mainFrame.setSize(new Dimension(WindowSize.MAIN_FRAME_WIDTH,WindowSize.MAIN_FRAME_HEIGHT));

        // 修改默认的图标
        ClassLoader classLoader = MainFrame.class.getClassLoader();

        // 这个在 windows 上应该是好用的，但是在Mac上不好用
        ImageIcon mainIcon = new FlatSVGIcon("images/mainframe/mainicon.svg", 50, 50, classLoader);
        mainFrame.setIconImage(mainIcon.getImage());
        // Mac上 : 修改图标 com.apple.eawt.Application
        Application.getApplication().setDockIconImage(mainIcon.getImage());


        // 2、创建根容器对象
        rootPanel = obtainRootPane(WindowSize.MAIN_FRAME_WIDTH,WindowSize.MAIN_FRAME_HEIGHT);

        // 3、向根容器对象中添加左侧的菜单组件
        siderBar = new SiderBar();
        rootPanel.add(siderBar.getSiderBarPanel());

        // 4、向根容器对象中添加右侧的容器对象
        contentPanel = ContentPanel.getInstance();
        rootPanel.add(contentPanel.getContent());



        // 5、初始化事件的监听对象


        // 把跟容器放到主窗体中
        mainFrame.add(rootPanel);
        // 3、设置主窗体的其它属性
        mainFrame.setResizable(false);
        mainFrame.setLayout(null);
        WindowTool.center(mainFrame);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        // 设置不要标题(mac 上无效,而且会有报错提示)
        //mainFrame.setUndecorated(true);

    }

    /**
     * 创建 根节点对象
     * @return
     */
    public static JPanel obtainRootPane(int width,int height){
        if (null == rootPanel){
            logger.info("rootPanel 是空的，需要创建一下");
            synchronized (MainFrame.class){
                if (null == rootPanel){
                    rootPanel = new JPanel(null);
                    rootPanel.setSize(new Dimension(width,height));
                    rootPanel.setBackground(new Color(255, 255, 255));
                }
            }
        }
        return rootPanel;
    }


}
