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
 * 游戏面板
 * 
 * @author mingrisoft
 *
 */
public class GamePanel extends JPanel implements KeyListener {
    public static boolean continu=false;
    private BufferedImage image;        // 主图片
    private BackgroundImage background; // 背景图片
    private Mario golden;            // 恐龙
    private Graphics2D g2;              // 主图片绘图对象
    private int addObstacleTimer = 0;   // 添加障碍计时器
    private boolean finish = false;     // 游戏结束标志
    //public boolean continu = false;     // 游戏结束动画
    private List<Obstacle> list = new ArrayList<Obstacle>();// 障碍对象集合（将障碍对象存储到集合中）
    private List<Obstacle> list1 = new ArrayList<Obstacle>();// 其它对象集合（将障碍对象存储到集合中）
    private final int FREASH = FreshThread.FREASH;   // 刷新时间

    int score = 0;      // 得分
    int scoreTimer = 0; // 分数计时器

    public GamePanel() {
        // 主图片采用宽800高300的彩色图片
        image = new BufferedImage(960, 300, BufferedImage.TYPE_INT_BGR);//背景
        g2 = image.createGraphics();// 获取主图片绘图对象
        background = new BackgroundImage();// 初始化滚动背景（调用自类）
        golden = new Mario();// 初始化人物模型
        list.add(new Obstacle());// 添加第一个障碍
        FreshThread t = new FreshThread(this);// 刷新帧线程
        t.start();// 启动线程
    }

    /**
     * 绘制主图片
     */
    private void paintImage() {
        background.roll();  // 背景图片开始滚动
        golden.move();      // 模型开始移动（踏步或跳跃）
        g2.drawImage(background.image, 0, 0, this);// 绘制滚动背景
        //随机出现障碍
        if (addObstacleTimer == 1300) {     // 每过1300毫秒
            if (Math.random() * 100 > 30) { // 60%概率出现障碍
                list.add(new Obstacle());
            }
            addObstacleTimer = 0;// 重新计时
        }
        //障碍移动、声音、删除
        for (int i = 0; i < list.size(); i++) {// 遍历障碍对象集合
            Obstacle o = list.get(i);// 获取障碍对象
            if (o.isLive()) {// 如果是有效障碍
                o.move();// 障碍移动
                // 画板绘制障碍
                g2.drawImage(o.image, o.x, o.y, this);
                    // 如果模型头脚碰到障碍（intersects是矩形类Rectangle碰撞方法）
                if (o.getBounds().intersects(golden.getFootBounds()) || o.getBounds().intersects(golden.getHeadBounds())) {
                    if (o.a!=3){

                        Sound.hit();// 播放撞击声音
                        gameOver(); // 游戏结束
                    }
                    else {
                        Sound.get();    // 播放金币声音
                        score += 50;        // 加五十分
                        list.remove(i);

                    }

                }
            } else {// 如果不是有效障碍
                list.remove(i);     // 删除此障碍
                i--;                // 循环变量前移 保证障碍循环状态，不加此项会导致连续对障碍进行触碰，分数记录多次
                //System.out.println(i); -1 -1 -1
            }
        }
        //画板绘制恐龙
        g2.drawImage(golden.image, golden.x, golden.y, this);// 绘制恐龙
        if (scoreTimer >= 500) {// 每过500毫秒
            score += 10;        // 加十分
            scoreTimer = 0;     // 重新计时
        }
        //画板分数绘制
        g2.setColor(Color.RED); // 使用红色
        g2.setFont(new Font("黑体", Font.BOLD, 24));      // 设置字体
        g2.drawString(String.format("%06d", score), 700, 30);   // 绘制分数  %06d 0填充 000001

        addObstacleTimer += FREASH; // 障碍计时器递增（目的：到一定秒数添加障碍对象）
        scoreTimer += FREASH;       // 分数计时器递增
    }

    /**
     * 重写绘制组件方法
     *///这是canvas和Jpanel的重载方法(执行一次初始化)
    @Override
    public void paint(Graphics g) {//当创建本类的时候，先调用继承的构造→本类的构造→继承的重载  ？？
        paintImage();// 绘制主图片内容方法
        g.drawImage(image, 0, 0, this);//绘制当前画板图像（提供整体画板）
    }

    /**
     * 游戏是否结束
     * 
     * @return
     */
    public boolean isFinish() {//在FreshThread类中被调用
        return finish;
    }//在FreshThread类中被调用
    //public static boolean iscontinu() {return continu; }

    /**
     * 使游戏结束
     */
    public void gameOver() {
        System.out.println("000");
        ScoreRecorder.addNewScore(score);// 记录当前分数  ScoreRecorder自类的addNewScore方法添加排序
        finish = true;
    }

    /**
     * 实现按下键盘按键方法
     */
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();// 获取按下的按键值
        if (code == KeyEvent.VK_SPACE) {// 如果是空格
            golden.jump();// 人物模型跳跃
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}