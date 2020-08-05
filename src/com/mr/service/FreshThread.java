package com.mr.service;//�����ػ��߳�

import java.awt.Container;
import com.mr.view.GamePanel;//������Ϸ�������
import com.mr.view.MainFrame;
import com.mr.view.ScoreDialog;
import com.mr.modle.Mario;

/**
 * ˢ��֡�߳�
 * 
 * @author mingrisoft
 *
 */
public class FreshThread extends Thread {
    public static final int FREASH = 10;// ˢ��ʱ�䣨��Dinosaur����ã�ˢ��ģ���볡������һ�£�
    GamePanel p;// ��Ϸ���

    public FreshThread(GamePanel p) {
        this.p = p;
    }

    public void run() {
        while (!p.isFinish()) { // �����Ϸδ����
            p.repaint();        // �ػ���Ϸ���
            try {
                Thread.sleep(FREASH);// ����ˢ��ʱ������
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //��Ϸ������
        Mario.over1=true;
        GamePanel.continu=false;
        while (!GamePanel.continu) { // �����Ϸ����
            p.repaint();             // �ػ���Ϸ���
            try {
                Thread.sleep(FREASH);// ����ˢ��ʱ������
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //���ã��Ի���ǰ�ã����������¿�ʼ
        Container c = p.getParent();        // ��ȡ��常����
        while (!(c instanceof MainFrame)) { // �����������������������
            c = c.getParent();              // ������ȡ�������ĸ�����
        }
        MainFrame frame = (MainFrame) c;// ������ǿ��ת��Ϊ��������

        new ScoreDialog(frame);         // �����÷ּ�¼�Ի�������

        frame.restart();                // ���������ؿ�ʼ��Ϸ
    }
}
