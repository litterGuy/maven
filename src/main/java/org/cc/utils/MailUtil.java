package org.cc.utils;


import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.cc.entity.MailEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class MailUtil {
	@Resource
	private JavaMailSender mailSender;
	/**
	 * 发送富文本邮件、带图片
	 */
	public  void sendRegisterMail(MailEntity mail) throws MessagingException{
	    MimeMessage mailMessage = mailSender.createMimeMessage(); 
	    //注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，         
	    //multipart模式 
	    MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true); 
	    //设置收件人，寄件人 
	    messageHelper.setTo(mail.getReceivers()); 
	    messageHelper.setFrom(mail.getSender()); 
	    messageHelper.setSubject(mail.getSubject()); 
	    //true 表示启动HTML格式的邮件 
	    messageHelper.setText(mail.getTemplate(),true); 
	    //TODO 邮件内插入图片
	   // FileSystemResource img = new FileSystemResource(new File("C:/Users/litterGuy/Desktop/image/2.jpg")); 
	   //<img src='cid:aaa'/>
	   // messageHelper.addInline("aaa",img); 
	    
	    //发送邮件 
	    mailSender.send(mailMessage); 
	}
}
