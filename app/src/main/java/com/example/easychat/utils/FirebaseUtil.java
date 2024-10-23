package com.example.easychat.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.List;

public class FirebaseUtil {

    private static final String TAG = "FirebaseUtil";

    // Get the current user ID or return null if not authenticated
    public static String currentUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    // Check if the user is logged in
    public static boolean isLoggedIn() {
        return currentUserId() != null;
    }

    // Get a reference to the current user's document in Firestore, with a null check
    public static DocumentReference currentUserDetails() {
        String userId = currentUserId();
        if (userId != null) {
            return FirebaseFirestore.getInstance().collection("users").document(userId);
        } else {
            Log.e(TAG, "currentUserDetails: User ID is null, user might not be logged in.");
            return null;  // Return null or handle it appropriately
        }
    }

    // Get a reference to the "users" collection in Firestore
    public static CollectionReference allUserCollectionReference() {
        return FirebaseFirestore.getInstance().collection("users");
    }

    // Get a reference to a specific chatroom in Firestore
    public static DocumentReference getChatroomReference(String chatroomId) {
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);
    }

    // Get a reference to the "chats" collection inside a specific chatroom
    public static CollectionReference getChatroomMessageReference(String chatroomId) {
        return getChatroomReference(chatroomId).collection("chats");
    }

    // Generate a unique chatroom ID based on the hash codes of two user IDs
    public static String getChatroomId(String userId1, String userId2) {
        if (userId1.hashCode() < userId2.hashCode()) {
            return userId1 + "_" + userId2;
        } else {
            return userId2 + "_" + userId1;
        }
    }

    // Get a reference to the "chatrooms" collection in Firestore
    public static CollectionReference allChatroomCollectionReference() {
        return FirebaseFirestore.getInstance().collection("chatrooms");
    }

    // Get a reference to the other user in a chatroom (excluding the current user)
    public static DocumentReference getOtherUserFromChatroom(List<String> userIds) {
        String currentUserId = FirebaseUtil.currentUserId();
        if (currentUserId == null) {
            Log.e(TAG, "getOtherUserFromChatroom: Current user ID is null.");
            return null;  // Handle the case where the user is not logged in
        }

        if (userIds.get(0).equals(currentUserId)) {
            return allUserCollectionReference().document(userIds.get(1));
        } else {
            return allUserCollectionReference().document(userIds.get(0));
        }
    }

    // Convert a Firestore Timestamp to a human-readable time format
    @SuppressLint("SimpleDateFormat")
    public static String timestampToString(Timestamp timestamp) {
        return new SimpleDateFormat("HH:mm").format(timestamp.toDate());
    }

    // Log out the current user
    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    // Get a reference to the current user's profile picture in Firebase Storage
    public static StorageReference getCurrentProfilePicStorageRef() {
        String userId = FirebaseUtil.currentUserId();
        if (userId != null) {
            return FirebaseStorage.getInstance().getReference().child("profile_pic").child(userId);
        } else {
            Log.e(TAG, "getCurrentProfilePicStorageRef: User ID is null, user might not be logged in.");
            return null;  // Handle the null case if needed
        }
    }

    // Get a reference to another user's profile picture in Firebase Storage
    public static StorageReference getOtherProfilePicStorageRef(String otherUserId) {
        return FirebaseStorage.getInstance().getReference().child("profile_pic").child(otherUserId);
    }
}
