package com.volume2.ch5.local;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RetireFrame extends JFrame {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 3422464873105449586L;

	private JTextField savingField = new JTextField(10);
	private JTextField contribField = new JTextField(10);
	private JTextField incomeField = new JTextField(10);
	private JTextField currentAgeField = new JTextField(4);
	private JTextField retireAgeField = new JTextField(4);
	private JTextField deathAgeField = new JTextField(4);
	private JTextField inflationPercentField = new JTextField(6);
	private JTextField investPercentField = new JTextField(6);
	private JTextArea retireText = new JTextArea(10, 25);
	private RetireComponent retireCanvas = new RetireComponent();
	private JButton computeButton = new JButton();
	private JLabel savingsLabel = new JLabel();
	private JLabel contribLabel = new JLabel();
	private JLabel incomeLabel = new JLabel();
	private JLabel currentAageLabel = new JLabel();
	private JLabel retireAgeLabel = new JLabel();
	private JLabel deathAgeLabel = new JLabel();
	private JLabel inflationPercentLabel = new JLabel();
	private JLabel investPercentLabel = new JLabel();
	private RetireInfo info = new RetireInfo();
	private Locale[] locales = {Locale.US, Locale.CHINA, Locale.GERMAN};
	private Locale currentLocale;
	private JComboBox<String> localeCombo = new JComboBox<>();
	private ResourceBundle res;
	private ResourceBundle resStrings;
	private NumberFormat currencyFormat;
	private NumberFormat numberFormat;
	private NumberFormat percentFormat;
	

	public RetireFrame() {
	}
	
}

