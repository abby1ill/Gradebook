import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class loginPage {
   private JFrame loginFrame;
   private JLabel headerLabel;
   private JPanel loginPanel;
   private JTextField usernameField;
   private JPasswordField passwordField;

   public loginPage(){
      loginFrameCreation();
		showLogin();
   }

   private void loginFrameCreation(){
      loginFrame = new JFrame("Welcome to Gradebook");
      loginFrame.setSize(1000,700);
      loginFrame.setLayout(new BorderLayout());
      loginFrame.getContentPane().setBackground(new Color(188, 86, 86));


      loginFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
           System.exit(0);
         }
      });
      loginPanel = new JPanel();
      loginPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

      loginFrame.add(loginPanel, BorderLayout.CENTER);
      loginFrame.setVisible(true);
   }

   private void showLogin(){
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

      loginFrame.add(headerPanel, BorderLayout.NORTH);
      loginFrame.setVisible(true);
   }

   public class ButtonClickListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();
         if(command.equals( "Login" ))  {
            String username = "cpapadakis";
            char[] password = { 'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};

            String userEntry = usernameField.getText();
            char[] passEntry = passwordField.getPassword();
            if (userEntry.equals(username) & Arrays.equals(passEntry, password)){
               mainPage MainPage = new mainPage();
					loginFrame.dispose();
            }
            else {
               JOptionPane.showMessageDialog(loginFrame, "Invalid username/password", "Error", JOptionPane.ERROR_MESSAGE);
            }
         }
      }
   }
}
