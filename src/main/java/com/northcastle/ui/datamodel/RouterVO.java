package com.northcastle.ui.datamodel;

import com.alibaba.fastjson.JSON;

import javax.swing.*;
import java.util.List;

/**
 * @Author: northcastle
 * @CreateTime: 2023-02-28  21:53
 * @Description: 一个路由跳转的对象，包含 标题，图标，提示词，目标类，子对象
 *               目标类可以通过 反射进行对象的创建
 */
public class RouterVO {

    /**
     * 标题
     */
    private String title;
    /**
     * 图标
     */
    private ImageIcon icon;
    /**
     * 路由对象的类路径
     */
    private String routerClass;
    /**
     * 提示信息
     */
    private String tip;
    /**
     * 子对象
     */
    private List<RouterVO> children;


    public RouterVO() {
    }

    public RouterVO(String title, ImageIcon icon, String routerClass, String tip, List<RouterVO> children) {
        this.title = title;
        this.icon = icon;
        this.routerClass = routerClass;
        this.tip = tip;
        this.children = children;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRouterClass() {
        return routerClass;
    }

    public void setRouterClass(String routerClass) {
        this.routerClass = routerClass;
    }


    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public List<RouterVO> getChildren() {
        return children;
    }

    public void setChildren(List<RouterVO> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
