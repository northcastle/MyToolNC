package com.northcastle.ui.event.menu;

import com.northcastle.basebeans.BaseBean;
import com.northcastle.ui.component.NcJLabel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @Author: northcastle
 * @CreateTime: 2023-04-01  21:34
 * @Description: 菜单主题，被点击选中的时候，需要把事件发送出去
 */
public class MenuSubject extends Observable implements BaseBean {
    /**
     * 事件传递的信息对象
     */
   private NcJLabel jLabel;

    public NcJLabel getjLabel() {
        return jLabel;
    }

    public void setjLabel(NcJLabel jLabel) {
        this.jLabel = jLabel;
    }

    public void menuClicked(NcJLabel clickedJLabelOld,NcJLabel clickedJLabel){
        setjLabel(clickedJLabel);
        // 设置更新状态
        setChanged();
        List<NcJLabel> menuClickedList = new ArrayList<>();
        menuClickedList.add(clickedJLabelOld);
        menuClickedList.add(clickedJLabel);
        // 发送通知
        notifyObservers(menuClickedList);
    }
}
