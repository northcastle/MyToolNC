package com.northcastle.ui.event.menu;

import com.northcastle.basebeans.BaseBean;

/**
 * @Author: northcastle
 * @CreateTime: 2023-04-01  21:45
 * @Description: 菜单的事件处理器，在菜单中、主界面中，需要用到的是同一个对象，
 *               因此，这个对象需要是个单例模式的
 *               本案例采用【双重锁校验】的单例模式，懒加载 + 高性能
 */
public class MenuEventHandler implements BaseBean {

    /**
     * 菜单主题对象
     */
    private MenuSubject menuSubject;
    /**
     * 一个菜单主题的订阅对象
     */
    private MenuObserver menuObserver;

    /**
     * 声明一个 单例对象,volatile 修饰，线程安全
     */
    private static volatile  MenuEventHandler menuEventHandler;

    /**
     * 构造方法 : 私有化
     */
    private MenuEventHandler() {
        logger.info("MenuEventHandler 初始化成功");
        // 1、初始化一个主题对象
        menuSubject = new MenuSubject();
        // 2、初始化一个订阅对象
        menuObserver = new MenuObserver();
        // 3、把订阅对象加入到主题对象的观察者列表中
        menuSubject.addObserver(menuObserver);
    }


    /**
     * 获取 单例对象 的方法
     * @return
     */
    public static MenuEventHandler getInstance(){
        if (menuEventHandler == null){
            synchronized (MenuEventHandler.class){
                if (menuEventHandler == null){
                    menuEventHandler = new MenuEventHandler();
                }
            }
        }
        return menuEventHandler;
    }

    public MenuSubject getMenuSubject() {
        return menuSubject;
    }

    public void setMenuSubject(MenuSubject menuSubject) {
        this.menuSubject = menuSubject;
    }

    public MenuObserver getMenuObserver() {
        return menuObserver;
    }

    public void setMenuObserver(MenuObserver menuObserver) {
        this.menuObserver = menuObserver;
    }
}
