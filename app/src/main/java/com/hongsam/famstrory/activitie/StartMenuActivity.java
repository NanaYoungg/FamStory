package com.hongsam.famstrory.activitie;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.hongsam.famstrory.R;
import com.hongsam.famstrory.define.Define;
import com.hongsam.famstrory.dialog.FamFindDialog;

/**

 * 초이스 버튼들이 있는 화면

    버튼 2개가 있는 창
 */



public class StartMenuActivity extends AppCompatActivity {

    Button btnCreate, btnFind;

    String famName;

    LinearLayout layoutButton;
    LinearLayout layoutLoading;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);  // 컨텐츠의 화면을 (main_button.xml) 으로 등록한다.

        SharedPreferences pref = getSharedPreferences(getPackageName(), Activity.MODE_PRIVATE);
        famName = pref.getString(Define.KEY_FAMILY_NAME, "");

        layoutButton = findViewById(R.id.a_start_menu_layout_button);
        layoutLoading = findViewById(R.id.a_start_menu_layout_loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!famName.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    layoutLoading.setVisibility(View.GONE);
                    layoutButton.setVisibility(View.VISIBLE);

                    //button 가족생성 클릭시 화면 띄우기
                    btnCreate = (Button) findViewById(R.id.a_start_menu_btn_create);
                    btnCreate.setOnClickListener(new View.OnClickListener() {
                        @Override //방만들기 레이아웃으로 이동하기
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), FamCreateActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    btnFind = (Button)findViewById(R.id.a_start_menu_btn_find);
                    btnFind.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FamFindDialog dialog = new FamFindDialog(StartMenuActivity.this);
                            dialog.setOnResultListener(new FamFindDialog.FamFindResultListener() {
                                @Override
                                public void onResult(String famName, String name, String relation, String password) {

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("famName", famName);
                                    intent.putExtra("name", name);
                                    intent.putExtra("relation", relation);

                                    intent.putExtra("password", password);
 
                                    startActivity(intent);

                                    finish();
                                }
                            });
                            dialog.show();
//                            Intent intent = new Intent(getApplicationContext(), FamFindActivity.class);
//                            startActivity(intent);
//                            finish();
                        }
                    });
                }
            }
        }, 1300);


    }
}
