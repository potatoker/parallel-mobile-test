package com.ray.offloading1.Transfer;

import android.os.Environment;

/**
 * Created by pete on 2015/3/19.
 */
public interface Constants {

 //  public  String externalStorage= Environment.getExternalStoragePublicDirectory().getPath();

    public final static String RECEIVE_FILE_PATH = "/data/data/com.ray.offloading1/test/";

    public final static String SEND_FILE_PATH = "/sdcard/TransReceive/";

    public final static String PROJECT_DIR="/sdcard/TransReceive/";

   // public final static String PROJECT_DIR="/data/data/com.ray.offloading1/TransReceive/";
   // public final static String SEND_PROJECT="/data/data/com.ray.offloading1/proj/parts";

    public final static String PROJECT_PATH="/sdcard/TransReceive/proj";

    public final  static String RETURNED_RESULT_PATH="/sdcard/TransReceive/iproj1/result/returnedResult/";

   // public final static String RETURNED_RESULT_PATH="/data/data/com.ray.offloading1/TransReceive/proj/result/returnedResult/";

  public final static String SUMDEX_FILE_PATH="/sdcard/TransReceive/iproj1/result/isum.dex";
 //   public final static String SUMDEX_FILE_PATH="/data/data/com.ray.offloading1/TransReceive/proj/result/sumResult.dex";

   public final static String SUMCONFIG_FILE_PATH="/sdcard/TransReceive/iproj1/result/sR.config";

 //   public final static String SUMCONFIG_FILE_PATH="/data/data/com.ray.offloading1/TransReceive/proj/result/sR.config";

    public final static String SHOW_RESULT_FILE_PATH="/sdcard/TransReceive/iproj1/result/sum.txt";

  //  public final static String SHOW_RESULT_FILE_PATH="/data/data/com.ray.offloading1/TransReceive/proj/result/sum.txt";

    public final static int DEFAULT_BIND_PORT = 5123;

    public final static int BUFFERED_SIZE=8192;

}
