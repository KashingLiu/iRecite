package com.company.Kashingliu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Main  {
    private static String[] change_sharp(String a) {
        if (a!=null){
            String[] result = a.split("#");
            String[] fine = {result[0],result[1]};
            return fine;
        }
        else return null;
    }
    private static String change_sharp_list_key(String a) {
        if (a!=null){
            return a.split("#")[1];
        }
        else return null;
    }
    private static String change_sharp_list_value(String a) {
        if (a!=null){
            return a.split("#")[0];
        }
        else return null;
    }
    private static String[] change_sharp_list(String a) {
        if (a!=null) {
            return a.split("#");
        }
        else return null;
    }
    private static String[] change_is(String a) {
        if (a!=null){
            int second_split = a.lastIndexOf("=");
            String re = a.substring(0,second_split);
            return re.split("=");
        }
        else return null;
    }

    private static Vector out(String[] a) {
        Vector<String> out = new Vector<>(2);
        out.add(a[0]);
        out.add(a[1]);
        return out;
    }
    public static void main(String[] args) throws IOException {

        // 最大的frame，设置成boxlayout，横向的
        JFrame Main = new JFrame("天天背单词");
        Container ct = Main.getContentPane();
        ct.setLayout(new BoxLayout(ct,BoxLayout.Y_AXIS));


        // 第一个图标，单词搜索
        ImageIcon im1_white = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/search3.png");
        ImageIcon im1_gray = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/search1.png");
        ImageIcon im1_black = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/search2.png");
        // 第二个图标，背单词，图书图标
        ImageIcon im2_gray = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/tsg1.png");
        ImageIcon im2_white = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/tsg3.png");
        ImageIcon im2_black = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/tsg2.png");
        // 第三个图标，历史回顾
        ImageIcon im3_gray = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/back1.png");
        ImageIcon im3_white = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/back3.png");
        ImageIcon im3_black = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/back2.png");

        JRadioButton button1 = new JRadioButton(im1_white);
        JRadioButton button2 = new JRadioButton(im2_gray);
        JRadioButton button3 = new JRadioButton(im3_gray);
        ButtonGroup group = new ButtonGroup();

        button1.setBorderPainted(false);
        button2.setBorderPainted(false);
        button3.setBorderPainted(false);
        group.add(button1);
        group.add(button2);
        group.add(button3);




        JPanel up_panel = new JPanel();
        up_panel.setLayout(new BoxLayout(up_panel,BoxLayout.X_AXIS));
        up_panel.add(Box.createHorizontalStrut(70));
        up_panel.add(button1);
        up_panel.add(Box.createHorizontalStrut(10));
        up_panel.add(button2);
        up_panel.add(Box.createHorizontalStrut(10));
        up_panel.add(button3);
        up_panel.add(Box.createHorizontalStrut(70));




        JPanel down_panel = new JPanel();
        down_panel.setLayout(new BoxLayout(down_panel,BoxLayout.X_AXIS));


        // 把单词放入dic_list
        ArrayList<String[]> dic_list = new ArrayList<>();
        // 搜索结果以表格（JTable）实现，这个是列名
        Vector<String> name = new Vector<>(2);
        name.add("English");
        name.add("中文");
        // 表格内部模型
        DefaultTableModel tableModel = new DefaultTableModel(name,100);


        FileReader all_dictionary_list = new FileReader("Dict/grouplist.txt");
        BufferedReader buffer = new BufferedReader(all_dictionary_list);
        String reader = "";
        ArrayList<String[]> all_list = new ArrayList<>(50);
        Map map = new HashMap();
        while (reader != null) {
            reader = buffer.readLine();
            if (reader != null) {
                all_list.add(change_sharp_list(reader));
                map.put(change_sharp_list_key(reader), change_sharp_list_value(reader));
            }
        }
        System.out.println(all_list.get(0)[1]);
        DefaultListModel<String> left_listModel = new DefaultListModel<>();
        for (String[] i : all_list) {
            left_listModel.addElement(i[1]);
        }
        JList<String> left_list = new JList<>(left_listModel);
        JPanel left_panel = new JPanel();
        left_panel.setLayout(new BoxLayout(left_panel, BoxLayout.Y_AXIS));
        JScrollPane list_left = new JScrollPane(left_list);
        left_panel.add(Box.createVerticalStrut(10));
        left_panel.add(list_left);
        left_panel.add(Box.createVerticalStrut(10));
        MouseAdapter DoubleClicked = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList theList = (JList) e.getSource();
                if (e.getClickCount()==2) {
                    dic_list.clear();
                    tableModel.setRowCount(0);
                    int index = theList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        Object o = theList.getModel().getElementAt(index);
                        try {
                            String path = "Dict/"+map.get(o.toString());
                            FileReader a = new FileReader(path);
                            BufferedReader br = new BufferedReader(a);
                            // 读取文件，对文件进行按行划分，用change_sharp来表示中间是#的，用**_is来表示中间是等号的
                            String string = "";
                            while ( string != null ) {
                                string = br.readLine();
                                if (string!=null) {
                                    dic_list.add(change_sharp(string));
                                    tableModel.addRow(change_sharp(string));
                                }
                            }
                        } catch (Exception exc) {
                            System.out.println(exc.toString());
                        }
                    }
                }
            }
        };
        left_list.addMouseListener(DoubleClicked);


        CardLayout cardLayout = new CardLayout();
        JPanel right_panel = new JPanel(cardLayout);




//         放置搜索框和搜索结果的main JPanel
        JPanel main = new JPanel();
        // 搜索框
        java.awt.TextField tf = new java.awt.TextField(12);
        JTable test_table = new JTable(tableModel);
        // 表格的下拉条
        JScrollPane jScrollPane = new JScrollPane(test_table);
        // 搜索框对应的JPanel
        JPanel search = new JPanel();
        search.setLayout(new BoxLayout(search,BoxLayout.X_AXIS));
        search.add(Box.createHorizontalStrut(30));
        search.add(tf);
        search.add(Box.createHorizontalStrut(30));
        // 搜索结果对应的JPanel
        JPanel table = new JPanel();
        table.setLayout(new BoxLayout(table,BoxLayout.X_AXIS));
        table.add(Box.createHorizontalStrut(12));
        table.add(jScrollPane);
        table.add(Box.createHorizontalStrut(12));
        // 表格中的行高30
        test_table.setRowHeight(30);
        // 表格字体大小
        test_table.setFont(new Font("Menu.font",Font.PLAIN,18));
        // 搜索栏的最大尺寸和字体
        tf.setMaximumSize(new Dimension(200,30));
        tf.setFont(new Font("Menu.font",Font.PLAIN,15));
//        // 读文件的
//        FileReader a = new FileReader("/Users/Kashingliu/Documents/EnglishDictionary/Dict/College_Grade6.txt");
//        BufferedReader br = new BufferedReader(a);
//        // 读取文件，对文件进行按行划分，用change_sharp来表示中间是#的，用**_is来表示中间是等号的
//        String string = "";
//        while ( string != null ) {
//            string = br.readLine();
//            if (string!=null) {
//                dic_list.add(change_sharp(string));
//                tableModel.addRow(change_sharp(string));
//            }
//        }

        // main使用BoxLayout，纵方向
        main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
        // 对于搜索框添加监控器
        tf.addTextListener((e)-> {
                try {
                    tableModel.setRowCount(0);
                    int len = tf.getText().length();
                    for (String[] i : dic_list) {
                        if ((i[0].length()>=len)&&i[0].substring(0, len).equals(tf.getText())) {
                            System.out.println(i[0].substring(0, len));
                            System.out.println(tf.getText());
                            tableModel.addRow(out(i));
                            test_table.setModel(tableModel);
                        }
                    }

                } catch (Exception e1) {
                    System.out.println(e1.toString());
                }
            });
        // main里面有搜索框和结果栏
        main.add(Box.createVerticalStrut(6));
        main.add(search);
//        main.add(Box.createVerticalBox());
        main.add(table);
        main.add(Box.createVerticalStrut(6));








        // Main里有main
        down_panel.add(Box.createHorizontalStrut(5));
        down_panel.add(left_panel);
        down_panel.add(Box.createHorizontalStrut(5));



        JPanel second = new JPanel();
        second.setLayout(new BoxLayout(second,BoxLayout.X_AXIS));
        second.add(new JButton("test"));
        JPanel third = new JPanel();
        third.setLayout(new BoxLayout(third,BoxLayout.X_AXIS));
        third.add(new JButton("test"));


        right_panel.add(main,"first");
        right_panel.add(second,"second");
        right_panel.add(third,"third");

        ActionListener al_first = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(right_panel,"first");
            }
        };
        ActionListener al_second = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(right_panel,"second");
            }
        };
        ActionListener al_third = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(right_panel,"third");
            }
        };

        ItemListener il = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (button1.isSelected()) {
                    button1.setIcon(im1_white);
                    button2.setIcon(im2_gray);
                    button3.setIcon(im3_gray);
                }
                else if (button2.isSelected()) {
                    button1.setIcon(im1_gray);
                    button2.setIcon(im2_white);
                    button3.setIcon(im3_gray);
                }
                else if (button3.isSelected()) {
                    button1.setIcon(im1_gray);
                    button2.setIcon(im2_gray);
                    button3.setIcon(im3_white);
                }
            }
        };
        MouseListener ml = new MouseListener() {
            JRadioButton rb = new JRadioButton();

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                rb = (JRadioButton)e.getSource();
                if (rb == button1) {
                    button1.setIcon(im1_black);
                }
                else if (rb == button2) {
                    button2.setIcon(im2_black);
                }
                else if (rb == button3) {
                    button3.setIcon(im3_black);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                rb = (JRadioButton)e.getSource();
                if (rb == button1) {
                    button1.setIcon(im1_white);
                    button2.setIcon(im2_gray);
                    button3.setIcon(im3_gray);
                }
                else if (rb == button2) {
                    button1.setIcon(im1_gray);
                    button2.setIcon(im2_white);
                    button3.setIcon(im3_gray);
                }
                else if (rb == button2) {
                    button1.setIcon(im1_gray);
                    button2.setIcon(im2_gray);
                    button3.setIcon(im3_white);
                }
            }


            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
        button1.addMouseListener(ml);
        button2.addMouseListener(ml);
        button3.addMouseListener(ml);
        button1.addItemListener(il);
        button2.addItemListener(il);
        button3.addItemListener(il);
        button1.addActionListener(al_first);
        button2.addActionListener(al_second);
        button3.addActionListener(al_third);

        cardLayout.show(right_panel,"first");
        down_panel.add(right_panel);

//        Main.add(main);
        Main.add(Box.createVerticalStrut(20));
        Main.add(up_panel);
        Main.add(Box.createVerticalStrut(20));
        Main.add(down_panel);
        Main.setMinimumSize(new Dimension(600,700));
        Main.setVisible(true);
        Main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}



//        class Swing_OnValueChanged implements DocumentListener {
//            JTextField init = new JTextField(30);
//            Swing_OnValueChanged(JTextField textField) {
//                this.init = textField;
//            }
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                for ( String[] i: dic_list) {
//                    if (i[0].substring(0,init.getText().length()).equals(init.getText())) {
//                        System.out.println(out(i));
//                    }
//                }
//            }
//            @Override
//            public void changedUpdate(DocumentEvent e) {}
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                for ( String[] i: dic_list) {
//                    if (i[0].substring(0,init.getText().length()).equals(init.getText())) {
//                        System.out.println(out(i));
//                    }
//                }
//            }
//        }

//        JTextField textField = new JTextField(30);