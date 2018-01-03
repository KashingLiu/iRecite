package com.company.Kashingliu;

import javax.swing.*;
import java.awt.*;

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
