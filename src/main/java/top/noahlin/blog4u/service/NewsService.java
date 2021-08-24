package top.noahlin.blog4u.service;

import org.springframework.stereotype.Service;
import top.noahlin.blog4u.dao.NewsDAO;
import top.noahlin.blog4u.model.News;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewsService {
    @Resource
    private NewsDAO newsDAO;

    public List<News> getLatestNews(int userId, int offset, int limit){
        return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
    }
}
