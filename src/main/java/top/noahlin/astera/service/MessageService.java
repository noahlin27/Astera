package top.noahlin.astera.service;

import top.noahlin.astera.model.Message;

import java.util.List;

public interface MessageService {
    int addMessage(Message message);

    List<Message> getMessageList(String conversationId);

    List<Message> getConversationList(int userId);

    int getUnreadCount(int userId, String conversationId);

    int read(String conversationId);
}
