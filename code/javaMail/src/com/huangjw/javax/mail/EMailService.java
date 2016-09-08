package com.huangjw.javax.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.MessageIDTerm;
import javax.mail.search.SearchTerm;

import org.apache.commons.lang.StringUtils;

import com.huangjw.javax.mail.model.EMailAccount;
import com.huangjw.javax.mail.model.EMailFolder;
import com.huangjw.javax.mail.model.EMailMessage;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.pop3.POP3Folder;

/**
 * 邮件收发功能
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class EMailService {
	
	private static Properties settings = new Properties();
	
	public static String getProperty(String key){
		if(settings==null || settings.isEmpty()){
			FileInputStream in;
			try {
				in = new FileInputStream("mail_settings.properties");
				settings.load(in);
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		return settings.getProperty(key);
	}
	
	private static Properties getPOP3Settings(){
		Properties props = new Properties();
        props.put("mail.pop3.ssl.enable", Boolean.parseBoolean(getProperty(IEMailConstants.KEY_IN_SSL)));
        props.put("mail.pop3.host", getProperty(IEMailConstants.KEY_IN_HOST));
        props.put("mail.pop3.port", getProperty(IEMailConstants.KEY_IN_PORT));
        return props;
	}
	
	private static Properties getIMAPSettings(){
		Properties props = new Properties();
        props.put("mail.imap.ssl.enable", Boolean.parseBoolean(getProperty(IEMailConstants.KEY_IN_SSL)));
        props.put("mail.imap.host", getProperty(IEMailConstants.KEY_IN_HOST));
        props.put("mail.imap.port", getProperty(IEMailConstants.KEY_IN_PORT));
        return props;
	}
	
	private static Properties getSMTPSettings(){
		Properties props = new Properties();
        props.put("mail.smtp.ssl.enable", Boolean.parseBoolean(getProperty(IEMailConstants.KEY_IN_SSL)));
        props.put("mail.smtp.host", getProperty(IEMailConstants.KEY_IN_HOST));
        props.put("mail.smtp.port", getProperty(IEMailConstants.KEY_IN_PORT));
        return props;
	}
	
	/**
	 * 登陆校验(1-成功, 0-失败, -1-用户名密码错误)
	 * @param userName
	 * @param password
	 * @return
	 */
	public static int checkUser(String userName, String password){
		Session session = Session.getDefaultInstance(getIMAPSettings());
		Store store = null;
		try {
			store = session.getStore(IEMailConstants.PROTOCOL_IMAP);
			if(store == null){
				return 0;
			}
			store.connect(userName, password);
			return 1;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			return 0;
		} catch (MessagingException e) {
			e.printStackTrace();
			if(e instanceof AuthenticationFailedException){
				return -1;
			}else{
				return 0;
			}
		}finally{
			try {
				if(store != null)
					store.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取收件箱邮件列表
	 * (策略：获取最新的时候，永远只显示前20条，查看历史时要出去中间新到达的条数；不考虑用户删除邮件)
	 * @param userName
	 * @param password
	 * @param urlName {@link EMailFolder#getUrlName()}，文件夹的全URL名称
	 * @param topMsgID 客户端时间最top的邮件ID
	 * @param receiveCount 客户端以收取的数量
	 * @return
	 */
	public static EMailMessage[] getMailsInFolder(String userName, String password, String urlName, String topMsgID, int receiveCount) {
        ArrayList<EMailMessage> result = new ArrayList<EMailMessage>();
        String protocol = getProperty(IEMailConstants.KEY_IN_PROTOCAL);
		Store store = null;
        Folder folder = null;
		
        try {
        	if(protocol.trim().equalsIgnoreCase(IEMailConstants.PROTOCOL_POP3)){
        		Session session = Session.getDefaultInstance(getPOP3Settings());
    			store = session.getStore(IEMailConstants.PROTOCOL_POP3);
    		}else{
    			Session session = Session.getDefaultInstance(getIMAPSettings());
    			store = session.getStore(IEMailConstants.PROTOCOL_IMAP);
    		}
            store.connect(userName, password);
            if(StringUtils.isBlank(urlName))
            	urlName = "INBOX";
            folder = store.getFolder(urlName);
            folder.open(Folder.HOLDS_MESSAGES);
            
            
            //ATTENTION 获取所有邮件会不会影响性能???
            // 获取的邮件是按照时间由远及近的
            Message[] messages = folder.getMessages();
            int start = messages.length-1;
            if(receiveCount > 0){
	            for(; start>0; start--){
	            	MimeMessage msg = (MimeMessage) messages[start];
	            	if(msg.getMessageID().equals(topMsgID)){
	            		break;
	            	}
	            }
            }
            
            // 客户端接收的邮件中时间最早的一封的下标
            start = start - receiveCount;
            int end = start - 20;
            if(end < 0){
            	end = 0;
            }
            for(; start>=end; start--){
            	EMailMessage item = new EMailMessage();
            	item.setMessageID(((MimeMessage)messages[start]).getMessageID());
            	InternetAddress sender = (InternetAddress) messages[start].getFrom()[0];
            	item.setFromUser(new EMailAccount(sender.getPersonal(), sender.getAddress()));
//	            item = handleEnvelope(msg);
	            item.setTo(((MimeMessage)messages[start]).getRecipients(RecipientType.TO));
	            item.setCc(((MimeMessage)messages[start]).getRecipients(RecipientType.CC));
	            item.setBcc(((MimeMessage)messages[start]).getRecipients(RecipientType.BCC));
	            item.setSentDate(messages[start].getSentDate().toString());
	            item.setReceiveDate(messages[start].getReceivedDate().toString());
	            item.setSubject(messages[start].getSubject());
	            item.setMsgNum(messages[start].getMessageNumber());
	            Flags.Flag[] flags = messages[start].getFlags().getSystemFlags();
	            for(Flags.Flag flag : flags){
	            	if(flag == Flags.Flag.SEEN){
	            		item.setRead(true);
	            	} else if(flag == Flags.Flag.FLAGGED){
	            		item.setFlaged(true);
	            	}
	            }
	            System.out.println(item.getMessageID());
	            result.add(item);
            }
            
            EMailMessage[] array = result.toArray(new EMailMessage[]{});
            return array;
            
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            try {
                if (store != null) {
                    store.close();
                }
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return null;
	}
	
	/**
	 * 查看邮件内容
	 * 
	 * @param userName
	 * @param password
	 * @param msgID
	 * @param folderName
	 */
	public static void getMailContent(String userName, String password, String msgID, String folderName) {
		String protocol = getProperty(IEMailConstants.KEY_IN_PROTOCAL);
		Store store = null;
		Folder folder = null;

		try {
			if (protocol.trim().equalsIgnoreCase(IEMailConstants.PROTOCOL_POP3)) {
				Session session = Session.getDefaultInstance(getPOP3Settings());
				store = session.getStore(IEMailConstants.PROTOCOL_POP3);
			} else {
				Session session = Session.getDefaultInstance(getIMAPSettings());
				store = session.getStore(IEMailConstants.PROTOCOL_IMAP);
			}
			store.connect(userName, password);
			if (StringUtils.isBlank(folderName))
				folderName = "INBOX";
			folder = store.getFolder(folderName);
			folder.open(Folder.HOLDS_MESSAGES);
			SearchTerm query = new MessageIDTerm(msgID);
			Message[] queryResult = folder.search(query);
			System.out.println(queryResult);
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (store != null) {
					store.close();
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		// return null;
	}

	
	/**
	 * 获取文件夹列表
	 * 
	 * @param userName
	 * @param password
	 * @param parent 父文件夹全路径名，若为空则代表从根目录开始查找，<br>
	 *  {@link EMailFolder#getUrlName()}
	 * @return
	 */
	public static EMailFolder[] getFolders(String userName, String password, String parent){
		ArrayList<EMailFolder> result = new ArrayList<EMailFolder>();
		Session session = null;
		Store store = null;
        Folder defaultFolder = null;
        String protocol = getProperty(IEMailConstants.KEY_IN_PROTOCAL);
        
        try {
        	if(protocol.trim().equalsIgnoreCase(IEMailConstants.PROTOCOL_POP3)){
    			session = Session.getDefaultInstance(getPOP3Settings());
    			store = session.getStore(IEMailConstants.PROTOCOL_POP3);
    		}else{
    			session = Session.getDefaultInstance(getIMAPSettings());
    			store = session.getStore(IEMailConstants.PROTOCOL_IMAP);
    		}
            store.connect(userName, password);
            defaultFolder = store.getDefaultFolder();
            if(StringUtils.isNotBlank(parent)){
            	defaultFolder = defaultFolder.getFolder(parent);
            	defaultFolder.open(Folder.HOLDS_FOLDERS);
            }
            
            Folder[] folders = defaultFolder.list();
            for(Folder folder : folders){
            	EMailFolder item = new EMailFolder();
            	item.setName(folder.getFullName());
            	item.setUrlName(folder.getURLName().getFile());
            	item.setCount(folder.getMessageCount());
            	item.setNewCount(folder.getNewMessageCount());
            	item.setType(folder.getType());
            	if(folder instanceof IMAPFolder){
            		item.setUnReadCount( ((IMAPFolder)folder).getUnreadMessageCount() );
            	} else if(folder instanceof POP3Folder){
            		item.setUnReadCount( ((POP3Folder)folder).getUnreadMessageCount() );
            	} else {
            		item.setUnReadCount(item.getNewCount());
            	}
            	result.add(item);
            }
            
            return result.toArray(new EMailFolder[result.size()]);
            
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try {
				if(store != null)
					store.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
        }
		return null;
	}
	
	/**
	 * 发送邮件
	 * 
	 * @param subject 邮件主题
	 * @param userName
	 * @param password
	 * @param to 收件人
	 * @param cc 抄送人
	 * @param bcc 密送人
	 * @param bodyText 邮件正文
	 * @param files 附件
	 * @param draft 存草稿
	 */
	public void sendMail(String subject, String userName, String password, String[] to,
			String[] cc, String[] bcc, String bodyText, List<File> files, boolean draft) {
		Store store = null;
		String protocol = getProperty(IEMailConstants.KEY_OUT_PROTOCAL);
        
        try {
    		Session session = Session.getDefaultInstance(getSMTPSettings());
    		store = session.getStore(protocol);
            store.connect(userName, password);
            
            MimeMessage message = new MimeMessage(session);
			// 设置发件人地址
			message.setFrom(new InternetAddress(userName));
			// 设置主题
			message.setSubject(subject);

			// 设置收件人
			if (to != null) {
				for (String item : to) {
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(item));
				}
			}

			if (cc != null) {
				for (String item : cc) {
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(item));
				}
			}

			if (bcc != null) {
				for (String item : bcc) {
					message.addRecipient(Message.RecipientType.BCC, new InternetAddress(item));
				}
			}

			if (draft) {
				message.setFlag(Flags.Flag.DRAFT, true);
			}

			// 设置邮件内容
			Multipart multipart = new MimeMultipart();
			MimeBodyPart txtPart = new MimeBodyPart();
			txtPart.setContent(bodyText, "text/html;charset=UTF-8");
			multipart.addBodyPart(txtPart);

			// 添加附件
			if (files!=null && files.size()> 0) {
				for (File f : files) {
					MimeBodyPart part = new MimeBodyPart();
					FileDataSource fds = new FileDataSource(f); // 得到数据源
					part.setDataHandler(new DataHandler(fds)); // 得到附件本身并至入BodyPart
					String filename = MimeUtility.encodeText(fds.getName());
					part.setFileName(filename); // 得到文件名同样至入BodyPart
					multipart.addBodyPart(part);
				}
			}

			message.setContent(multipart);
			message.setFlag(Flags.Flag.RECENT, true);
			message.saveChanges();
			if (!draft) {
				Transport transport = session.getTransport("smtp");
				transport.connect(getProperty(IEMailConstants.KEY_OUT_HOST), 
						Integer.parseInt(getProperty(IEMailConstants.KEY_OUT_PORT)), userName, password);
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
			}
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		String userName = "huangjw@primeton.com";// change accordingly
		String password = "As1124huang";// change accordingly
		String msgID = "<2016090714414622801242@yovole.com>";
		
		// Call method fetch
//		getFolders(userName, password, "");
//		getMailsInFolder(userName, password, ""	, null, 0);
		getMailContent(userName, password, msgID, "INBOX");
	}

}
