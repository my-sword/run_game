
package com.mr.view;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import com.mr.service.ScoreRecorder;
import com.mr.service.Sound;
/**
 * 主窗体
 * @author mingrisoft
 *
 */
public class MainFrame extends JFrame {

    public MainFrame() {
        restart();// 开始（自方法）
        setBounds(340, 150, 821, 260);// 设置横纵坐标和宽高
        setTitle("小跑酷");    // 标题
        Sound.backgroud();              // 播放背景音乐
        ScoreRecorder.init();           // 读取得分记录
        addListener();                  // 添加监听
        setDefaultCloseOperation(EXIT_ON_CLOSE);// 关闭窗体则停止程序

        ImageIcon imageIcon = new ImageIcon("image/a/0.png");
        setIconImage(imageIcon.getImage());
    }

    /**
     * 重新开始
     *///FreshThread中碰到障碍被调用
    public void restart() {
        Container c = getContentPane();     // 获取主容器对象
        c.removeAll();                      // 删除容器中所有组件(游戏重新开始，需要配合c.validate())
        GamePanel panel = new GamePanel();  // 创建新的游戏面板
        c.add(panel);
        addKeyListener(panel);// 添加键盘事件（）
        c.validate();// 容器重新验证所有组件（确保组件具有有效的布局，repaint()方法是重绘,而validate()是重载）
    }

    /**
     * 添加监听
     */
    private void addListener() {
        addWindowListener(new WindowAdapter() {// 添加窗体监听
            public void windowClosing(WindowEvent e) {// 窗体关闭前
                ScoreRecorder.saveScore();// 保存比分
            }
        });
    }
}
