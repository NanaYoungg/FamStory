package com.hongsam.famstory.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hongsam.famstory.R;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.adapter.RecyclerAdapter;
import com.hongsam.famstory.data.Member;
import com.hongsam.famstory.define.Define;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import com.hongsam.famstory.util.SharedManager;
import com.hongsam.famstory.util.FirebaseManager;
import com.hongsam.famstory.util.GlobalMethod;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {
    private final String TAG = "ProfileFragment";

    MainActivity mainActivity;
    View mContentView;

    CardView cvMain;
    ImageView ivMain;
    RecyclerView rvMember;

    EditText etTitle;
    TextView tvFamName, tvTitle, tvEmpty;
    Button btnTitle;

    ProfileOnLongClickListener mLongClickListener;

    //StorageReference storageRef;

    String famName = "재훈이네가족";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);

        mLongClickListener = new ProfileOnLongClickListener();
        //storageRef = FirebaseManager.firebaseStorage.getReference("family");
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

        mContentView = inflater.inflate(R.layout.fragment_profile, null);

        init(mContentView);
        setImageResource();

        return mContentView;
    }


    /**
     * 컨트롤 초기화 해주는 함수
     * */
    public void init(View v) {
        if (v != null) {

            tvFamName = v.findViewById(R.id.f_profile_tv_fam_name);
            etTitle = v.findViewById(R.id.f_profile_et_title);
            tvTitle = v.findViewById(R.id.f_profile_tv_title);
            tvEmpty = v.findViewById(R.id.f_profile_tv_empty);
            btnTitle = v.findViewById(R.id.f_profile_btn_title);
            cvMain = v.findViewById(R.id.f_profile_cv_main);
            ivMain = v.findViewById(R.id.f_profile_iv_main);

            tvTitle.setOnLongClickListener(mLongClickListener);
            tvEmpty.setOnLongClickListener(mLongClickListener);

            btnTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (etTitle.getText().length() == 0) {
                        Toast.makeText(mainActivity, "다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    tvTitle.setText(etTitle.getText().toString());
                    tvTitle.setVisibility(View.VISIBLE);
                    etTitle.setVisibility(View.GONE);
                    btnTitle.setVisibility(View.GONE);

                    mainActivity.showKeyboard(etTitle, false);
                }
            });

            //ivMain.setClipToOutline(true);
            ivMain.setOnLongClickListener(mLongClickListener);

            tvFamName.setText(famName);

            ArrayList<Member> memberList = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                Member member = new Member("홍길동"+i, "호칭"+i, "https://firebasestorage.googleapis.com/v0/b/hongkathon.appspot.com/o/selfie.png?alt=media&token=2b623453-a82f-4a33-8195-d6ae948ebc59");
                memberList.add(member);
            }

            rvMember = mContentView.findViewById(R.id.f_profile_rv_member);
            RecyclerAdapter rvAdapterB = new RecyclerAdapter(mainActivity, R.layout.item_member, Define.VIEWTYPE_MEMBER, memberList);
            rvMember.setAdapter(rvAdapterB);



        }
    }


    /**
     * 이미지 리소스 세팅해주는 함수
     * */
    public void setImageResource() {
        // 예시) button1.setBackgroundResource(R.drawable.image1);
        checkServerPicture();
    }

    public void uploadPicture(final Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = FirebaseManager.storageFamRef.child(famName+FirebaseManager.pathImgTitle).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "이미지 업로드 실패!");
                cvMain.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
            }
        });
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "이미지 업로드 성공!");
                cvMain.setVisibility(View.VISIBLE);
                tvEmpty.setVisibility(View.GONE);
                SharedManager.writeLong(Define.KEY_FAMILY_PICTURE_SIZE, taskSnapshot.getTotalByteCount());

                String picturePath = GlobalMethod.saveToInternalStorage(mainActivity, bm, "family.jpg");
                SharedManager.writeObject(Define.KEY_FAMILY_PICTURE_PATH, picturePath);
            }
        });
    }

    public void downloadPicture() {
        StorageReference ref = FirebaseManager.storageFamRef.child(famName+FirebaseManager.pathImgTitle);

        try {
            final File localFile = File.createTempFile("images", "jpg");

            ref.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    // Local temp file has been created
                    Bitmap picture = GlobalMethod.FileToBitmap(localFile);
                    ivMain.setImageBitmap(picture);

                    uploadPicture(picture);
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

    public void checkServerPicture() {
        StorageReference ref = FirebaseManager.storageFamRef.child(famName+FirebaseManager.pathImgTitle);
        final Task<StorageMetadata> sm = ref.getMetadata();

        sm.addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
            @Override
            public void onSuccess(StorageMetadata storageMetadata) {
                long newSize = sm.getResult().getSizeBytes();
                long oldSize = SharedManager.readLong(Define.KEY_FAMILY_PICTURE_SIZE, 0);
                if (newSize != oldSize) {
                    downloadPicture();
                    Log.d(TAG, "서버 이미지랑 다름! 이미지 다운로드!");
                } else {
                    Log.d(TAG, "서버 이미지랑 같음! 내장이미지 적용!");

                    String path = SharedManager.readString(Define.KEY_FAMILY_PICTURE_PATH, "");
                    Bitmap picture = GlobalMethod.loadImageFromStorage(path, "family.jpg");

                    if (picture == null) {
                        tvEmpty.setVisibility(View.VISIBLE);
                        cvMain.setVisibility(View.GONE);
                        return;
                    } else {
                        ivMain.setImageBitmap(picture);
                    }
                }
                tvEmpty.setVisibility(View.GONE);
                cvMain.setVisibility(View.VISIBLE);
            }
        });

        sm.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                tvEmpty.setVisibility(View.VISIBLE);
                cvMain.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK) {
            try {
                InputStream is = mainActivity.getContentResolver().openInputStream(data.getData());
                Bitmap bm = GlobalMethod.RotateBitmap(BitmapFactory.decodeStream(is), GlobalMethod.GetPathFromUri(mainActivity, data.getData()));

                is.close();

                tvEmpty.setVisibility(View.GONE);
                cvMain.setVisibility(View.VISIBLE);
                ivMain.setImageBitmap(bm);

                uploadPicture(bm);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 각종 리소스 null 처리
     * */
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 예시) button1 = null;
    }

    class ProfileOnLongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            int id = v.getId();

            if (id == R.id.f_profile_tv_empty || id == R.id.f_profile_iv_main) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 101);

            } else if (id == R.id.f_profile_tv_title) {
                etTitle.setVisibility(View.VISIBLE);
                btnTitle.setVisibility(View.VISIBLE);
                tvTitle.setVisibility(View.GONE);
                mainActivity.showKeyboard(etTitle, true);

            }
            return true;
        }
    }

}
