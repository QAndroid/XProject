package workshop1024.com.xproject.model.subscribe.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import workshop1024.com.xproject.model.subscribe.Subscribe;

@Dao
public interface SubscribeDao {

    @Query("SELECT * FROM subscribes WHERE isSubscribed = 1")
    List<Subscribe> getSubscribes();

    @Query("DELETE FROM subscribes WHERE subscribeId = :subscribeId")
    int deleteSubscribeById(String subscribeId);

    @Query("UPDATE subscribes SET customName = :customName WHERE subscribeId = :subscribeId")
    void updateName(String subscribeId, String customName);

    @Query("DELETE FROM subscribes")
    void deleteAllSubscribes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSubscribe(Subscribe subscribe);


}
