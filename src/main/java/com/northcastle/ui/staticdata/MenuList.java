package com.northcastle.ui.staticdata;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.northcastle.ui.datamodel.RouterVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: northcastle
 * @CreateTime: 2023-04-02  21:54
 * @Description: 菜单数据
 */
public class MenuList {

    /**
     * 把要对应的数据直接写到这里来进行返回
     * @return
     */
    public static List<RouterVO> getMenuList(){

        List<RouterVO> menuList = new ArrayList<>();

        ClassLoader classLoader = MenuList.class.getClassLoader();
        RouterVO shouYe = new RouterVO("首页",new FlatSVGIcon("images/menu/firstPage.svg",40,35,classLoader),
                "com.northcastle.ui.module.FirstPage", "首页",null);
        RouterVO zuJian = new RouterVO("组件",new FlatSVGIcon("images/menu/components.svg",35,35,classLoader),
                "com.northcastle.ui.module.ComponentsPage", "组件",null);
        RouterVO tongZhi = new RouterVO("通知",new FlatSVGIcon("images/menu/notice.svg",40,35,classLoader),
                "com.northcastle.ui.module.NotifyPage", "通知",null);

        menuList.add(shouYe);
        menuList.add(zuJian);
        menuList.add(tongZhi);

        return menuList;
    }
}
