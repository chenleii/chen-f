package com.chen.f.core.util;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

/**
 * 发送邮件工具类
 *
 * @author chen
 * @since 2018/1/3 20:14.
 */
public class MailUtils {

    private static final JavaMailSenderImpl MAIL_SENDER;

    //发件人地址
    private static final String FROM = "23213424@qq.com";
    private static final String HOST = "smtp.qq.com";
    private static final int PORT = 465;
    private static final String PROTOCOL = "smtp";
    private static final String USERNAME = FROM;
    private static final String PASSWORD = "qmadxwlothttwwhd";
    private static final String ENCODING = "UTF-8";
    private static final String TIMEOUT = "3000";
    private static final String AUTH = "true";

    static {
        MAIL_SENDER = new JavaMailSenderImpl();

        MAIL_SENDER.setHost(HOST);
        MAIL_SENDER.setPort(PORT);
        MAIL_SENDER.setProtocol(PROTOCOL);
        MAIL_SENDER.setUsername(USERNAME);
        MAIL_SENDER.setPassword(PASSWORD);
        MAIL_SENDER.setDefaultEncoding(ENCODING);
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", TIMEOUT);
        p.setProperty("mail.smtp.auth", AUTH);

        p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        p.setProperty("mail.smtp.socketFactory.fallback", "false");
        p.setProperty("mail.smtp.socketFactory.port", "465");

        MAIL_SENDER.setJavaMailProperties(p);


    }

    /**
     * 发送普通文本邮件
     *
     * @param to      收件人地址
     * @param subject 标题
     * @param content 内容
     */
    public static void sendTextEmail(String to, String subject, String content) {

        MimeMessage mimeMessage = MAIL_SENDER.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, ENCODING);
            messageHelper.setFrom(FROM);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        MAIL_SENDER.send(mimeMessage);
    }

    /**
     * 发送有HTML格式邮件
     *
     * @param to         收件人地址
     * @param subject    标题
     * @param htmlConent html内容
     */
    public static void sendHtmlEmail(String to, String subject, String htmlConent) {

        MimeMessage mimeMessage = MAIL_SENDER.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, ENCODING);
            messageHelper.setFrom(FROM);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);

            messageHelper.setText(htmlConent, true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        MAIL_SENDER.send(mimeMessage);
    }

    /**
     * 发送内嵌图片的邮件   （cid:资源名）
     *
     * @param to      收件人邮箱
     * @param subject 主题
     * @param html    html代码
     * @param img     图片
     * @return
     */
    public static void sendHtmlEmailInlineImg(String[] to, String subject, String html, File img) {
        boolean result = true;

        MimeMessage mimeMessage = MAIL_SENDER.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, ENCODING);
            messageHelper.setFrom(FROM);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            // true 表示启动HTML格式的邮件
            messageHelper.setText(html, true);

            messageHelper.addInline(img.getName(), img);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // 发送邮件
        MAIL_SENDER.send(mimeMessage);
    }


    /**
     * 发送带附件的邮件
     *
     * @param to         收件人地址
     * @param subject    标题
     * @param htmlConent html内容
     * @param attachment 附件
     */
    public static void sendFileEmail(String to, String subject, String htmlConent, File attachment) {

        MimeMessage mimeMessage = MAIL_SENDER.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, ENCODING);
            messageHelper.setFrom(FROM);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);

            messageHelper.setText(htmlConent, true);

            messageHelper.addAttachment(attachment.getName(), attachment);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        MAIL_SENDER.send(mimeMessage);
    }

    /**
     * 发送带附件的邮件
     *
     * @param to         收件人地址
     * @param subject    标题
     * @param htmlConent html内容
     * @param attachment 附件
     */
    public static void sendFileEmail(String to, String subject, String htmlConent, byte[] attachment) {

        MimeMessage mimeMessage = MAIL_SENDER.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, ENCODING);
            messageHelper.setFrom(FROM);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);

            messageHelper.setText(htmlConent, true);

            ByteArrayResource byteArrayResource = new ByteArrayResource(attachment);
            messageHelper.addAttachment("附件", byteArrayResource);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        MAIL_SENDER.send(mimeMessage);
    }
}
