import java.util.*;
import java.util.List;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class loginPage {
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JPanel loginPanel, topPanel, classPagePanel;
   private JTextField usernameField;
   private JPasswordField passwordField;

   public loginPage(){
      mainFrameCreation();
   }

   public static void main(String[] args) {
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

   public class ButtonClickListener implements ActionListener{
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

			if (command.equals("createSemester")) {
				String newSemester = JOptionPane.showInputDialog(mainFrame, "Enter semester and year: ");

				try {
					File semesterFile = new File("/Users/abbyoneill/JavaPractice/Gradebook/testing.txt");

					if(!semesterFile.exists()){
						semesterFile.createNewFile();
					}

					FileWriter writer = new FileWriter(semesterFile.getName(),true);

					BufferedWriter buffer = new BufferedWriter(writer);
					buffer.write(newSemester + "\n");
					buffer.close();

					topPanel.revalidate();
					topPanel.repaint();
				}

				catch(IOException creationError){
    				creationError.printStackTrace();
    			}
			} 
      }		
   }


	public void classPageGUI(){
		JPanel leftPanel, classListPanel;
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

		createSemester.setActionCommand("createSemester");
      createSemester.addActionListener(new ButtonClickListener());

		headerLabel.setText("Current Semester:");
      headerLabel.setFont(new Font("Georgia", Font.PLAIN, 30));
      headerLabel.setForeground(Color.white);

      String[] semesters = getSemesterList();
		JComboBox<String> semesterList = new JComboBox<>(semesters);

		int size = semesters.length;

		JLabel classList = new JLabel("Classes: ");
      classList.setFont(new Font("Georgia", Font.PLAIN, 25));
      classList.setForeground(Color.white);

		JButton[] classButton = new JButton[size];

		for (int i=0; i<size; i++) {
			classButton[i] = new JButton("Class " + i);
			classButton[i].setAlignmentX(Component.CENTER_ALIGNMENT);
		}

      classPagePanel.setBackground(new Color(188, 86, 86));
      topPanel.setBackground(new Color(188, 86, 86));
      leftPanel.setBackground(new Color(188, 86, 86));
      classListPanel.setBackground(new Color(188, 86, 86));

  	 	
   	topPanel.add(headerLabel);
		topPanel.add(semesterList);
		topPanel.add(createSemester);
		leftPanel.add(createClass);
		classListPanel.add(classList);
		
		for (int j=0; j<size; j++) {
			classListPanel.add(classButton[j]);
		}

		classPagePanel.add(topPanel, BorderLayout.NORTH);
		classPagePanel.add(leftPanel, BorderLayout.WEST);
		classPagePanel.add(classListPanel, BorderLayout.CENTER);
   	classPagePanel.setVisible(true);

		mainFrame.add(classPagePanel);
	}

	private String[] getSemesterList() {
		try {
			Scanner semesterFile = new Scanner(new File("/Users/abbyoneill/JavaPractice/Gradebook/testing.txt"));

			List<String> lines = new ArrayList<String>();
			while (semesterFile.hasNextLine()) {
  				lines.add(semesterFile.nextLine());
			}
			String[] semesters = lines.toArray(new String[0]);
	
			return semesters;
		}
		catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
			String[] semesters = { "No semesters created" };	
		
			return semesters;
		}
	}
}

