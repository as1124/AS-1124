package com.volume2.ch1.io.zip;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.java.core.log.JavaCoreLogger;

/**
 * A Frame with a text area to show the contents of a file inside a zip
 * archive, a combo-box to select different files in the archive, and a 
 * menu to load a new archive.
 * 
 * @author as1124huang@gmail.com
 *
 */
public class ZipCaseFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1323491256963136675L;

	private JMenuItem openItem;

	private JMenuItem exitItem;

	private JTextArea fileText;

	private JComboBox<String> fileCombo;

	private String zipname;

	public ZipCaseFrame() {
		setTitle("ZipTest");

		//add the menu and the Open and Exit menu items
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");

		openItem = new JMenuItem("Open");
		menu.add(openItem);
		openItem.addActionListener(this);

		exitItem = new JMenuItem("Exit");
		menu.add(exitItem);
		exitItem.addActionListener(this);

		menuBar.add(menu);
		this.setJMenuBar(menuBar);

		//add the text area and combo box
		fileText = new JTextArea();
		fileText.setAutoscrolls(true);
		JScrollPane scroller = new JScrollPane(fileText);
		fileCombo = new JComboBox<>();
		fileCombo.addActionListener(this);

		add(fileCombo, BorderLayout.SOUTH);
		add(scroller, BorderLayout.CENTER);
	}

	/**
	 * Scans the contents of the zip archive and populates the combo box
	 */
	public void scanZipFile() {
		//一个background task
		new SwingWorker<Void, String>() {

			protected Void doInBackground() throws Exception {
				try (FileInputStream fileIn = new FileInputStream(zipname)) {
					ZipInputStream in = new ZipInputStream(fileIn);
					ZipEntry entry = null;
					while ((entry = in.getNextEntry()) != null) {
						publish(entry.getName());
						in.closeEntry();
					}
					in.close();
				} catch (Exception e) {
					JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
				}

				return null;
			}

			@Override
			protected void process(java.util.List<String> chunks) {
				for (String name : chunks) {
					fileCombo.addItem(name);
				}
			}
		}.execute();
	}

	/**
	 * Loads a file from the zip archive into the text area
	 * @param name
	 */
	public void loadZipFile(final String entryName) {
		fileCombo.setEditable(false);
		fileText.setText("");
		new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				try (FileInputStream fileIn = new FileInputStream(zipname)) {
					ZipInputStream in = new ZipInputStream(fileIn);
					ZipEntry entry = null;

					//find entry with matching name in archive
					while ((entry = in.getNextEntry()) != null) {
						if (entry.getName().equals(entryName)) {
							//read entry into text area
							Scanner entryIn = new Scanner(in);
							while (entryIn.hasNextLine()) {
								fileText.append(entryIn.nextLine());
								fileText.append("\r\n");
							}
							entryIn.close();
						}
						in.closeEntry();
					}

					in.close();
				} catch (Exception e) {
					JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
				}
				return null;
			}

			@Override
			protected void done() {
				super.done();
				fileCombo.setEnabled(true);
			}
		}.execute();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == null) {
			return;
		}

		if (source == openItem) {
			//open the archive file
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			int r = fileChooser.showOpenDialog(this);
			if (r == JFileChooser.APPROVE_OPTION) {
				this.zipname = fileChooser.getSelectedFile().getAbsolutePath();
				fileCombo.removeAllItems();
				scanZipFile();
			}
		} else if (source == exitItem) {
			System.exit(0);
		} else if (source == fileCombo) {
			loadZipFile((String) fileCombo.getSelectedItem());
		}

	}

}
