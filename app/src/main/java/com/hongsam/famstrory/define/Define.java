package com.hongsam.famstrory.define;

import android.widget.TextView;
import com.hongsam.famstrory.R;
import com.hongsam.famstrory.activitie.MainActivity;
import com.hongsam.famstrory.data.Keyword;
import com.hongsam.famstrory.data.Member;

import java.util.ArrayList;
import java.util.Map;

public class Define {
    public static final int FRAGMENT_ID = 1000;
    public static final int FRAGMENT_ID_MENU = FRAGMENT_ID + 1;
    public static final int FRAGMENT_ID_FAM_CREATE = FRAGMENT_ID + 2;
    public static final int FRAGMENT_ID_TIME_LINE = FRAGMENT_ID + 3;
    public static final int FRAGMENT_ID_CALENDAR = FRAGMENT_ID + 4;
    public static final int FRAGMENT_ID_LETTER_LIST = FRAGMENT_ID + 5;
    public static final int FRAGMENT_ID_LETTER_WRITE = FRAGMENT_ID + 6;
    public static final int FRAGMENT_ID_EMOTION = FRAGMENT_ID + 7;
    public static final int FRAGMENT_ID_PROFILE = FRAGMENT_ID + 8;
    public static final int FRAGMENT_ID_SETTING = FRAGMENT_ID + 9;
    public static final int FRAGMENT_ID_LETTER_READ = FRAGMENT_ID + 10;
    public static final int FRAGMENT_ID_MONTH_LIST = FRAGMENT_ID + 11;
    public static final int FRAGMENT_ID_SPINNER_MANGER = FRAGMENT_ID+12;
    public static final int FRAGMENT_ID_CHATTING = FRAGMENT_ID+13;


    public static final int TIME_LINE_VIEW_OTHER_MSG=8080;
    public static final int TIME_LINE_VIEW_MY_MESSAGE=TIME_LINE_VIEW_OTHER_MSG+1;
    public static final int TIME_LINE_DATE_LINE = TIME_LINE_VIEW_OTHER_MSG+2;



    public static String USER = MainActivity.famName;
 
    public static String FAMSTORY_DB = "famstory.db";
    public static String MY_MESSAGE_TABLE_NAME = "myMsgTBL";
    public static int UPDATE_DIALOG = 5000;
    public static int CREATE_DIALOG = UPDATE_DIALOG+1;
    public static void setViewText(TextView view, String str){
        view.setText(str);
    }

    public static final String DB_REFERENCE = "Family";
    public static final String CALENDAR_DB = "CalendarDB";
    public static final String LETTER_CONTENTS = "";
    public static final String MEMBERS = "members";
    public static final String MESSAGES = "messages";

    public static final String KEY_FIREBASE_TOKEN = "KEY_FIREBASE_TOKEN";
    public static  String MY_RELATION = "my";
    public static String MY_TOKEN = "a";
    public static final String KEY_FAMILY_NAME = "KEY_FAMILY_NAME";
    public static final String KEY_FAMILY_PICTURE = "KEY_FAMILY_PICTURE";
    public static final String KEY_FAMILY_PICTURE_SIZE = "KEY_FAMILY_PICTURE_SIZE";
    public static final String KEY_FAMILY_PICTURE_STRING = "KEY_IMAGE_STRING";
    public static final String KEY_FAMILY_PICTURE_PATH = "KEY_FAMILY_PICTURE_PATH";
    public static final String KEY_FAMILY_TITLE = "KEY_FAMILY_TITLE";

    public static final String KEY_MY_RELATION = "KEY_MY_RELATION";
    public static final String KEY_MY_NAME = "KEY_MY_NAME";

    public static final String KEY_LETTER_PICTURE_SIZE = "KEY_LETTER_PICTURE_SIZE";
    public static final String KEY_LETTER_PICTURE_PATH = "KEY_LETTER_PICTURE_PATH";

    public static final int VIEWTYPE_MEMBER = 0;

    public static int TIME_PICKER_START = 10000;
    public static int TIME_PICKER_END = TIME_PICKER_START+1;

    public static int[] LETTER_PAPAER_ARRAY = {


            R.drawable.paper3,
            R.drawable.paper4,
            R.drawable.paper5
    };
    public static int DATA_IS_NULL = 510;
    public static int DATA_IS_NOT_NULL = DATA_IS_NULL+1;

    public static Map<String, Keyword> keywordMap = null;
    public static ArrayList<Member> memberList = null;
    public static ArrayList<String> memberTokenList = null;

//    public static int CALL_MEMBER_GRAND_MOTHER = 0;    // 할머니
//    public static int CALL_MEMBER_GRAND_FATHER = 1;    // 할아버지
//    public static int CALL_MEMBER_MOTHER = 2;          // 엄마
//    public static int CALL_MEMBER_FATHER = 3;          // 아빠
//    public static int CALL_MEMBER_OLDER_BROTHER = 4;   // 형
//    public static int CALL_MEMBER_OLDER_SISTER = 5;    // 누나
//    public static int CALL_MEMBER_YOUNGER_BROTHER = 6; // 남동생
//    public static int CALL_MEMBER_YOUNGER_SISTER = 7;  // 여동생
//    public static int CALL_MEMBER_ME = 8;              // 나


}
