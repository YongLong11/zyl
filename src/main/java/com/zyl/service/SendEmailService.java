package com.zyl.service;

import com.alibaba.fastjson.JSON;
import com.zyl.pojo.SendEmailDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class SendEmailService {
    @Resource
    private JavaMailSender javaMailSender;
    @Resource
    private TemplateEngine templateEngine;
    @Resource
    MailProperties mailProperties;
    public void sendEmail(SendEmailDto sendEmailDTO) throws MessagingException {
        Assert.notNull(sendEmailDTO, "邮件发送入参为空");
        Assert.isTrue(!CollectionUtils.isEmpty(sendEmailDTO.getTos()), "邮件收件人为空");
        String from = sendEmailDTO.getFrom();
        if(StringUtils.isBlank(from)){
            from = mailProperties.getUsername();
        }else {
            Assert.isTrue(sendEmailDTO.getFrom().contains(".com"), "请发件人指定邮箱类型");
        }
        Assert.isTrue(sendEmailDTO.getTos().stream().allMatch(to -> to.contains(".com")), "请发件人指定邮箱类型");
        log.info("发送邮件，title —> {}, 收件人 -> {}", sendEmailDTO.getTitle(), JSON.toJSONString(sendEmailDTO.getTos()));
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setFrom(from);
        messageHelper.setTo(sendEmailDTO.getTos().stream().distinct().toArray(String[]::new));
        if(!CollectionUtils.isEmpty(sendEmailDTO.getCcs())){
            messageHelper.setCc(sendEmailDTO.getCcs().stream().distinct().toArray(String[]::new));
        }
        messageHelper.setSubject(sendEmailDTO.getTitle());
        // 创建一个 Thymeleaf 上下文
        Context context = new Context();

        // 添加动态数据到上下文
        context.setVariable("columns", sendEmailDTO.getColumns());
        context.setVariable("data", sendEmailDTO.getCells());
        context.setVariable("title", sendEmailDTO.getTitle());
        // 使用模板引擎生成 HTML
        String html = templateEngine.process("sendEmail.html", context);
        messageHelper.setText(html, true);
        // 发送邮件
        javaMailSender.send(mimeMessage);
    }

}
