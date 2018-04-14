package workshop1024.com.xproject.model.news.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import workshop1024.com.xproject.model.news.News;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM news")
    List<News> getNewses();

    @Query("SELECT * FROM news WHERE publisherId = :publisherId")
    List<News> getNewsByPublisherId(String publisherId);
}
