package workshop1024.com.xproject.model.publisher.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import workshop1024.com.xproject.model.publisher.Publisher;

@Dao
public interface PublisherDao {
    /**
     * 保存发布者信息
     *
     * @param publisher 要保存的发布者信息
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void savePublisher(Publisher publisher);

    /**
     * 获取指定发布类型的发布者信息
     *
     * @param type 发布者指定的发布类型
     * @return 获取的指定类型的发布者信息集合
     */
    @Query("SELECT * FROM publishers WHERE type = :type")
    List<Publisher> getPublishersByType(String type);

    /**
     * 获取指定语言的发布者信息
     *
     * @param language 发布者指定的语言
     * @return 获取的指定语言的发布者信息集合
     */
    @Query("SELECT * FROM publishers WHERE language = :language")
    List<Publisher> getPublishersByLanguage(String language);

    /**
     * 获取已订阅的发布者信息集合
     *
     * @return 已订阅的发布者信息集合
     */
    @Query("SELECT * FROM publishers WHERE isSubscribed = 1")
    List<Publisher> getSubscribedPublishers();

    /**
     * 订阅指定id的发布者
     *
     * @param id 要订阅的发布者id
     */
    @Query("UPDATE publishers SET isSubscribed = 1 WHERE mPublisherId = :id")
    void subscribePublisher(String id);

    /**
     * 取消订阅指定id的发布者
     *
     * @param id 要取消订阅的发布者id
     */
    @Query("UPDATE publishers SET isSubscribed = 0 WHERE mPublisherId = :id")
    void unSubscribePublisher(String id);
}
