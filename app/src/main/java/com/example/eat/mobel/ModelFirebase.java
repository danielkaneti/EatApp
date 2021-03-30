package com.example.eat.mobel;


import android.graphics.Bitmap;
import android.net.Uri;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {


    public static void getAllPost(long since,final Model.Listener<List<Post>> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp ts = new Timestamp(since,0);
        db.collection("posts").whereGreaterThanOrEqualTo("lastUpdated", ts).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                Log.d("TAG","refresh " + postsData.size());
            }
        });
    }

    private static Post factory ( Map<String, Object> json ) {
            Post newPost = new Post();
            newPost.postid = (String) json.get("postId");
            newPost.posttitle = (String) json.get("postTitle");
            newPost.postinfo = (String) json.get("postContent");
            newPost.postImgUrl = (String) json.get("postImgUrl");
            newPost.userId = (String) json.get("userId");
            newPost.username = (String) json.get("username");
            Timestamp ts = (Timestamp)json.get("lastUpdated");
            if (ts != null)
                newPost.lastUpdated = ts.getSeconds();
            return newPost;
    }

    public void addPost(Post post, Model.AddPostListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").document ( post.getPostid () ).set ( toJson ( post ) )
                .addOnSuccessListener ( new OnSuccessListener<Void> ( ) {
                    @Override
                    public void onSuccess ( Void o ) {
                        Log.d("TAG", "post added successfully");
                        listener.onComplete ();
                    }
                }).addOnFailureListener ( new OnFailureListener ( ) {
            @Override
            public void onFailure ( @NonNull Exception e ) {
                Log.d("TAG", "failed adding post");
                listener.onComplete ();
            }
        } );

    }

    private static Map<String, Object> toJson( Post post){
        HashMap<String, Object> json = new HashMap<>();
        json.put("postId", post.postid);
        json.put("postTitle", post.posttitle);
        json.put("postContent", post.postinfo);
        json.put("postImgUrl", post.postImgUrl);
        json.put("userId", post.userId);
        json.put("username", post.username);
        return json;
    }

    public void updatePost ( Post post , Model.AddPostListener listener ) {
        addPost ( post,listener );
    }

    public void getPost ( String id ,final Model.GetPostListener listener ) {
        FirebaseFirestore db = FirebaseFirestore.getInstance ( );
        db.collection ( "posts" ).document ( id ).get ().addOnCompleteListener ( new OnCompleteListener<DocumentSnapshot> ( ) {
            @Override
            public void onComplete ( @NonNull Task<DocumentSnapshot> task ) {
                Post post= null;
                if (task.isSuccessful ( )) {
                    DocumentSnapshot doc = task.getResult ( );
                    if(doc!=null) {
                        post = task.getResult ( ).toObject ( Post.class );
                    }
                }
                listener.onComplete ( post );
            }
        });
    }

    public void deletePost (Post post,  Model.DeleteListener listener ) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection ( "posts" ).document ( post.getPostid () ).delete ().addOnCompleteListener ( new OnCompleteListener<Void> ( ) {
            @Override
            public void onComplete ( @NonNull Task<Void> task ) {
                listener.onComplete ();
            }
        });
    }


    public void uploadImage( Bitmap imageBmp, final Model.UploadImageListener listener){

        Date date = new Date ();
        String imageName = User.getInstance ().Username + date.getTime ();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("images").child(imageName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });
    }


    public interface Listener<T> {
        void onComplete();
        void onFail();
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
