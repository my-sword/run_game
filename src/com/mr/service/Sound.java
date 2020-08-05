package com.mr.service;//���ֲ��Žӿ�

import java.io.FileNotFoundException;

/**
 * ��Ч��
 * 
 * @author mingrisoft
 *
 */
public class Sound {
    static final String DIR = "music1/";        // �����ļ���
    static final String BACKGROUD = "bg.wav";   // ��������
    static final String JUMP = "jump.wav";      // ��Ծ��Ч
    static final String HIT = "hit.wav";        // ײ����Ч
    static final String MONEY = "money.wav";    // �����Ч

    /**
     * ������Ծ��Ч
     */
    static public void jump() {
        play(DIR + JUMP, false);// ����һ����Ծ��Ч
    }

    /**
     * ����ײ����Ч
     */
    static public void hit() {
        play(DIR + HIT, false);// ����һ��ײ����Ч
    }

    /**
     * ���Ž����Ч
     */
    static public void get() {
        play(DIR + MONEY, false);// ����һ�ν����Ч
    }

    /**
     * ���ű�������
     */
    static public void backgroud() {
        play(DIR + BACKGROUD, true);// ѭ�����ű�������
    }

    /**
     * ����
     * 
     * @param file
     *            �����ļ���������
     * @param circulate
     *            �Ƿ�ѭ������
     */
    private static void play(String file, boolean circulate) {
        try {
            // ����������
            MusicPlayer player = new MusicPlayer(file, circulate);
            player.play();// ��������ʼ����
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
