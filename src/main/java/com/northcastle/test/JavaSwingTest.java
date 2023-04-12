package com.northcastle.test;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * @Author: northcastle
 * @CreateTime: 2023-03-20  11:20
 * @Description: java swing 的测试
 */
public class JavaSwingTest {
    // 加载普通图片
    public void test01(){
        // 主窗口对象
        JFrame jFrame = new JFrame("测试窗口");

        // 加载图片
        ClassLoader classLoader = JavaSwingTest.class.getClassLoader();
        URL imageURl = classLoader.getResource("images/menu/a.png");
        ImageIcon imageIcon = new ImageIcon(imageURl);
        // JLable 展示
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);

        // JLabel 放入主窗口中
        jFrame.add(jLabel, BorderLayout.CENTER);

        // 主窗口的基本属性
        jFrame.setBounds(300,300,300,300);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

    // 修改图片大小
    public void test02(){
        // 1.加载原始的图片
        ClassLoader classLoader = JavaSwingTest.class.getClassLoader();
        URL imageURl = classLoader.getResource("images/menu/a.png");
        ImageIcon imageIcon = new ImageIcon(imageURl);
        int iconWidth = imageIcon.getIconWidth();
        int iconHeight = imageIcon.getIconHeight();
        System.out.println("修改前 ： width = "+iconWidth+" ; height = "+iconHeight);

        // 2.修改尺寸
        Image newImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon imageIconNew = new ImageIcon(newImage);
        int iconWidthNew = imageIconNew.getIconWidth();
        int iconHeightNew = imageIconNew.getIconHeight();
        System.out.println("修改后 ： width = "+iconWidthNew+" ; height = "+iconHeightNew);


        // 3.在窗口中展示出来
        JFrame jFrame = new JFrame("测试窗口");

        // 使用 JLabel 进行 icon 的展示
        JLabel jLabel1 = new JLabel("", imageIcon,JLabel.CENTER);
        JLabel jLabel2 = new JLabel("", imageIconNew,JLabel.CENTER);
        // 左右布局的box
        Box box = Box.createHorizontalBox();
        box.add(jLabel1);
        box.add(jLabel2);


        // box 放入主窗口中
        jFrame.add(box, BorderLayout.CENTER);

        // 主窗口的基本属性
        jFrame.setBounds(300,300,400,300);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

    // 加载 svg 格式图片
    public void test03(){


        // 1.主窗体对象
        JFrame jFrame = new JFrame("测试窗口");

        // 2.使用 JLabel 进行 icon 的展示:读取 svg 对象
        ClassLoader classLoader = JavaSwingTest.class.getClassLoader();
        ImageIcon flatSVGIcon = new FlatSVGIcon("images/menu/firstPage.svg", 50, 50, classLoader);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(flatSVGIcon);

        // 鼠标放上来展示成小手
        jLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        jFrame.add(jLabel, BorderLayout.CENTER);

        // 主窗口的基本属性
        jFrame.setBounds(300,300,200,200);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

    // 测试自定义的 带背景图的 JPanel
    public void test04(){
        // 1.主窗体对象
        JFrame jFrame = new JFrame("测试窗口");

//
        JPanel rootPanel = new JPanel();
        rootPanel.setSize(new Dimension(1200,700));
        rootPanel.setBackground(new Color(255, 255, 255));
        rootPanel.setLocation(0,0);


        JPanel contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension(1200,700));
        contentPanel.setBackground(new Color(224, 177, 177));
        contentPanel.setLocation(0,0);

        rootPanel.add(contentPanel);



//        JPanel content = new JPanel();
//        content.setSize(new Dimension(1000,700));
//        content.setLayout(null);
//        content.setLocation(70,0);
//        content.setBackground(new Color(232, 230, 189, 0));


//        String backgroundImagePath = "images/firstpage/firstback02.jpg";
//        BackgroundJPanel backgroundJPanel = new BackgroundJPanel(backgroundImagePath);
//        rootPanel.add(backgroundJPanel);

        jFrame.add(rootPanel);




//        jFrame.add(rootPanel);


        // 主窗口的基本属性
        jFrame.setBounds(300,300,1130,700);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }


    public static void main(String[] args) {

        new JavaSwingTest().test04();

    }
}
