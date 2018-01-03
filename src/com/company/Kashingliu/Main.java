package com.company.Kashingliu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import static com.company.Kashingliu.Main.dic_list;
import static com.company.Kashingliu.Read_config.for_list;
import static com.company.Kashingliu.Recite_way_2.hashSet;

public class Main  {
    static int right_count = 0;
    static int wrong_count = 0;
    static int all_count = 0;
    static ArrayList<String[]> wrong_answer = new ArrayList<>(2000);
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
    // 列表中用到的图片
    static ImageIcon question = new ImageIcon("/Users/kashingliu/Documents/iRecite/icon/Question.png");
    // 顶上的三个按钮
    static JRadioButton button1 = new JRadioButton(im1_white);
    static JRadioButton button2 = new JRadioButton(im2_gray);
    static JRadioButton button3 = new JRadioButton(im3_gray);

    // 把单词放入Dic_list
    static ArrayList<String[]> dic_list = new ArrayList<>();
    // 搜索结果以表格（JTable）实现，这个是列名
    static Vector<String> name = new Vector<>(2);

    static JPanel up_panel = new JPanel();
    static JPanel down_panel = new JPanel();
    // 把用#分隔的单词和汉语意思分开
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
    public static void main(String[] args) throws Exception {

        Container ct = Main.getContentPane();
        ct.setLayout(new BoxLayout(ct,BoxLayout.Y_AXIS));

        // 上面三个button设置为无边框的
        button1.setBorderPainted(false);
        button2.setBorderPainted(false);
        button3.setBorderPainted(false);

        // 把一些变量的定义都放在了函数外边


        // up_panel是上面的三个按钮所组成的一个panel，全局的
        up_panel.setLayout(new BoxLayout(up_panel,BoxLayout.X_AXIS));
        up_panel.add(Box.createHorizontalStrut(70));
        up_panel.add(button1);
        up_panel.add(Box.createHorizontalStrut(10));
        up_panel.add(button2);
        up_panel.add(Box.createHorizontalStrut(10));
        up_panel.add(button3);
        up_panel.add(Box.createHorizontalStrut(70));

        // 下面的panel，包括左边的选单词那里和右边的查单词、考查方式等等
        down_panel.setLayout(new BoxLayout(down_panel,BoxLayout.X_AXIS));

        // 对第一个功能，下面的表格中添加列名
        name.add("English");
        name.add("中文");

        // 表格内部模型
        DefaultTableModel tableModel = new DefaultTableModel(name,100);

        // 读取外部的字典列表，以键值对方式存
        FileReader all_dictionary_list = new FileReader("Dict/grouplist.txt");
        BufferedReader buffer = new BufferedReader(all_dictionary_list);
        String reader = "";
        // all_list是储存字典列表那个中文和文件名字的
        ArrayList<String[]> all_list = new ArrayList<>(50);
        Map map = new HashMap();
        while (reader != null) {
            reader = buffer.readLine();
            if (reader != null) {
                all_list.add(change_sharp_list(reader));
                map.put(change_sharp_list_key(reader), change_sharp_list_value(reader));
            }
        }
        // left_listModel是存中文名字的，词典的中文名
        DefaultListModel<String> left_listModel = new DefaultListModel<>();
        for (String[] i : all_list) {
            left_listModel.addElement(i[1]);
        }
        // left_list是左边那个列表
        JList<String> left_list = new JList<>(left_listModel);
        // left_panel是左边放list的panel
        JPanel left_panel = new JPanel();
        left_panel.setLayout(new BoxLayout(left_panel, BoxLayout.Y_AXIS));
        JScrollPane list_left = new JScrollPane(left_list);
        left_panel.add(Box.createVerticalStrut(10));
        left_panel.add(list_left);
        left_panel.add(Box.createVerticalStrut(10));
        // 这里是对左边那个列表添加鼠标双击事件
        MouseAdapter DoubleClicked = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JList theList = (JList) e.getSource();
                // 如果点击次数为2，说明选中
                if (e.getClickCount()==2) {
                    // 清空dic_list，hashSet
                    dic_list.clear();
                    hashSet.clear();
                    // 把右面那个单词的table也清空
                    tableModel.setRowCount(0);
                    // 通过双击的位置获取选择的是哪一项
                    int index = theList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        // 获取到了哪一项之后，就读取对应的文件
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
                                    // 既在单词列表中添加，又在表格中添加
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
        // 至此，可以完成通过双击左边的选项来选择想要的字典

        CardLayout cardLayout = new CardLayout();
        JPanel right_panel = new JPanel(cardLayout);

//         放置搜索框和搜索结果的main JPanel
        JPanel main = new JPanel();
        // 搜索框
        java.awt.TextField tf = new java.awt.TextField(12);
        JTable test_table = new JTable(tableModel);
        test_table.setEnabled(false);
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


        // main使用BoxLayout，纵方向
        main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
        // 对于搜索框添加监控器
        tf.addTextListener((e)-> {
                try {
                    // 首先设置表格的行数为0
                    tableModel.setRowCount(0);
                    // 获取搜索框中输入的字符的长度
                    int len = tf.getText().length();
                    // 遍历整个字典，对于字典中的每个字符串数组而言
                    for (String[] i : dic_list) {
                        // 如果说字典英语单词的长度比输入的单词长度要大，并且将英语单词从0截取到输入长度这么多，其和得到的字符串相等时
                        if ((i[0].length()>=len)&&i[0].substring(0, len).equals(tf.getText())) {
                            // 在表格Model中添加处理后的英语单词和中文意思
                            tableModel.addRow(out(i));
                            // 重新设定表格Model
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


        JPanel panel_three = new JPanel();
        setUp_panel_three(panel_three);




        // 关闭时保存
        Main.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    Save.main();
                    Save.usr_dic();
                } catch (Exception e1) {
                }
            }
        });


        // 增加三个cardlayout的panel
        right_panel.add(main,"first");
        right_panel.add(panel_two,"second");
        right_panel.add(panel_three,"third");


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

        


        // 第一张显示考察单词的那个
        cardLayout.show(right_panel,"first");
        down_panel.add(right_panel);


        panel_two();
        Main.add(Box.createVerticalStrut(20));
        Main.add(up_panel);
        Main.add(Box.createVerticalStrut(20));
        Main.add(down_panel);
        Main.setMinimumSize(new Dimension(600,700));
        Main.setVisible(true);
        Main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void setUp_panel_three(JPanel panel_three) throws Exception {
        panel_three.setLayout(new BoxLayout(panel_three,BoxLayout.X_AXIS));
        Read_config.main();
        String[] list_three = new String[3+3*(for_list.size()-1)];
        list_three[0] = "背单词时间";
        list_three[1] = "背单词总数";
        list_three[2] = "上次错误总数";
        for (int i = 3; i< 3+3*(for_list.size()-1); i++) {
            list_three[i] = for_list.get((i-3)/3)[(i-3)%3];
        }
        JList list_third = new JList(list_three);
        list_third.setLayoutOrientation(JList.HORIZONTAL_WRAP);  //设置多行显示
        list_third.setVisibleRowCount(list_three.length/3);    //设置行数

        panel_three.add(list_third);
    }


    static JPanel panel_two = new JPanel();
    private static DefaultListModel<String> defaultListModel = new DefaultListModel<>();

    static MouseAdapter choose = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            JList init_list = (JList)e.getSource();
            if (e.getClickCount()==2) {
                int index = init_list.locationToIndex(e.getPoint());
                if (index==0) {
                    if (dic_list.size() == 0) {
                        JOptionPane.showMessageDialog(panel_two,"请在左侧选择词典");
                    } else {
                        Recite_way_2 recite_way_2 = new Recite_way_2();
                        recite_way_2.main();
                    }
                } else if (index == 1) {
                    if (dic_list.size() == 0) {
                        JOptionPane.showMessageDialog(panel_two,"请在左侧选择词典");
                    } else {
                        Recite_way_3 recite_way_3 = new Recite_way_3();
                        recite_way_3.main();
                    }
                } else if (index == 2) {
                    if (dic_list.size() == 0) {
                        JOptionPane.showMessageDialog(panel_two,"请在左侧选择词典");
                    } else {
                        Recite_way_4 recite_way_4 = new Recite_way_4();
                        recite_way_4.main();
                    }
                }
            }
        }
    };

    public static void panel_two() throws IOException {

        JList<String> list = new JList<>(defaultListModel);
        list.setFixedCellHeight(100);
        list.setFixedCellWidth(400);
        list.setVisibleRowCount(6);
        defaultListModel.addElement("复习模式");
        defaultListModel.addElement("填空模式");
        defaultListModel.addElement("选择模式");
        list.addMouseListener(choose);
        // 传递的参数，为自己加入的icon
        Icon[] new_icons = new Icon[3];
        new_icons[0] = question;
        new_icons[1] = question;
        new_icons[2] = question;
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
        setFont(new Font("Helvetica",Font.PLAIN,30));



//        setMinimumSize(new Dimension(400,200));
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
//        setFont(list.getFont());
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



// 考查方式：单项选择
// 英文为题目，中文为选项