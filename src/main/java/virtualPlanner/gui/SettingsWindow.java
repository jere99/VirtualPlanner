package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import virtualPlanner.backend.Course;
import virtualPlanner.reference.Fonts;
import virtualPlanner.reference.Preferences;

public class SettingsWindow {

	/**Size of the settings window*/
	private static final Dimension SETTINGS_SIZE = new Dimension(430, 500);

	/**Size of the numUpcomingDays JTextField*/
	private static final Dimension UPCOMING_DAYS_SIZE = new Dimension(30, 25);

	/**Size of this specific GUISampleColorButton*/
	private static final Dimension BUTTON_SIZE = new Dimension(250, 30);

	/**The JFrame for this Settings Window*/
	private JFrame frame;
	/**The GUIController for this Settings Window frame*/
	private GUIController controller;
	
	
	/**This ArrayList holds of the GUISampleColorButtons that give a preview to the user for the color they pick for each class*/
	private ArrayList<GUISampleColorButton> buttons;
	/**JRadioButton to customize whether assignments show on the Date assigned*/
	private JRadioButton buttonShowDateAssigned;
	/**JRadioButton to customize whether assignments show on the Date due*/
	private JRadioButton buttonShowDateDue;
	/**Field which represents the number of days the Upcoming Events JList show include*/
	private JTextField upcomingDaysField;


	/**
	 * Constructor for this SettingsWindow
	 * @param controller the GUIController for this SettingsWindow
	 */
	protected SettingsWindow(GUIController controller) {
		//Set Field
		this.controller = controller;

		//Initialize + set settings for frame
		frame = new JFrame();
		frame.setResizable(false);
		frame.setSize(SETTINGS_SIZE);
		
		//Override windowClosing
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			//This method is called whenever the user exits out of the settings window
			public void windowClosing(WindowEvent windowEvent) {
				
				//Check that the UpcomingDaysField has valid input
				try {Preferences.setNumDaysUpcoming(Integer.parseInt(upcomingDaysField.getText()));}
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Invalid Input for Upcoming Days", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//Save all the settings to the BackEnd
				if(buttonShowDateDue.isSelected())
					Preferences.setDisplayOnDue(true);
				else
					Preferences.setDisplayOnDue(false);
				for(GUISampleColorButton b: buttons)
					Preferences.addColor(b.getCourse().getID(), b.getBackground());
				
				//Close from and inform controller
				frame.dispose();
				controller.settingsClosed();
			}
		});
		addComponents();
		frame.setVisible(true);
	}

	/**
	 * Adds the Java Swing components to this Settings Window's JFrame
	 */
	private void addComponents() {
		
		//JLabel for Course Colors
		JLabel blockLabel = new JLabel("Course Colors:");
		blockLabel.setFont(Fonts.CALENDAR_SETTINGS);
		blockLabel.setForeground(Color.BLACK);
		//Nested JFrame to respect 
		JPanel panelBlockLabel = new JPanel();
		panelBlockLabel.add(blockLabel);

		Box mainVertical = Box.createVerticalBox();
		mainVertical.add(panelBlockLabel);

		//Create a GUISampleColorButton for each course
		Course[] courses = controller.getAllCourses();
		buttons = new ArrayList<GUISampleColorButton>();
		for (Course course : courses) {
			GUISampleColorButton b = new GUISampleColorButton(course);
			b.setBackground(Preferences.getColor(course.getID()));
			buttons.add(b);
			JPanel tempPanel = new JPanel();
			tempPanel.add(b);
			mainVertical.add(tempPanel);
		}

		//Option to choose when to Display the Assignments: Assigned or Due
		JLabel labelShowAssignmentOptions = new JLabel("Show assignments when: ");
		labelShowAssignmentOptions.setFont(Fonts.CALENDAR_SETTINGS);
		labelShowAssignmentOptions.setForeground(Color.BLACK);
		JPanel panelLabelShowAssignmentOptions = new JPanel();
		panelLabelShowAssignmentOptions.add(labelShowAssignmentOptions);

		buttonShowDateAssigned = new JRadioButton("Assigned");
		buttonShowDateAssigned.setFont(Fonts.CALENDAR_SETTINGS);
		JPanel panelButtonShowDateAssigned = new JPanel();
		panelButtonShowDateAssigned.add(buttonShowDateAssigned);

		buttonShowDateDue = new JRadioButton("Due");
		buttonShowDateDue.setFont(Fonts.CALENDAR_SETTINGS);
		JPanel panelButtonShowDateDue = new JPanel();
		panelButtonShowDateDue.add(buttonShowDateDue);

		if(Preferences.displayOnDue())
			buttonShowDateDue.setSelected(true);
		else
			buttonShowDateAssigned.setSelected(true);

		//Make at max 1 JRadioButton clickable
		ButtonGroup group = new ButtonGroup();
		group.add(buttonShowDateAssigned);
		group.add(buttonShowDateDue);

		Box showAssignmentOptionsBox = Box.createHorizontalBox();
		showAssignmentOptionsBox.add(panelLabelShowAssignmentOptions);
		showAssignmentOptionsBox.add(panelButtonShowDateAssigned);
		showAssignmentOptionsBox.add(panelButtonShowDateDue);

		mainVertical.add(showAssignmentOptionsBox);

		//Number of days of upcoming events
		JLabel numDaysLabel = new JLabel("Number of Days 'Upcoming Events' shows: ");
		numDaysLabel.setFont(Fonts.CALENDAR_SETTINGS);
		JPanel panelNumDaysLabel = new JPanel();
		panelNumDaysLabel.add(numDaysLabel);

		upcomingDaysField = new JTextField("" + Preferences.numDaysUpcoming());
		upcomingDaysField.setPreferredSize(UPCOMING_DAYS_SIZE);
		upcomingDaysField.setFont(Fonts.CALENDAR_SETTINGS);
		upcomingDaysField.setHorizontalAlignment(JTextField.CENTER);
		JPanel panelUpcomingDaysField = new JPanel();
		panelUpcomingDaysField.add(upcomingDaysField);

		Box showUpcomingDaysBox = Box.createHorizontalBox();
		showUpcomingDaysBox.add(panelNumDaysLabel);
		showUpcomingDaysBox.add(panelUpcomingDaysField);

		mainVertical.add(showUpcomingDaysBox);

		frame.add(mainVertical);
	}

	/**
	 * This class represents an extension to the javax.swing.JButton class with added functionality for displaying colors and choosing colors
	 * Used in the Settings JFrame, where the user can choose custom colors for each course 
	 * @author KevinGao
	 */
	@SuppressWarnings("serial")
	private class GUISampleColorButton extends JButton implements ActionListener {

		/**Course which this GUISampleColorButton corresponds to*/
		private Course course;

		/**
		 * Constructor for GUISampleColorButton
		 * Sets all the defaults of the butotn
		 * @param name text for the GUISampleColorButton to show
		 * @param course which corresponds to this GUISampleColorButton
		 */
		private GUISampleColorButton(Course course) {
			super(course.getName());
			this.setFocusPainted(false);
			this.setFont(Fonts.CALENDAR_BLOCK);
			this.setBackground(Color.WHITE);
			this.setPreferredSize(BUTTON_SIZE);
			this.setOpaque(true);
			this.setBorderPainted(false);
			this.addActionListener(this);
			this.course = course;
		}

		/**
		 * @return the course which corresponds to this GUISampleColorButton
		 */
		private Course getCourse() {
			return this.course;
		}

		/**
		 * actionPerformed method which handles the ActionEvents for all GUISampleColorButtons
		 */
		public void actionPerformed(ActionEvent e) {
			//Show a color palatte and save the user's choice
			Color c = JColorChooser.showDialog(this, this.getText() + " Block Color", this.getBackground());

			//If the user cancelled out, do nothing
			if (c == null)
				return;

			//If the user selected a color, update the color
			this.setBackground(c);
		}

	}

}
