package workshop1024.com.xproject.model.publisher.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import workshop1024.com.xproject.model.publisher.Publisher;

@Database(entities = {Publisher.class}, version = 1, exportSchema = false)
public abstract class PublisherDatabase extends RoomDatabase {
    private static PublisherDatabase INSTANCE;

    public abstract PublisherDao publisherDao();

    private static final Object sLock = new Object();

    public static PublisherDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PublisherDatabase.class,
                        "Publishers.db").build();
            }
            return INSTANCE;
        }
    }
}
