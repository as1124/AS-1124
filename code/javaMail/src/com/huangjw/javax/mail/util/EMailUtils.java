package com.huangjw.javax.mail.util;

public class EMailUtils {

//	/**
//	 * 分析附件
//	 * @param part
//	 */
//	private static void parseAttachment(Part part){
//		try {
//			String dsp = part.getDisposition();
//			if(part.isMimeType("multipart/*") || part.isMimeType("message/rfc822")){
//				if (part.getContent() instanceof Multipart) {
//					Multipart multipart = (Multipart) part.getContent();
//					int counts = multipart.getCount();
//					for (int i = 0; i < counts; i++) {
//						parseAttachment(multipart.getBodyPart(i));
//					}
//				}
//			}else if (dsp != null && dsp.equals(Part.ATTACHMENT)) {
//	            attachmentCount++;
//	            String filename = part.getFileName();
//	            if (filename.indexOf("=?gb18030?") != -1) {
//                    filename = filename.replace("gb18030", "gb2312");
//                }
//                filename = MimeUtility.decodeText(filename);
//	        }else if(part.isMimeType("image/*")){
//	        	//将图片作为附件处理
//	        	attachmentCount++;
//	        }
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * 根本就没用
//	 * @param userName
//	 * @param password
//	 * @param mailID
//	 * @param index
//	 */
//	@Deprecated
//	@Bizlet("更改邮件状态为已读")
//	public static void setSeen(String userName, String password, String mailID, int index){
//		userName = userName + DOMAIN;
//		Session session = null;
//		Store store = null;
//        Folder folder = null;
//		
//        try {
//			session = createImapSession();
//			store = session.getStore(PROTOCOL_IMAP);
//            store.connect(HOST, userName, password);
//
//            folder = store.getFolder("INBOX");
//            folder.open(Folder.READ_WRITE);
//
//            //int size = folder.getMessageCount();
//            Message[] message = folder.getMessages();
//            
//            //先不考虑用户删除邮件
//            int oldSize = 0;
//            if(inboxCount.containsKey(userName)){
//            	oldSize = inboxCount.get(userName);
//            }else{
//            	oldSize = message.length;
//            }
//            int currentSize = message.length;
//            int newMessageCount = currentSize - oldSize;
//            Message msg = message[currentSize - newMessageCount -index - 1];
//            Flags seenFlag = new Flags();
//            seenFlag.add(Flags.Flag.SEEN);
//            folder.setFlags(new Message[]{msg}, seenFlag, true);
//            msg.setFlag(Flags.Flag.SEEN, true);
//            //msg.saveChanges();
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (folder != null) {
//                    folder.close(false);
//                }
//                if (store != null) {
//                    store.close();
//                }
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            }
//        }
//	}
//	
//	/**
//	 * This method checks for content-type based on which, it processes and
//	 * fetches the content of the message
//	 */
//	public static void writePart(Part p) throws Exception {
//		// check if the content is plain text
//		if (p.isMimeType("text/plain")) {
//			System.out.println("This is plain text");
//			System.out.println("---------------------------");
//			strBuf.append( p.getContent().toString() );
//		}
//		
//		// check if the content has attachment
//		else if (p.isMimeType("multipart/*")) {
//			System.out.println("This is a Multipart");
//			System.out.println("---------------------------");
//			Multipart mp = (Multipart) p.getContent();
//			int count = mp.getCount();
//			for (int i = 0; i < count; i++)
//				writePart(mp.getBodyPart(i));
//		}
//		
//		// check if the content is a nested message
//		else if (p.isMimeType("message/rfc822")) {
//			System.out.println("This is a Nested Message");
//			System.out.println("---------------------------");
//			writePart((Part) p.getContent());
//		}
//		
//		// check if the content is an inline image
//		else if (p.isMimeType("image/jpeg")) {
//			/*System.out.println("--------> image/jpeg");
//			Object o = p.getContent();
//
//			InputStream x = (InputStream) o;
//			// Construct the required byte array
//			System.out.println("x.length = " + x.available());
//			int i = 0;
//			byte[] bArray = new byte[x.available()];
//
//			while ((i = (int) ((InputStream) x).available()) > 0) {
//				int result = (int) (((InputStream) x).read(bArray));
//				if (result == -1)
//					break;
//			}
//			FileOutputStream f2 = new FileOutputStream("D:/tmp/image.jpg");
//			f2.write(bArray);*/
//		} else if (p.getContentType().contains("image/")) {
//			/*System.out.println("content type" + p.getContentType());
//			File f = new File("image" + new Date().getTime() + ".jpg");
//			DataOutputStream output = new DataOutputStream(
//					new BufferedOutputStream(new FileOutputStream(f)));
//			com.sun.mail.util.BASE64DecoderStream test = (com.sun.mail.util.BASE64DecoderStream) p.getContent();
//			byte[] buffer = new byte[1024];
//			int bytesRead;
//			while ((bytesRead = test.read(buffer)) != -1) {
//				output.write(buffer, 0, bytesRead);
//			}*/
//		} else {
//			Object o = p.getContent();
//			if (o instanceof String) {
//				System.out.println("This is a string");
//				System.out.println("---------------------------");
//				strBuf.append(o.toString());
//			} else if (o instanceof InputStream) {
//				System.out.println("This is just an input stream");
//				System.out.println("---------------------------");
//				/*InputStream is = (InputStream) o;
//				is = (InputStream) o;
//				int c;
//				while ((c = is.read()) != -1)
//					System.out.write(c);*/
//			} else {
//				System.out.println("This is an unknown type");
//				System.out.println("---------------------------");
//				System.out.println(o.toString());
//			}
//		}
//		
//		String dsp = p.getDisposition();
//		if (dsp != null && dsp.equals(Part.ATTACHMENT)) {
//           attachmentCount++;
//        // 获取附件
//           String filename = p.getFileName();
//           if (filename != null) {
//               if (filename.indexOf("=?gb18030?") != -1) {
//                   filename = filename.replace("gb18030", "gb2312");
//               }
//               filename = MimeUtility.decodeText(filename);
//           }
//           attach.add(filename);
//       } else if(p.isMimeType("image/*")){
//       	//将图片作为附件处理
//       	attachmentCount++;
//       	attach.add(p.getFileName());
//       }
//		
//	}
	//
//	/**
//	 * 处理基础信息
//	 * 
//	 * @param msg
//	 * @return
//	 * @throws MessagingException 
//	 */
//	private static EMailMessage handleEnvelope(Message msg) throws MessagingException{
//		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd hh:MM");
//		EMailMessage item = new EMailMessage();
//        item.setSubject(msg.getSubject());
//        item.setDate(formatter.format(msg.getSentDate()));
//        
//        parseAttachment(msg);
//        if(attachmentCount > 0){
//        	item.setHasAttachment(true);
//        	attachmentCount = 0;
//        }
//        
//        Flags.Flag[] sysFlags = msg.getFlags().getSystemFlags();
//        for(Flags.Flag flag :sysFlags){
//        	if(flag == Flags.Flag.SEEN){
//        		item.setIsread(true);
//        		break;
//        	}else{
//        		item.setIsread(false);
//        	}
//        }
//        Address addr = msg.getFrom()[0];
//        if(addr instanceof InternetAddress){
//        	String nickName = ((InternetAddress)addr).getPersonal();
//        	
//        	String name = ((InternetAddress)addr).getAddress();
//        	if(nickName == null || nickName.trim().equals(""))
//        		nickName = name;
//        	MailAccount user = new MailAccount( nickName, name );
//        	item.setSentUser(user);
//        }else{
//        	MailAccount user = new MailAccount( addr.toString(), addr.toString() );
//        	item.setSentUser(user);
//        }
//        item.setMessageID(((IMAPMessage)msg).getMessageID());
//
//        Address[] addrs = null;
//		// TO
//		if ((addrs = msg.getRecipients(Message.RecipientType.TO)) != null) {
//			ArrayList<MailAccount> tolist = new ArrayList<MailAccount>();
//			for (int j = 0; j < addrs.length; j++){
//				if(addrs[j] instanceof InternetAddress){
//					String nickName = ((InternetAddress)addrs[j]).getPersonal();
//		        	
//		        	String name = ((InternetAddress)addrs[j]).getAddress();
//		        	if(nickName == null || nickName.trim().equals(""))
//		        		nickName = name;
//		        	MailAccount user = new MailAccount( nickName, name );
//		        	tolist.add(user);
//		        }else{
//		        	MailAccount user = new MailAccount( addrs[j].toString(), addrs[j].toString() );
//		        	tolist.add(user);
//		        }
//			}
//			item.setReceivers(tolist.toArray(new MailAccount[tolist.size()]));
//		}
//
//		// cc
//		if ((addrs = msg.getRecipients(Message.RecipientType.CC)) != null) {
//			ArrayList<MailAccount> tolist = new ArrayList<MailAccount>();
//			for (int j = 0; j < addrs.length; j++){
//				if(addrs[j] instanceof InternetAddress){
//					String nickName = ((InternetAddress)addrs[j]).getPersonal();
//		        	
//		        	String name = ((InternetAddress)addrs[j]).getAddress();
//		        	if(nickName == null || nickName.trim().equals(""))
//		        		nickName = name;
//		        	MailAccount user = new MailAccount( nickName, name );
//		        	tolist.add(user);
//		        }else{
//		        	MailAccount user = new MailAccount( addrs[j].toString(), addrs[j].toString() );
//		        	tolist.add(user);
//		        }
//			}
//			item.setCc(tolist.toArray(new MailAccount[tolist.size()]));
//		}
//		
//		return item;
//	}
}
