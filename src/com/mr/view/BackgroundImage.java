//��������
package com.mr.view;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * ��������
 * 
 * @author mingrisoft
 *
 */
public class BackgroundImage {
    public BufferedImage image;             // ����ͼƬ
    private BufferedImage image1, image2;   // ����������ͼƬ
    private Graphics2D g;               // ����ͼƬ�Ļ�ͼ����
    public int x1, x2;                  // ��������ͼƬ������
    public static final int SPEED = 2;  // �����ٶȣ���Obstacle���� �ٶ�һ�£����ؾ��룩

    public BackgroundImage() {
        try {
            image1 = ImageIO.read(new File("image/a/00.png"));//Ŀ�ģ�����ͼ����
            image2 = ImageIO.read(new File("image/a/01.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ��ͼƬ���ÿ�960��300�Ĳ�ɫͼƬ
        image = new BufferedImage(960, 300, BufferedImage.TYPE_INT_RGB);
        g = image.createGraphics();// ��ȡ��ͼƬ��ͼ����
        x1 = 0;     // ��һ��ͼƬ��ʼ����Ϊ0
        x2 = 800;   // �ڶ���ͼƬ��ʼ������Ϊ800
        g.drawImage(image1, x1, 0, null);//������ͼ�� ˮƽ ��ֱ ����ָ��ͼ���������ŵ��ʺ�ָ�������ڲ���ͼ��
    }
    /**
     * ����С��960 ���Ч��
     */
    public void roll() {
        x1 -= SPEED;// ��һ��ͼƬ����
        x2 -= SPEED;// �ڶ���ͼƬ����
        if (x1 <= -800) {// �����һ��ͼƬ�Ƴ���Ļ
            x1 = 800;// �ص���Ļ�Ҳ�
        }
        if (x2 <= -800) {// ����ڶ���ͼƬ�Ƴ���Ļ
            x2 = 800;// �ص���Ļ�Ҳ�
        }
        g.drawImage(image1, x1, 0, null); // ����ͼƬ�л�������ͼƬ
        g.drawImage(image2, x2, 0, null);
    }
}
