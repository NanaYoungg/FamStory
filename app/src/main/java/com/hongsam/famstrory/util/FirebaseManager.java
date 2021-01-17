package com.hongsam.famstrory.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseManager {

    // 파이어베이스 db 객체
    public static FirebaseDatabase firebaseDB = FirebaseDatabase.getInstance();
    public static DatabaseReference dbFamRef = firebaseDB.getReference("Family");

    // 파이어베이스 storage 객체
    public static FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    public static StorageReference storageFamRef = firebaseStorage.getReference("Family");

    public static final String pathImgTitle = "/title.jpg";
    public static final String pathImgSelfie = "/selfie.jpg";
}
