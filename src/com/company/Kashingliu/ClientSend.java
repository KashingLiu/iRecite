package com.company.Kashingliu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;


/**
 *
 * 文件名：ClientSend.java
 * 实现功能：作为客户端向服务器发送一个文件
 *
 * 具体实现过程：
 * 1、建立与服务器端的连接，IP：127.0.0.1， port：4004
 * 2、将文件的名字和大小通过自定义的文件传输协议，发送到服务器
 * 3、循环读取本地文件，将文件打包发送到数据输出流中
 * 4、关闭文件，结束传输
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

public class ClientSend {

    public static void main() {

        /**与服务器建立连接的通信句柄*/
        Socket s = null;

        /**定义文件对象，即为要发送的文件
         * 如果使用绝对路径，不要忘记使用'/'和'\'的区别
         * 具体区别，请读者自行查询
         * */
        File sendfile = new File("config.txt");
        /**定义文件输入流，用来打开、读取即将要发送的文件*/
        FileInputStream fis = null;
        /**定义byte数组来作为数据包的存储数据包*/
        byte[] buffer = new byte[4096 * 5];

        /**定义输出流，使用socket的outputStream对数据包进行输出*/
        OutputStream os = null;

        /**检查要发送的文件是否存在*/
        if(!sendfile.exists()){
            System.out.println("客户端：要发送的文件不存在");
            return;
        }

        /**与服务器建立连接*/
        try {
            s = new Socket("39.106.187.98", 6244);
        }catch (IOException e) {
            System.out.println("未连接到服务器");
        }

        /**用文件对象初始化fis对象
         * 以便于可以提取出文件的大小
         * */
        try {
            fis = new FileInputStream(sendfile);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }


        /**首先先向服务器发送关于文件的信息，以便于服务器进行接收的相关准备工作
         * 具体的准备工作，请查看服务器代码。
         *
         * 发送的内容包括：发送文件协议码（此处为111）/#文件名（带后缀名）/#文件大小
         * */
        try {
            PrintStream ps = new PrintStream(s.getOutputStream());
            ps.println("111/#" + sendfile.getName() + "/#" + fis.available());
            ps.flush();
        } catch (IOException e) {
            System.out.println("服务器连接中断");
        }


        /**
         * 此处睡眠2s，等待服务器把相关的工作准备好
         * 也是为了保证网络的延迟
         * 读者可自行选择添加此代码
         * */
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }



        /**之前的准备工作结束之后
         * 下面就是文件传输的关键代码
         * */
        try {

            /**获取socket的OutputStream，以便向其中写入数据包*/
            os = s.getOutputStream();

            /** size 用来记录每次读取文件的大小*/
            int size = 0;

            /**使用while循环读取文件，直到文件读取结束*/
            while((size = fis.read(buffer)) != -1){
                System.out.println("客户端发送数据包，大小为" + size);
                /**向输出流中写入刚刚读到的数据包*/
                os.write(buffer, 0, size);
                /**刷新一下*/
                os.flush();
            }
        } catch (FileNotFoundException e) {
            System.out.println("客户端读取文件出错");
        } catch (IOException e) {
            System.out.println("客户端输出文件出错");
        }finally{

            /**
             * 将打开的文件关闭
             * 如有需要，也可以在此关闭socket连接
             * */
            try {
                if(fis != null)
                    fis.close();
            } catch (IOException e) {
                System.out.println("客户端文件关闭出错");
            }//catch (IOException e)
        }//finally

    }//public static void main(String[] args)
}//public class ClientSend
