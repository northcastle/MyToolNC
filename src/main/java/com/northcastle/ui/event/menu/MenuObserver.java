package com.northcastle.ui.event.menu;

import com.northcastle.basebeans.BaseBean;
import com.northcastle.ui.component.NcJLabel;
import com.northcastle.ui.datamodel.RouterVO;
import com.northcastle.ui.module.ContentPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * @Author: northcastle
 * @CreateTime: 2023-04-01  21:39
 * @Description: 菜单的观察者，菜单事件有更新时，这边需要实时更新
 */
public class MenuObserver implements Observer, BaseBean {

    @Override
    public void update(Observable o, Object arg) {
        List<NcJLabel> menuClickedList  = (List<NcJLabel>) arg;
        // 0 = 点击之前的菜单； 1 = 点击的目标菜单
        NcJLabel ncJLabelNew = menuClickedList.get(1);

        ContentPanel contentPanel = ContentPanel.getInstance();
        JPanel content = contentPanel.getContent();

        RouterVO routerVO = ncJLabelNew.getRouterVO();
        if (routerVO != null){
            String routerClass = routerVO.getRouterClass();

            try {
                // 1、移除所有的组件，
                content.removeAll();

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
}
