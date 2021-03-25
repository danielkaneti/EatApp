package com.example.eat.mobel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {
    @Query("select * from Post")
    List<Post> getAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post...posts);

    @Delete
    void deletePost(Post post);



}
