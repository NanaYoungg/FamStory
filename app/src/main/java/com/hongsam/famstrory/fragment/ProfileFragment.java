package com.hongsam.famstrory.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.adapter.RecyclerAdapter;
import com.hongsam.famstrory.data.Member;
import com.hongsam.famstrory.database.DBFamstory;
import com.hongsam.famstrory.define.Define;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import com.hongsam.famstrory.util.FirebaseManager;
import com.hongsam.famstrory.util.GlobalMethod;
import com.hongsam.famstrory.util.SharedManager;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {
    private final String TAG = "ProfileFragment";

    MainActivity mainActivity;
    View mContentView;

 
     

    LinearLayout layoutImage, layoutEmpty;

    CardView cvMain;
    ImageView ivMain, ivEmpty;
    RecyclerView rvMember;

    EditText etTitle;
    TextView tvFamName, tvTitle, tvEmpty;
    Button btnTitle;

    ProfileOnLongClickListener mLongClickListener;

    String famName = "테스트가족";
    ArrayList<Member> memberList;
    RecyclerAdapter memberAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);

        mLongClickListener = new ProfileOnLongClickListener();
        memberList = new ArrayList<>();

        famName = SharedManager.readString(Define.KEY_FAMILY_NAME, "");
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
        checkServerTitle();

        return mContentView;
    }


    /**
     * 컨트롤 초기화 해주는 함수
     * */
    public void init(View v) {
        if (v != null) {
            memberList = DBFamstory.getInstance(mainActivity).selectAllMemberList();

            Log.d(TAG, "memberList size : " + memberList.size());
 

            if (memberList == null) {
                memberList = new ArrayList<>();
            }

            layoutImage = v.findViewById(R.id.f_profile_layout_image);

            layoutEmpty = v.findViewById(R.id.f_profile_layout_empty);
 

            tvFamName = v.findViewById(R.id.f_profile_tv_fam_name);
            etTitle = v.findViewById(R.id.f_profile_et_title);
            tvTitle = v.findViewById(R.id.f_profile_tv_title);
            tvEmpty = v.findViewById(R.id.f_profile_tv_empty);
            btnTitle = v.findViewById(R.id.f_profile_btn_title);
            cvMain = v.findViewById(R.id.f_profile_cv_main);
            ivMain = v.findViewById(R.id.f_profile_iv_main);
            ivEmpty = v.findViewById(R.id.f_profile_iv_empty);

            tvTitle.setOnLongClickListener(mLongClickListener);
            //tvEmpty.setOnLongClickListener(mLongClickListener);
            layoutEmpty.setOnLongClickListener(mLongClickListener);

            btnTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (etTitle.getText().length() == 0) {
                        Toast.makeText(mainActivity, "다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String title = etTitle.getText().toString();
                    tvTitle.setText(title);
                    tvTitle.setVisibility(View.VISIBLE);
                    etTitle.setText("");
                    etTitle.setVisibility(View.GONE);
                    btnTitle.setVisibility(View.GONE);

                    mainActivity.showKeyboard(etTitle, false);

                    //SharedPreference와 firebase에 저장
                    SharedManager.writeString(Define.KEY_FAMILY_TITLE, title);
                    FirebaseManager.dbFamRef.child(famName).child("famTitle").setValue(title);
                    Log.d(TAG, "가훈 저장! : " + title);
                }
            });

            ivMain.setOnLongClickListener(mLongClickListener);

            tvFamName.setText(famName);
            tvTitle.setText(SharedManager.readString(Define.KEY_FAMILY_TITLE, ""));

            rvMember = mContentView.findViewById(R.id.f_profile_rv_member);
            rvMember.addItemDecoration(new RecyclerViewMargin(10, memberList.size()));
            memberAdapter = new RecyclerAdapter(mainActivity, R.layout.item_member, Define.VIEWTYPE_MEMBER, memberList);
            rvMember.setAdapter(memberAdapter);

            //getFamilyMembers();
        }
    }


    /**
     * 이미지 리소스 세팅해주는 함수
     * */
    public void setImageResource() {
        // 예시) button1.setBackgroundResource(R.drawable.image1);
        checkServerPicture();
    }

//    public void getFamilyMembers() {
//
//        FirebaseManager.dbFamRef.child(famName).child("members").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot singleShapshot : snapshot.getChildren()) {
//                    memberList.add(singleShapshot.getValue(Member.class));
//                }
//                memberAdapter = new RecyclerAdapter(mainActivity, R.layout.item_member, Define.VIEWTYPE_MEMBER, memberList);
//                rvMember.setAdapter(memberAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    public void uploadPicture(final Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = FirebaseManager.storageFamRef.child(famName+FirebaseManager.pathImgTitle).putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "이미지 업로드 실패!");
                layoutImage.setVisibility(View.GONE);

                //tvEmpty.setVisibility(View.VISIBLE);
                layoutEmpty.setVisibility(View.VISIBLE);

            }
        });
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "이미지 업로드 성공!");
                layoutImage.setVisibility(View.VISIBLE);

                //tvEmpty.setVisibility(View.GONE);
                layoutEmpty.setVisibility(View.GONE);

                SharedManager.writeLong(Define.KEY_FAMILY_PICTURE_SIZE, taskSnapshot.getTotalByteCount());

                String picturePath = GlobalMethod.saveToInternalStorage(mainActivity, bm, "family.png");
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

//    public void downloadPicture() {
//        StorageReference ref = FirebaseManager.storageFamRef.child(famName+FirebaseManager.pathImgTitle);
//
//        try {
//            ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    Glide.with(mainActivity)
//                            .load(task.getResult())
//                            .centerCrop()
//                            .into(ivMain);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                }
//            });
//        } catch (Exception e) {
//
//        }
//    }

    public void checkServerTitle() {
        FirebaseManager.dbFamRef.child(famName).child("famTitle").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String serverTitle = snapshot.getValue(String.class);
                if (!tvTitle.getText().toString().equals(serverTitle)) {
                    Log.d(TAG, "가훈이 서버랑 다름! : " + serverTitle);
                    SharedManager.writeString(Define.KEY_FAMILY_TITLE, serverTitle);
                    tvTitle.setText(serverTitle);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                    Bitmap picture = GlobalMethod.loadImageFromStorage(path, "family.png");

                    if (picture == null) {
               //tvEmpty.setVisibility(View.VISIBLE);
                        layoutEmpty.setVisibility(View.VISIBLE);
                        layoutImage.setVisibility(View.GONE);
                        return;
                    } else {
//                        Glide.with(mContentView.getContext()).load(picture).into(ivMain);
//                        Log.d(TAG, "탔겠지");
                        ivMain.setImageBitmap(picture);
                    }
                }
                //tvEmpty.setVisibility(View.GONE);
                layoutEmpty.setVisibility(View.GONE);

                layoutImage.setVisibility(View.VISIBLE);
            }
        });

        sm.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //tvEmpty.setVisibility(View.VISIBLE);
                layoutEmpty.setVisibility(View.VISIBLE);

                layoutImage.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        if (requestCode == 101 && resultCode == RESULT_OK) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = mainActivity.getContentResolver().openInputStream(data.getData());
                        Bitmap bm = GlobalMethod.RotateBitmap(BitmapFactory.decodeStream(is), GlobalMethod.GetPathFromUri(mainActivity, data.getData()));
                        GlobalMethod.SaveBitmapToFileCache(bm, mainActivity.getFilesDir().getPath(), "/photo.png");

                        // 이미지 압축 작업
                        File file = new File(mainActivity.getFilesDir().getPath() + "/photo.png");
                        file = new Compressor(mainActivity).setQuality(50).setMaxWidth(600).setMaxHeight(600).compressToFile(file);
                        bm = GlobalMethod.FileToBitmap(file);


                        is.close();


                        //tvEmpty.setVisibility(View.GONE);
                        layoutEmpty.setVisibility(View.GONE);
                        layoutImage.setVisibility(View.VISIBLE);
                        ivMain.setImageBitmap(bm);

                        uploadPicture(bm);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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

            if (id == R.id.f_profile_layout_empty || id == R.id.f_profile_iv_main) {
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

    public class RecyclerViewMargin extends RecyclerView.ItemDecoration {
        private final int columns;
        private int margin;

        /**
         * constructor
         * @param margin desirable margin size in px between the views in the recyclerView
         * @param columns number of columns of the RecyclerView
         */
        public RecyclerViewMargin(@IntRange(from=0)int margin , @IntRange(from=0) int columns ) {
            this.margin = margin;
            this.columns=columns;

        }

        /**
         * Set different margins for the items inside the recyclerView: no top margin for the first row
         * and no left margin for the first column.
         */
        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {

            int position = parent.getChildLayoutPosition(view);
            //set right margin to all
            outRect.right = margin;
            //set bottom margin to all
            outRect.bottom = margin;
            //we only add top margin to the first row
            if (position <columns) {
                outRect.top = margin;
            }
            //add left margin only to the first column
            if(position%columns==0){
                outRect.left = margin;
            }
        }
    }

}
