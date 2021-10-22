package top.noahlin.astera.service.impl;

import org.springframework.stereotype.Service;
import top.noahlin.astera.dao.FeedDAO;
import top.noahlin.astera.model.Feed;
import top.noahlin.astera.service.FeedService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FeedServiceImpl implements FeedService {
    @Resource
    FeedDAO feedDAO;

    @Override
    public boolean addFeed(Feed feed) {
        feedDAO.insert(feed);
        return feed.getId()>0;
    }

    @Override
    public Feed getFeed(int id) {
        return feedDAO.selectById(id);
    }

    @Override
    public int getLatestId() {
        return feedDAO.selectLatestId();
    }

    @Override
    public List<Feed> getFeeds(int maxId, List<Integer> userIds, int count) {
        return feedDAO.selectFeeds(maxId, userIds, count);
    }
}
