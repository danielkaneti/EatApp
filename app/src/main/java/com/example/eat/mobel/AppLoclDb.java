package com.example.eat.mobel;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.eat.EatAppApplication;


@Database(entities = {Post.class}, version = 1)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDao PostDao();
}
class AppLocalDb {
    static public AppLocalDbRepository db =
            Room.databaseBuilder(
                    EatAppApplication.context,
                    AppLocalDbRepository.class,
                    "AppLocalDb.db")
                    .fallbackToDestructiveMigration()
                    .build();
}