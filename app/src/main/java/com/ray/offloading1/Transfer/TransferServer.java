package com.ray.offloading1.Transfer;

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Created by pete on 2015/3/19.
 */
public class TransferServer {
    private int port = Constants.DEFAULT_BIND_PORT;
private TextView stv;
    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private final int POOL_SIZE = 4;
private Handler handler;

    public TransferServer(TextView stv,Handler handler) throws Exception{
        int threadNum=Runtime.getRuntime().availableProcessors() * POOL_SIZE;
        executorService = Executors.newFixedThreadPool(threadNum);
        Log.i("num of threads:",String.valueOf(threadNum));

        try {


            serverSocket = new ServerSocket(port);


             } catch (Exception e) {
            e.printStackTrace();
            Log.i("portProblem:",""+port);
        }
this.stv=stv;
        this.handler=handler;
    }


    public void serviceStart(){
        Socket socket=null;

        while (true){
            try{
                socket=serverSocket.accept();
                
                executorService.execute(new ReceivePart(socket,stv,handler));
              //  executorService.execute(new ReceiveFile(socket));
                //executorService.execute(new Receivepart2());
            }catch (Exception e){
                e.printStackTrace();
           }
        }
    }


}







