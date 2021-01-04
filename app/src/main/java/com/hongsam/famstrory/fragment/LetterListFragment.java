package com.hongsam.famstrory.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.adapter.LatterListAdapter;

import java.io.File;

public class LetterListFragment extends Fragment {

    MainActivity mainActivity;
    View mContentView;
    Toolbar mtoolbar;
    ImageButton mbackBtn;
    ImageButton mdeleteBtn;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    LatterListAdapter mAdapter = null;

    FirebaseDatabase mDb;
    DatabaseReference mFamRef;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);

//        mDb = FirebaseDatabase.getInstance();
//        mFamRef = mDb.getReference("Latter");

    }

    private class ViewHoleder{
        TextView mSender;
        TextView mContent;
        TextView mDate;
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

        mContentView = inflater.inflate(R.layout.fragment_letter_list, container, false);
//        //뒤로가기 버튼 -> 타임라인으로
//        mbackBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mainActivity.onFragmentChanged(0);
//            }
//        });


        setHasOptionsMenu(true);

        init(mContentView);

        return mContentView;
    }


    /**
     * 컨트롤 초기화 해주는 함수
     * */
    public void init(View v) {
        if (v != null) {
            mtoolbar = v.findViewById(R.id.toolbar);
            mbackBtn = v.findViewById(R.id.back_btn);
            mdeleteBtn = v.findViewById(R.id.trash_btn);
        }
    }


    /**
     * 이미지 리소스 세팅해주는 함수
     * */
    public void setImageResource() {
//        mbackBtn.setImageResource(R.drawable.back_btn_customise);

    }


//    @Override // Delete 버튼
//    public void onDeleteClick(View v, int pos) {
//        Log.d("aaaa", "버튼을 누른 아이템의 위치는 " + pos + " 의 삭제버튼");
//        String mediaPath = "/firebase/Letter"+LetterList.get(pos).getName();
//        File file = new File(mediaPath);
//        if(file.exists()){
//            if(LetterList.size()!=0) {
//                file.delete();
//
//                // Data 삭제시 firebase DB에 있는 데이터 까지 삭제.
//                try {
//                    // path를 통해 파일 스캔 후 삭제
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                Toast.makeText(LetterListFragment.this, LetterList.get(pos).getName()+" 편지가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
//            }else{
//                Toast.makeText(LetterListFragment.this, "잘못된 요청입니다.", Toast.LENGTH_SHORT).show();
//            }
//        }else{
//            Toast.makeText(LetterListFragment.this, "잘못된 요청입니다.", Toast.LENGTH_SHORT).show();
//        }
//
//    }
//



    /**
     * 각종 리소스 null 처리
     * */
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 예시) button1 = null;
    }
}
