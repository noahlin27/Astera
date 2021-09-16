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
        return messageDAO.insert(message) > 0 ? message.getId() : 0;
    }

    @Override
    public List<Message> getMessageList(String conversationId, int offset, int limit) {
        return  messageDAO.selectLatest(conversationId, offset, limit);
    }
}
