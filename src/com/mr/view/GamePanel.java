package com.mr.view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import com.mr.modle.*;
import com.mr.service.*;
/**
 * ��Ϸ���
 * 
 * @author mingrisoft
 *
 */
public class GamePanel extends JPanel implements KeyListener {
    public static boolean continu=false;
    private BufferedImage image;        // ��ͼƬ
    private BackgroundImage background; // ����ͼƬ
    private Mario golden;            // ����
    private Graphics2D g2;              // ��ͼƬ��ͼ����
    private int addObstacleTimer = 0;   // ����ϰ���ʱ��
    private boolean finish = false;     // ��Ϸ������־
    //public boolean continu = false;     // ��Ϸ��������
    private List<Obstacle> list = new ArrayList<Obstacle>();// �ϰ����󼯺ϣ����ϰ�����洢�������У�
    private List<Obstacle> list1 = new ArrayList<Obstacle>();// �������󼯺ϣ����ϰ�����洢�������У�
    private final int FREASH = FreshThread.FREASH;   // ˢ��ʱ��

    int score = 0;      // �÷�
    int scoreTimer = 0; // ������ʱ��

    public GamePanel() {
        // ��ͼƬ���ÿ�800��300�Ĳ�ɫͼƬ
        image = new BufferedImage(960, 300, BufferedImage.TYPE_INT_BGR);//����
        g2 = image.createGraphics();// ��ȡ��ͼƬ��ͼ����
        background = new BackgroundImage();// ��ʼ�������������������ࣩ
        golden = new Mario();// ��ʼ������ģ��
        list.add(new Obstacle());// ��ӵ�һ���ϰ�
        FreshThread t = new FreshThread(this);// ˢ��֡�߳�
        t.start();// �����߳�
    }

    /**
     * ������ͼƬ
     */
    private void paintImage() {
        background.roll();  // ����ͼƬ��ʼ����
        golden.move();      // ģ�Ϳ�ʼ�ƶ���̤������Ծ��
        g2.drawImage(background.image, 0, 0, this);// ���ƹ�������
        //��������ϰ�
        if (addObstacleTimer == 1300) {     // ÿ��1300����
            if (Math.random() * 100 > 30) { // 60%���ʳ����ϰ�
                list.add(new Obstacle());
            }
            addObstacleTimer = 0;// ���¼�ʱ
        }
        //�ϰ��ƶ���������ɾ��
        for (int i = 0; i < list.size(); i++) {// �����ϰ����󼯺�
            Obstacle o = list.get(i);// ��ȡ�ϰ�����
            if (o.isLive()) {// �������Ч�ϰ�
                o.move();// �ϰ��ƶ�
                // ��������ϰ�
                g2.drawImage(o.image, o.x, o.y, this);
                    // ���ģ��ͷ�������ϰ���intersects�Ǿ�����Rectangle��ײ������
                if (o.getBounds().intersects(golden.getFootBounds()) || o.getBounds().intersects(golden.getHeadBounds())) {
                    if (o.a!=3){

                        Sound.hit();// ����ײ������
                        gameOver(); // ��Ϸ����
                    }
                    else {
                        Sound.get();    // ���Ž������
                        score += 50;        // ����ʮ��
                        list.remove(i);

                    }

                }
            } else {// ���������Ч�ϰ�
                list.remove(i);     // ɾ�����ϰ�
                i--;                // ѭ������ǰ�� ��֤�ϰ�ѭ��״̬�����Ӵ���ᵼ���������ϰ����д�����������¼���
                //System.out.println(i); -1 -1 -1
            }
        }
        //������ƿ���
        g2.drawImage(golden.image, golden.x, golden.y, this);// ���ƿ���
        if (scoreTimer >= 500) {// ÿ��500����
            score += 10;        // ��ʮ��
            scoreTimer = 0;     // ���¼�ʱ
        }
        //�����������
        g2.setColor(Color.RED); // ʹ�ú�ɫ
        g2.setFont(new Font("����", Font.BOLD, 24));      // ��������
        g2.drawString(String.format("%06d", score), 700, 30);   // ���Ʒ���  %06d 0��� 000001

        addObstacleTimer += FREASH; // �ϰ���ʱ��������Ŀ�ģ���һ����������ϰ�����
        scoreTimer += FREASH;       // ������ʱ������
    }

    /**
     * ��д�����������
     *///����canvas��Jpanel�����ط���(ִ��һ�γ�ʼ��)
    @Override
    public void paint(Graphics g) {//�����������ʱ���ȵ��ü̳еĹ��������Ĺ�����̳е�����  ����
        paintImage();// ������ͼƬ���ݷ���
        g.drawImage(image, 0, 0, this);//���Ƶ�ǰ����ͼ���ṩ���廭�壩
    }

    /**
     * ��Ϸ�Ƿ����
     * 
     * @return
     */
    public boolean isFinish() {//��FreshThread���б�����
        return finish;
    }//��FreshThread���б�����
    //public static boolean iscontinu() {return continu; }

    /**
     * ʹ��Ϸ����
     */
    public void gameOver() {
        System.out.println("000");
        ScoreRecorder.addNewScore(score);// ��¼��ǰ����  ScoreRecorder�����addNewScore�����������
        finish = true;
    }

    /**
     * ʵ�ְ��¼��̰�������
     */
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();// ��ȡ���µİ���ֵ
        if (code == KeyEvent.VK_SPACE) {// ����ǿո�
            golden.jump();// ����ģ����Ծ
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}