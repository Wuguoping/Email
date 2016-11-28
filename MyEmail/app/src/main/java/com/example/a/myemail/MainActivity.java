package com.example.a.myemail;

import android.content.pm.PackageInstaller;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import javax.mail.AuthenticationFailedException;
import javax.mail.Session;
import javax.mail.Transport;


import java.util.Properties;

public class MainActivity extends AppCompatActivity {
    private EditText editText_email;
    private EditText editText_pwd;

    protected static String address = "";
    protected static String pwd = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button_login=(Button)findViewById(R.id.button_login);
        editText_email=(EditText)findViewById(R.id.editText_email);
        editText_pwd=(EditText)findViewById(R.id.editText_pwd);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Loginin()){
                    Toast.makeText(getApplicationContext(), "登录成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean Loginin(){
        try{
            address=editText_email.getText().toString();
            pwd=editText_pwd.getText().toString();
            Properties props=new Properties();
            props.setProperty("mail.smtp.auth","true");
            props.setProperty("mail.transport.protocol","smtp");
            Session session=Session.getInstance(props);
            session.setDebug(true);
            Transport transport=session.getTransport();
            transport.connect("smtp.163.com",25,address,pwd);
            transport.close();
            return  true;
        }catch(AuthenticationFailedException e){
            Toast.makeText(getApplicationContext(), "用户名或密码有误！", Toast.LENGTH_SHORT).show();
            return false;
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), "登录失败！", Toast.LENGTH_SHORT).show();
            editText_email.setText(e.toString());
            return false;
        }
    }
}


