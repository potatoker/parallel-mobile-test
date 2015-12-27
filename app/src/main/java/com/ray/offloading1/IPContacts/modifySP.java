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
import android.widget.TextView;

import com.ray.offloading1.MainActivity;
import com.ray.offloading1.R;

public class modifySP extends ActionBarActivity implements View.OnClickListener {

     EditText modName,modIP,modLevel;
    Button edit_bt,delete_bt;
    serverHelper sh;
    int m_id;
    DatabaseHandler dbh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_sp);

        dbh=new DatabaseHandler(this);
        try{
            dbh.open();
        }catch (Exception e){
            e.printStackTrace();
        }
        modName=(EditText)findViewById(R.id.mod_name);
        modIP=(EditText)findViewById(R.id.mod_IP);
        modLevel=(EditText)findViewById(R.id.mod_level);
        edit_bt = (Button) findViewById(R.id.update_bt_id);
        delete_bt = (Button) findViewById(R.id.delete_bt_id);

        Intent i=getIntent();
        String selectedId=i.getStringExtra("memId");

        m_id=Integer.parseInt(selectedId);

        sh=dbh.getHelper(m_id);
        modName.setText(sh.getName());
        modIP.setText(sh.getIP());
        modLevel.setText(Integer.toString(sh.getLevel()));


        edit_bt.setOnClickListener(this);
        delete_bt.setOnClickListener(this);




    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.update_bt_id:
                String name_upd=modName.getText().toString();
                String IP_upd=modIP.getText().toString();
                String level_upd=modLevel.getText().toString();
                sh.setName(name_upd);
                sh.setIP(IP_upd);
                sh.setLevel(Integer.parseInt(level_upd));
                dbh.updateHelper(sh);
                this.returnHome();
                break;
            case R.id.delete_bt_id:
                dbh.deleteHelper(sh);
                this.returnHome();
                break;

        }

    }
    public void returnHome() {

        Intent home_intent = new Intent(getApplicationContext(),
                providerShow.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(home_intent);
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modify_s, menu);
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
