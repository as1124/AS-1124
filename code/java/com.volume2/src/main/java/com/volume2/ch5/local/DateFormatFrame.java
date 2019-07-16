package com.volume2.ch5.local;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class DateFormatFrame extends JFrame {

	private static final long serialVersionUID = 6347804053532571984L;

	private Locale[] locales;

	private Date currentDate;

	private Date currentTime;

	private DateFormat currentDateFormat;

	private DateFormat currentTimeFormat;

	private JComboBox<String> localCombo = new JComboBox<>();

	private JButton dateParseButton = new JButton("Parse Date");

	private JButton timeParseButton = new JButton("Parse Time");

}
