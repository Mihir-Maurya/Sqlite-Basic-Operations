package com.example.sqliteintro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
  EditText mname,muserName,mpassword;
  Button minsert,mdelete,mupdate,mview;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mname= findViewById(R.id.name);
        muserName=findViewById(R.id.username);
        mpassword=findViewById(R.id.password);
        minsert=findViewById(R.id.insert);
        mdelete=findViewById(R.id.delete);
        mupdate = findViewById(R.id.update);
        mview = findViewById(R.id.view);
        //obj of database class that we have created
        Database database = new Database(this);
        //now we will use the database obj
//        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
       minsert.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String name = mname.getText().toString();
               String username = muserName.getText().toString();
               String password = mpassword.getText().toString();
               if(name.equals("")||username.equals("")||password.equals("")){
                   Toast.makeText(MainActivity.this,"Enter All Fields",Toast.LENGTH_SHORT).show();
               }
               else if(password.length()<7){
                   Toast.makeText(MainActivity.this,"Password Should Be Greater Than 6 Letters",Toast.LENGTH_SHORT).show();
               }
               else{
                  boolean isInserted =  database.insertData(name,username,password);
                         if(isInserted){
                             Toast.makeText(MainActivity.this,"Sucessful",Toast.LENGTH_SHORT).show();
                         }else{
                             Toast.makeText(MainActivity.this,"Failed To Insert, Something Went Wrong",Toast.LENGTH_SHORT).show();
                         }
                         mname.setText("");
                         muserName.setText("");
                         mpassword.setText("");
               }
           }
       });
       mview.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Cursor data = database.getInfo();
               if(data.getCount()==0){
                   Toast.makeText(MainActivity.this,"No Data Found",Toast.LENGTH_SHORT).show();
               }
               StringBuffer buffer = new StringBuffer();
               while(data.moveToNext()){
                   buffer.append("name:"+data.getString(1)+"\n");
                   buffer.append("username:"+data.getString(2)+"\n");
                   buffer.append("password:"+data.getString(3)+"\n\n\n");
               }
               AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
               builder.setCancelable(true);//if we click outside it is willl disappers
               builder.setTitle("SignUp Users Data");
               builder.setMessage(buffer.toString());
               builder.show();
           }
       });
       mupdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String name = mname.getText().toString();
               String username = muserName.getText().toString();
               String password = mpassword.getText().toString();
               boolean i=database.updateData(name,username,password);
               if(i){
                   Toast.makeText(MainActivity.this,"Sucessful",Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(MainActivity.this,"Not Sucessful",Toast.LENGTH_SHORT).show();
               }
           }
       });
        mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = muserName.getText().toString();
                boolean i=database.deleteData(username);
                if(i){
                    Toast.makeText(MainActivity.this,"Sucessful",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Not Sucessful",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}