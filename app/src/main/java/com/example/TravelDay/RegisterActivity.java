package com.example.TravelDay;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
//회원가입 창
public class RegisterActivity extends AppCompatActivity {

    private int userAge2; // String userAge 값을 저장
    private EditText et_id, et_pass, et_name, et_age;
    private Button btn_register,btn_vaildateID;
    private boolean validate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //액티비티 시작시 처음으로 실행되는 생명주기!

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_join);

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);


        btn_vaildateID = findViewById(R.id.btn_validateID);
        btn_vaildateID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //ID중복체크
                String userID = et_id.getText().toString();
                if(validate){
                    return;
                }
                if(userID.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( RegisterActivity.this );
                    AlertDialog dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder=new AlertDialog.Builder( RegisterActivity.this );
                                AlertDialog dialog=builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                et_id.setEnabled(false);
                                validate=true;
                                btn_vaildateID.setText("확인");
                            }
                            else{
                                AlertDialog.Builder builder=new AlertDialog.Builder( RegisterActivity.this );
                                AlertDialog dialog=builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest=new ValidateRequest(userID,responseListener);
                RequestQueue queue= Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {//회원가입
            @Override
            public void onClick(View v) {
                //EditText에 현재 입력되어있는 값 저장
                final String userID = et_id.getText().toString();
                final String userPass = et_pass.getText().toString();
                final String userName = et_name.getText().toString();
                final String userAge = et_age.getText().toString(); //int형으로 하면 에러

                //중복체크를 안누른 경우
                if (!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    AlertDialog dialog = builder.setMessage("아이디 중복체크를 했는지 확인하세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                //한 칸이라도 입력 안했을 경우
                if (userID.equals("") || userPass.equals("") || userName.equals("") || userAge.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    AlertDialog dialog = builder.setMessage("모두 공백없이 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "회원 등록 완료", Toast.LENGTH_SHORT).show();
                                userAge2 = Integer.parseInt(userAge);
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                                startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder=new AlertDialog.Builder( RegisterActivity.this );
                                AlertDialog dialog=builder.setMessage("회원 등록 실패")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Intent intent = new Intent();
                intent.putExtra("userName",userName);
                RegisterRequest registerRequest = new RegisterRequest(userID, userPass, userName, userAge2, responseListener);
                RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                requestQueue.add(registerRequest);

            }
        });


    }
}