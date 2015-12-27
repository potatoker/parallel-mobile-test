package com.ray.offloading1.Transfer;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

/**
 * Created by pete on 2015/3/21.
 */
public class SendPart2 implements Runnable{
    private String partDir;
    private  String destIP;
    private ExecutorService exec=null;

    public void SendPart2(String _partDir,String _destIp){
        partDir=_partDir;
        destIP=_destIp;
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


    public  void sendFiles(){


    }

    public  void receiveResult(){}

    public void executeResult(){}


    public void run(){

        sendFiles();

        receiveResult();

        executeResult();


    }





}



