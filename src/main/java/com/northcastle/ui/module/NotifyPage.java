package com.northcastle.ui.module;

import com.northcastle.basebeans.BaseBean;

import javax.swing.*;
import java.awt.*;

/**
 * @Author: northcastle
 * @CreateTime: 2023-04-03  08:21
 * @Description: 首页的对象
 */
public class NotifyPage extends JPanel implements BaseBean {
    public NotifyPage() {
        logger.info("通知的对象已经进行成功创建");
//        setLayout(null);
        setLocation(70,0);
        setBackground(new Color(65, 145, 225));
        JLabel jLabel = new JLabel("通知 哈哈");
        jLabel.setLocation(100,0);
        add(jLabel);

    }
}
