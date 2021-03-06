package com.example.eat.mobel;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.eat.EatAppApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {

    final static String POST_COLLECTION = "posts";

    public static void addPost(Post post, Model.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(POST_COLLECTION).document(post.getPostid()).set(toJson(post)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (listener!=null){
                    listener.onComplete(task.isSuccessful());
                }
            }
        });
    }

    private static Map<String, Object> toJson(Post post){
        HashMap<String, Object> json = new HashMap<>();
        json.put("postId", post.postid);
        json.put("postTitle", post.posttitle);
        json.put("postContent", post.postinfo);
        json.put("postImgUrl", post.postImgUrl);
        json.put("userId", post.userId);
        json.put("username", post.username);
        json.put("contact", post.contact);
        json.put("lastUpdated", FieldValue.serverTimestamp());
        return json;
    }

    public static void deletePost(Post post, Model.Listener<Boolean> listener) {
    }
    public static void getAllPostsSince(long since, final Model.Listener<List<Post>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp ts = new Timestamp(since,0);
        db.collection(POST_COLLECTION).whereGreaterThanOrEqualTo("lastUpdated", ts).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Post> postsData = null;
                if (task.isSuccessful()){
                    postsData = new LinkedList<Post>();
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        Map<String,Object> json = doc.getData();
                        Post posts = factory(json);
                        postsData.add(posts);
                    }
                }
                listener.onComplete(postsData);
                Log.d("TAG","refresh " );
            }

            private Post factory(Map<String, Object> json) {
                Post newPost = new Post();
                newPost.postid = (String) json.get("postId");
                newPost.posttitle = (String) json.get("postTitle");
                newPost.postinfo = (String) json.get("postContent");
                newPost.postImgUrl = (String) json.get("postImgUrl");
                newPost.userId = (String) json.get("userId");
                newPost.username = (String) json.get("username");
                newPost.contact = (String) json.get("contact");
                Timestamp ts = (Timestamp)json.get("lastUpdated");
                if (ts != null)
                    newPost.lastUpdated = ts.getSeconds();
                return newPost;
            }
        });
    }
    public interface Listener<T> {
        void onComplete();
        void onFail();
    }
    public static void getDeletedPostsId(final Model.Listener<List<String>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("deleted").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> deletedPostsIds = null;
                if (task.isSuccessful()){
                    deletedPostsIds = new LinkedList<String>();
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        String deleted = (String) doc.getData().get("postId");
                        deletedPostsIds.add(deleted);
                    }
                }
                listener.onComplete(deletedPostsIds);
            }
        });
    }

    public static void loginUser(final String email, String password, final Listener<Boolean> listener){

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(EatAppApplication.context, "Welcome!", Toast.LENGTH_SHORT).show();
                    listener.onComplete();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EatAppApplication.context, "Failed to login: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    listener.onFail();
                }
            });


    }

    public static String getCurrentUserId() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null)
            return firebaseUser.getUid();
        return null;
    }
    public static void registerUserAccount(final String username, String password, final String email, final Listener<Boolean> listener){

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null){
            mAuth.signOut();
        }
        if (mAuth.getCurrentUser() == null &&
                username != null && !username.equals("") &&
                password != null && !password.equals("") &&
                email != null && !email.equals("")
               ){
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        User user=new User(username,email);

                        FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String id = getCurrentUserId();

                            }

                        });
                        listener.onComplete();
                    }
                }
            });
        }
        else {
            Toast.makeText(EatAppApplication.context, "Please fill all input fields and profile image", Toast.LENGTH_SHORT).show();
            listener.onFail();
        }


    }




}
