package top.noahlin.astera.service;

import top.noahlin.astera.model.Message;

import java.util.List;

public interface MessageService {
    int addMessage(Message message);

    List<Message> getMessageList(String conversationId, int offset, int limit);
}
