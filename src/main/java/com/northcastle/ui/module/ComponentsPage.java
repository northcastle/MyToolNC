package com.northcastle.ui.module;

import com.northcastle.basebeans.BaseBean;
import com.northcastle.ui.component.NcButtons.NcJButton;

import javax.swing.*;
import java.awt.*;

/**
 * @Author: northcastle
 * @CreateTime: 2023-04-07  12:01
 * @Description: 自定义组件的展示页
 */
public class ComponentsPage extends JPanel implements BaseBean {

    public ComponentsPage() {
        logger.info("组件展示页面创建成功");
        setLayout(new BorderLayout());
        // 1、最外层的容器
        Box containerBox = Box.createVerticalBox();

        // 2.1 自定义按钮
        JButton jButton = new JButton("aa");
        jButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        containerBox.add(jButton);

        NcJButton bbBtn = new NcJButton("bb");
        containerBox.add(bbBtn);


        // 3、把最外层的容器放进来
        add(containerBox);


    }
}
