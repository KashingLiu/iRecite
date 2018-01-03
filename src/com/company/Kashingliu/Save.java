package com.company.Kashingliu;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.company.Kashingliu.Main.all_count;
import static com.company.Kashingliu.Main.wrong_answer;
import static com.company.Kashingliu.Main.wrong_count;

public class Save {
    static JList list;

    public static void TestJList(){
        JFrame maim = new JFrame();
        //文件数量：fileName.length / 3
        String[] fileName = new String[]{"", "", "","", "", "","", "", "","", "", ""};
        DefaultListModel<String[]> a = new DefaultListModel<>();
        list = new JList(fileName);
        a.addElement(fileName);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);  //设置多行显示
        list.setVisibleRowCount(fileName.length/3);    //设置行数
        maim.setLayout(new FlowLayout());
        maim.add(list);
        maim.setSize(300, 300);
        maim.setVisible(true);
    }
    public static void main() throws Exception{
        Date now = new Date();
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = format0.format(now.getTime());
        FileWriter fw = new FileWriter("config.txt",true);
        fw.write(time+"\n");
        fw.write(Integer.toString(all_count)+"\n");
        fw.write(Integer.toString(wrong_count)+"\n");
        fw.close();
    }

    public static void usr_dic() throws Exception {
        Date now = new Date();
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = format0.format(now.getTime());
        FileWriter fw = new FileWriter("Dict/usr_dic.txt",true);
        for (String[] i :wrong_answer) {
            fw.write(i[0]+"#"+i[1]+"\n");
        }
        FileWriter usr = new FileWriter("usr_dic.txt",true);
        usr.write(time+"\n");
        for (String[] i :wrong_answer) {
            usr.write(i[0]+"#"+i[1]+"\n");
        }
        fw.close();
        usr.close();
    }
}
