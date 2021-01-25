package com.hongsam.famstrory.activitie;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hongsam.famstrory.R;
import com.hongsam.famstrory.data.Family;
import com.hongsam.famstrory.define.Define;
import com.hongsam.famstrory.util.FirebaseManager;

import java.lang.reflect.Field;

public class FamCreateActivity extends AppCompatActivity {
    EditText etFamName, etPw, etPwCheck, etName;
    Spinner spRelation;
    Button btnCreate, btnCheck;

    boolean isFamOver = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fam_create);  // 컨텐츠의 화면을 (main_button.xml) 으로 등록한다.
        init();
    }

    private void init() {
        etFamName = (EditText) findViewById(R.id.a_fam_create_et_fam_name);
        etPw = (EditText) findViewById(R.id.a_fam_create_et_pw);
        etPwCheck = (EditText) findViewById(R.id.a_fam_create_et_pw_check);
        etName = (EditText) findViewById(R.id.a_fam_create_et_name);
        spRelation = (Spinner) findViewById(R.id.a_fam_create_sp_relation);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            ListPopupWindow window = (ListPopupWindow)popup.get(spRelation);
            window.setHeight(300); //pixel
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnCheck = (Button) findViewById(R.id.a_fam_create_btn_check);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strFamName = etFamName.getText().toString();
                famNameCheck(strFamName);
            }
        });

        btnCreate = (Button) findViewById(R.id.a_fam_create_btn_create);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strFamName = etFamName.getText().toString();
                String strPw = etPw.getText().toString();
                String strPwCheck = etPwCheck.getText().toString();
                String strName = etName.getText().toString();
                String strRelation = spRelation.getSelectedItem().toString();

                if (isFamOver) {
                    Toast.makeText(getApplicationContext(), "가족명 중복확인을 해주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!strPw.equals(strPwCheck)) {
                    Toast.makeText(getApplicationContext(), "패스워드를 확인해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                writeNewFamily(strFamName, strPw);

                saveMyInfo(strName, strRelation);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("famName", strFamName);
                intent.putExtra("name", strName);
                intent.putExtra("relation", strRelation);
                intent.putExtra("password", strPw);

                startActivity(intent);
                finish();
            }
        });
    }

    private void saveMyInfo(String name, String relation) {
        SharedPreferences pref = getSharedPreferences(getPackageName(), Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Define.KEY_MY_NAME, name);
        editor.putString(Define.KEY_MY_RELATION, relation);
        editor.apply();
    }

    private void writeNewFamily(String FamilyName, String FamilyPw) {
        Family family = new Family(FamilyPw, FamilyName);
        FirebaseManager.dbFamRef.child(FamilyName).setValue(family);
    }

    private void famNameCheck(final String famName) {
        FirebaseManager.dbFamRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                boolean flag = false;
                for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                    flag = famName.equals(singleSnapshot.getKey());
                }

                if (flag) {
                    isFamOver = true;
                    Toast.makeText(getApplicationContext(), "중복된 가족명입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    isFamOver = false;
                    Toast.makeText(getApplicationContext(), "사용 가능한 가족명입니다.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), StartMenuActivity.class);
        startActivity(intent);
        finish();

    }
}
