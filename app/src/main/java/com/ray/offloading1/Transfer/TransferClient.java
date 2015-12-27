package com.ray.offloading1.Transfer;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.ray.offloading1.IPContacts.DatabaseHandler;
import com.ray.offloading1.IPContacts.serverHelper;
import com.ray.offloading1.MainActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by pete on 2015/3/19.
 */
public class TransferClient {

    private Handler handler;
    private static ArrayList<File> partFile=new ArrayList<File>();
    private static ArrayList<serverHelper> serList;

    private static ArrayList<String> partList = new ArrayList<String>();
    private static ArrayList<String> serverIPs=new ArrayList<String>();
    private int port=Constants.DEFAULT_BIND_PORT;


    private Context context;
private TextView tv;
    public TransferClient(String projectDir,Context context,TextView _tv,Handler handler){
            getPartList(projectDir);
            getServers(context);
            tv=_tv;
        this.handler=handler;
    }

    public void getPartList(String projectDir){
        File dir=new File(projectDir);
        File[] files=dir.listFiles();

        for(int i=0;i<files.length;i++){
            if(files[i].isDirectory())
              //  partList.add(files[i].getAbsolutePath());
                partFile.add(files[i]);
            }

//        Collections.sort(partFile,new partComparator());

        for(int i=0;i<partFile.size();i++)
            partList.add(partFile.get(i).getAbsolutePath());


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




public void getServers(Context context){
    DatabaseHandler dbh=new DatabaseHandler(context);
    try{
        dbh.open();
        serList=dbh.getAllHelpers();
    }catch(Exception e){
        e.printStackTrace();
    }

    Collections.sort(serList,new LevelComparator());


    for(int i=0;i<serList.size();i++){
        serverIPs.add(serList.get(i).getIP());
    }

}




    /*

    public void TransferStart(){
        ExecutorService exec= Executors.newCachedThreadPool();

        if(partList.size()>serverIPs.size()){
            Log.i("error:","partitions are too much");
            return;
        }
        for(int i=0;i<partList.size();i++){

            ArrayList<String> files=getFileList(partList.get(i));
            Log.i("transfer part:",""+partList.get(i));

            for(int j=0;j<files.size();j++){
                String fileAbsoluteDir=files.get(j);
                Socket socket=null;
                try{
                    socket=new Socket(serverIPs.get(i),port);


                }catch (Exception e){
                    Log.i("connection:","failed");
                }

                exec.execute(new SendFile(socket,fileAbsoluteDir));
            }

        }
    }

*/

       public void sumResult(){



          DexExecutor dx=new DexExecutor(Constants.SUMDEX_FILE_PATH,Constants.SUMCONFIG_FILE_PATH,Constants.SHOW_RESULT_FILE_PATH);
          String r=dx.executeDex();

//           Message m=new Message();
//           m.obj=r;
//           handler.sendMessage(m);
       }



   public void TransferStart(){

       long start=System.currentTimeMillis();


        ExecutorService exec= Executors.newCachedThreadPool();

        if(partList.size()>serverIPs.size()){
            Log.i("error:","partitions are too much");
            return;
        }


        for(int i=0;i<partList.size();i++){
           try{
               Log.i("IP",serverIPs.get(i));
               Socket socket=new Socket(serverIPs.get(i),port);

               if(socket!=null){
                   Log.i("connection","success");
                   exec.execute(new SendPart(partList.get(i),socket));
               }


           }catch(Exception e){e.printStackTrace();}
          // exec.execute(new SendPart(partList.get(i),serverIPs.get(i)));

            }

       exec.shutdown();
       while(!exec.isTerminated());



        sumResult();


       long p=System.currentTimeMillis()-start;
        Log.i("process time",""+p);
        //  tv.setText("process time"+Long.toString(p));
Message mm=new Message();
       mm.obj=Long.toString(p);
       handler.sendMessage(mm);

        }



}















