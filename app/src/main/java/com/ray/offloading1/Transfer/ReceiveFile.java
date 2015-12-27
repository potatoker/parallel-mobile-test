package com.ray.offloading1.Transfer;

import android.util.Log;

import java.io.*;
import java.net.Socket;

/**
 * Created by pete on 2015/3/19.
 */
public class ReceiveFile implements Runnable {
    private Socket socket;

    public ReceiveFile(Socket _socket){
        socket=_socket;
    }

    @Override
    public void run() {
        Log.i("start: ","new connection "+socket.getInetAddress()+":"+socket.getPort());
        DataInputStream dis = null;
        DataOutputStream dos = null;

        int bufferSize = 8192;
        byte[] buf = new byte[bufferSize];

        try {
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            String partName=dis.readUTF();
            Log.i("part Name",partName);

            String saveDir = Constants.RECEIVE_FILE_PATH + partName+"/";

            Log.i("saveDir",saveDir);
            File f = new File(saveDir);
            if (!f.exists())
                f.mkdir();

            String savePath =saveDir+dis.readUTF();


            long length = dis.readLong();
            dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(savePath)));

            int read = 0;
            long passedlen = 0;
            while ((read = dis.read(buf)) != -1) {
                passedlen += read;
                dos.write(buf, 0, read);
                Log.i("processing","文件[" + savePath + "]已经接收: " + passedlen * 100L/ length + "%");
            }
            Log.i("completion","文件: " + savePath + "接收完成!");

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("error:","接收文件失败!");
        }finally{
            try {
                if(dos != null){
                    dos.close();
                }
                if(dis != null){
                    dis.close();
                }
                if(socket != null){
                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }





}





