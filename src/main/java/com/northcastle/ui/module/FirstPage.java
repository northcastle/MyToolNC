package com.northcastle.ui.module;

import com.northcastle.basebeans.BaseBean;
import com.northcastle.ui.component.BackgroundJPanel;
import com.northcastle.ui.staticdata.WindowSize;

import javax.swing.*;
import java.awt.*;

/**
 * @Author: northcastle
 * @CreateTime: 2023-04-03  08:21
 * @Description: 首页的对象
 */
public class FirstPage extends JPanel implements BaseBean {

    public FirstPage() {
        try {
            logger.info("首页的对象已经进行成功创建");
            // 1、设置首页的位置
            setLayout(new BorderLayout());
            setLocation(WindowSize.SIDER_BAR_WIDTH,0);
            setPreferredSize(new Dimension(WindowSize.CONTENT_PANEL_WIDTH,WindowSize.CONTENT_PANEL_HEIGHT));

            // 2、画图:获取到
            String backgroundImagePath = "images/firstpage/firstback04.jpg";
            BackgroundJPanel backgroundJPanel = new BackgroundJPanel(backgroundImagePath);
            // 3、把画布添加到当前Panel中
            add(backgroundJPanel);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
