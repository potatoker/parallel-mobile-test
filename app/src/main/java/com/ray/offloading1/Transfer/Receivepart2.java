package com.ray.offloading1.Transfer;

import java.net.Socket;

/**
 * Created by pete on 2015/3/21.
 */
public class Receivepart2 implements Runnable {

    Socket socket;

    public  Receivepart2(Socket _socket){
        socket=_socket;

    }


public  void receiveFiles(){}

    public void executeDex(){}

    public void run(){

        receiveFiles();

        executeDex();



    }

}
