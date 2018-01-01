package com.company.Kashingliu;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import static com.company.Kashingliu.Main.dic_list;
import static com.company.Kashingliu.Recite_way_2.hashSet;
import static com.company.Kashingliu.Recite_way_2.iterator;
import static com.company.Kashingliu.Recite_way_2.random;

public class Main  {
    // 最大的frame，设置成boxlayout，横向的
    static JFrame Main = new JFrame("天天背单词");

    // 第一个图标，单词搜索
    static ImageIcon im1_white = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/search3.png");
    static ImageIcon im1_gray = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/search1.png");
    static ImageIcon im1_black = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/search2.png");
    // 第二个图标，背单词，图书图标
    static ImageIcon im2_gray = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/tsg1.png");
    static ImageIcon im2_white = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/tsg3.png");
    static ImageIcon im2_black = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/tsg2.png");
    // 第三个图标，历史回顾
    static ImageIcon im3_gray = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/back1.png");
    static ImageIcon im3_white = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/back3.png");
    static ImageIcon im3_black = new ImageIcon("/Users/kashingliu/Downloads/tsg/icon/back2.png");

    static JRadioButton button1 = new JRadioButton(im1_white);
    static JRadioButton button2 = new JRadioButton(im2_gray);
    static JRadioButton button3 = new JRadioButton(im3_gray);
    static ButtonGroup group = new ButtonGroup();

    // 把单词放入Dic_list
    static ArrayList<String[]> dic_list = new ArrayList<>();
    // 搜索结果以表格（JTable）实现，这个是列名
    static Vector<String> name = new Vector<>(2);

    static JPanel up_panel = new JPanel();
    static JPanel down_panel = new JPanel();
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


        Container ct = Main.getContentPane();
        ct.setLayout(new BoxLayout(ct,BoxLayout.Y_AXIS));

        button1.setBorderPainted(false);
        button2.setBorderPainted(false);
        button3.setBorderPainted(false);
        group.add(button1);
        group.add(button2);
        group.add(button3);


// 下一步是单词的考查，准备模仿provoc的来做，有多重考察方式
            //用户输入单词，系统判断是否正确

        // 把一些变量的定义都放在了函数外边



        up_panel.setLayout(new BoxLayout(up_panel,BoxLayout.X_AXIS));
        up_panel.add(Box.createHorizontalStrut(70));
        up_panel.add(button1);
        up_panel.add(Box.createHorizontalStrut(10));
        up_panel.add(button2);
        up_panel.add(Box.createHorizontalStrut(10));
        up_panel.add(button3);
        up_panel.add(Box.createHorizontalStrut(70));


        down_panel.setLayout(new BoxLayout(down_panel,BoxLayout.X_AXIS));


        // 对第一个功能，下面的表格中添加列名
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
                    hashSet.clear();
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



//        try {
//            System.out.println(dic_list.get(0)[0]);
//        } catch (IndexOutOfBoundsException e) {
//            System.out.println(e.getCause().toString());
//            // 请在左侧选择词典
//        }
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
//        读文件的
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
        main.add(table);
        main.add(Box.createVerticalStrut(6));


        down_panel.add(Box.createHorizontalStrut(5));
        down_panel.add(left_panel);
        down_panel.add(Box.createHorizontalStrut(5));

        JPanel second = new JPanel();
        second.setLayout(new BoxLayout(second,BoxLayout.X_AXIS));
        second.add(new JButton("test"));
        JPanel third = new JPanel();
        third.setLayout(new BoxLayout(third,BoxLayout.X_AXIS));

        JButton thir = new JButton("test");
        thir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dic_list.size() == 0) {
                    JOptionPane.showMessageDialog(third,"请在左侧选择词典");
                } else {
                    Recite_way_2 reciteWay2 = new Recite_way_2();
                    reciteWay2.main();
                }
            }
        });
        third.add(thir);


        JButton forth = new JButton("test");
        forth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dic_list.size() == 0) {
                    JOptionPane.showMessageDialog(third,"请在左侧选择词典");
                } else {
                    Recite_way_3 reciteWay3 = new Recite_way_3();
//                    reciteWay1.aaa = 0;
                    reciteWay3.main();
                }
            }
        });


        JButton fifth = new JButton("test");
        fifth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dic_list.size() == 0) {
                    JOptionPane.showMessageDialog(third,"请在左侧选择词典");
                } else {
                    Recite_way_4 reciteWay4 = new Recite_way_4();
//                    reciteWay1.aaa = 0;
                    reciteWay4.main();
                }
            }
        });


        third.add(thir);
        third.add(forth);
        third.add(fifth);


        // 增加三个cardlayout的panel
        right_panel.add(main,"first");
        right_panel.add(panel_two,"second");
        right_panel.add(third,"third");


        // 这些是给那三个按钮添加监听器
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
                else if (rb == button3) {
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
        panel_two();
        Main.add(Box.createVerticalStrut(20));
        Main.add(up_panel);
        Main.add(Box.createVerticalStrut(20));
        Main.add(down_panel);
        Main.setMinimumSize(new Dimension(600,700));
        Main.setVisible(true);
        Main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    static JPanel panel_two = new JPanel();
    private static DefaultListModel<String> defaultListModel = new DefaultListModel<>();
//    static


    public static void panel_two() throws IOException {
        JList<String> list = new JList<>(defaultListModel);
        defaultListModel.addElement("1");
        defaultListModel.addElement("2");
        defaultListModel.addElement("  ");
        defaultListModel.addElement("  ");
        defaultListModel.addElement("  ");
//        defaultListModel.addElement("");


        Icon[] icons = { im1_gray, im1_white, im1_black };
        // 传递的参数，为自己加入的icon
        Icon[] new_icons = new Icon[5];
        new_icons[0] = im1_black;
        list.setCellRenderer(new MyCellRenderer(new_icons));


        // 把用户选择的项目所对应的单词文件读到程序中
        panel_two.setLayout(new BoxLayout(panel_two,BoxLayout.X_AXIS));
        panel_two.add(list);
    }
}

class MyCellRenderer extends JLabel implements ListCellRenderer {
    Icon[] icons;
    public MyCellRenderer(Icon[] icons) {
        this.icons=icons;
    }
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        String s = value.toString();
        setText(s);


        // 这个是设置边框得找找文档!!
        setBorder(BorderFactory.createEmptyBorder());//加入宽度为5的空白边框

        // 选中后的处理，选中后颜色是蓝色，未选中是为灰白相间
        if (isSelected && index%2 == 0) {
            setBackground(new Color(22,116,233));
        } else if (isSelected && index%2 != 0) {
            setBackground(new Color(22,116,233));
        } else if (!isSelected && index%2 == 0) {
            setBackground(Color.lightGray);
        } else if (!isSelected && index%2 != 0) {
            setBackground(Color.WHITE);
        }
//        if (icons.length <= index) {
            setIcon(icons[index]);//设置图片
//        }
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        return this;
    }

}

class CellRenderer extends JLabel implements ListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        return this;
    }
}
//
//class MyKey extends KeyAdapter {
//    private int init_flag;
//    MyKey(int flag) {
//        init_flag = flag;
//    }
//    int return_flag() {
//        return init_flag;
//    }
//    @Override
//    public void keyPressed(KeyEvent e) {
//        super.keyPressed(e);
//    }
//
//    @Override
//    public void keyTyped(KeyEvent e) {
//        super.keyTyped(e);
//        if (e.getKeyChar()==KeyEvent.VK_SPACE) {
//            init_flag++;
//        }
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) {
//        super.keyReleased(e);
//    }
//}
//class MyKey2 extends KeyAdapter {
//    private int init_flag;
//    MyKey2(int flag) {
//        init_flag = flag;
//    }
//    int return_flag() {
//        return init_flag;
//    }
//    @Override
//    public void keyReleased(KeyEvent e) {
//        super.keyReleased(e);
//    }
//    @Override
//    public void keyTyped(KeyEvent e) {
//        super.keyTyped(e);
//        if (e.getKeyChar()==KeyEvent.VK_ESCAPE) {
//            a.dispose();
//        }
//        if (e.getKeyChar()==KeyEvent.VK_SPACE) {
//            chinese_panel.setVisible(true);
//            chinese_word.addKeyListener(new MyKey(flag));
//        }
//    }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//        super.keyPressed(e);
//    }
//}

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
//        aaa = 0;
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


//        KeyAdapter listen_chinese = new KeyAdapter() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                super.keyTyped(e);
//                english_word.setText(dic_list.get((Integer)iterator.next())[0]);
//
//            }
//        };
//        KeyAdapter listen_english = new KeyAdapter() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                super.keyTyped(e);
//                if (e.getKeyChar()==KeyEvent.VK_ESCAPE) {
//                    a.dispose();
//                }
//                if (e.getKeyChar()==KeyEvent.VK_SPACE) {
//                    chinese_panel.setVisible(true);
//                    chinese_word.addKeyListener();
//                }
//            }
//        };



        a.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        KeyAdapter key = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
//                int cnt;
                if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    a.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    a.dispose();
                    System.out.println(aaa);
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

class Recite_way_2 {
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
        System.out.println(result.get(0));
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

class Recite_way_3 {
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

// 考查方式：单项选择
// 英文为题目，中文为选项
class Recite_way_4 {

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
        JFrame main = new JFrame();
        Container ct = main.getContentPane();
        ct.setLayout(new BoxLayout(ct,BoxLayout.Y_AXIS));

        JPanel question = new JPanel();
        question.setLayout(new BoxLayout(question,BoxLayout.X_AXIS));

        JPanel answer = new JPanel();
        answer.setLayout(new BoxLayout(answer,BoxLayout.X_AXIS));

        JPanel button = new JPanel();
        button.setLayout(new BoxLayout(button,BoxLayout.X_AXIS));

        // 这是四个选项
        JButton choice1 = new JButton("0");
        JButton choice2 = new JButton("1");
        JButton choice3 = new JButton("2");
        JButton choice4 = new JButton("3");
        // 由四个选项构成的数组
        ArrayList<JButton> choices = new ArrayList<>(4);
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
        Iterator choice_iterator = result.iterator();
        // 迭代器，对选出来的单词所构成的String数组进行迭代
        iterator = recite_list.iterator();

        // 选出来一个单词，记为数组selected
        String[] selected = (String[])iterator.next();

        // 记录挑出来那个单词在字典中的位置
        int right_choice_int = (int)choice_iterator.next();
        System.out.println(selected[1]);
        System.out.println(right_choice_int);

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

        ArrayList<Integer> other_choice = new ArrayList<>(other_choices);
        other_choices.remove(right_choice_int);
        System.out.println(other_choices);

        // 之前不知道哪个是正确的选项，所以记录正确选项
        int right_answer_index = random.nextInt(4);
        choices.get(right_answer_index).setText(selected[1]);
        int i = 0;
        for (JButton c:choices) {
            if (c.getText().equals(Integer.toString(right_answer_index))) {
                System.out.println("in");
                continue;

            } else {
                c.setText(dic_list.get(other_choice.get(i))[1]);
//                System.out.println("out");
                i++;
            }
        }

        JLabel chinese = new JLabel("chinese");
        chinese.setText(selected[0]);





        main.add(choice1);
        main.add(choice2);
        main.add(choice3);
        main.add(choice4);

        main.setMinimumSize(new Dimension(300,200));
        main.setVisible(true);

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
