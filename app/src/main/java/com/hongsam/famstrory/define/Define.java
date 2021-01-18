package com.hongsam.famstrory.define;

import android.widget.TextView;

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

    public static String user = "user1234";
    public static int UPDATE_DIALOG = 5000;
    public static int CREATE_DIALOG = UPDATE_DIALOG+1;
    public static void setViewText(TextView view, String str){
        view.setText(str);
    }

    public static final String KEY_FAMILY_NAME = "KEY_FAMILY_NAME";
    public static final String KEY_FAMILY_PICTURE = "KEY_FAMILY_PICTURE";
    public static final String KEY_FAMILY_PICTURE_SIZE = "KEY_FAMILY_PICTURE_SIZE";
    public static final String KEY_FAMILY_PICTURE_STRING = "KEY_IMAGE_STRING";
    public static final String KEY_FAMILY_PICTURE_PATH = "KEY_FAMILY_PICTURE_PATH";

    public static final String KEY_LETTER_PICTURE_SIZE = "KEY_LETTER_PICTURE_SIZE";
    public static final String KEY_LETTER_PICTURE_PATH = "KEY_LETTER_PICTURE_PATH";

    public static final int VIEWTYPE_MEMBER = 0;

    public static int TIME_PICKER_START = 10000;
    public static int TIME_PICKER_END = TIME_PICKER_START+1;


//    public static int MEMBER_GRAND_MOTHER = 0;    // 할머니
//    public static int MEMBER_GRAND_FATHER = 1;    // 할아버지
//    public static int MEMBER_MOTHER = 2;          // 엄마
//    public static int MEMBER_FATHER = 3;          // 아빠
//    public static int MEMBER_OLDER_BROTHER = 4;   // 형
//    public static int MEMBER_OLDER_SISTER = 5;    // 누나
//    public static int MEMBER_YOUNGER_BROTHER = 6; // 남동생
//    public static int MEMBER_YOUNGER_SISTER = 7;  // 여동생
//    public static int MEMBER_ME = 8;              // 나


}
