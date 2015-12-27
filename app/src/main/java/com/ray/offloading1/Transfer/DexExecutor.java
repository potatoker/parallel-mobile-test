package com.ray.offloading1.Transfer;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Created by pete on 2015/3/22.
 */
public class DexExecutor {

    private String dexPath;
    private String configPath;
    private String resultPath;


    public DexExecutor(String _dexPath,String _configPath,String _resultPath){
        dexPath=_dexPath;
        configPath=_configPath;
        resultPath=_resultPath;

    }

    public void writeResultFile(String _result){
        PrintWriter os=null;
        try {
            os=new PrintWriter(new FileWriter(resultPath));
            os.println(_result);


        }catch (Exception e){e.printStackTrace();}
        finally {
            if(os!=null){os.close();}
        }
    }


    public String readClassName(String fileAbsoluteDir){
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




    public String executeDex(){
        String result=new String("0");
        DataOutputStream os = null;
        Process proc=null;

        try{
            proc=Runtime.getRuntime().exec("su");

            Log.i("caution:", "su get");

            String cmd1= "chmod 777 "+dexPath;
            Log.i("cdm1",cmd1);

            String className=readClassName(configPath);
            Log.i("className",className);
            //  String cmd= "/system/bin/dalvikvm -cp /data/data/edu.mit.haoqili.file_transfer/test/Hello.dex hello.Hello";
//            File dir=new File(dexPath);
//            String cdir=dir.getParent();
//
//            String cmd2="cd "+cdir;
//            Log.i("cmd2",cmd2);

            String cmd3="/system/bin/dalvikvm -cp "+dexPath+" "+className;
            Log.i("cmd3 ",cmd3);

            os = new DataOutputStream(proc.getOutputStream());
            os.writeBytes(cmd1 + "\n");
          //  os.writeBytes(cmd2+"\n");
            os.writeBytes(cmd3 + "\n");
            os.writeBytes("exit\n");

            os.flush();
            proc.waitFor();
            java.io.InputStream is=proc.getInputStream();
            byte b[]=new byte[is.available()];
            is.read(b,0,b.length);
            result=new String(b);
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

        return result;
    }


}
