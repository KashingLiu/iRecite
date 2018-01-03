package com.company.Kashingliu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import static com.company.Kashingliu.Main.dic_list;
import static com.company.Kashingliu.Recite_way_2.hashSet;
import static com.company.Kashingliu.Recite_way_2.iterator;
import static com.company.Kashingliu.Recite_way_2.random;

public class Recite_way_4 {
    static JLabel chinese = new JLabel();
    public static String[] selected;
    static int right_answer_index;
    static ArrayList<JButton> choices;
    static Iterator choice_iterator;
    public static ArrayList<String[]> from_int_String_Array(ArrayList<Integer> in_it){
        ArrayList<String[]> out = new ArrayList<>(30);
        for (int i = 0;i<30;i++) {
            out.add(new String[2]);
            out.get(i)[0] = dic_list.get(in_it.get(i))[0];
            out.get(i)[1] = dic_list.get(in_it.get(i))[1];
        }
        return out;
    }
    public static String[] getSelected() {
        return selected;
    }
    public static void right() {
        selected = (String[])iterator.next();
        int right_choice_int = (int)choice_iterator.next();
        HashSet<Integer> other_choices = new HashSet<>(4);
        other_choices.add(right_choice_int);
        for (int i = 0; i < 10; i++) {
            if (other_choices.size()==4) {
                break;
            }
            other_choices.add(random.nextInt(dic_list.size()));
        }
        other_choices.remove(right_choice_int);
        right_answer_index = random.nextInt(4);
        choices.get(right_answer_index).setForeground(Color.black);
        choices.get(right_answer_index).setText(selected[1]);
        Iterator other_choice = other_choices.iterator();
        for (int i = 0; i<4; i++) {
            if (i == right_answer_index) {
                continue;
            } else {
                choices.get(i).setText(dic_list.get((int)other_choice.next())[1]);
            }
        }
        chinese.setBackground(Color.black);
        chinese.setText(selected[0]);
    }
    public void main() {
        JFrame main = new JFrame();
        Container ct = main.getContentPane();
        ct.setLayout(new BoxLayout(ct,BoxLayout.Y_AXIS));

        JPanel question = new JPanel();
        question.setLayout(new BoxLayout(question,BoxLayout.X_AXIS));

        JPanel answer = new JPanel();
        answer.setLayout(new BoxLayout(answer,BoxLayout.Y_AXIS));

        JPanel button = new JPanel();
        button.setLayout(new BoxLayout(button,BoxLayout.X_AXIS));

        JPanel A = new JPanel();
        A.setLayout(new BoxLayout(A,BoxLayout.X_AXIS));
        JPanel B = new JPanel();
        B.setLayout(new BoxLayout(B,BoxLayout.X_AXIS));
        JPanel C = new JPanel();
        C.setLayout(new BoxLayout(C,BoxLayout.X_AXIS));
        JPanel D = new JPanel();
        D.setLayout(new BoxLayout(D,BoxLayout.X_AXIS));

        // 这是四个选项
        JButton choice1 = new JButton("0");
        JButton choice2 = new JButton("1");
        JButton choice3 = new JButton("2");
        JButton choice4 = new JButton("3");
        // 由四个选项构成的数组
        choices = new ArrayList<>(4);
        choices.add(choice1);
        choices.add(choice2);
        choices.add(choice3);
        choices.add(choice4);

        // 以第二种方式为基准，如果第二种没点击的话，hashSet就是空的，那就更新
        // 否则的话就用第二种的那个
        if (Recite_way_2.hashSet.isEmpty()) {
            for (int i = 0; i < 35; i++) {
                if (Recite_way_2.hashSet.size() == 30) {
                    break;
                }
                Recite_way_2.hashSet.add(random.nextInt(dic_list.size()));
            }
        }

        // result是选出来的下标构成的一个数组
        ArrayList<Integer> result = new ArrayList<>(hashSet);
        // 把选出来的下标通过dic_list存到recite_list中
        ArrayList<String[]> recite_list = from_int_String_Array(result);

        // 迭代器，对选出来的单词的下标那个数组进行迭代
        choice_iterator = result.iterator();
        // 迭代器，对选出来的单词所构成的String数组进行迭代
        iterator = recite_list.iterator();

        // 选出来一个单词，记为数组selected
        selected = (String[])iterator.next();

        // 记录挑出来那个单词在字典中的位置
        int right_choice_int = (int)choice_iterator.next();

        // 构建一个不重复的哈希集合用来储存所有的选项
        HashSet<Integer> other_choices = new HashSet<>(4);
        // 先把正确的选项加进去
        other_choices.add(right_choice_int);
        // 把剩余的三个选项随机填入
        for (int i = 0; i < 10; i++) {
            if (other_choices.size()==4) {
                break;
            }
            other_choices.add(random.nextInt(dic_list.size()));
        }

        other_choices.remove(right_choice_int);

        // 之前不知道哪个是正确的选项，所以记录正确选项
        right_answer_index = random.nextInt(4);
        choices.get(right_answer_index).setText(selected[1]);

        Iterator other_choice = other_choices.iterator();

        for (int i = 0; i<4; i++) {
            if (i == right_answer_index) {
                continue;
            } else {
                choices.get(i).setText(dic_list.get((int)other_choice.next())[1]);
            }
        }

        chinese.setText(selected[0]);
        chinese.setFont(new Font("Helvetica",Font.PLAIN,70));
        for (JButton c:choices) {
            c.setBorderPainted(false);
            c.setFont(new Font("Helvetica",Font.PLAIN,30));
        }

        A.add(Box.createHorizontalStrut(10));
        A.add(choice1);
        A.add(Box.createHorizontalGlue());
        B.add(Box.createHorizontalStrut(10));
        B.add(choice2);
        B.add(Box.createHorizontalGlue());
        C.add(Box.createHorizontalStrut(10));
        C.add(choice3);
        C.add(Box.createHorizontalGlue());
        D.add(Box.createHorizontalStrut(10));
        D.add(choice4);
        D.add(Box.createHorizontalGlue());
        question.add(Box.createHorizontalGlue());
        question.add(chinese);
        question.add(Box.createHorizontalGlue());

        answer.add(Box.createVerticalGlue());
        answer.add(A);
        answer.add(B);
        answer.add(C);
        answer.add(D);
        answer.add(Box.createVerticalGlue());

        choice1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (choice1.getText().equals(getSelected()[1])) {
                    right();
                }
            }
        });

        choice2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (choice2.getText().equals(getSelected()[1])) {
                    right();
                }
            }
        });

        choice3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (choice3.getText().equals(getSelected()[1])) {
                    right();
                }
            }
        });

        choice4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (choice4.getText().equals(getSelected()[1])) {
                    right();
                }
            }
        });

        main.add(Box.createVerticalStrut(20));
        main.add(question);
        main.add(Box.createVerticalGlue());
        main.add(answer);
        main.add(Box.createVerticalGlue());

        main.setMinimumSize(new Dimension(600,400));
        main.setVisible(true);
    }
}
