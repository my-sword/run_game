
package com.mr.view;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import com.mr.service.ScoreRecorder;
import com.mr.service.Sound;
/**
 * ������
 * @author mingrisoft
 *
 */
public class MainFrame extends JFrame {

    public MainFrame() {
        restart();// ��ʼ���Է�����
        setBounds(340, 150, 821, 260);// ���ú�������Ϳ��
        setTitle("С�ܿ�");    // ����
        Sound.backgroud();              // ���ű�������
        ScoreRecorder.init();           // ��ȡ�÷ּ�¼
        addListener();                  // ��Ӽ���
        setDefaultCloseOperation(EXIT_ON_CLOSE);// �رմ�����ֹͣ����

        ImageIcon imageIcon = new ImageIcon("image/a/0.png");
        setIconImage(imageIcon.getImage());
    }

    /**
     * ���¿�ʼ
     *///FreshThread�������ϰ�������
    public void restart() {
        Container c = getContentPane();     // ��ȡ����������
        c.removeAll();                      // ɾ���������������(��Ϸ���¿�ʼ����Ҫ���c.validate())
        GamePanel panel = new GamePanel();  // �����µ���Ϸ���
        c.add(panel);
        addKeyListener(panel);// ��Ӽ����¼�����
        c.validate();// ����������֤���������ȷ�����������Ч�Ĳ��֣�repaint()�������ػ�,��validate()�����أ�
    }

    /**
     * ��Ӽ���
     */
    private void addListener() {
        addWindowListener(new WindowAdapter() {// ��Ӵ������
            public void windowClosing(WindowEvent e) {// ����ر�ǰ
                ScoreRecorder.saveScore();// ����ȷ�
            }
        });
    }
}
