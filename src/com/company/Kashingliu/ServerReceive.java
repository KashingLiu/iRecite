package com.company.Kashingliu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * 文件名：ServerReceive.java
 * 实现功能：作为服务器接收客户端发送的文件
 *
 * 具体实现过程：
 * 1、建立SocketServer，等待客户端的连接
 * 2、当有客户端连接的时候，按照双方的约定，这时要读取一行数据
 *      其中保存客户端要发送的文件名和文件大小信息
 * 3、根据文件名在本地创建文件，并建立好流通信
 * 4、循环接收数据包，将数据包写入文件
 * 5、当接收数据的长度等于提前文件发过来的文件长度，即表示文件接收完毕，关闭文件
 * 6、文件接收工作结束
 *
 *
 * 【注：此代码仅为演示客户端与服务器传送文件使用，
 *      每一个数据包之前没有文件协议命令
 *      具体的协议传输和文件传出的使用阶段可根据自己程序自行放置】
 *
 *
 * 作者：小菜鸟
 * 创建时间：2014-08-19
 *
 * */




public class ServerReceive {
    /**与服务器建立连接的通信句柄*/
    static ServerSocket ss = null;
    static Socket s = null;

    /**定义用于在接收后在本地创建的文件对象和文件输出流对象*/
    static File file = null;
    static FileOutputStream fos = null;

    /**定义输入流，使用socket的inputStream对数据包进行输入*/
    static InputStream is = null;

    /**定义byte数组来作为数据包的存储数据包*/
    static byte[] buffer = new byte[4096 * 5];

    /**用来接收文件发送请求的字符串*/
    static String comm = null;

    public static void receive() {
        /**读取一行客户端发送过来的约定信息*/
        try {
            InputStreamReader isr = new InputStreamReader(s.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            comm = br.readLine();
        } catch (IOException e) {
            System.out.println("服务器与客户端断开连接");
        }

        /**开始解析客户端发送过来的请求命令*/
        int index = comm.indexOf("/#");

        /**判断协议是否为发送文件的协议*/
        String xieyi = comm.substring(0, index);
        if(!xieyi.equals("111")){
            System.out.println("服务器收到的协议码不正确");
            return;
        }

        /**解析出文件的名字和大小*/
        comm = comm.substring(index + 2);
        index = comm.indexOf("/#");
        String filename = comm.substring(0, index).trim();
        String filesize = comm.substring(index + 2).trim();


        /**创建空文件，用来进行接收文件*/
        file = new File(filename);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("服务器端创建文件失败");
            }
        }else{
            /**在此也可以询问是否覆盖*/
            System.out.println("本路径已存在相同文件，进行覆盖");
        }

        /**【以上就是客户端代码中写到的服务器的准备部分】*/


        /**
         * 服务器接收文件的关键代码*/
        try {
            /**将文件包装到文件输出流对象中*/
            fos = new FileOutputStream(file);
            long file_size = Long.parseLong(filesize);
            is = s.getInputStream();
            /**size为每次接收数据包的长度*/
            int size = 0;
            /**count用来记录已接收到文件的长度*/
            long count = 0;

            /**使用while循环接收数据包*/
            while(count < file_size){
                /**从输入流中读取一个数据包*/
                size = is.read(buffer);

                /**将刚刚读取的数据包写到本地文件中去*/
                fos.write(buffer, 0, size);
                fos.flush();

                /**将已接收到文件的长度+size*/
                count += size;
                System.out.println("服务器端接收到数据包，大小为" + size);
            }

        } catch (FileNotFoundException e) {
            System.out.println("服务器写文件失败");
        } catch (IOException e) {
            System.out.println("服务器：客户端断开连接");
        }finally{
            /**
             * 将打开的文件关闭
             * 如有需要，也可以在此关闭socket连接
             * */
            try {
                if(fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }//catch (IOException e)
        }//finally
    }
    public static void main(String[] args) {

        /**建立socekt通信，等待服务器进行连接*/
        try {
            while (true) {
                ss = new ServerSocket(6244);
                s = ss.accept();
                receive();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }//public static void main(String[] args)
}//public class ServerReceive
