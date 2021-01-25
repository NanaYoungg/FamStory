package com.hongsam.famstrory.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;
import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.data.Family;
import com.hongsam.famstrory.util.FirebaseManager;
import com.hongsam.famstrory.util.GlobalMethod;

import java.io.File;

public class FamFindDialog extends Dialog {
    private final String TAG = "FamFindDialog";

    InputMethodManager imm;

    LinearLayout layoutFind, layoutJoin;
    EditText etFamName, etPw, etName;
    Spinner spRelation;
    Button btnFind;
    ProgressBar progressBar;
    CardView cvFamFind;
    ImageView ivPhoto;

    boolean isJoin = false;

    private FamFindResultListener famfindResultListener;

    public interface FamFindResultListener {
        void onResult(String famName, String name, String relation);
    }

    public void setOnResultListener(FamFindResultListener famfindResultListener) {
        this.famfindResultListener = famfindResultListener;
    }

    public FamFindDialog(@NonNull Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_fam_find);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        layoutFind = findViewById(R.id.d_fam_find_layout_find);
        layoutJoin = findViewById(R.id.d_fam_find_layout_join);

        etFamName = findViewById(R.id.d_fam_find_et_fam_name);
        etName = findViewById(R.id.d_fam_find_et_name);
        etPw = findViewById(R.id.d_fam_find_et_pw);

        spRelation = findViewById(R.id.d_fam_find_spinner);

        btnFind = findViewById(R.id.d_fam_find_btn_find);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isJoin) {
                    dismiss();
                    famfindResultListener.onResult(etFamName.getText().toString(), etName.getText().toString(), spRelation.getSelectedItem().toString());
                } else {
                    imm.hideSoftInputFromWindow(etFamName.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(etPw.getWindowToken(), 0);
                    checkFamName(etFamName.getText().toString(), etPw.getText().toString());
                }
            }
        });

        progressBar = findViewById(R.id.d_fam_find_progress);
        cvFamFind = findViewById(R.id.d_fam_find_cv_main);
        ivPhoto = findViewById(R.id.d_fam_find_iv_photo);

    }

    public void checkFamName(final String famName, final String pw) {
        if (etPw.getText().toString().length() == 0) {
            Toast.makeText(getContext(), "패스워드를 입력해 주세요!", Toast.LENGTH_SHORT).show();
        } else {
            Query query = FirebaseManager.dbFamRef.orderByChild(famName);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean result = false;

                    for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                        if (singleSnapshot.getKey().equals(famName)) {
                            Family family = singleSnapshot.getValue(Family.class);
                            if (pw.equals(family.getFamPw())) {
                                result = true;
                            }
                        }
                    }

                    if (result) {
                        isJoin = true;
                        Toast.makeText(getContext(), "가족 확인 완료!", Toast.LENGTH_SHORT).show();
                        layoutFind.setVisibility(View.GONE);
                        layoutJoin.setVisibility(View.VISIBLE);

                        btnFind.setText("참가");
                        loadFamImage();
                    } else {
                        Toast.makeText(getContext(), "가족 정보를 확인해주세요!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d(TAG, "onCancelled", error.toException());
                }
            });
        }
    }

    public void loadFamImage() {
        String famName = etFamName.getText().toString();
        StorageReference ref = FirebaseManager.storageFamRef.child(famName+FirebaseManager.pathImgTitle);

        try {
            final File localFile = File.createTempFile("images", "jpg");

            ref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created
                    Bitmap picture = GlobalMethod.FileToBitmap(localFile);
                    ivPhoto.setImageBitmap(picture);

                    progressBar.setVisibility(View.GONE);
                    cvFamFind.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Log.d(TAG, "이미지 다운로드 실패!");
                }
            });
        } catch (Exception e) {

        }
    }


}
