package com.company.Kashingliu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import static com.company.Kashingliu.Main.dic_list;
import static com.company.Kashingliu.Recite_way_2.random;

public class Recite_way_3 {

    static String[] mid;
    static Iterator iter;
    public static ArrayList<String[]> from_int_String_Array(ArrayList<Integer> in_it){
        ArrayList<String[]> out = new ArrayList<>(30);
        for (int i = 0;i<30;i++) {
            out.add(new String[2]);
            out.get(i)[0] = dic_list.get(in_it.get(i))[0];
            out.get(i)[1] = dic_list.get(in_it.get(i))[1];
        }
        return out;
    }
    public void main() {
        JFrame main_frame = new JFrame();
        Container main = main_frame.getContentPane();
        main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));

        JPanel up = new JPanel();
        up.setLayout(new BoxLayout(up,BoxLayout.X_AXIS));
        JPanel text_down = new JPanel();
        text_down.setLayout(new BoxLayout(text_down,BoxLayout.X_AXIS));
        JPanel button = new JPanel();
        button.setLayout(new BoxLayout(button, BoxLayout.X_AXIS));

        JTextArea answer = new JTextArea(1,20);
        answer.setForeground(Color.black);

        if (Recite_way_2.hashSet.isEmpty()) {
            for (int i = 0; i < 35; i++) {
                if (Recite_way_2.hashSet.size() == 30) {
                    break;
                }
                Recite_way_2.hashSet.add(random.nextInt(dic_list.size()));
            }
        }
        ArrayList<Integer> result_in_three = new ArrayList<>(Recite_way_2.hashSet);
        ArrayList<String[]> recite_list = from_int_String_Array(result_in_three);

        iter = recite_list.iterator();
        mid = (String[])iter.next();

        JLabel question = new JLabel();

        question.setText(mid[1]);
        question.setFont(new Font("Helvetica",Font.PLAIN,30));

        answer.setMargin(new Insets(3,3,1,3));
        answer.setFont(new Font("Helvetica",Font.PLAIN,40));

        up.add(Box.createHorizontalStrut(50));
        up.add(question);
        up.add(Box.createHorizontalStrut(50));

        text_down.add(Box.createHorizontalStrut(50));
        text_down.add(answer);
        text_down.add(Box.createHorizontalStrut(50));

        JButton verify = new JButton("确认");
        JButton finish = new JButton("退出");
        JButton give_answer = new JButton("正确答案");
        JButton next = new JButton("跳过");

        button.add(Box.createHorizontalStrut(20));
        button.add(give_answer);
        button.add(Box.createHorizontalStrut(20));
        button.add(finish);
        button.add(Box.createHorizontalStrut(20));
        button.add(verify);
        button.add(Box.createHorizontalStrut(20));
        button.add(next);
        button.add(Box.createHorizontalStrut(20));


        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answer.setForeground(Color.black);
                main_frame.dispose();
            }
        });


        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answer.setForeground(Color.black);
                if (!iter.hasNext()) {
                    main_frame.dispose();
                }
                mid = (String[])iter.next();
                question.setText(mid[1]);
                answer.setText("");
            }
        });

        verify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answer.setForeground(Color.black);
                String input = answer.getText();
                String right = mid[0];
                if (input.equals(right)) {
                    mid = (String[])iter.next();
                    question.setText(mid[1]);
                    answer.setText("");
                } else {
                    for (String[] i : dic_list) {
                        if (i[0].equals(input)) {
                            give_input_answer.main(i[0],i[1]);
                        }
                    }
                    answer.setForeground(Color.red);
                    answer.setText(mid[0]);
                }
            }
        });

        JLabel right_answer = new JLabel();
        right_answer.setText(mid[0]);
        right_answer.setForeground(Color.red);
        right_answer.setFont(new Font("Helvetica",Font.PLAIN,40));

        give_answer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answer.setForeground(Color.red);
                answer.setText(mid[0]);
            }
        });

        main_frame.add(Box.createVerticalStrut(20));
        main_frame.add(up);
        main_frame.add(Box.createVerticalStrut(30));
        main_frame.add(text_down);
        main_frame.add(Box.createVerticalStrut(20));
        main_frame.add(button);
        main_frame.add(Box.createVerticalStrut(10));
        main_frame.setMinimumSize(new Dimension(600,400));
        main_frame.setVisible(true);
    }
}

class give_input_answer {
    public static void main(String chinese, String english) {
        JFrame show = new JFrame("你的答案");
        Container cp = show.getContentPane();
        cp.setLayout(new BoxLayout(cp,BoxLayout.Y_AXIS));

        JPanel eng = new JPanel();
        eng.setLayout(new BoxLayout(eng,BoxLayout.X_AXIS));

        JPanel chi = new JPanel();
        chi.setLayout(new BoxLayout(chi,BoxLayout.X_AXIS));

        JLabel up = new JLabel(chinese);
        JLabel down = new JLabel(english);

        up.setFont(new Font("Helvetica",Font.PLAIN,50));
        down.setFont(new Font("Helvetica",Font.PLAIN,50));

        eng.add(Box.createHorizontalStrut(5));
        eng.add(up);
        eng.add(Box.createHorizontalStrut(5));


        chi.add(Box.createHorizontalStrut(5));
        chi.add(down);
        chi.add(Box.createHorizontalStrut(5));

        show.add(eng);
        show.add(chi);
        show.setVisible(true);
        show.setMinimumSize(new Dimension(350,250));
    }
}
