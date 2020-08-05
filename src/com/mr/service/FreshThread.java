package com.mr.service;//窗口重绘线程

import java.awt.Container;
import com.mr.view.GamePanel;//窗体游戏面板自类
import com.mr.view.MainFrame;
import com.mr.view.ScoreDialog;
import com.mr.modle.Mario;

/**
 * 刷新帧线程
 * 
 * @author mingrisoft
 *
 */
public class FreshThread extends Thread {
    public static final int FREASH = 10;// 刷新时间（被Dinosaur类调用，刷新模型与场景保持一致）
    GamePanel p;// 游戏面板

    public FreshThread(GamePanel p) {
        this.p = p;
    }

    public void run() {
        while (!p.isFinish()) { // 如果游戏未结束
            p.repaint();        // 重绘游戏面板
            try {
                Thread.sleep(FREASH);// 按照刷新时间休眠
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //游戏结束后
        Mario.over1=true;
        GamePanel.continu=false;
        while (!GamePanel.continu) { // 如果游戏结束
            p.repaint();             // 重绘游戏面板
            try {
                Thread.sleep(FREASH);// 按照刷新时间休眠
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //作用，对话框前置，主窗口重新开始
        Container c = p.getParent();        // 获取面板父容器
        while (!(c instanceof MainFrame)) { // 如果父容器不是主窗体自类
            c = c.getParent();              // 继续获取父容器的父容器
        }
        MainFrame frame = (MainFrame) c;// 将容器强制转换为主窗体类

        new ScoreDialog(frame);         // 弹出得分记录对话框自类

        frame.restart();                // 主窗体重载开始游戏
    }
}
