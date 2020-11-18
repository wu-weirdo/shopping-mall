package com.edaochina.shopping.common.utils.mail;

import com.edaochina.shopping.common.utils.Md5Util;
import com.edaochina.shopping.common.utils.ThreadUtils;
import com.edaochina.shopping.common.utils.cache.RedisTool;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Objects;

/**
 * @author : wangpenglei
 * @version : 1.0
 * @apiNote : 发送邮件
 * @since : 2019/7/10 13:52
 */
@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    private final static String MAIL_ACTIVE = "config";
    @Value("${developer.mail}")
    private List<String> mails;
    @Value("${spring.profiles.active}")
    private String active;

    public void report(Exception e) {
        if (!Objects.equals(active, MAIL_ACTIVE)) {
            return;
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String content = "RequestBody: \n" + "ThreadKey: \n" + MDC.get("key") + "\n" +
                "Exception: \n" + sw.toString();
        mails.forEach(mail -> this.sendExceptionMail(mail, "24H发生异常", content));
    }

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMail(String to, String subject, String content) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        // 邮件发送者
        message.setFrom(from);
        // 邮件接受者
        message.setTo(to);
        // 主题
        message.setSubject(subject);
        // 内容
        message.setText(content);
        javaMailSender.send(message);
    }

    public void sendExceptionMail(String to, String subject, String content) throws MailException {
        Runnable runnable = () -> {
            String key = Md5Util.MD5(content);
            try {
                RedisTool.lock("mail:" + key);
                if (RedisTool.get("exceptionMail:" + key) != null) {
                    return;
                }
                RedisTool.set("exceptionMail:" + key, 1);
                RedisTool.expire("exceptionMail:" + key, 3600L);
                SimpleMailMessage message = new SimpleMailMessage();
                // 邮件发送者
                message.setFrom(from);
                // 邮件接受者
                message.setTo(to);
                // 主题
                message.setSubject(subject);
                // 内容
                message.setText(content);
                javaMailSender.send(message);
            } finally {
                RedisTool.unLock("mail:" + key);
            }
        };
        ThreadUtils.execute(runnable);
    }

}
