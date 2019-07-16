package com.volume2.ch5.local;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.logging.Level;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.java.core.log.JavaCoreLogger;

/**
 * This frame contains radio buttons to select a number format, a combo box to 
 * pick a locale, a text field to display a formatted number, and a button to 
 * parse the text field contents.
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class NumberFormatFrame extends JFrame {

	private static final long serialVersionUID = 3876715204527356709L;

	private Locale[] locales = null;

	private double currentNumber;

	private JComboBox<String> localeCombo = new JComboBox<>();

	private JButton parseButton = new JButton("Parse");

	private JTextField numberText = new JTextField(30);

	private JRadioButton numberRadioButton = new JRadioButton("Number");

	private JRadioButton currencyRadioButton = new JRadioButton("Currency");

	private JRadioButton percentRadioButton = new JRadioButton("Percent");

	private ButtonGroup rbGroup = new ButtonGroup();

	private NumberFormat currentNumberFormat = null;

	public NumberFormatFrame() {
		setLayout(new GridBagLayout());
		ActionListener listener = event -> updateDisplay();
		JPanel panel = new JPanel();
		addRadioButton(panel, numberRadioButton, rbGroup, listener);
		addRadioButton(panel, currencyRadioButton, rbGroup, listener);
		addRadioButton(panel, percentRadioButton, rbGroup, listener);

		GridBagConstraints labelConstraints = new GridBagConstraints();
		labelConstraints.gridx = 0;
		labelConstraints.gridy = 0;
		labelConstraints.anchor = GridBagConstraints.EAST;
		add(new JLabel("Locale: "), labelConstraints);

		GridBagConstraints panelConstraints = new GridBagConstraints();
		panelConstraints.gridx = 1;
		panelConstraints.gridy = 1;
		add(panel, panelConstraints);

		GridBagConstraints parseButtonConstraints = new GridBagConstraints();
		parseButtonConstraints.gridx = 0;
		parseButtonConstraints.gridy = 2;
//		parseButtonConstraints.insets = 2;
		add(parseButton, parseButtonConstraints);

		GridBagConstraints comboConstraints = new GridBagConstraints();
		comboConstraints.gridx = 1;
		comboConstraints.gridy = 0;
		comboConstraints.anchor = GridBagConstraints.WEST;
		add(localeCombo, comboConstraints);

		GridBagConstraints numberButtonConstraints = new GridBagConstraints();
		numberButtonConstraints.gridx = 1;
		numberButtonConstraints.gridy = 2;
		numberButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(numberText, numberButtonConstraints);
		locales = NumberFormat.getAvailableLocales();
		Comparator<Locale> comparator = (o1, o2) -> o1.getDisplayName().compareTo(o2.getDisplayName());
		Arrays.sort(locales, comparator);

		for (Locale loc : locales) {
			localeCombo.addItem(loc.getDisplayName());
		}
		localeCombo.setSelectedItem(Locale.getDefault().getDisplayName());
		currentNumber = 123456.78;
		updateDisplay();

		localeCombo.addActionListener(listener);

		parseButton.addActionListener(e -> {
			String s = numberText.getText().trim();
			try {
				Number number = currentNumberFormat.parse(s);
				if (number != null) {
					currentNumber = number.doubleValue();
					updateDisplay();
				} else {
					numberText.setText("Parse error: " + s);
				}
			} catch (ParseException e1) {
				numberText.setText("Parse Error: " + e1.getMessage());
				JavaCoreLogger.log(Level.SEVERE, e1.getMessage(), e1);
			}
		});

		pack();
	}

	/**
	 * Adds a radio button a container.
	 * @param panel the container into which to place the button
	 * @param radio the button
	 * @param group the button group
	 * @param listener listener button listener
	 */
	private void addRadioButton(Container panel, JRadioButton radio, ButtonGroup group, ActionListener listener) {
		radio.setSelected(group.getButtonCount() == 0);
		radio.addActionListener(listener);
		group.add(radio);
		panel.add(radio);
	}

	/**
	 * Updates the display and formats the number according to the user settings.
	 */
	public void updateDisplay() {
		Locale currentLocale = locales[localeCombo.getSelectedIndex()];
		currentNumberFormat = null;
		if (numberRadioButton.isSelected())
			currentNumberFormat = NumberFormat.getNumberInstance(currentLocale);
		else if (currencyRadioButton.isSelected())
			currentNumberFormat = NumberFormat.getCurrencyInstance(currentLocale);
		else 
			currentNumberFormat = NumberFormat.getPercentInstance(currentLocale);

		String str = currentNumberFormat.format(currentNumber);
		numberText.setText(str);
	}
}
