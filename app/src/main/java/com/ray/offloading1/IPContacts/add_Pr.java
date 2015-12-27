package com.ray.offloading1.IPContacts;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ray.offloading1.MainActivity;
import com.ray.offloading1.R;

public class add_Pr extends ActionBarActivity implements View.OnClickListener {

    EditText newName;
    EditText newIP;
    EditText newLevel;
    Button addPr;
    DatabaseHandler dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__pr);

        newName=(EditText)findViewById(R.id.new_name);
        newIP=(EditText)findViewById(R.id.new_IP);
        newLevel=(EditText)findViewById(R.id.new_level);
        addPr=(Button)findViewById(R.id.addPr);
        dbh=new DatabaseHandler(this);
        try{
            dbh.open();
        }catch(Exception e){
            e.printStackTrace();
        }

        addPr.setOnClickListener(this);


    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.addPr:
                String name = newName.getText().toString();
                String IP=newIP.getText().toString();
                int level=Integer.parseInt(newLevel.getText().toString());
                serverHelper sh=new serverHelper(name,IP,level);
                dbh.addHelper(sh);
                Intent main = new Intent(add_Pr.this, providerShow.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
                break;

            default:
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_, menu);
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
}
