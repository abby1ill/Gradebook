import java.util.Arrays;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class loginPage {
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JPanel loginPanel;
   private JTextField usernameField;
   private JPasswordField passwordField;

   public loginPage(){
      loginGUI();
   }

   public static void main(String[] args){
      loginPage LoginPage = new loginPage();  
      LoginPage.showLoginEvent();       
   }
      
   private void loginGUI(){
      mainFrame = new JFrame("Login to Gradebook");
      mainFrame.setSize(1000,700);
      mainFrame.setLayout(new GridLayout(3, 1));
      mainFrame.getContentPane().setBackground(new Color(188, 86, 86));

      headerLabel = new JLabel("",JLabel.CENTER );

      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
	        System.exit(0);
         }        
      });    
      loginPanel = new JPanel();
      loginPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(loginPanel);
      mainFrame.setVisible(true);  
   }

   private void showLoginEvent(){
      headerLabel.setText("Welcome to Gradebook"); 
      headerLabel.setFont(new Font("Georgia", Font.PLAIN, 48));
      headerLabel.setForeground(Color.white);
      
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
      loginPanel.add(userLabel);
      loginPanel.add(usernameField);
      loginPanel.add(passLabel);
      loginPanel.add(passwordField);
      loginPanel.add(loginButton);

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
            }
            else {
               JOptionPane.showMessageDialog(mainFrame, "Invalid username/password");
            }
         } 
      }		
   }
}
