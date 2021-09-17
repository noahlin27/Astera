package top.noahlin.astera.service.impl;

import org.springframework.stereotype.Service;
import top.noahlin.astera.dao.MessageDAO;
import top.noahlin.astera.model.Message;
import top.noahlin.astera.service.MessageService;
import top.noahlin.astera.util.SensitiveFilterUtil;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    MessageDAO messageDAO;

    @Resource
    SensitiveFilterUtil sensitiveFilterUtil;

    @Override
    public int addMessage(Message message) {
        message.setContent(sensitiveFilterUtil.filter(message.getContent()));
        int fromId = message.getFromId();
        int toId = message.getToId();
        if (fromId < toId) {
            message.setConversationId(String.format("%d_%d", fromId, toId));
        } else {
            message.setConversationId(String.format("%d_%d", toId, fromId));
        }
        return messageDAO.insert(message) > 0 ? message.getId() : 0;
    }

    @Override
    public List<Message> getMessageList(String conversationId) {
        return  messageDAO.selectLatest(conversationId, 0, 10);
    }

    @Override
    public List<Message> getConversationList(int userId) {
        return messageDAO.selectConversations(userId, 0, 10);
    }
}
