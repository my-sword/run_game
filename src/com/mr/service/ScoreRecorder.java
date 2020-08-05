package com.mr.service;//分数计数器

import java.io.*;
import java.util.Arrays;

/**
 * 分数记录器
 * 
 * @author mingrisoft
 *
 */
public class ScoreRecorder {
    private static final String SCOREFILE = "data/soure";// 得分记录文件（soure为没有后缀的文件）
    private static int scores[] = new int[3];// 当前得分最高前三名

    /**
     * 分数初始化或读取文件数据
     */
    public static void init() {
        File f = new File(SCOREFILE);// 创建记录文件
        if (!f.exists()) {// 如果文件不存在
            try {
                f.createNewFile();// 创建新文件
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;// 停止方法
        }
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            //读写三层流Input==read
            fis = new FileInputStream(f);       // 文件字节输入流
            isr = new InputStreamReader(fis);   // 字节流转字符流
            br = new BufferedReader(isr);       // 缓冲字符流

            String value = br.readLine();               // 读取一行
            if (!(value == null || "".equals(value))) { //如果不为空 （value == null对象是空  "".equals(value)空字符串）
                String vs[] = value.split(",");  // 分割字符串
                if (vs.length < 3) {// 如果分割结果小于3（即初始化填充0）
                    Arrays.fill(scores, 0);        // 数组填充0
                } else {
                    for (int i = 0; i < 3; i++) {
                        // 将记录文件中的值赋给当前分数数组
                        scores[i] = Integer.parseInt(vs[i]);//文本字符转数字
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {// 倒序关闭流
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 保存分数
     */
    public static void saveScore() {
        // 拼接得分数组
        String value = scores[0] + "," + scores[1] + "," + scores[2];//初始化后存在数据
        //读写三层流Output==write
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {//记录文件
            fos = new FileOutputStream(SCOREFILE);  // 文件字节输出流
            osw = new OutputStreamWriter(fos);      // 字节流转字符流
            bw = new BufferedWriter(osw);           // 缓冲字符流
            bw.write(value);// 写入拼接后的字符串
            bw.flush();     // 字符流刷新
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {// 依次关闭流
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加分数。如果新添加的分数比排行榜分数高，则会将新分数记入排行榜。
     * 
     * @param score
     *            新分数
     */
    static public void addNewScore(int score) {
        // 在得分组数不改变长度情况下创建一个长度为4的临时数组并复制scores
        int tmp[] = Arrays.copyOf(scores, 4);
        tmp[3] = score;     // 将新分数赋值给tmp第四个元素
        Arrays.sort(tmp);   // 临时数组升序排列
        scores = Arrays.copyOfRange(tmp, 1, 4);// 将后三个元素赋值给得分数组（1,2,3）

    }

    /**
     * 获取分数()
     * 
     * @return
     */
    static public int[] getScores() {
        return scores;
    }

}
