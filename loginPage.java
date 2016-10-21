import java.util.Arrays;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class loginPage {
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JPanel loginPanel, classPagePanel;
   private JTextField usernameField;
   private JPasswordField passwordField;

   public loginPage(){
      mainFrameCreation();
   }

   public static void main(String[] args){
      loginPage LoginPage = new loginPage();  
      LoginPage.showLoginEvent();       
   }
      
   private void mainFrameCreation(){
      mainFrame = new JFrame("Welcome to Gradebook");
      mainFrame.setSize(1000,700);
      mainFrame.setLayout(new BorderLayout());
      mainFrame.getContentPane().setBackground(new Color(188, 86, 86));


      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
	        System.exit(0);
         }        
      });    
      loginPanel = new JPanel();
      loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

      mainFrame.add(loginPanel, BorderLayout.CENTER);
      mainFrame.setVisible(true);  
   }

   private void showLoginEvent(){
		headerLabel = new JLabel("",JLabel.CENTER );
      headerLabel.setText("Welcome to Gradebook"); 
      headerLabel.setFont(new Font("Georgia", Font.PLAIN, 48));
      headerLabel.setForeground(Color.white);
      
		JPanel headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

      JLabel  userLabel= new JLabel("Username: ", JLabel.CENTER);
      userLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));
      userLabel.setForeground(Color.white);
      JLabel  passLabel = new JLabel("Password: ", JLabel.CENTER);
      passLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));
      passLabel.setForeground(Color.white);
      usernameField = new JTextField(20);
      passwordField = new JPasswordField(20);

      JButton loginButton = new JButton("Login");

      loginButton.setActionCommand("Login");
      loginButton.addActionListener(new ButtonClickListener()); 


      loginPanel.setBackground(new Color(188, 86, 86));
		headerPanel.setBackground(new Color(188, 86, 86));
		headerPanel.add(headerLabel);
      loginPanel.add(userLabel);
      loginPanel.add(usernameField);
      loginPanel.add(passLabel);
      loginPanel.add(passwordField);
      loginPanel.add(loginButton);

      mainFrame.add(headerPanel, BorderLayout.NORTH);
      mainFrame.setVisible(true);  
   }

   private class ButtonClickListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();  
         if(command.equals( "Login" ))  {
	    String username = "cpapadakis";
   	    char[] password = { 'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};

            String userEntry = usernameField.getText();
	    char[] passEntry = passwordField.getPassword();
	    if (userEntry.equals(username) & Arrays.equals(passEntry, password)) {
               loginPanel.setVisible(false);
					classPageGUI();
            }
            else {
               JOptionPane.showMessageDialog(mainFrame, "Invalid username/password", "Error", JOptionPane.ERROR_MESSAGE);
            }
         } 
      }		
   }


	private void classPageGUI(){
		JPanel topPanel, leftPanel, classListPanel;
		JButton testClass;

      classPagePanel = new JPanel();
      classPagePanel.setLayout(new BorderLayout());

		topPanel = new JPanel();
		leftPanel = new JPanel();
		classListPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 5));
		leftPanel.setLayout(new FlowLayout());
		classListPanel.setLayout(new BoxLayout(classListPanel, BoxLayout.Y_AXIS));

		JButton createSemester = new JButton("Create new semester");
		JButton createClass = new JButton("Create new class");

		headerLabel.setText("Current Semester:");
      headerLabel.setFont(new Font("Georgia", Font.PLAIN, 30));
      headerLabel.setForeground(Color.white);

      String[] semesters = { "Fall 2016", "Spring 2015", "Fall 2015"};
		JComboBox<String> semesterList = new JComboBox<>(semesters);

      testClass= new JButton("Test label will go here");
		JButton testClass2 = new JButton("Test label 2");
		testClass.setAlignmentX(Component.CENTER_ALIGNMENT);
      testClass2.setAlignmentX(Component.CENTER_ALIGNMENT);


      classPagePanel.setBackground(new Color(188, 86, 86));
      topPanel.setBackground(new Color(188, 86, 86));
      leftPanel.setBackground(new Color(188, 86, 86));
      classListPanel.setBackground(new Color(188, 86, 86));

  	 	
   	topPanel.add(headerLabel);
		topPanel.add(semesterList);
		topPanel.add(createSemester);
		leftPanel.add(createClass);
		classListPanel.add(testClass);
		classListPanel.add(testClass2);
		classPagePanel.add(topPanel, BorderLayout.NORTH);
		classPagePanel.add(leftPanel, BorderLayout.WEST);
		classPagePanel.add(classListPanel, BorderLayout.CENTER);
   	classPagePanel.setVisible(true);

		mainFrame.add(classPagePanel);
	}
}
