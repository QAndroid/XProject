package workshop1024.com.xproject.model.news.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import workshop1024.com.xproject.model.ListConverters;
import workshop1024.com.xproject.model.news.News;

@Database(entities = {News.class}, version = 1, exportSchema = false)
@TypeConverters(ListConverters.class)
public abstract class NewsDatabase extends RoomDatabase {
    private static NewsDatabase INSTANCE;

    public abstract NewsDao newsDao();

    private static final Object sLock = new Object();

    public static NewsDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NewsDatabase.class,
                        "News.db").build();
            }
            return INSTANCE;
        }
    }
}
