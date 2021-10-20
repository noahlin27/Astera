package top.noahlin.astera.service;

import top.noahlin.astera.model.Feed;

import java.util.List;

public interface FeedService {
    boolean addFeed(Feed feed);

    Feed getFeed(int id);

    List<Feed> getFeeds(int maxId, List<Integer> userIds, int count);
}
