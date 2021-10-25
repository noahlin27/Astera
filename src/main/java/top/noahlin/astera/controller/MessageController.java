package top.noahlin.astera.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.noahlin.astera.model.HostHolder;
import top.noahlin.astera.model.Message;
import top.noahlin.astera.model.User;
import top.noahlin.astera.model.ViewObject;
import top.noahlin.astera.service.MessageService;
import top.noahlin.astera.service.UserService;
import top.noahlin.astera.util.JsonUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    @Resource
    HostHolder hostHolder;

    @Resource
    UserService userService;

    @Resource
    MessageService messageService;

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @PostMapping("/msg/addMessage")
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content) {
        try {
            if (hostHolder.getUser() == null) {
                return JsonUtil.getJSONString(999, "未登录");
            }
            User user = userService.getUser(toName);
            if (user == null) {
                return JsonUtil.getJSONString(1, "用户不存在");
            }
            Message message = new Message();
            message.setFromId(hostHolder.getUser().getId());
            message.setToId(user.getId());
            message.setContent(content);
            message.setCreateTime(new Date());

            messageService.addMessage(message);
            return JsonUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("发送消息失败" + e.getMessage());
            return JsonUtil.getJSONString(1, "发信失败");
        }
    }

    @GetMapping("/msg/list")
    public String getMessageList(HttpServletRequest request) {
        if (hostHolder.getUser() == null) {
            return "redirect:/reglogin";
        }
        int localUserId = hostHolder.getUser().getId();
        List<Message> conversationList = messageService.getConversationList((localUserId));
        List<ViewObject> conversationVO = new ArrayList<>();
        for (Message message : conversationList) {
            ViewObject vo = new ViewObject();
            vo.set("conversation", message);
            int targetId = message.getFromId() == localUserId ? message.getToId() : message.getFromId();
            vo.set("user", userService.getUser(targetId));
            vo.set("unread", messageService.getUnreadCount(localUserId, message.getConversationId()));
            conversationVO.add(vo);
        }
        request.setAttribute("vos", conversationVO);
        return "messageList";
    }

    @GetMapping("/msg/detail")
    public String getMessageDetail(@RequestParam("conversationId") String conversationId,
                                   HttpServletRequest request) {
        try {
            if (hostHolder.getUser() == null) {
                return "redirect:/reglogin";
            }
            List<Message> messageList = messageService.getMessageList(conversationId);
            List<ViewObject> vos = new ArrayList<>();
            for (Message message : messageList) {
                if (hostHolder.getUser().getId() != message.getToId() && hostHolder.getUser().getId() != message.getFromId()) {
                    throw new Exception();
                }
                ViewObject vo = new ViewObject();
                vo.set("message", message);
                vo.set("user", userService.getUser(message.getFromId()));
                vos.add(vo);
            }
            request.setAttribute("vos", vos);
            messageService.read(conversationId);
        } catch (Exception e) {
            logger.error("获取消息详情失败" + e.getMessage());
        }
        return "messageDetail";
    }
}
