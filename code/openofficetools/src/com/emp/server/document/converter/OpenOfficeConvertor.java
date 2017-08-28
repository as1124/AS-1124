package com.primeton.tools.openoffice;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.DocumentFormatRegistry;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

/**
 * Office文档转换服务
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 * 
 */
public class OpenOfficeConvertor {

	//
	private static OpenOfficeConnection connection = null;

	private static Process sofficeProcess = null;

	private static Object lock = new Object();

	/**
	 * @param openOfficeHome OpenOffice 安装目录
	 * @param servicePort OpenOffice 监听的端口，默认使用<code>8100</code>
	 * @param sourceFile 输入文件
	 * @param targetFile 输出文件
	 * @return
	 */
	public static boolean convertFile(String openOfficeHome, int servicePort, String sourceFile, String targetFile) {
//		String openOfficeHome = "C:\\Program Files (x86)\\OpenOffice 4";
		File localFile = new File(sourceFile);
		if (!localFile.exists()) {
			System.out.println("[ERROR]源文件不存在: " + sourceFile);
			return false;
		}

		boolean flag = checkAndStareService(openOfficeHome, servicePort);
		if (!flag) {
			return false;
		}

		System.out.println("[INFO]开始转换文档: " + sourceFile);
		File destFile = new File(targetFile);
		if (destFile.getParentFile().exists() == false) {
			destFile.getParentFile().mkdirs();
		}
		OpenOfficeConnection serviceConnection = getConnection(servicePort);
		if(serviceConnection!=null && serviceConnection.isConnected()) {
			OpenOfficeDocumentConverter converter = new OpenOfficeDocumentConverter(serviceConnection);
	
			DocumentFormatRegistry formatRegistry = new DefaultDocumentFormatRegistry();
			String sourceExtension = FilenameUtils.getExtension(localFile.getName());
			DocumentFormat inputFormat = formatRegistry.getFormatByFileExtension(sourceExtension);
			if(!inputFormat.isImportable()) {
				inputFormat = guessDocumentFormat(inputFormat);
			}
			
			String targetExtension = FilenameUtils.getExtension(destFile.getName());
			DocumentFormat outputFormat = formatRegistry.getFormatByFileExtension(targetExtension);
			
			converter.convert(localFile, inputFormat, destFile, outputFormat);
	
			System.out.println("[INFO]文档转换完成");
			return destFile.exists();
		} else {
			return false;
		}
	}

	/**
	 * 尝试处理系统默认的文件格式
	 * @param normalFormat 系统默认的文件格式
	 * @return
	 */
	public static DocumentFormat guessDocumentFormat(DocumentFormat normalFormat) {
		DocumentFormat inputFormat = new DocumentFormat(normalFormat.getName(), DocumentFamily.PRESENTATION,
				normalFormat.getMimeType(), normalFormat.getFileExtension());

		Iterator<?> it = null;
		
		//Register "TEXT" DocumentFamily
		Map<?, ?> textOptions = normalFormat.getExportOptions(DocumentFamily.TEXT);
		if (textOptions != null && !textOptions.isEmpty()) {
			it = textOptions.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next().toString();
				inputFormat.setExportOption(DocumentFamily.TEXT, key, textOptions.get(key));
			}
		}
		
		//Register "PRESENTATION" DocumentFamily
		Map<?, ?> presentationOptions = normalFormat.getExportOptions(DocumentFamily.PRESENTATION);
		if (presentationOptions != null && !presentationOptions.isEmpty()) {
			it = presentationOptions.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next().toString();
				inputFormat.setExportOption(DocumentFamily.PRESENTATION, key, presentationOptions.get(key));
			}
		}
		
		//Register "SPREADSHEET" DocumentFamily
		Map<?, ?> spreadsheetOptions = normalFormat.getExportOptions(DocumentFamily.SPREADSHEET);
		if (spreadsheetOptions != null && !spreadsheetOptions.isEmpty()) {
			it = spreadsheetOptions.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next().toString();
				inputFormat.setExportOption(DocumentFamily.SPREADSHEET, key, spreadsheetOptions.get(key));
			}
		}
		
		//Register "DRAWING" DocumentFamily
		Map<?, ?> drawingOptions = normalFormat.getExportOptions(DocumentFamily.DRAWING);
		if (drawingOptions != null && !drawingOptions.isEmpty()) {
			it = drawingOptions.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next().toString();
				inputFormat.setExportOption(DocumentFamily.DRAWING, key, drawingOptions.get(key));
			}
		}
		
		//
		Map<?, ?> importOptions = normalFormat.getImportOptions();
		if (importOptions != null && !importOptions.isEmpty()) {
			it = importOptions.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next().toString();
				inputFormat.setImportOption(key, importOptions.get(key));
			}
		}
		
		return inputFormat;
	}
		
	/**
	 * 检查<code>OpenOffice</code>转换服务是否启动，如果没有则启动则启动服务<br/>
	 * 按照配置的端口启动转化服务监听,默认使用<code>8100</code>
	 * 
	 * @param openOfficeHome
	 *            <code>OpenOffice</code> 软件安装目录
	 * @param servicePort
	 * @return
	 */
	protected static boolean checkAndStareService(String openOfficeHome, int servicePort) {
		if(servicePort <= 0)
			servicePort = 8100;
		synchronized (lock) {
			if(sofficeProcess!=null) {
				return true;
			}
			
			Runtime runObject = Runtime.getRuntime();
			Map<String, String> map = System.getenv();
			Iterator<String> it = map.keySet().iterator();
			String[] envp = new String[map.size()];
			int i = 0;
			while (it.hasNext()) {
				String key = it.next();
				envp[i] = key + "=" + map.get(key);
				i++;
			}

			File sofficeEXE = new File(openOfficeHome + "/program");
			try {
				if (new File(sofficeEXE, "soffice.exe").exists() == false) {
					System.err.println("[ERROR]系统未安装OpenOffice软件服务");
					return false;
				}
				sofficeProcess = runObject.exec("cmd /c start soffice -headless -accept=\"socket,host=127.0.0.1,port=" + servicePort + ";urp;\"",
						envp, sofficeEXE);
				Thread.sleep(500);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		}
	}

	public static OpenOfficeConnection getConnection(int servicePort) {
		if(connection!=null && connection.isConnected())
			return connection;
		connection = new SocketOpenOfficeConnection(servicePort);
		try {
			connection.connect();
		} catch (ConnectException e) {
			System.err.println("[ERROR]无法连接转换服务，请检查进程状态");
			e.printStackTrace();
			return null;
		}
		return connection;
	}
	
	public static boolean isSupport(String sourceExtension) {
		DocumentFormatRegistry formatRegistry = new DefaultDocumentFormatRegistry();
		DocumentFormat inputFormat = formatRegistry.getFormatByFileExtension(sourceExtension);
		return (inputFormat!=null);
	}
	
	
	/**
	 * 关闭<code>OpenOffice</code> 转换服务
	 */
	public static void stopService() {
		System.out.println("[INFO]关闭office转换服务");
		if (connection != null) {
			connection.disconnect();
			connection = null;
		}
		
		// 如果p不为空，那么要清空
		if (null != sofficeProcess) {
			sofficeProcess.destroy();
			sofficeProcess = null;
		}
		System.out.println("[INFO]关闭office转换成功!");
	}

}
