package top.noahlin.astera.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;

@Component
public class MailUtil implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);
    private JavaMailSenderImpl mailSender;

    @Resource
    private SpringTemplateEngine templateEngine;

    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender = new JavaMailSenderImpl();
        mailSender.setUsername("example@163.com");
        mailSender.setPassword("{第三方授权密码}");
        mailSender.setHost("smtp.163.com");
        mailSender.setPort(465);
        mailSender.setDefaultEncoding("utf8");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable", true);
        mailSender.setJavaMailProperties(javaMailProperties);
    }

    public boolean sendWithTemplate(String to, String subject, String template, Map<String, Object> map){
        try{
            String nickname = MimeUtility.encodeText("Astera");
            InternetAddress from = new InternetAddress(nickname+"<example@163.com>");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            Context context = new Context();
            context.setVariables(map);
            String text = templateEngine.process(template, context);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text,true);
            mailSender.send(mimeMessage);
            return true;
        }catch (Exception e){
            logger.error("发送邮件失败");
            return false;
        }
    }
}
