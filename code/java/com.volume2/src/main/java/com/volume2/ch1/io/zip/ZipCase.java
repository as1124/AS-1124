package com.volume2.ch1.io.zip;

import java.awt.EventQueue;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JFrame;

import com.java.core.log.JavaCoreLogger;

/**
 * zip文件操作
 * 
 * @author as1124huang@gmail.com
 *
 */
public class ZipCase {

	public static void main(String[] args) {

		testZipRead();

		testZipWrite();
	}

	public static void testZipRead() {
		EventQueue.invokeLater(() -> {
			ZipCaseFrame frame = new ZipCaseFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}

	public static void testZipWrite() {
		ZipOutputStream zipOut = null;
		String content = "亲爱的你，你在哪里...！";
		try (FileOutputStream fileOut = new FileOutputStream("D:/darling.zip");) {
			zipOut = new ZipOutputStream(fileOut);
			ZipEntry entry = new ZipEntry("abc/testEnry.txt");
			zipOut.putNextEntry(entry);
			zipOut.write(content.getBytes());
			zipOut.closeEntry();

			zipOut.close();
		} catch (IOException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
