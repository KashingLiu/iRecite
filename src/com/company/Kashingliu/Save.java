package com.company.Kashingliu;

import com.company.Kashingliu.ClientSend;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

public class Save {
    public static void main(String args[]) throws Exception{
        FileWriter fw = new FileWriter("a.txt");
        fw.write("1211212");
        fw.close();
        ClientSend.main();
    }
}
