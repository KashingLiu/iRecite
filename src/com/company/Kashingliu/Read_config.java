package com.company.Kashingliu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import static com.company.Kashingliu.Main.usr_email;

public class Read_config {
    static String time = "";
    static String all_count;
    static String wrong_count;
    static ArrayList<String[]> for_list = new ArrayList<>(200);
    public static void main() throws Exception {
        File a = new File(usr_email+".txt");
        if (!a.exists()) {
            a.createNewFile();
        }
        FileReader reader = new FileReader(usr_email+".txt");
        BufferedReader buffer = new BufferedReader(reader);
        int i = 0;
        while (time != null) {
            time = buffer.readLine();
            all_count = buffer.readLine();
            wrong_count = buffer.readLine();
            String[] init = {time, all_count, wrong_count};
            for_list.add(init);
            i++;
        }
        buffer.close();
        reader.close();
    }
}
