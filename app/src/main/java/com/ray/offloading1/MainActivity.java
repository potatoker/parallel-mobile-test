package com.ray.offloading1;

import android.content.Intent;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import com.ray.offloading1.IPContacts.providerShow;
import com.ray.offloading1.Transfer.Constants;
import com.ray.offloading1.Transfer.TransferClient;
import com.ray.offloading1.Transfer.TransferServer;

import java.io.DataOutputStream;
import java.io.File;


import android.os.BatteryManager;
//import android.os.ServiceManager;
import android.content.Context;


public class MainActivity extends ActionBarActivity {

    private Button ServerButton;
    private Button clientButton;
    private Button showSP;
    private TextView resultText;
    private TextView stv;
    private TextView ctv;

    private String projDir=Constants.PROJECT_DIR;
    private String partsPath=null;

    private Handler handler;

    public static boolean upgradeRootPermission(String pkgCodePath) {
        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd="chmod 777 " + pkgCodePath;
            process = Runtime.getRuntime().exec("su"); //切换到root帐号
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

      /*  BatteryManager mBatteryManager =
                (BatteryManager)Context.getSystemService(Context.BATTERY_SERVICE);
        Long energy =
                mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER);
        Log.i("power:", "Remaining energy = " + energy + "nWh");
        Log.i("","");
*/
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        upgradeRootPermission(getPackageCodePath());


        stv=(TextView)findViewById(R.id.spTime);
        ctv=(TextView)findViewById(R.id.cpTime);
        resultText=(TextView)findViewById(R.id.result_Text);
        handler=new Handler(){
            public void handleMessage(Message msg){
                String str=(String)msg.obj;
                resultText.setText(str);
            }
        };




        ServerButton=(Button)findViewById(R.id.button_server);
        ServerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Log.i("action:","sercer click");


                new Thread(){
                    @Override
                    public void run() {
                        try{ startService();}catch (Exception e){e.printStackTrace();}
                    }
                }.start();



            }});


        clientButton=(Button)findViewById(R.id.button_client);
        clientButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){


                Log.i("action:","client click");

                if(getProjectPath()) {
                    new Thread() {
                        @Override
                        public void run() {
                            startTransmit();
                        }
                    }.start();

                }
            }
        });



        showSP=(Button)findViewById(R.id.showSP);
        showSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toShow=new Intent(MainActivity.this,providerShow.class);
                startActivity(toShow);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public void startService()throws Exception{
        new TransferServer(stv,handler).serviceStart();

    }


    public boolean getProjectPath(){
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String projName = editText.getText().toString();
        String projectDir=projDir+projName;

        Log.i("projectDir",projectDir);

        File PDir=new File(projectDir);


        if(!PDir.exists()){

            Toast.makeText(getApplicationContext(), "project not exist",
                    Toast.LENGTH_SHORT).show();

            Log.i("err","not exist");
            return false;
        }

        partsPath=projectDir+"/parts";
        return true;
    }

    public void startTransmit(){


        new TransferClient(partsPath,this.getApplicationContext(),ctv,handler).TransferStart();
    }

}
