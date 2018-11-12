package sarath.com.news;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM News")
    List<News> getAll();

    @Insert
    public void insertNews(News news);

    @Delete
    public void deleteNews(News news);

    @Query("SELECT COUNT(*) FROM news")
    int countUsers();

    @Query("DELETE FROM News")
    void deleteAll();
}
