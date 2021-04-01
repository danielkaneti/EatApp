package com.example.eat.mobel;

import android.os.AsyncTask;

import java.util.List;

public class ModelSql {


    public interface GetAllPostListener {
        void onComplete(List<Post> data);
    }
//    public void getAllPost(final Model.GetAllPostListener listener){
//        class MyAsyncTask extends AsyncTask{
//            List<Post> data;
//            @Override
//            protected Object doInBackground ( Object[] objects ) {
//                data = AppLocalDb.db.postDao().getAll();
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute ( Object o ) {
//                super.onPostExecute (o);
//                listener.onComplete ( data );
//            }
//        }
//        MyAsyncTask task = new MyAsyncTask ( );
//        task.execute ();
//    }
    public interface AddPostListener{
        void onComplete();
    }
    public void addPost(Post post, Model.AddPostListener listener){
        class MyAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground ( Object[] objects ) {
                AppLocalDb.db.postDao ().insertAll ( post );
                return null;
            }

            @Override
            protected void onPostExecute ( Object o ) {
                super.onPostExecute ( o );
                if(listener!=null){
                    listener.onComplete ();
                }
            }
        };
        MyAsyncTask task = new MyAsyncTask ();
        task.execute (  );
    }
}
