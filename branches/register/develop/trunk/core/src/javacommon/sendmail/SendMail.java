package javacommon.sendmail;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-7-8
 * Time: 11:33:15
 * To change this template use File | Settings | File Templates.
 */

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


//import javax.mail.internet.MailDateFormat;
@SuppressWarnings("unchecked")
public class SendMail
{
        public static String TEXT = "text/plain;charset=UTF-8";
        public static String HTML = "text/html;charset=UTF-8";
        private String host; //邮件服务器
        private String user; //用户名
        private String pass;//用户密码
        private String from;//发信人
        private String to;//收信人
        private String cc;//Carbon Copy, 抄送邮件给某人
        private String bc;//bcc Blind Carbon Copy,隐蔽副本 隐蔽抄送给某人
        private String subject;//邮件主题
        private BodyPart body;//邮件内容
        private boolean needAuth; //是否需要认证
        private List attaches;//邮件附件
        /**
         * 构造方法
         *
         */
        public SendMail() {
                needAuth = true;
                attaches = new ArrayList();
                /*String host=CacheManager.getInstance().getSetting("2");//设置smtp地址
                String user=CacheManager.getInstance().getSetting("3");//设置邮箱地址
                String pass=CacheManager.getInstance().getSetting("4");//设置邮箱密码
                String subject=CacheManager.getInstance().getSetting("5");//设置邮件主题*/
//                setHost(host);
//        	    setUser(user);
//        	    setPass(pass);
//        	    setFrom(user);
//        	    setSubject(subject);
                setHost("smtp.163.com");
        	    setUser("sendemail2011@163.com");
        	    setPass("sendemail");
        	    setSubject("审核申领结果通知");
        	    setFrom("sendemail2011@163.com");
        }
        /**
         * 构造方法
         * @param host
         */
        public SendMail(String host) {
                needAuth = true;
                attaches = new ArrayList();
                this.host = host;
        }
        /**
         * 构造方法
         * @param host
         * @param user
         * @param pass
         */
        public SendMail(String host, String user, String pass) {
                needAuth = true;
                attaches = new ArrayList();
                this.pass = pass;
        }
        //设置邮件服务器是否需要认证
        public void setNeedAuth(boolean needAuth) {
                this.needAuth = needAuth;
        }
        public void setFrom(String from) {
                this.from = from;
        }
        public void setTo(String to) {
                this.to = to;
        }
        public String getPass() {
                return pass;
        }
        public String getUser() {
                return user;
        }
        public void setPass(String string) {
                pass = string;
        }
        public void setUser(String string) {
                user = string;
        }

        public String getFrom() {
                return from;
        }
        public String getHost() {
                return host;
        }
        public boolean isNeedAuth() {
                return needAuth;
        }
        public String getSubject() {
                return subject;
        }
        public void setHost(String string) {
                host = string;
        }
        public void setBlindTo(String bc) {
                this.bc = bc;
        }
        public void setCopyTo(String cc) {
                this.cc = cc;
        }
        public void setSubject(String subject) {
                this.subject = subject;
        }
/**
* 设置邮件内容的形式
* @param string
* @param contentType
*/
        public void setBody(String string, String contentType) {
                try {
                        body = new MimeBodyPart();
                        DataHandler dh = new DataHandler(string, contentType);
                        body.setDataHandler(dh);
                } catch (Exception exception) {
                }
        }
/**
* 设置邮件的内容的格式为文本格式
* @param string
*/
        public void setBodyAsText(String string) {
                setBody(string, TEXT);
        }
        /**
         * 以HTMl的形式存放内容
	* @param string
	*/
        public void setBodyAsHTML(String string) {
                setBody(string, HTML);
        }
/**
* 从文件中自动导入邮件内容
* @param filename
*/
        public void setBodyFromFile(String filename) {
                try {
                        BodyPart mdp = new MimeBodyPart();
                        FileDataSource fds = new FileDataSource(filename);
                        DataHandler dh = new DataHandler(fds);
                        mdp.setDataHandler(dh);
                        attaches.add(mdp);
                } catch (Exception exception) {
                }
        }
/**
* 从一个URL导入邮件的内容
* @param url
*/
        public void setBodyFromUrl(String url) {
                try {
                        BodyPart mdp = new MimeBodyPart();
                        URLDataSource ur = new URLDataSource(new URL(url));
                        DataHandler dh = new DataHandler(ur);
                        mdp.setDataHandler(dh);
                        attaches.add(mdp);
                } catch (Exception exception) {
                }
        }
       /**
        * 将String中的内容存放入文件showname，并将这个文件作为附件发送给收件人
        * @param string 为邮件的内容
        * @param showname 显示的文件的名字
        */
                public void addAttachFromString(String string, String showname) {
                        try {
                                BodyPart mdp = new MimeBodyPart();
                                DataHandler dh = new DataHandler(string, TEXT);
                                mdp.setFileName(MimeUtility.encodeWord(showname, "UTF-8", null));
                                mdp.setDataHandler(dh);
                                attaches.add(mdp);
                        } catch (Exception exception) {
                        }
                }
        /**
        * filename为邮件附件
        *在收信人的地方以showname这个文件名来显示
        * @param filename
        * @param showname
        */
                public void addAttachFromFile(String filename, String showname) {
                        try {
                                BodyPart mdp = new MimeBodyPart();
                                FileDataSource fds = new FileDataSource(filename);
                                DataHandler dh = new DataHandler(fds);
                                mdp.setFileName(MimeUtility.encodeWord(showname, "UTF-8", null));
                                mdp.setDataHandler(dh);
                                attaches.add(mdp);
                        } catch (Exception exception) {
                        }
                }
        /**
        * 将互联网上的一个文件作为附件发送给收信人
        * 并在收信人处显示文件的名字为showname
        * @param url
        * @param showname
        */
                public void addAttachFromUrl(String url, String showname) {
                        try {
                                BodyPart mdp = new MimeBodyPart();
                                URLDataSource ur = new URLDataSource(new URL(url));
                                DataHandler dh = new DataHandler(ur);
                                mdp.setFileName(MimeUtility.encodeWord(showname, "UTF-8", null));
                                mdp.setDataHandler(dh);
                                attaches.add(mdp);
                        } catch (Exception exception) {
                        }
                }
        /**
        * 发送邮件,需要身份认证
        * @throws Exception
        */
                public void send() throws Exception {
                        try {
                           //*****会话类*****//
                                Properties props = new Properties();
                                if (host != null && !host.trim().equals(""))
                                        props.setProperty("mail.smtp.host", host);//key   value
                                else
                                        throw new Exception("没有指定发送邮件服务器");
                                if (needAuth)
                                        props.setProperty("mail.smtp.auth", "true");
                                Session s = Session.getInstance(props, null);
                                //*****消息类*****//
                                MimeMessage msg = new MimeMessage(s);
                                msg.setSubject("审核申领结果通知");//设置邮件主题
                                msg.setSentDate(new Date());//设置邮件发送时间
                                //*****地址类*****//
                                if (from != null)
                                        msg.addFrom(InternetAddress.parse(from));//指定发件人
                                else
                                        throw new Exception("没有指定发件人");
                                if (to != null)
                                        msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));//指定收件人
                                else
                                        throw new Exception("没有指定收件人地址");
                                if (cc != null)
                                        msg.addRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));//指定抄送
                                if (bc != null)
                                        msg.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(bc));//指定密送
                                Multipart mm = new MimeMultipart();
                                if (body != null)
                                        mm.addBodyPart(body);//设置邮件的附件
                                for (int i = 0; i < attaches.size(); i++) {
                                        BodyPart part = (BodyPart) attaches.get(i);
                                        mm.addBodyPart(part);
                                }
                                msg.setContent(mm);//设置邮件的内容
                                //*****传输类*****//
                                msg.saveChanges();//保存所有改变
                                Transport transport = s.getTransport("smtp");//发送邮件服务器（SMTP）
                                transport.connect(host, user, pass);//访问邮件服务器
                                transport.sendMessage(msg, msg.getAllRecipients());//发送信息
                                transport.close();//关闭邮件传输
                        } catch (Exception e) {
                           e.printStackTrace();
                                throw new Exception("发送邮件失败:", e);
                        }
                }


//                public static void main(String[] args)
//                {
//                	SendMail sendMail=new SendMail();
//                	String host = "smtp.163.com";
//                	String user = "courage52dan@163.com";
//                	String password = "1385378654511";
//                	String subject = "邮件";
//                	//subject = new ToChinese().getUTF-8(subject);
//                	String to = "hyunhui2006@126.com";
//                	String from = "courage52dan@163.com";
//                	String bodyashtml = "正文";
//                	//bodyashtml = new ToChinese().getUTF-8(bodyashtml);
//                	//String attachfile = request.getParameter("attachfile");
//                	//attachfile = new ToChinese().getUTF-8(attachfile);
//                	try {
//                	    SendMail send = new SendMail();
//                	    send.setHost(host);
//                	    send.setUser(user);
//                	    send.setPass(password);
//                	    send.setSubject(subject);
//                	    send.setTo(to);
//                	    send.setFrom(from);
//                	    send.setBodyAsHTML(bodyashtml);//以HTML形式存放内容
//                	    //send.addAttachFromFile(attachfile,"aa.txt");//附件路径，附件名称
//                	    //System.out.println(attachfile);
//                	    send.send();
//                	    System.out.println("邮件发送成功！！！");
//                	   } catch (Exception e) {
//                	    System.out.println(e.getMessage());
//                	   }
//                }
        }

