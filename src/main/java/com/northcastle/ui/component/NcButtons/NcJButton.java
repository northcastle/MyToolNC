package com.northcastle.ui.component.NcButtons;

import javax.swing.*;
import java.awt.*;

/**
 * @Author: northcastle
 * @CreateTime: 2023-04-08  22:06
 * @Description: 默认的按钮
 */
public class NcJButton extends JButton {

    private String text;

    public NcJButton() {
    }

    public NcJButton(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {

        System.out.println("重新绘制了一个按钮 :"+text);
        g.setColor(new Color(255,255,255));
        g.fillRect(0,0,3000,200);

        g.setColor(new Color(210, 67, 67));
        g.drawString(text,0,0);
//        super.paintComponent(g);
    }
}
