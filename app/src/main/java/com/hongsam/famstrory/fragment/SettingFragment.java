package com.hongsam.famstrory.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.activitie.StartMenuActivity;
import com.hongsam.famstrory.define.Define;
import com.hongsam.famstrory.dialog.YesNoDialog;
import com.hongsam.famstrory.util.FirebaseManager;
import com.hongsam.famstrory.util.SharedManager;

public class SettingFragment extends Fragment {
    private final String TAG = "SettingFragment";

    MainActivity mainActivity;
    View mContentView;

    TextView tvVersion;
    CheckBox cbUseCall;
    EditText etPw;
    Button btnExitFam, btnDeleteFam;

    String famName, famPw, token;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    /**
     * View 객체를 얻는 시점
     * */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null)
            return null;

        if (inflater == null)
            return null;

        mainActivity = (MainActivity) getActivity();

        mContentView = inflater.inflate(R.layout.fragment_setting, null);

        init(mContentView);

        return mContentView;
    }


    /**
     * 컨트롤 초기화 해주는 함수
     * */
    public void init(View v) {
        if (v != null) {
            famName = SharedManager.readString(Define.KEY_FAMILY_NAME, "");
            famPw = SharedManager.readString(Define.KEY_FAMILY_PASSWORD, "");
            token = SharedManager.readString(Define.KEY_FIREBASE_TOKEN, "");

            tvVersion = v.findViewById(R.id.f_setting_tv_version);
            cbUseCall = v.findViewById(R.id.f_setting_cb_use_call);
            btnExitFam = v.findViewById(R.id.f_setting_btn_exit);
            btnDeleteFam = v.findViewById(R.id.f_setting_btn_delete);

            etPw = v.findViewById(R.id.f_setting_et_pw);

            tvVersion.setText("v" + Define.APP_VERSION);

            cbUseCall.setChecked(SharedManager.readBoolean(Define.KEY_SETTING_USE_CALL, true));

            cbUseCall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        SharedManager.writeBoolean(Define.KEY_SETTING_USE_CALL, true);
                    } else {
                        SharedManager.writeBoolean(Define.KEY_SETTING_USE_CALL, false);
                    }
                    Log.d(TAG, "isChecked : " + isChecked);
                }
            });

            btnExitFam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    YesNoDialog yesNoDialog = new YesNoDialog(mainActivity, "확인", "정말 가족을 탈퇴하시겠습니까?");
                    yesNoDialog.setOnYesNoListener(new YesNoDialog.YesNoDialogListener() {
                        @Override
                        public void onYes() {
                            FirebaseManager.dbFamRef.child(famName).child("members").child(token).removeValue();
                            SharedManager.writeString(Define.KEY_FAMILY_NAME, "");

                            Intent intent = new Intent(mainActivity, StartMenuActivity.class);
                            startActivity(intent);
                            mainActivity.finish();
                        }

                        @Override
                        public void onNo() {

                        }
                    });
                    yesNoDialog.show();
                }
            });

            btnDeleteFam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btnDeleteFam.getText().toString().equals("확인")) {
                        if (etPw.getText().length() == 0 || !etPw.getText().toString().equals(famPw)) {
                            Toast.makeText(getContext(), "암호를 확인해주세요!", Toast.LENGTH_SHORT).show();
                        } else {
                            YesNoDialog yesNoDialog = new YesNoDialog(mainActivity, "확인", "정말 가족을 삭제하시겠습니까?");
                            yesNoDialog.setOnYesNoListener(new YesNoDialog.YesNoDialogListener() {
                                @Override
                                public void onYes() {
                                    FirebaseManager.dbFamRef.child(famName).removeValue();
                                    FirebaseManager.storageFamRef.child(famName).delete();

                                    SharedManager.writeString(Define.KEY_FAMILY_NAME, "");

                                    Intent intent = new Intent(mainActivity, StartMenuActivity.class);
                                    startActivity(intent);
                                    mainActivity.finish();
                                }

                                @Override
                                public void onNo() {
                                    etPw.setVisibility(View.GONE);
                                    btnDeleteFam.setText("가족 삭제");
                                }
                            });
                            yesNoDialog.show();
                        }
                    } else {
                        etPw.setVisibility(View.VISIBLE);
                        btnDeleteFam.setText("확인");
                    }
                }
            });
        }
    }


    /**
     * 이미지 리소스 세팅해주는 함수
     * */
    public void setImageResource() {
        // 예시) button1.setBackgroundResource(R.drawable.image1);
    }

    /**
     * 각종 리소스 null 처리
     * */
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 예시) button1 = null;
    }
}
