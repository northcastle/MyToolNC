package com.northcastle.ui.component;

import com.northcastle.ui.datamodel.RouterVO;

import javax.swing.*;

/**
 * @Author: northcastle
 * @CreateTime: 2023-03-31  15:06
 * @Description: 扩展的 JLabel 对象
 */
public class NcJLabel extends JLabel {

    /**
     * 标志是否删除
     */
    private boolean clicked = false;

    /**
     * 关联的数据对象
     */
    private RouterVO routerVO;


    public NcJLabel() {
    }

    public NcJLabel(String text) {
        super(text);
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public RouterVO getRouterVO() {
        return routerVO;
    }

    public void setRouterVO(RouterVO routerVO) {
        this.routerVO = routerVO;
    }
}
