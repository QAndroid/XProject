package workshop1024.com.xproject.model.subscribe.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import workshop1024.com.xproject.model.subscribe.Subscribe;

@Database(entities = {Subscribe.class}, version = 1,exportSchema = false)
public abstract class SubscribeDatabase extends RoomDatabase {
    private static SubscribeDatabase INSTANCE;

    public abstract SubscribeDao subscribeDao();

    private static final Object sLock = new Object();

    public static SubscribeDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SubscribeDatabase.class,
                        "Subscribes.db").build();
            }
            return INSTANCE;
        }
    }
}
