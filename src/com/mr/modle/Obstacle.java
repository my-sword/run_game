package com.mr.modle;//�谭ģ�����

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.mr.view.BackgroundImage;//����������

/**
 * �ϰ���
 * 
 * @author mingrisoft
 *
 */

public class Obstacle {
    public int x, y,y1;         // ��������
    public BufferedImage image;
    private BufferedImage turtle;// �ڹ�ͼƬ
    private BufferedImage cacti;// ��ͼƬ
    private BufferedImage fire; // ���ͼƬ
    private BufferedImage money;// ���ͼƬ
    private int speed;          // �ƶ��ٶȣ��볡��һ�£����ؾ���4��
    public int a;              //

    public Obstacle() {
        try {
            turtle = ImageIO.read(new File("image/a/21.png"));
            cacti = ImageIO.read(new File("image/a/22.png"));
            fire = ImageIO.read(new File("image/a/24.png"));
            money = ImageIO.read(new File("image/a/23.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Random r = new Random();        // �����������
        this.a=r.nextInt(4);
        if ( a== 0) {// ��0��1,2��ȡһֵ
            image = cacti;              // ���ù�ͼƬ
        } else if(a == 1) {
            image = turtle;              // �����ڹ�ͼƬ
        }else if(a == 2) {
            image = fire;
        }
        else image = money;
        

        x = 800;                            // ��ʼ������
        if ( a== 0 || a == 1){
            y = 200 - image.getHeight();    // �ڹꡢ��������
        }
        else {
            y=100 - image.getHeight();      // ���������
        }

        speed = BackgroundImage.SPEED;      // �ƶ��ٶ��뱳��ͬ�������ؾ��룩
    }

    /**
     * �ƶ�
     */
    public void move() {
        if(this.a==2) x -= speed*2;// �������������ݼ�
        else x -= speed;
    }

    /**
     * ��ȡ�߽�
     * 
     * @return
     */
    public Rectangle getBounds() {
        if (image == cacti) {// ���ʹ�ù�ͼƬ��x:��Դ����ͼ��λ�� 7�����ͼ�����Чλ�ã�
            // ���ع�ı߽�
            return new Rectangle(x + 7, y, 15, image.getHeight());
        }else if(image == fire){
            return new Rectangle(x, y+8, image.getWidth(), 25);
        }
         else if (image == turtle){   // �����ڹ�ı߽�
            return new Rectangle(x + 5, y + 4, 23, 21);
        }
        else return new Rectangle(x , y , 32, 32);

    }

    /**
     * �Ƿ���
     * 
     * @return
     */
    public boolean isLive() {//Ϊ�˼�����Դռ��
        // ������������Ƴ�����Ϸ����
        if (x <= -image.getWidth()) {
            return false;// ����
        }
        return true;// ���
    }
}
