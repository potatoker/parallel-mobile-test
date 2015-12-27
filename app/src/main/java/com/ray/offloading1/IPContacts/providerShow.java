package com.ray.offloading1.IPContacts;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.ray.offloading1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class providerShow extends ActionBarActivity{
    private ListView list;
    private Button addPr;
    DatabaseHandler dbh;
    TextView sid,sname,sip,slevel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_show);
        list =(ListView)findViewById(R.id.Providerss);
        addPr=(Button)findViewById(R.id.addPr);
        dbh=new DatabaseHandler(this);
        try{dbh.open();}catch (Exception e){e.printStackTrace();}

        addPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_Pr_intent=new Intent(providerShow.this,add_Pr.class);
                startActivity(add_Pr_intent);
            }
        });

        Cursor cursor=dbh.readData();
        String[] from=new String[]{MySQLiteHelper.KEY_ID,MySQLiteHelper.KEY_NAME,MySQLiteHelper.KEY_IP,MySQLiteHelper.KEY_LEVEL};
        int[] to=new int[]{R.id.mid,R.id.sProviderName,R.id.Ip,R.id.level};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
               providerShow.this, R.layout.my_listitem, cursor, from, to);

        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                   sid=(TextView)view.findViewById(R.id.mid);
                   String selectedId=sid.getText().toString();

                Intent modify_intent=new Intent(getApplicationContext(),modifySP.class);
                modify_intent.putExtra("memId",selectedId);
                startActivity(modify_intent);

            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_provider_show, menu);
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



//    public void showTable(){
//        ArrayList<HashMap<String,String>> myList=new ArrayList<HashMap<String, String>>();
//        DatabaseHandler dbh=new DatabaseHandler(this.getApplicationContext());
//
//        List<serverHelper> providers=dbh.getAllHelpers();
//        int count=dbh.getHelperCount();
//        for(int i=0;i<count;i++){
//            HashMap<String,String> map=new HashMap<String,String>();
//            map.put("name",providers.get(i).getName());
//            map.put("IP",providers.get(i).getIP());
//            map.put("level",Integer.toString(providers.get(i).getLevel()));
//            myList.add(map);
//        }
//        SimpleAdapter mSchedule = new SimpleAdapter(this, //没什么解释
//                myList,//数据来源
//                R.layout.my_listitem,//ListItem的XML实现
//
//                //动态数组与ListItem对应的子项
//                new String[] {"name", "IP","level"},
//
//                //ListItem的XML文件里面的两个TextView ID
//                new int[] {R.id.sProviderName,R.id.Ip,R.id.level});
//        //添加并且显示
//        list.setAdapter(mSchedule);
//
//    }
}
