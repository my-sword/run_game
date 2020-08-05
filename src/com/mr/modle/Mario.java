package com.mr.modle;//模型设计
/**
 * 入口类
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
 * 人物模型类
 * 
 * @author mingrisoft
 *
 */

public class Mario {
    //缓冲区是一块特定的内存区域，其目的是通过缓解应用程序上下层之间的性能差异，减少上层对下层的等待时间，以此提高系统性能。
    public BufferedImage image;         // 模型显示图片(缓存流flush)
    private BufferedImage image1, image2, image3;   // 跑步图片(循环播放)
    public int x, y;                    // 模型坐标（与其它包互不影响，是本类的x,y）
    private int jumpValue = 0;          // 跳跃的增变量
    private boolean jumpState = false;  // 跳跃状态
    //private boolean over = false;       // 结束状态
    public static boolean over1 = false;      // 跳转状态
    private int stepTimer = 0;          // 踏步计时器（分数，初始为0）
    private final int JUMP_HIGHT = 100; // 跳起最大高度
    private final int LOWEST_Y = 120;   // 落地最低坐标
    private final int FREASH = FreshThread.FREASH;  // 模型刷新时间（调用 FreshThread自类的常量20）

    public Mario() {
        x = 50;      //固定坐标（人物不动，场景动）
        y = LOWEST_Y;// 落地最低坐标
        try {
            image1 = ImageIO.read(new File("image/a/1.png"));
            image2 = ImageIO.read(new File("image/a/2.png"));
            image3 = ImageIO.read(new File("image/a/3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
	 * 移动
	 */
    public void move() {
        step();         // 不断踏步（调用自方法）
        if (jumpState) {// 如果正在跳跃
            if (y >= LOWEST_Y) {    // 如果纵坐标大于等于最低点
                jumpValue = -2;     // 模型上升
            }
            if (y <= LOWEST_Y - JUMP_HIGHT) {// 如果跳过最高点
                jumpValue = 2;      // 模型下降
            }
            y += jumpValue;         // 纵坐标发生变化
            if (y >= LOWEST_Y) {    // 如果再次落地
                jumpState = false;  // 停止跳跃
            }
        }
        if (over1) {
            jumpState = false;  // 停止跳跃
            y += 4;         // 纵坐标发生变化
            if (y > 300+image.getHeight()){
                over1=false;
                GamePanel.continu=true;
            }
        }

    }

    /**
     * 跳跃
     */
    public void jump() {
        if (!jumpState) {// 如果没处于跳跃状态
            Sound.jump();// 播放跳跃音效
        }
        jumpState = true;// 处于跳跃状态
    }

    /**
     * 踏步
     */
    private void step() {
        // 每过250毫秒，更换一张图片。因为共有3图片，所以除以3取余，轮流展示这三张
        int tmp = stepTimer / 250 % 3;// stepTimer 踏步计时器当计时器递增到250时，图片转换
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
        stepTimer += FREASH;// 计时器递增20
    }

    /**
     * 足部边界区域
     * 
     * @return
     */
    public Rectangle getFootBounds() {
        return new Rectangle(x + 30, y + 59, 29, 18);//awt类的矩形方法 相对坐标(x,y) 宽高
        //图像左上角的(x+30,y+59),宽29,高18  足部矩形区域（计算碰撞）
    }

    /**
     * 头部边界区域
     * 
     * @return
     */
    public Rectangle getHeadBounds() {
        return new Rectangle(x + 66, y + 25, 32, 22);
    }
        //图像左上角的(x+66,y+25),宽32,高22  头部矩形区域
}