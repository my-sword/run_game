package com.mr.modle;//ģ�����
/**
 * �����
 *
 * @author mingrisoft
 *
 */
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import com.mr.service.FreshThread;
import com.mr.service.Sound;

import com.mr.view.GamePanel;

/**
 * ����ģ����
 * 
 * @author mingrisoft
 *
 */

public class Mario {
    //��������һ���ض����ڴ�������Ŀ����ͨ������Ӧ�ó������²�֮������ܲ��죬�����ϲ���²�ĵȴ�ʱ�䣬�Դ����ϵͳ���ܡ�
    public BufferedImage image;         // ģ����ʾͼƬ(������flush)
    private BufferedImage image1, image2, image3;   // �ܲ�ͼƬ(ѭ������)
    public int x, y;                    // ģ�����꣨������������Ӱ�죬�Ǳ����x,y��
    private int jumpValue = 0;          // ��Ծ��������
    private boolean jumpState = false;  // ��Ծ״̬
    //private boolean over = false;       // ����״̬
    public static boolean over1 = false;      // ��ת״̬
    private int stepTimer = 0;          // ̤����ʱ������������ʼΪ0��
    private final int JUMP_HIGHT = 100; // �������߶�
    private final int LOWEST_Y = 120;   // ����������
    private final int FREASH = FreshThread.FREASH;  // ģ��ˢ��ʱ�䣨���� FreshThread����ĳ���20��

    public Mario() {
        x = 50;      //�̶����꣨���ﲻ������������
        y = LOWEST_Y;// ����������
        try {
            image1 = ImageIO.read(new File("image/a/1.png"));
            image2 = ImageIO.read(new File("image/a/2.png"));
            image3 = ImageIO.read(new File("image/a/3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
	 * �ƶ�
	 */
    public void move() {
        step();         // ����̤���������Է�����
        if (jumpState) {// ���������Ծ
            if (y >= LOWEST_Y) {    // �����������ڵ�����͵�
                jumpValue = -2;     // ģ������
            }
            if (y <= LOWEST_Y - JUMP_HIGHT) {// ���������ߵ�
                jumpValue = 2;      // ģ���½�
            }
            y += jumpValue;         // �����귢���仯
            if (y >= LOWEST_Y) {    // ����ٴ����
                jumpState = false;  // ֹͣ��Ծ
            }
        }
        if (over1) {
            jumpState = false;  // ֹͣ��Ծ
            y += 4;         // �����귢���仯
            if (y > 300+image.getHeight()){
                over1=false;
                GamePanel.continu=true;
            }
        }

    }

    /**
     * ��Ծ
     */
    public void jump() {
        if (!jumpState) {// ���û������Ծ״̬
            Sound.jump();// ������Ծ��Ч
        }
        jumpState = true;// ������Ծ״̬
    }

    /**
     * ̤��
     */
    private void step() {
        // ÿ��250���룬����һ��ͼƬ����Ϊ����3ͼƬ�����Գ���3ȡ�࣬����չʾ������
        int tmp = stepTimer / 250 % 3;// stepTimer ̤����ʱ������ʱ��������250ʱ��ͼƬת��
        //System.out.println(tmp);
        switch (tmp) {
            case 1 :
                image = image1;
                break;
            case 2 :
                image = image2;
                break;
            default :
                image = image3;
        }
        stepTimer += FREASH;// ��ʱ������20
    }

    /**
     * �㲿�߽�����
     * 
     * @return
     */
    public Rectangle getFootBounds() {
        return new Rectangle(x + 30, y + 59, 29, 18);//awt��ľ��η��� �������(x,y) ���
        //ͼ�����Ͻǵ�(x+30,y+59),��29,��18  �㲿�������򣨼�����ײ��
    }

    /**
     * ͷ���߽�����
     * 
     * @return
     */
    public Rectangle getHeadBounds() {
        return new Rectangle(x + 66, y + 25, 32, 22);
    }
        //ͼ�����Ͻǵ�(x+66,y+25),��32,��22  ͷ����������
}