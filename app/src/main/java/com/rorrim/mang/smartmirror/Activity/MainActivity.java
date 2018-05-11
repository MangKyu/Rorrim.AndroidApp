package com.rorrim.mang.smartmirror.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rorrim.mang.smartmirror.Controller.NetworkController;
import com.rorrim.mang.smartmirror.Model.HttpConnection;
import com.rorrim.mang.smartmirror.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NetworkController nc;
    private HttpConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nc = new NetworkController(this);

        if(!nc.isConnected()){
            Toast.makeText(this, "Network is not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        if(nc.checkWifi()){
            conn = HttpConnection.getInstance("http://hezo25.com/get_json.php");
            conn.start();
        }

        Toast.makeText(this, "Connected on" + nc.getNetworkTypeName(), Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(this, CalendarActivity.class);
        //startActivity(intent);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private Intent getJson(){

        conn.setDataList();
        Intent intent = new Intent(MainActivity.this, ContentActivity.class);
        intent.putExtra("status", conn.getDataList().get(0).toString());
        intent.putExtra("result", conn.getDataList().get(1).toString());
        return intent;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.json_btn:
                intent = getJson();
                break;
            default:
                break;
        }

        if(intent != null){
            startActivity(intent);
        }

    }
}