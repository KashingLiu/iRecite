package com.company.Kashingliu;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerReceive implements Runnable {

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        try {
            final ServerSocket server = new ServerSocket(6244);
            Thread th = new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        try {
                            System.out.println("开始监听。。。");
                            Socket socket = server.accept();
                            System.out.println("有链接");
                            receiveFile(socket);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

            });
            th.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void receiveFile(Socket socket) throws IOException {
        byte[] inputByte = null;
        int length = 0;
        DataInputStream din = null;
        FileOutputStream fout = null;
        try {
            din = new DataInputStream(socket.getInputStream());

            fout = new FileOutputStream(new File("/root/"+din.readUTF()));
//            Runtime.getRuntime().exec("touch "+fine + ".sh");
//            Runtime.getRuntime().exec("chmod 777 " + fine+".sh");
//            "echo "+"#!/bin/sh file=\"$(basename $0)\" echo \"${file%.*}\" file_name=${file%.*} while true do OLD_SIZE=`stat -c %s $file_name\".txt\"` sleep 2 NEW_SIZE=`stat -c %s $file_name\".txt\"` echo $file_name\".txt\" if [[ \"$OLD_SIZE\" != \"$NEW_SIZE\" ]] then : touch $filename\"cron\" echo \"* * * * * /usr/java/jdk1.8.0_151/bin/java -cp :/root/javax.mail.jar Email_a $finename 2>> /root/a.out\" > $filename\"cron\" crontab /root/$filename\"cron\" echo \"ok\" else : echo \"文件大小没有变化，在这里执行cp\" fi done\"");
            Runtime.getRuntime().exec("echo '#!/bin/sh' >> aa.sh");
            Runtime.getRuntime().exec("echo 'file=\"$(basename $0)\" >> aa.sh");
            Runtime.getRuntime().exec("echo '#!/bin/sh' >> aa.sh");
            inputByte = new byte[1024];
            System.out.println("开始接收数据...");
            while (true) {

                if (din != null) {
                    length = din.read(inputByte, 0, inputByte.length);
                }
                if (length == -1) {
                    break;
                }
                System.out.println(length);
                fout.write(inputByte, 0, length);
                fout.flush();
            }
            System.out.println("完成接收");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
//            Runtime.getRuntime.exec("     echo '文件大小没有变化，在这里执行cp'"+">>"+fine+".sh");
            if (fout != null)
                fout.close();
            if (din != null)
                din.close();
            if (socket != null)
                socket.close();

            //* * * * * /usr/java/jdk1.8.0_151/bin/java -cp :/root/javax.mail.jar Email_a 624453893@qq.com 2>> /root/a.out
        }
    }

}