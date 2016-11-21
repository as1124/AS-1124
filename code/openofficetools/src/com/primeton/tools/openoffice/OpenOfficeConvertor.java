package com.primeton.tools.openoffice;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Iterator;
import java.util.Map;

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

	private static OpenOfficeConnection connection = null;

	private static Process sofficeProcess = null;

	/**
	 * <code>OpenOffice</code> 转换默认端口
	 */
	public static int PORT = 8100;

	private static Object lock = new Object();

	/**
	 * @param officeFile
	 *            office文档
	 * @param htmlFile
	 *            输出文件
	 * @return
	 */
	public static boolean toHtmlFile(String officeFile, String htmlFile) {
		//ATTENTION OpenOffice的安装目录应该从配置文件获取
		String openOfficeHome = "C:\\Program Files (x86)\\OpenOffice 4";
		boolean flag = checkAndStareService(openOfficeHome);
		if (!flag) {
			return false;
		}

		System.out.println("[INFO]开始文档转换: " + officeFile);
		File targetFile = new File(htmlFile);
		if (targetFile.getParentFile().exists() == false) {
			targetFile.getParentFile().mkdirs();
		}
		OpenOfficeDocumentConverter converter = new OpenOfficeDocumentConverter(
				connection);
		converter.convert(new File(officeFile), targetFile);
		System.out.println("[INFO]文档转换完成");
		return targetFile.exists();
	}

	/**
	 * 检查<code>OpenOffice</code>转换服务是否启动，如果没有则启动则启动服务<br/>
	 * 按照配置的端口启动转化服务监听,默认使用<code>8100</code>
	 * 
	 * @param openOfficeHome
	 *            <code>OpenOffice</code> 软件安装目录
	 * @return
	 */
	public static boolean checkAndStareService(String openOfficeHome) {
		synchronized (lock) {
			if (connection != null && connection.isConnected()) {
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
					System.err.println("[ERROR]系统未安装OpenOffice服务");
					return false;
				}
				sofficeProcess = runObject.exec(
						"cmd /c start soffice -headless -accept=\"socket,host=127.0.0.1,port="
								+ PORT + ";urp;\"", envp, sofficeEXE);
				Thread.sleep(1000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			connection = new SocketOpenOfficeConnection(PORT);
			System.out.println("[INFO]启动office转换服务");
			try {
				connection.connect();
				System.out.println("[INFO]office转换服务启动成功");
			} catch (ConnectException e) {
				System.err.println("[ERROR]office转换服务启动失败");
				e.printStackTrace();
				return false;
			}
			return true;
		}
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

	public static void main(String[] args) {
		OpenOfficeConvertor.toHtmlFile("D:/test.doc",
				"D:/testhtml/huangjw.html");

	}

}
