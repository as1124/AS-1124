package com.volume2.ch1.io.zip;

import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JFrame;

/**
 * zip文件操作
 * 
 * @author as1124huang@gmail.com
 *
 */
public class ZipTest {

	public static void main(String[] args) {

		//testZipRead();
		testZipWrite();
	}

	public static void testZipRead() {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				ZipTestFrame frame = new ZipTestFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
	
	public static void testZipWrite(){
		ZipOutputStream zipOut = null;
		String content = "亲爱的胡敏，我爱你。么么哒！";
		try {
			zipOut = new ZipOutputStream(new FileOutputStream("D:/darling.zip"));
			ZipEntry entry = new ZipEntry("abc/testEnry.txt");
			zipOut.putNextEntry(entry);
			zipOut.write(content.getBytes());
			zipOut.closeEntry();
			
			zipOut.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
