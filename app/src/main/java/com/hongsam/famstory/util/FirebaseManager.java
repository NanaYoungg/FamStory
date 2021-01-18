package com.hongsam.famstory.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * 파이어베이스 관련 객체 및 레퍼런스 관리
 * 작성자 : 한재훈
 */

public class FirebaseManager {

    private FirebaseManager() {}

    private static class InnerInstanceClass {
        private static final FirebaseManager instance = new FirebaseManager();
    }

    public static FirebaseManager getInstance() {
        return InnerInstanceClass.instance;
    }

    // 파이어베이스 db 객체
    public static FirebaseDatabase firebaseDB = FirebaseDatabase.getInstance();
    public static DatabaseReference dbFamRef = firebaseDB.getReference("Family");

    // 파이어베이스 storage 객체
    public static FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    public static StorageReference storageFamRef = firebaseStorage.getReference("Family");

    public static final String pathImgTitle = "/title.jpg";
    public static final String pathImgSelfie = "/selfie.jpg";

    public static final String pathImgLetter = "/letter.jpg";
}
