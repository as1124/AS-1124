package com.as1124.document.converter.impl.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.util.XMLHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.as1124.document.converter.AbstractTextConverter;
import com.as1124.document.converter.DocumentConvertException;
import com.as1124.document.converter.DocumentServiceConstants;

/**
 * Excel文档转HTML，文件后缀：<code>xls</code>
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class Excel2HTMLConverter extends AbstractTextConverter {

	Logger logger = LoggerFactory.getLogger(Excel2HTMLConverter.class);

	@Override
	public boolean doConvert(File inputSource, File targetFile, Map<String, ?> opts)
			throws IOException, DocumentConvertException {

		try (FileInputStream inStream = new FileInputStream(inputSource);
				HSSFWorkbook excelBook = new HSSFWorkbook(inStream);) {
			ExcelToHtmlConverter excelToHtmlConverter = new ExcelToHtmlConverter(
					XMLHelper.getDocumentBuilderFactory().newDocumentBuilder().newDocument());
			excelToHtmlConverter.processWorkbook(excelBook);

			Document htmlDocument = excelToHtmlConverter.getDocument();

			DOMSource domSource = new DOMSource(htmlDocument);
			StreamResult streamResult = new StreamResult(targetFile);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer serializer = tf.newTransformer();
			String encoding = DocumentServiceConstants.CHARSET_UTF8;
			if (getConvertOpts() != null && getConvertOpts().containsKey(OPT_CHARSET)) {
				encoding = getConvertOpts().get(OPT_CHARSET).toString();
			}
			serializer.setOutputProperty(OutputKeys.ENCODING, encoding);
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(OutputKeys.METHOD, "html");
			serializer.transform(domSource, streamResult);
		} catch (IllegalArgumentException | TransformerFactoryConfigurationError | TransformerException e) {
			logger.error(e.getMessage(), e);
		} catch (ParserConfigurationException e) {
			DocumentConvertException dce = new DocumentConvertException(e.getMessage());
			dce.initCause(e.getCause());
			throw dce;
		}

		return true;
	}

	@Override
	public void afterConver(File inputSource, File targetFile) {
		// do nothing
	}

	@Override
	public boolean isSupported(String inputExtension, String targetExtension) {
		return ("xls".equalsIgnoreCase(inputExtension) && "html".equalsIgnoreCase(targetExtension));
	}

}
