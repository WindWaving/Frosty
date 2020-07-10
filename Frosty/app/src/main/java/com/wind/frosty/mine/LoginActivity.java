package com.wind.frosty.mine;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.wind.frosty.BaseCallback;
import com.wind.frosty.MainActivity;
import com.wind.frosty.R;
import com.wind.frosty.network.HttpManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText login_userField;
    TextInputEditText login_pwdField;
    TextInputEditText signup_nickname;
    TextInputEditText signup_pwd;
    TextInputEditText signup_pwdRepeat;
    TextInputEditText signup_phone;
    Button signBtn;

    HttpManager httpManager;
    LoginCallback callback;
    boolean isSigningUp=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //初始化卡片为登录卡片
        if(savedInstanceState==null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.card_container,new LoginCard())
                    .commit();
        }
        httpManager=HttpManager.getInstance();
        callback=new LoginCallback();
        signBtn=findViewById(R.id.sign_btn);
        signBtn.setText(getText(R.string.login));
    }

    //滑动触发切换卡片
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x1 = 0,x2,y1 = 0,y2;
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            x1=event.getX();
            y1=event.getY();
        }
        else if(event.getAction()==MotionEvent.ACTION_UP){
            x2=event.getX();
            y2=event.getY();
            if(Math.pow((x1-x2),2)+Math.pow((y1-y2),2)!=0){
                this.toggleSign(null);
            }
        }
        return super.onTouchEvent(event);
    }

    //切换登录和注册面
    public void toggleSign(View view){
        if(isSigningUp){
            isSigningUp=false;
            getSupportFragmentManager().popBackStack();
            signBtn.setText(getText(R.string.login));
            return;
        }
        isSigningUp=true;
        signBtn.setText(getText(R.string.signup));
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.card_flip_right_in,R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,R.animator.card_flip_left_out)
                .replace(R.id.card_container,new SignupCard())
                .addToBackStack(null)
                .commit();

    }

    public void toLogin(View view){
        login_userField=findViewById(R.id.login_user);
        login_pwdField=findViewById(R.id.login_pwd);
        String userfield=login_userField.getText().toString();
        String pwd=login_pwdField.getText().toString();
        if(userfield.length()==0||pwd.length()==0){
            Toast.makeText(this,"请将信息填写完整",Toast.LENGTH_SHORT).show();
            return;
        }
        String url=HttpManager.apiUrl+"user/login";
        Map<String,String> map=new HashMap<>();
        map.put("userfield",userfield);
        map.put("password",pwd);
        httpManager.post(url,map,callback);
    }

    public void toRegister(View view){
        signup_nickname=findViewById(R.id.signup_nickname);
        signup_pwd=findViewById(R.id.signup_pwd);
        signup_pwdRepeat=findViewById(R.id.signup_repeat);
        signup_phone=findViewById(R.id.signup_phone);
        String nickname=signup_nickname.getText().toString();
        String pwd=signup_pwd.getText().toString();
        String pwdRepeat=signup_pwdRepeat.getText().toString();
        String phone=signup_phone.getText().toString();
        System.out.println(pwd);
        System.out.println(pwdRepeat);
        if(TextUtils.isEmpty(nickname)||TextUtils.isEmpty(pwd)||TextUtils.isEmpty(phone)){
            Toast.makeText(this,"请填写完整信息",Toast.LENGTH_SHORT).show();
        }
        else if(!pwd.equals(pwdRepeat)){
            Toast.makeText(this,"两次密码不同",Toast.LENGTH_SHORT).show();
            return;
        }
        String url=HttpManager.apiUrl+"user/register";
        Map<String,String> map=new HashMap<>();
        map.put("nickname",nickname);
        map.put("password",pwd);
        map.put("phoneNumber",phone);
        httpManager.post(url,map,callback);
    }

    public void toSign(View view) {
        Button btn=(Button)view;
        switch (btn.getText().toString()){
            case "登录" :
                this.toLogin(view);
                break;
            case "注册":
                this.toRegister(view);
                break;
        }
    }

    class LoginCallback extends BaseCallback{
        @Override
        public void onFailure(Call call, IOException e) {
            System.out.println("发送请求失败 "+e.getMessage());
        }

        @Override
        public void onSuccess(Call call, JSONObject jsonObject) throws IOException, JSONException {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        @Override
        public void onServerError(Call call, String reason) {
            System.out.println("服务器响应错误: "+reason);
            Toast.makeText(getApplicationContext(),"失败:"+reason,Toast.LENGTH_SHORT).show();
        }
    }
}
