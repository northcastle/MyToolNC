package com.northcastle.ui.module;

import com.northcastle.basebeans.BaseBean;
import com.northcastle.ui.datamodel.RouterVO;
import com.northcastle.ui.staticdata.MenuList;
import com.northcastle.ui.staticdata.WindowSize;

import javax.swing.*;
import java.awt.*;

/**
 * @Author: northcastle
 * @CreateTime: 2023-04-03  09:07
 * @Description: 界面右侧的容器,单例模式
 */
public class ContentPanel implements BaseBean {

    /**
     * 这就是那个容器
     */
    private JPanel content;
    /**
     * 单例对象
     */
    private static volatile ContentPanel contentPanel;


    private ContentPanel() {
        content = new JPanel(new BorderLayout());
        content.setSize(new Dimension(WindowSize.CONTENT_PANEL_WIDTH,WindowSize.CONTENT_PANEL_HEIGHT));
        content.setLocation(WindowSize.SIDER_BAR_WIDTH,0);
        content.setBackground(new Color(211, 193, 193));

        // 初始化的时候，直接展示首页
        RouterVO firstPageVO = MenuList.getMenuList().get(0);
        if (firstPageVO != null){
            String routerClass = firstPageVO.getRouterClass();

            try {
                // 1、移除所有的组件，
                content.removeAll();
                // 使用垂直布局
                Box verticalBox = Box.createVerticalBox();

                // 2、然后再添加目标组件
                Class<?> clazz = Class.forName(routerClass);
                Component cNew = (Component) clazz.newInstance();

                verticalBox.add(cNew);
                content.add(verticalBox);

                // 3、动态的修改的话，必须进行动态更新
                content.updateUI();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            logger.info("未配置跳转的路由地址");
        }


    }

    /**
     * 单例对象实例化
     * @return
     */
    public static ContentPanel getInstance(){
        if (contentPanel == null){
            synchronized (ContentPanel.class){
                if (contentPanel == null){
                    contentPanel = new ContentPanel();
                }
            }
        }
        return contentPanel;
    }

    public JPanel getContent() {
        return content;
    }

    public void setContent(JPanel content) {
        this.content = content;
    }
}
