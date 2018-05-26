package virtualPlanner.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Class the represents a Login window for the VirtualPlanner
 * @author Kevin
 */
public class GUILoginWindow implements ActionListener, FocusListener, KeyListener{

	/**This class' JFrame*/
	private JFrame frame;

	/**Title label shown at the town of the window*/
	private static JLabel titleLabel = new JLabel("Virtual Planner Login");

	/**The size of the JFrame*/
	private static final Dimension frameSize = new Dimension(450, 500);
	/**Default Sizes of Fields and Buttons*/
	private static final Dimension defaultSize = new Dimension(300, 35);

	/**Fonts for this JFrame*/
	private static final Font titleFont = new Font("Dialog", Font.BOLD, 28);
	private static final Font infoFont = new Font("Dialog", Font.BOLD, 18);
	private static final Font defaultFont = new Font("Dialog", Font.PLAIN, 18);
	private static final Font buttonFont = new Font("Dialog", Font.BOLD, 22);

	/**Default hints for the user input fields*/
	private static final String usernameFieldDefaultText = "Username";
	private static final String passwordFieldDefaultText = "Password";


	/**Info label to directly contact/inform the user*/
	private JLabel infoLabel;

	/**JTextField for username*/
	private JTextField usernameField;

	/**JPasswordField for password*/
	private JPasswordField passwordField;

	/**JCheckBox to determine whether or not user wants to stay logged in*/
	private JCheckBox remember;

	/**JButton to submit user input*/
	private JButton loginButton;

	/**JButton for new Account Window*/
	private JButton createAccountButton;

	/**Boolean used in FocusListener: ignoring the defualt firstFocus*/
	private boolean firstFocus;

	/**Boolean used in toggling between Log-in and Create Account features*/
	private boolean showingLoginFeatures;

	/**
	 * Constructor which initializes a Login Window
	 */
	public GUILoginWindow(){
		//Frame settings
		frame = new JFrame("Virtual Planner");
		frame.setSize(frameSize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Title JLabel
		titleLabel.setFont(titleFont);
		titleLabel.setPreferredSize(defaultSize);
		JPanel panelTitleLabel = new JPanel();
		panelTitleLabel.add(titleLabel);

		infoLabel = new JLabel("Please Enter User Credentials:");
		infoLabel.setPreferredSize(defaultSize);
		infoLabel.setFont(infoFont);
		infoLabel.setForeground(Color.BLACK);
		JPanel panelInfoLabel = new JPanel();
		panelInfoLabel.add(infoLabel);

		//Username JTextField
		usernameField = new JTextField(usernameFieldDefaultText);
		usernameField.setPreferredSize(defaultSize);
		usernameField.setFont(defaultFont);
		usernameField.setForeground(Color.GRAY);
		usernameField.addFocusListener(this);
		usernameField.addKeyListener(this);
		JPanel panelUsernameField = new JPanel();
		panelUsernameField.add(usernameField);

		//Password JTextField
		passwordField = new JPasswordField(passwordFieldDefaultText);
		passwordField.setPreferredSize(defaultSize);
		passwordField.setFont(defaultFont);
		passwordField.setForeground(Color.GRAY);
		passwordField.addFocusListener(this);
		System.out.println((int)(passwordField.getEchoChar()));
		passwordField.setEchoChar((char) 0);
		JPanel panelPasswordField = new JPanel();
		panelPasswordField.add(passwordField);

		//Stay Logged In JCheckBox and JLabel
		remember = new JCheckBox();
		remember.setOpaque(true);
		remember.setForeground(Color.WHITE);
		JLabel labelRemember = new JLabel("Keep me logged in");
		Box rememberHorizontal = Box.createHorizontalBox();
		rememberHorizontal.add(remember);
		rememberHorizontal.add(labelRemember);
		JPanel panelRememberHorizontal = new JPanel();
		panelRememberHorizontal.add(rememberHorizontal);
		panelRememberHorizontal.setPreferredSize(defaultSize);

		//Login JButton
		loginButton = new JButton("Log in");
		loginButton.setPreferredSize(defaultSize);
		loginButton.setBackground(Color.CYAN);
		loginButton.setForeground(Color.WHITE);
		loginButton.setFont(buttonFont);
		loginButton.setFocusPainted(false);
		loginButton.addActionListener(this);
		frame.getRootPane().setDefaultButton(loginButton);
		JPanel panelLoginButton = new JPanel();
		panelLoginButton.add(loginButton);

		//New Account JButton
		createAccountButton = new JButton("Create an Account");
		createAccountButton.setPreferredSize(defaultSize);
		createAccountButton.setBackground(Color.BLUE);
		createAccountButton.setForeground(Color.WHITE);
		createAccountButton.setFont(buttonFont);
		createAccountButton.setFocusPainted(false);
		createAccountButton.addActionListener(this);
		JPanel panelCreateAccountButton = new JPanel();
		panelCreateAccountButton.add(createAccountButton);

		//Final Organizing Box
		Box mainVertical = Box.createVerticalBox();
		mainVertical.add(panelTitleLabel);
		mainVertical.add(panelInfoLabel);
		mainVertical.add(panelUsernameField);
		mainVertical.add(panelPasswordField);
		mainVertical.add(panelRememberHorizontal);
		mainVertical.add(panelLoginButton);
		mainVertical.add(panelCreateAccountButton);

		//Final Settings
		firstFocus = true;
		showingLoginFeatures = true;
		JPanel mainPanel = new JPanel();
		mainPanel.add(mainVertical);
		frame.add(mainPanel);
		frame.setVisible(true);
	}

	/**
	 * Method that handles login attempts
	 */
	private void login(){

		//Obtain current text in passowrd securely
		String password = "";
		char[] pswrd = passwordField.getPassword();
		for (char c : pswrd)
			password += c;

		boolean successfulLogin = false; //New Account/User(usernameField.getText, password);

		if (successfulLogin){
			frame.dispose();
		} 
		else{
			infoLabel.setForeground(Color.RED);
			infoLabel.setText("Invalid Username or Password");
		}

		System.out.println("Login Attempt:" + usernameField.getText() + " + " + password + " + remember? " + remember.isSelected());
	}

	/**
	 * Method that processes new accounts
	 */
	private void createAccount(){
		//Obtain current text in passowrd
		String password = "";
		char[] pswrd = passwordField.getPassword();
		for (char c : pswrd)
			password += c;
		
		boolean successfulNewAccount = false; //New Account/User(usernameField.getText, password);
		
		if (successfulNewAccount){
			frame.dispose();
		} 
		else{
			infoLabel.setForeground(Color.RED);
			infoLabel.setText("Invalid Input");
		}
		
		System.out.println("New Account:" + usernameField.getText() + " + " + password + " + remember? " + remember.isSelected());

	}

	/**
	 * This method toggles between the Log-in and create new account interfaces
	 * Changes some Swing Components
	 */
	private void toggleCreateAccountFeatures(){
		if(showingLoginFeatures){
			infoLabel.setText("Create Account:");
			infoLabel.setForeground(Color.BLACK);
			loginButton.setText("Finish and Log in");
			loginButton.setBackground(Color.GREEN);
			createAccountButton.setText("Back");
			createAccountButton.setBackground(Color.RED);
			showingLoginFeatures = false;
		}
		else{
			infoLabel.setText("Please Enter User Credentials:");
			infoLabel.setForeground(Color.BLACK);
			loginButton.setText("Log in");
			loginButton.setBackground(Color.CYAN);
			createAccountButton.setText("Create an Account");
			createAccountButton.setBackground(Color.BLUE);
			showingLoginFeatures = true;
		}
	}

	public static void main(String[] args)
	{
		new GUILoginWindow();
	}

	/**
	 * This method handles ActionEvents for the LoginWindow
	 */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src.equals(loginButton)){
			if (showingLoginFeatures){
				login();
			}
			else{
				createAccount();
			}
		}

		else if (src.equals(createAccountButton)){
			toggleCreateAccountFeatures();
		}

	}

	/**
	 * This method handles focusGained FocusEvents for the Login Window
	 * Manual Implementation of the JTextField "Hint"
	 */
	public void focusGained(FocusEvent e) {

		//The usernameField is set to the default focus on startup: 
		//Don't trigger any Events on startup
		if (firstFocus){
			return;
		}

		//Obtain Source
		Object src = e.getSource();

		//usernameField gains focus
		if (src.equals(usernameField)){
			//User Clicks into the field with no user keyboard input
			if (usernameField.getText().equals(usernameFieldDefaultText)){
				//Remove hint and change color of text
				usernameField.setText("");
				usernameField.setForeground(Color.BLACK);
			}
		}

		//passwordField gains focus
		else if (src.equals(passwordField)){
			//Obtain current text in passowrd
			String password = "";
			char[] pswrd = passwordField.getPassword();
			for (char c : pswrd)
				password += c;

			//User Clicks into the field with no user keyboard input
			if (password.equals(passwordFieldDefaultText)){
				//Remove hint and change color of text
				passwordField.setText("");
				passwordField.setForeground(Color.BLACK);
				//Set the passwordField to hide characters
				passwordField.setEchoChar((char)8226);
			}
		}
	}

	/**
	 * This method handles focusLost FocusEvents for the Login Window
	 * Manual Implementation of the JTextField "Hint"
	 */
	public void focusLost(FocusEvent e) {

		//Obtain source
		Object src = e.getSource();

		//usernameField loses focus
		if (src.equals(usernameField)){

			//usernameField has lost first focus: handle events as usual now
			firstFocus = false;

			//If the field is blank
			if(usernameField.getText().equals("")){
				//Reinstate the "Hint", reset the color
				usernameField.setText(usernameFieldDefaultText);
				usernameField.setForeground(Color.GRAY);
			}
		}

		//passwordField loses focus
		else if (src.equals(passwordField)){
			//Obtain current text in passowrd
			String password = "";
			char[] pswrd = passwordField.getPassword();
			for (char c : pswrd)
				password += c;

			//If the field is blacnk
			if (password.equals("")){
				//Reinstate the "Hint, reset the color
				passwordField.setText(passwordFieldDefaultText);
				passwordField.setForeground(Color.GRAY);
				//Set the passwordField to show characters
				passwordField.setEchoChar((char)0);
			}
		}
	}

	/**
	 * This method handles keyPressed KeyEvents for the Login Window
	 * Manual Implementation of the JTextField "Hint"
	 */
	public void keyPressed(KeyEvent e) {

		//Normally the "Hint" disappears as soon a field gains focus
		//However, on startup, in order for the "Hint" to show up on the first item which gains default focus,
		//A special KeyEvent handler is used to delete the "Hint" on KeyPressed when the user starts typing.
		//This works even if the user enters the password first
		if(firstFocus && usernameField.hasFocus()){
			firstFocus = false;
			usernameField.setText("");
			usernameField.setForeground(Color.BLACK);
		}
	}

	/**
	 * This method handles keyReleased KeyEvents for the Login Window
	 * Unused method
	 */
	public void keyReleased(KeyEvent e) {

	}

	/**
	 * This method handles keyTyped KeyEvents for the Login Window
	 * Unused method
	 */
	public void keyTyped(KeyEvent e) {

	}
}