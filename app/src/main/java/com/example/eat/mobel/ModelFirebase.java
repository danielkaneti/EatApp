package com.example.eat.mobel;


import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.eat.EatAppApplication;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFirebase {


    public void getAllPost(Model.GetAllPostListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Post> data=new LinkedList<Post>();
                if(task.isSuccessful()){
                    for (DocumentSnapshot doc:task.getResult()) {
                        Post post=doc.toObject(Post.class);
                        data.add(post);
                    }

                }
                listener.onComplete(data);
            }
        });

    }
    public void uploadImage(Bitmap imageBmp, String name, final Model.UploadImageListener listener){

       // Date date = new Date ();
        String imageName = User.getInstance().userId ;
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("images").child(name);
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
    public static String getExtension(Uri uri){
        try{
            ContentResolver contentResolver = EatAppApplication.context.getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

        } catch (Exception e) {
            Toast.makeText(EatAppApplication.context, "Register page: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    public void addPost(Post post, Model.AddPostListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
//        Map<String, Object> data = new HashMap<>();
//        data.put("first", "Ada");
//        data.put("last", "Lovelace");
//        data.put("born", 1815);

// Add a new document with a generated ID
        db.collection("posts").document(post.getPostid()).set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Tag","post add ");
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Tag","post not add ");
                listener.onComplete();
            }
        });


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
                        post = new Post ();
                        post.fromMap ( task.getResult ().getData () );
                        //post = task.getResult ( ).toObject ( Post.class );
                    }
                }
                listener.onComplete ( post );
            }
        });
    }
    public interface Listener<T> {
        void onComplete();
        void onFail();
    }
    

    public static void loginUser(final String email, String password, final Listener<Boolean> listener){

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if(email != null && !email.equals ( "" ) && password !=null && !password.equals ( "" )) {
            if(firebaseAuth.getCurrentUser () != null){
                firebaseAuth.signOut ();
            }
            firebaseAuth.signInWithEmailAndPassword ( email , password ).addOnSuccessListener ( new OnSuccessListener<AuthResult> ( ) {
                @Override
                public void onSuccess ( AuthResult authResult ) {
                    Toast.makeText ( EatAppApplication.context , "Welcome!" , Toast.LENGTH_SHORT ).show ( );
                    setUserAppData ( email );
                    listener.onComplete ( );
                }
            } ).addOnFailureListener ( new OnFailureListener ( ) {
                @Override
                public void onFailure ( @NonNull Exception e ) {
                    Toast.makeText ( EatAppApplication.context , "Failed to login: " + e.getMessage ( ) , Toast.LENGTH_SHORT ).show ( );
                    listener.onFail ( );
                }
            } );

        }
        else {
            Toast.makeText ( EatAppApplication.context,"Please fill both data fields", Toast.LENGTH_SHORT ).show ();
        }
    }

//    public static String getCurrentUserId() {
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (firebaseUser != null)
//            return firebaseUser.getUid();
//        return null;
//    }
    public static void registerUserAccount(final String username, String password, final String email,final Uri imageUri ,final Listener<Boolean> listener){

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null){
            mAuth.signOut();
        }
        if (mAuth.getCurrentUser() == null &&
                username != null && !username.equals("") &&
                password != null && !password.equals("") &&
                email != null && !email.equals("")&&
                imageUri !=null){
            mAuth.createUserWithEmailAndPassword ( email,password ).addOnSuccessListener ( new OnSuccessListener<AuthResult> ( ) {
                @Override
                public void onSuccess ( AuthResult authResult ) {
                    Toast.makeText ( EatAppApplication.context, "User registered!",Toast.LENGTH_SHORT ).show ();
                    uploadUserData (username,email,imageUri);
                    listener.onComplete ();
                }
            } ).addOnFailureListener ( new OnFailureListener ( ) {
                @Override
                public void onFailure ( @NonNull Exception e ) {
                    Toast.makeText ( EatAppApplication.context,"Failed registering user", Toast.LENGTH_SHORT ).show ();
                    listener.onFail ();
                }
            } );

        }
        else {
            Toast.makeText(EatAppApplication.context, "Please fill all input fields and profile image", Toast.LENGTH_SHORT).show();
            listener.onFail();
        }


    }

    private static void uploadUserData(final String username, final String email, Uri imageUri){

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("images");

        if (imageUri != null){
            String imageName = username + "." + getExtension(imageUri);
            final StorageReference imageRef = storageReference.child(imageName);

            UploadTask uploadTask = imageRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return imageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){

                        Map<String,Object> data = new HashMap<>();
                        data.put("profileImageUrl", task.getResult().toString());
                        data.put("username", username);
                        data.put("email", email);
                        data.put("info", "NA");
                        firebaseFirestore.collection("userProfileData").document(email).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (firebaseAuth.getCurrentUser() != null){
                                    firebaseAuth.signOut();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EatAppApplication.context, "Fails to create user and upload data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if (!task.isSuccessful()){
                        Toast.makeText(EatAppApplication.context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(EatAppApplication.context, "Please choose a profile image", Toast.LENGTH_SHORT).show();
        }
    }

    public static void setUserAppData(final String email){
        FirebaseFirestore db = FirebaseFirestore.getInstance();;
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        db.collection("userProfileData").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    User.getInstance().Username = (String) task.getResult().get("username");
                    User.getInstance().profileImageUrl = (String) task.getResult().get("profileImageUrl");
                    User.getInstance().userInfo = (String) task.getResult().get("info");
                    User.getInstance().userEmail = email;
                    User.getInstance().userId = firebaseAuth.getUid();
                }
            }
        });
    }

    public static void updateUserProfile(String username, String info, String profileImgUrl, final Model.Listener<Boolean> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> json = new HashMap<>();
        if (username != null)
            json.put("username", username);
        else json.put("username", User.getInstance().Username);
        if (info != null)
            json.put("info", info);
        else json.put("info", User.getInstance().userInfo);
        if (profileImgUrl != null)
            json.put("profileImageUrl", profileImgUrl);
        else json.put("profileImageUrl", User.getInstance().profileImageUrl);
        json.put("email", User.getInstance().userEmail);

        db.collection("userProfileData").document(User.getInstance().userEmail).set(json).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (listener != null)
                    listener.onComplete(task.isSuccessful());
            }
        });
    }


}
