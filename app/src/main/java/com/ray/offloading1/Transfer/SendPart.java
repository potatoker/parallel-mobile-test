package com.ray.offloading1.Transfer;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.*;

/**
 * Created by pete on 2015/3/20.
 */
public class SendPart implements Runnable {

    private int port=Constants.DEFAULT_BIND_PORT;
    private String partDir;
  //  private ExecutorService exec=null;
   // private String destIP;
    private Socket socket=null;
    private int buffSize=Constants.BUFFERED_SIZE;

    private DataOutputStream sdos=null;
    private DataInputStream sdis=null;


    public SendPart(String _partDir,Socket _socket){
        partDir=_partDir;
      //  exec= Executors.newCachedThreadPool();
        socket=_socket;
        try {
            sdos = new DataOutputStream(socket.getOutputStream());
            sdis=new DataInputStream(socket.getInputStream());
        }catch (Exception e){e.printStackTrace();}
    }


    private ArrayList<String> getFileList(String dirPath){
        ArrayList<String> fileList=new ArrayList<String>();
        File dir=new File(dirPath);
        File[] files=dir.listFiles();

        if(files==null){
            return null;
        }

        for(int i = 0; i < files.length; i++){

            fileList.add(files[i].getAbsolutePath());

        }
        return fileList;

    }

    public void sendFiles(){

        ArrayList<String> files=getFileList(partDir);
        String fileAbsoluteDir;
        byte[] buf;
        DataInputStream fis;
     //   DataOutputStream dos;
        int read;
        int passedlen;
        Long length;
        File file;
        File file2;
      //  Socket socket=null;

      /*  try{
            socket=new Socket(destIP,port);


        }catch (Exception e){
            Log.i("connection:", "failed");
        }

*/
        try{
          //  dos = new DataOutputStream(socket.getOutputStream());
            file2=new File(partDir);
            sdos.writeUTF(file2.getName());

            Log.i("part Name:",file2.getName());

            sdos.flush();
            sdos.write(files.size());
            sdos.flush();

            // dos.close();

            for(int i=0;i<files.size();i++){

                // dos=new DataOutputStream(socket.getOutputStream());
                fileAbsoluteDir=files.get(i);
                file=new File(fileAbsoluteDir);
                buf=new byte[buffSize];

                fis = new DataInputStream(new BufferedInputStream(new FileInputStream(fileAbsoluteDir)));

                sdos.writeUTF(file.getName());
                sdos.flush();
                sdos.writeLong(file.length());
                //sdos.writeInt((int)file.length());
                sdos.flush();

                read = 0;
                // passedlen = 0;
                length = file.length();    //获得要发送文件的长度
                while ((read = fis.read(buf)) != -1) {
                    //    passedlen += read;
                    //    Log.i("processing:","已经完成文件 [" + file.getName() + "]百分比: " + passedlen * 100L / length + "%");
                    sdos.write(buf, 0, read);
                }
                // dos.writeUTF("end");
                sdos.flush();
                fis.close();

               // sdos.close();
                Log.i("finish: ",fileAbsoluteDir+"file transmition complite");

            }


        //    sdis.close();
        //    sdos.close();
        //    socket.close();
        }catch (Exception e){e.printStackTrace();}




    }

    public void closeSocket(){
        try {
            if (sdis != null) {
                sdis.close();
            }
            if(sdos!=null){
                sdos.close();
            }
            if(socket!=null){
                socket.close();
            }

        }catch (Exception e){e.printStackTrace();}
    }



    public void receiveResult(){
        DataOutputStream fos=null;
        String resultPath=null;
        int read=0;
        byte[] buf;

        try{

            String fileName=sdis.readUTF();

            Log.i("resultFIle:",fileName);
            resultPath=Constants.RETURNED_RESULT_PATH+fileName;
            fos=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(resultPath)));

            buf=new byte[buffSize];
            while((read=sdis.read(buf))!=-1){
                fos.write(buf,0,read);
            }


            fos.close();

        }catch(Exception e){
            e.printStackTrace();
        }


    }






    @Override
    public void run() {

        sendFiles();

       receiveResult();

       closeSocket();







    }


}
