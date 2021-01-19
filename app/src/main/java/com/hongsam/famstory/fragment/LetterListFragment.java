package com.hongsam.famstory.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hongsam.famstory.ItemTouchHelper.RecyclerItemTouchHelper;
import com.hongsam.famstory.R;
import com.hongsam.famstory.activitie.MainActivity;
import com.hongsam.famstory.adapter.LetterListAdapter;
import com.hongsam.famstory.data.LetterList;
import com.hongsam.famstory.define.Define;

import java.util.ArrayList;

import static com.hongsam.famstory.fragment.LetterWriteFragment.TEST_FAMILY;

/*
 * 편지 목록 화면
 * 1/4 , 오나영
 * */

public class LetterListFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private MainActivity mainActivity;
    private View mContentView;

    private FirebaseDatabase mDb;
    private DatabaseReference mFamRef;

    private RecyclerView recyclerView;
    private LetterListAdapter letterListAdapter;
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton fab;

    private ArrayList<LetterList> itemList;


    public LetterListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);


    }


    /**
     * View 객체를 얻는 시점
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container == null)
            return null;

        if (inflater == null)
            return null;

        mainActivity = (MainActivity) getActivity();
        //View mContentView;
        mContentView = inflater.inflate(R.layout.fragment_letter_list, container, false);

        init(mContentView);

        return mContentView;

    }


    public void onResume() {
        super.onResume();
        //recycle 관련
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        itemList = new ArrayList<>();

        //DB에서 편지 리스트 뿌려주기
        mFamRef = FirebaseDatabase.getInstance().getReference("Family").child(TEST_FAMILY).child("LetterContants");
        mFamRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot npsnapshot : snapshot.getChildren()) {
                        LetterList al = npsnapshot.getValue(LetterList.class);
                        itemList.add(al);
                    }
                    letterListAdapter = new LetterListAdapter(itemList);
                    recyclerView.setAdapter(letterListAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    /**
     * 컨트롤 초기화 해주는 함수
     */
    public void init(View v) {
        if (v != null) {
            coordinatorLayout = mContentView.findViewById(R.id.coordinatorlayout);
            fab = mContentView.findViewById(R.id.f_latter_send_fab_btn);
            recyclerView = mContentView.findViewById(R.id.f_latter_list_recycler);


            //삭제 관련  :  왼쪽으로 밀때 삭제된다
            ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
            new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


            //편지보내기로 전환
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.changeFragment(Define.FRAGMENT_ID_LETTER_WRITE);
                }
            });


            //스크롤시 fab 숨기 , 스크롤시 fab 나타남
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                //스크롤이 얼마나 되었는지의 값
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (dy > 0 ||dy < 0 && fab.isShown())
//                {
//                    fab.hide();
//                }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                    super.onScrollStateChanged(recyclerView, newState);

                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        fab.show();
                    } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        fab.hide();
                    }
                }
            });

        }
    }


//    /**
//     * 이미지 리소스 세팅해주는 함수
//     * */
//     * */
//    public void setImageResource() {
//        // 예시) button1.setBackgroundResource(R.drawable.image1);
//    }


    //swipe시 아이템 삭제, snackbar의 undo 누를시 아이템 복원
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof LetterListAdapter.ViewHolder) {
            //보낸이를 snackbar에 띄워주기위해 get
            String sender = itemList.get(viewHolder.getAdapterPosition()).getSender();

            // undo시 백업
//            final Item deletedItem = itemList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // 리사이클뷰에서 아이템 삭제
            letterListAdapter.removeItem(viewHolder.getAdapterPosition());

            // snackbar 옵션
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, sender + " 가 보낸 편지가 삭제되었습니다!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo 누를시 편지 복구하기
//                    letterListAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    //undo시 편지 복구
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate();
        return true;
    }


    //편지리스트 아이템값 추가 -> 추후 DB값 불러오기
//    private List<LetterList> initData() {
//
//        itemList = new ArrayList<>();
//        itemList.add(new LetterList("엄마", "우리딸 안녕~!~!", "2020년 04일 13년"));
//        itemList.add(new LetterList("아빠", "우리딸 안녕~!~!22222222222", "2020년 05일 13년"));
//        itemList.add(new LetterList("동생", "우리딸 안녕~!~!3333332222222222222223333", "2020년 06일 13년"));
//        itemList.add(new LetterList("언니", "우리딸 안녕~!~!34444433333333334444", "2020년 07일 13년"));
//        itemList.add(new LetterList("언니", "우리딸 안녕~!~!34444433333333334444", "2020년 07일 13년"));
//        itemList.add(new LetterList("언니", "우리딸 안녕~!~!34444433333333334444", "2020년 07일 13년"));
//        itemList.add(new LetterList("언니", "우리딸 안녕~!~!34444433333333334444", "2020년 07일 13년"));
//        itemList.add(new LetterList("언니", "우리딸 안녕~!~!34444433333333334444", "2020년 07일 13년"));
//        itemList.add(new LetterList("언니", "우리딸 안녕~!~!34444433333333334444", "2020년 07일 13년"));
//
//        return itemList;
//    }


    /**
     * 각종 리소스 null 처리
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 예시) button1 = null;
    }
}