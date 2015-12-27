package com.ray.offloading1.Transfer;

import android.util.Log;

import java.io.*;
import java.net.Socket;

/**
 * Created by pete on 2015/3/19.
 */
public class SendFile implements Runnable {
    private Socket socket;
    private String fileAbsoluteDir;
    private int buffSize=Constants.BUFFERED_SIZE;


    public SendFile(Socket _socket,String _fileAbsoluteDir){
        socket=_socket;
        fileAbsoluteDir=_fileAbsoluteDir;
    }

    @Override
    public void run() {
        File file=new File(fileAbsoluteDir);
        byte[] buf=new byte[buffSize];
        try {
            DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(fileAbsoluteDir)));
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            String partDir=file.getParent();
            Log.i("partDir",partDir);
            File f=new File(partDir);
            dos.writeUTF(f.getName());
            dos.flush();
            dos.writeUTF(file.getName());
            dos.flush();
            dos.writeLong(file.length());
            dos.flush();

            int read = 0;
            int passedlen = 0;
            long length = file.length();    //获得要发送文件的长度
            while ((read = fis.read(buf)) != -1) {
                passedlen += read;
                Log.i("processing:","已经完成文件 [" + file.getName() + "]百分比: " + passedlen * 100L / length + "%");
                dos.write(buf, 0, read);
            }
            dos.flush();
            fis.close();
            dos.close();
            socket.close();
            Log.i("finish: ",fileAbsoluteDir+"file transmition complite");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
