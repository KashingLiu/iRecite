package com.company.Kashingliu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import static com.company.Kashingliu.Main.dic_list;

class Recite_way_1 {
    private static JButton english_word = new JButton("english");
    private static JButton chinese_word = new JButton("chinese");
    private static Random random = new Random();
    private static JPanel english_panel = new JPanel();
    private static JPanel chinese_panel = new JPanel();
    public static int aaa = 0;
    static int aa;
    static Iterator iterator;

    public void main() {
        //在list或者table上加一个双击的监听器，如果触发监听器，调用类内的static方法，考察
        //怎么进入全屏界面啊？？？？
        JFrame a = new JFrame();
        Container ct = a.getContentPane();
        ct.setLayout(new BoxLayout(ct,BoxLayout.Y_AXIS));
        a.getContentPane().setBackground(Color.darkGray);

        english_panel.setLayout(new BoxLayout(english_panel,BoxLayout.X_AXIS));
        chinese_panel.setLayout(new BoxLayout(chinese_panel,BoxLayout.X_AXIS));
        english_panel.setBackground(Color.darkGray);
        chinese_panel.setBackground(Color.darkGray);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(a);
        english_word.setBorderPainted(false);
        chinese_word.setBorderPainted(false);


        HashSet<Integer> hashSet = new HashSet<>(10);
        for (int i = 0; i< 15; i++ ) {
            if (hashSet.size()==10) {
                break;
            }
            hashSet.add(random.nextInt(dic_list.size()));
        }
        ArrayList<Integer> result = new ArrayList<>(hashSet);


        iterator = result.iterator();

        aa = (int)iterator.next();
        String english = dic_list.get(aa)[0];
        String chinese = dic_list.get(aa)[1];

        english_word.setText(english);
        english_word.setForeground(Color.white);
        english_word.setFont(new Font("Helvetica",Font.PLAIN,130));
//        chinese_word.setText(chinese);
        chinese_word.setForeground(Color.white);
        chinese_word.setFont(new Font("Helvetica",Font.PLAIN,80));
        chinese_panel.setVisible(false);

        a.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        KeyAdapter key = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
//                int cnt;
                if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    a.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    a.dispose();
//                    System.out.println(aaa);
                    aaa = 0;
                }
                if (e.getKeyChar() == KeyEvent.VK_SPACE) {
                    if (aaa == 0) {
                        chinese_word.setText(dic_list.get(aa)[1]);
                        chinese_panel.setVisible(true);
                        aaa = 1;
                    } else if (aaa==1) {
                        aa = (int)iterator.next();
                        english_word.setText(dic_list.get(aa)[0]);
                        chinese_panel.setVisible(false);
//                        chinese_word.setText(dic_list.get(cnt)[1]);
                        aaa = 0;
                    }
                }
            }
        };
        english_word.addKeyListener(key);
//        new KeyAdapter() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                super.keyTyped(e);
//                if (e.getKeyChar()==KeyEvent.VK_ESCAPE) {
//                    a.dispose();
//                }
//                if (e.getKeyChar()==KeyEvent.VK_SPACE) {
//                    chinese_panel.setVisible(true);
//                    chinese_panel.addKeyListener(new KeyAdapter() {
//                        @Override
//                        public void keyTyped(KeyEvent e) {
//                            super.keyTyped(e);
//                            if (e.getKeyChar()==KeyEvent.VK_SPACE) {
//                                String english = dic_list.get((int)iterator.next())[0];
//                                String chinese = dic_list.get((int)iterator.next())[1];
//                                english_word.setText(english);
//                                chinese_word.setText(chinese);
//                                chinese_panel.setVisible(false);
//                            }
//                        }
//                    });
//                }
//            }
//        }

//        System.out.println(flag);

        english_panel.add(english_word);
        chinese_panel.add(chinese_word);


        a.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        a.add(Box.createVerticalStrut(200));
        a.add(english_panel);
        a.add(Box.createVerticalStrut(250));
        a.add(chinese_panel);
        a.setVisible(true);
    }
}
