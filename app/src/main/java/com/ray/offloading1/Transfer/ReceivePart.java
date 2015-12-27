package com.ray.offloading1.Transfer;


import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.os.Handler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * Created by pete on 2015/3/20.
 */
public class ReceivePart implements Runnable {

    Socket socket;
    private String pName;
    private String dexPath;
    private String configPath;
    private String resultpath;
    private int bufferSize =Constants.BUFFERED_SIZE;
private TextView stv;
    private Handler handler;
    private DataInputStream sdis=null;
    private DataOutputStream sdos=null;
    public ReceivePart(Socket _socket,TextView _stv,Handler handler) {
        stv=_stv;
        socket = _socket;
        pName=null;
        configPath=null;
        dexPath=null;
        this.handler=handler;
        try {
            sdis = new DataInputStream(socket.getInputStream());
            sdos=new DataOutputStream(socket.getOutputStream());
        }catch (Exception e){e.printStackTrace();}
    }



    public String getPostfix(String fileName){


       return fileName.substring(fileName.lastIndexOf(".")+1);
    }


    public void receiveFiles(){
       Log.i("start: ", "new connection " + socket.getInetAddress() + ":" + socket.getPort());

       String partName;
     //  DataInputStream dis = null;
       String saveDir;
       //Long length;
        Long fileSize;
        int length;
       int fileNum;
       String savePath;
       byte[] buf;

        int n;

       try {

           //dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

           //    while(true) {


           partName = sdis.readUTF();
           pName=partName;

           Log.i("read partName", "" + partName);
           saveDir = Constants.RECEIVE_FILE_PATH + partName;
           File f = new File(saveDir);
           if (!f.exists())
               f.mkdirs();

           resultpath=saveDir+"/"+pName+"result.txt";
          // Log.i("resultPath:",resultpath);

           fileNum = sdis.read();
           Log.i("read fileNum:", "" + fileNum);


           for (int i = 0; i < fileNum; i++) {
               buf = new byte[bufferSize];
               String fileName=sdis.readUTF();
               savePath = saveDir +"/"+ fileName;

               String postfix=getPostfix(fileName);
               Log.i("postfix:",postfix);

               if(postfix.equals("dex")){
                   dexPath=savePath;
                   Log.i("dexPath",dexPath);
               }
               if(postfix.equals("config")){
                   configPath=savePath;
                   Log.i("configPath",configPath);
               }

               Log.i("savePath", savePath);
          //     length=sdis.readInt();
               //length = dis.readLong();
               fileSize=sdis.readLong();
            //   Log.i("length",""+length);
                Log.i("fileSize:",""+fileSize);
               DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(savePath)));

             //    int read = 0;
              // long passedlen = 0;


           /*  while ((read = dis.read(buf)) != -1) {
                   if(passedlen==length)
                       break;
                   passedlen += read;
                   dos.write(buf, 0, read);
                   Log.i("processing", "文件[" + savePath + "]已经接收: " + passedlen * 100L / length + "%");
               }

*/

/*                while((read = dis.read(buf)) != -1){


                   if(read==bufferSize) {
                       dos.write(buf, 0, read);
                   }else if(buf.toString()!="end") {
                       dos.write(buf, 0, read);
                   }else {
                       Log.i("end buf:",buf.toString());
                       break;
                   }

               }
*/
              // sdis.readFully(buf, 0, length);

               while(fileSize>0&&(n=sdis.read(buf,0,(int)Math.min(buf.length,fileSize)))!=-1){
                   dos.write(buf,0,n);
                   fileSize -= n;
               }



           //   dos.write(buf,0,length);

               Log.i("completion", "文件: " + savePath + "接收完成!");
               dos.close();
           }




           //    }



       }catch(Exception e){
           e.printStackTrace();
       }
   }

 /*   public String readClassName(String fileAbsoluteDir){
        BufferedReader br=null;
        String className=null;
        try {
            br = new BufferedReader(new FileReader(fileAbsoluteDir));
            className = br.readLine();
            if(br!=null)br.close();
        }catch (Exception e)
        {e.printStackTrace();}


        return className;
   }


    public void writeResultFile(String _result){
        PrintWriter os=null;
        try {
           os=new PrintWriter(new FileWriter(resultpath));
           os.println(_result);


        }catch (Exception e){e.printStackTrace();}
        finally {
            if(os!=null){os.close();}
        }
    }

    public void executeDex(){


        DataOutputStream os = null;
        Process proc=null;

        try{
            proc=Runtime.getRuntime().exec("su");

            Log.i("caution:","su get");

            String cmd1= "chmod 777 "+dexPath;
            Log.i("cdm1",cmd1);

            String className=readClassName(configPath);
            Log.i("className",className);
          //  String cmd= "/system/bin/dalvikvm -cp /data/data/edu.mit.haoqili.file_transfer/test/Hello.dex hello.Hello";
            String cmd="/system/bin/dalvikvm -cp "+dexPath+" "+className;
            Log.i("cmd ",cmd);

            os = new DataOutputStream(proc.getOutputStream());
            os.writeBytes(cmd1 + "\n");
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");

            os.flush();
            proc.waitFor();
            java.io.InputStream is=proc.getInputStream();
            byte b[]=new byte[is.available()];
            is.read(b,0,b.length);
            String result=new String(b);
            Log.i("result",result);
            writeResultFile(result);
        }
        catch (Exception e) {
            Log.i("Read has Exception", "e:" + e);
        }
        finally{
            try {
                if (os != null) {
                    os.close();
                }
                proc.destroy();
            } catch (Exception e) {
            }
        }

    }

*/
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

    public void sendResult(){

        DataInputStream fis=null;
        byte[] buf;
        int read;
        try {

            File f=new File(resultpath);
            sdos.writeUTF(f.getName());
            fis = new DataInputStream(new BufferedInputStream(new FileInputStream(resultpath)));

            buf=new byte[bufferSize];

            while ((read = fis.read(buf)) != -1) {
                sdos.write(buf, 0, read);
            }

            fis.close();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    @Override
    public void run() {

     long start=System.currentTimeMillis();


      receiveFiles();


     // executeDex();

      DexExecutor dx=new DexExecutor(dexPath,configPath,resultpath);
      String r=dx.executeDex();

//        Message message=new Message();
//        message.obj=r;
//        handler.sendMessage(message);

      sendResult();

      closeSocket();


        long p=System.currentTimeMillis()-start;
       Log.i("part execute time:"," "+p);

      //  stv.setText("process time:"+Long.toString(p));
        Message m=new Message();
        m.obj=Long.toString(p);
        handler.sendMessage(m);

        }
    }
