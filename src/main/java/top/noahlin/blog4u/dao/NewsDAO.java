package top.noahlin.blog4u.dao;

import top.noahlin.blog4u.model.News;

import java.util.List;

public interface NewsDAO {
    String TABLE_NAME = "news";
    String INSERT_FIELDS = "title, link, image, like_count, comment_count, create_date, user_id";
    String SELECT_FIELDS = "id" + INSERT_FIELDS;

    int addNews(News news);

    List<News> selectByUserIdAndOffset(int userId, int offset, int limit);

//    void updatePassword();
//
//    void deleteById(int id);
}
