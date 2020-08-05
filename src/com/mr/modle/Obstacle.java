package com.mr.modle;//阻碍模型设计

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.mr.view.BackgroundImage;//导入别包的类

/**
 * 障碍类
 * 
 * @author mingrisoft
 *
 */

public class Obstacle {
    public int x, y,y1;         // 横纵坐标
    public BufferedImage image;
    private BufferedImage turtle;// 乌龟图片
    private BufferedImage cacti;// 鬼图片
    private BufferedImage fire; // 火箭图片
    private BufferedImage money;// 金币图片
    private int speed;          // 移动速度（与场景一致，像素距离4）
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
        Random r = new Random();        // 创建随机对象
        this.a=r.nextInt(4);
        if ( a== 0) {// 从0和1,2中取一值
            image = cacti;              // 采用鬼图片
        } else if(a == 1) {
            image = turtle;              // 采用乌龟图片
        }else if(a == 2) {
            image = fire;
        }
        else image = money;
        

        x = 800;                            // 初始横坐标
        if ( a== 0 || a == 1){
            y = 200 - image.getHeight();    // 乌龟、鬼纵坐标
        }
        else {
            y=100 - image.getHeight();      // 火箭纵坐标
        }

        speed = BackgroundImage.SPEED;      // 移动速度与背景同步（像素距离）
    }

    /**
     * 移动
     */
    public void move() {
        if(this.a==2) x -= speed*2;// 场景物体横坐标递减
        else x -= speed;
    }

    /**
     * 获取边界
     * 
     * @return
     */
    public Rectangle getBounds() {
        if (image == cacti) {// 如果使用鬼图片（x:相对窗体的图像位置 7：相对图像的有效位置）
            // 返回鬼的边界
            return new Rectangle(x + 7, y, 15, image.getHeight());
        }else if(image == fire){
            return new Rectangle(x, y+8, image.getWidth(), 25);
        }
         else if (image == turtle){   // 返回乌龟的边界
            return new Rectangle(x + 5, y + 4, 23, 21);
        }
        else return new Rectangle(x , y , 32, 32);

    }

    /**
     * 是否存活
     * 
     * @return
     */
    public boolean isLive() {//为了减少资源占用
        // 如果场景物体移出了游戏界面
        if (x <= -image.getWidth()) {
            return false;// 消亡
        }
        return true;// 存活
    }
}
