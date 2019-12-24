package com.as1124.document.converter.impl.libre;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

import org.jodconverter.LocalConverter;
import org.jodconverter.OfficeDocumentConverter;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.as1124.document.converter.AbstractTextConverter;
import com.as1124.document.converter.DocumentConvertException;

/**
 * 文档转换服务，支持常用Office格式，文本格式；目标文件PDF<br>
 * <li>该转换器的使用需要安装 <a href="https://www.libreoffice.org/download/download/">LibreOffice</a>, 
 * 需要注意的是请选择好正确的版本：如64bit系统不要安装32bit的LibreOffice.
 * <li>软件安装完成后需要将<code> ${install_home}/program </code>加入到系统PATH变量中
 * 
 * @author As-1124(mailto:as1124huang@gmail.com)
 */
public class LibreDocumentConverter extends AbstractTextConverter {

	/**
	 * Comment for <code>OPT_OFFICE_HOME</code>, 设置LibreOffice的安装路径<br>
	 * 
	 * {@link #doConvert(File, File, Map)}
	 */
	public static final String OPT_OFFICE_HOME = "LIBRE_OFFICE";

	/**
	 * Comment for <code>OPT_TASKS_PER_PROCESS</code>, 设置每个转换进程最大可支持的任务数
	 * 
	 * {@link #doConvert(File, File, Map)}
	 */
	public static final String OPT_TASKS_PER_PROCESS = "MAX_TASK_NUM";

	Logger logger = LoggerFactory.getLogger(LibreDocumentConverter.class);

	private OfficeManager officeManager = null;

	public boolean isSupported(String inputExtension, String targetExtension) {
		// 不支持用LibreOffice转成图片
		return ("png|jpg|jpeg|gif|bmp|wmf|emf|tiff|".indexOf(targetExtension.toLowerCase()) <= -1);
	}

	protected boolean doConvert(File inputSource, File targetFile, Map<String, ?> opts)
			throws IOException, DocumentConvertException {
		if (opts == null || !opts.containsKey(OPT_OFFICE_HOME)) {
			throw new DocumentConvertException(
					"[ERROR] Please set Install-Home of LibreOffice software using key Document2PDFConverter#OPT_OFFICE_HOME");
		}
		String officeHome = opts.get(OPT_OFFICE_HOME).toString();
		File programExe = new File(officeHome, "program/soffice.exe");
		if (!programExe.exists()) {
			throw new DocumentConvertException(
					MessageFormat.format("[ERROR] LibreOffice software service not avaiable in {0}.", officeHome));
		}

		int maxTaskNum = 200;
		if (opts.containsKey(OPT_TASKS_PER_PROCESS)) {
			Integer.parseInt(opts.get(OPT_TASKS_PER_PROCESS).toString());
		}

		// 防止用户没有将dll加入到系统path导致无法加载转化器
		System.setProperty("java.library.path",
			System.getProperty("java.library.path") + ";" + programExe.getAbsolutePath());

		officeManager = LocalOfficeManager.builder().officeHome(officeHome).pipeNames(inputSource.getAbsolutePath())
				.disableOpengl(false).maxTasksPerProcess(maxTaskNum).workingDir(new File(officeHome, "program"))
				.build();
		LocalConverter converter = LocalConverter.builder().officeManager(officeManager).build();

		//		DefaultOfficeManagerBuilder managerBuilder = new DefaultOfficeManagerBuilder();
		//		managerBuilder.setConnectionProtocol(OfficeConnectionProtocol.PIPE);
		//		managerBuilder.setMaxTasksPerProcess(maxTaskNum);
		//		managerBuilder.setOfficeHome(officeHome);
		//		managerBuilder.setPipeName(inputSource.getAbsolutePath());
		//		managerBuilder.setWorkingDir(new File(officeHome, "program"));
		//		officeManager = managerBuilder.build();
		//		OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);

		try {
			if (!officeManager.isRunning()) {
				officeManager.start();
			}
			//			converter.convert(inputSource, targetFile);
			converter.convert(inputSource);
		} catch (Exception e) {
			DocumentConvertException dce = new DocumentConvertException(e.getMessage());
			dce.initCause(e.getCause());
			throw dce;
		}

		return true;
	}

	@Override
	public void afterConver(File inputSource, File targetFile) {
		super.afterConver(inputSource, targetFile);
		try {
			if (this.officeManager != null) {
				this.officeManager.stop();
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
	}

	/**
	 * @return Returns the officeManager.
	 */
	public OfficeManager getOfficeManager() {
		return officeManager;
	}

	/**
	 * @param officeManager The officeManager to set.
	 */
	protected void setOfficeManager(OfficeManager officeManager) {
		this.officeManager = officeManager;
	}

}
