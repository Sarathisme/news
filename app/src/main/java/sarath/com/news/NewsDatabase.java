package sarath.com.news;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {News.class}, version = 5)
public abstract class NewsDatabase extends RoomDatabase {

    private static NewsDatabase INSTANCE;
    public abstract NewsDao newsDao();

    public static NewsDatabase getNewsDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),NewsDatabase.class,"news-database")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}
