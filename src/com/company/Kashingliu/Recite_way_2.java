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

public class Recite_way_2 {

    private static JButton english_word = new JButton("english");
    private static JButton chinese_word = new JButton("chinese");
    public static Random random = new Random();
    private static JPanel english_panel = new JPanel();
    private static JPanel chinese_panel = new JPanel();
    static int aa;
    static Iterator iterator;
    public static HashSet<Integer> hashSet = new HashSet<>(30);
    public void main() {
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

        if (hashSet.isEmpty()) {
            for (int i = 0; i < 35; i++) {
                if (hashSet.size() == 30) {
                    break;
                }
                hashSet.add(random.nextInt(dic_list.size()));
            }
        }
        ArrayList<Integer> result = new ArrayList<>(hashSet);
        iterator = result.iterator();

        aa = (int)iterator.next();
        String english = dic_list.get(aa)[0];
        String chinese = dic_list.get(aa)[1];

        english_word.setText(english);
        english_word.setForeground(Color.white);
        english_word.setFont(new Font("Helvetica",Font.PLAIN,130));
        chinese_word.setText(chinese);
        chinese_word.setForeground(Color.white);
        chinese_word.setFont(new Font("Helvetica",Font.PLAIN,80));

        english_word.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar()==KeyEvent.VK_ESCAPE) {
                    a.dispose();
                } else if (e.getKeyChar()==KeyEvent.VK_SPACE) {
                    aa = (int)iterator.next();
                    String en = dic_list.get(aa)[0];
                    String c = dic_list.get(aa)[1];
                    english_word.setText(en);
                    chinese_word.setText(c);
                }
            }
        });

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
