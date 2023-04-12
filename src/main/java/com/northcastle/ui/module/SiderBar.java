package com.northcastle.ui.module;

import com.northcastle.basebeans.BaseBean;
import com.northcastle.ui.component.NcJLabel;
import com.northcastle.ui.datamodel.RouterVO;
import com.northcastle.ui.event.menu.MenuEventHandler;
import com.northcastle.ui.event.menu.MenuSubject;
import com.northcastle.ui.staticdata.MenuList;
import com.northcastle.ui.staticdata.WindowSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * @Author: northcastle
 * @CreateTime: 2023-02-28  21:49
 * @Description: 左侧的菜单条
 */
public class SiderBar extends Component implements BaseBean {

    /**
     * 盛方左侧菜单的 panel
     */
    private JPanel siderBarPanel;
    /**
     * 容器的 宽和高
     */
//    private static int WIDTH = 70;
//    private static int HEIGHT = 700;
    /**
     * 菜单条
     */
    private List<RouterVO> menuList;

    /**
     * 记录被选中的菜单对象,方便操作当前选中的文字颜色的变化
     */
    private NcJLabel selectedNcJlabel = null;

    /**
     * 处理 菜单点击 事件的对象
     */
    private MenuEventHandler menuEventHandler = MenuEventHandler.getInstance();



    public SiderBar() {
        logger.info("siderBar create begin!");


        // 1.设置 panel 的属性
        siderBarPanel = new JPanel();
        // 设置panel的大小 ：
        siderBarPanel.setSize(new Dimension(WindowSize.SIDER_BAR_WIDTH,WindowSize.SIDER_BAR_HEIGHT));
        // 设置背景颜色
        siderBarPanel.setBackground(new Color(51, 52, 51, 255));
        // 设置在主窗体中的位置
        siderBarPanel.setLocation(0,0);

        // 2.设置按钮图标 : 技术点 ： 展示svg 的图片
        Box menuListBox = Box.createVerticalBox();
        // 获取到 已经配置好的对象
        menuList = MenuList.getMenuList();

        // 最上面放上一个空的，有个距离
        menuListBox.add(new NcJLabel(" "));

        // 获取 菜单事件处理对象的 主题对象
        MenuSubject menuSubject = menuEventHandler.getMenuSubject();


        // 把菜单图标放进来进行展示
        for (RouterVO menuVO : menuList) {
            // 定义自己扩展的 NcJLabel 对象
            NcJLabel jLabel = new NcJLabel();
            // 设置 图标
            jLabel.setIcon(menuVO.getIcon());
            // 设置 文本
            jLabel.setText(menuVO.getTitle());
            jLabel.setFont(new Font("宋体",Font.PLAIN,12));
            jLabel.setForeground(new Color(255, 255, 255));
            // 设置文本的水平的位置
            jLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            // 设置文本的垂直的位置
            jLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
            // 设置鼠标放上来的显示小手
            jLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            // 设置鼠标悬停时的提示文本
            jLabel.setToolTipText(menuVO.getTip());
            menuListBox.add(jLabel);
            // 再加上一个空的行
            menuListBox.add(new NcJLabel(" "));

            // 添加事件监听动作
            jLabel.addMouseListener(new MouseAdapter() {
                // 点击一个标签
                @Override
                public void mouseClicked(MouseEvent e) {
                    logger.info(" 点击了 菜单 ： {}",jLabel.getText() );
                    NcJLabel jLabelOld = selectedNcJlabel;
                    if (selectedNcJlabel == jLabel){
                        logger.info("当前已经是 {} 标签，不可重复切",jLabel.getText());
                        return;
                    }
                    // 修改被记录的 菜单对象
                    if (selectedNcJlabel != null){
                        selectedNcJlabel.setClicked(false);
                        selectedNcJlabel.setForeground(new Color(255,255,255));
                    }

                    // 标志 点击了
                    jLabel.setClicked(true);
                    // 设置文字的颜色
                    jLabel.setForeground(new Color(59, 146, 245));
                    // 更新当前被点击选中的对象
                    selectedNcJlabel = jLabel;

                    // 点击后把目标对象传递一下
                    jLabel.setRouterVO(menuVO);
                    menuSubject.menuClicked(jLabelOld,jLabel);

                }
                // 鼠标进来了
                @Override
                public void mouseEntered(MouseEvent e) {
                    jLabel.setForeground(new Color(59, 146, 245));
                }
                // 鼠标移出去了，如果没有被选中，颜色就消失了
                @Override
                public void mouseExited(MouseEvent e) {
                    if (!jLabel.isClicked()){
                        jLabel.setForeground(new Color(255,255,255));
                    }
                }


            });
        }


        siderBarPanel.add(menuListBox);


        logger.info("siderBar create success!");




    }

    public JPanel getSiderBarPanel() {
        return siderBarPanel;
    }

    public void setSiderBarPanel(JPanel siderBarPanel) {
        this.siderBarPanel = siderBarPanel;
    }
}
