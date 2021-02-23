package com.example.eat;
import android.app.Application;
import android.content.Context;
public class EatAppApplication extends Application{
    static public Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
