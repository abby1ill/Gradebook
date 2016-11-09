import java.util.List;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class createPage {
	private JFrame createFrame;
	private JPanel topPanel, fillPanel, buttonPanel, numberPanel, namePanel;
	private JLabel headerLabel, numberLabel, nameLabel;
	private JTextField courseNumber, courseNameField;
	private JButton homeButton, newRubricButton, oldRubricButton;
	private JComboBox<String> rubricList;
	public static String courseTitle, selectedRubric;
	
	public createPage(){
		createFrameCreation();
		createPageGUI();
	}

	private void createFrameCreation(){
      createFrame = new JFrame("Gradebook");
      createFrame.setSize(1000,700);
      createFrame.setLayout(new BorderLayout());
      createFrame.getContentPane().setBackground(new Color(188, 86, 86));


      createFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
           System.exit(0);
         }
      });
      
		createFrame.setVisible(true);
   }

	private void createPageGUI(){
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		topPanel = new JPanel();
		fillPanel = new JPanel();
		fillPanel.setLayout(new FlowLayout());
		numberPanel = new JPanel();
		numberPanel.setLayout(new BoxLayout(numberPanel, BoxLayout.X_AXIS));
		namePanel = new JPanel ();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));



		buttonPanel.setBackground(new Color(188, 86, 86));
		topPanel.setBackground(new Color(188, 86, 86));
		fillPanel.setBackground(new Color(188, 86, 86));
		numberPanel.setBackground(new Color(188, 86, 86));
		namePanel.setBackground(new Color(188, 86, 86));

		headerLabel = new JLabel ("Create a new class");
		numberLabel = new JLabel("Course #: ");
		nameLabel = new JLabel("Course Name: ");

		headerLabel.setFont(new Font("Georgia", Font.PLAIN, 38));
      headerLabel.setForeground(Color.white);
		numberLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));
      numberLabel.setForeground(Color.white);
		nameLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));
      nameLabel.setForeground(Color.white);

		courseNumber = new JTextField(20);
		courseNameField = new JTextField(20);

		newRubricButton = new JButton("Create new grading rubric");
		homeButton = new JButton("Home");
		oldRubricButton = new JButton("Work from past rubric");
		String [] rubrics = getRubricList();
		rubricList = new JComboBox<>(rubrics);

		newRubricButton.setActionCommand("newRubric");
		newRubricButton.addActionListener(new ButtonClickListener());

		oldRubricButton.setActionCommand("pastRubric");
		oldRubricButton.addActionListener(new ButtonClickListener());

		homeButton.setActionCommand("home");
		homeButton.addActionListener(new ButtonClickListener());

		topPanel.add(homeButton);
		topPanel.add(headerLabel);
		numberPanel.add(numberLabel);
		numberPanel.add(courseNumber);
		namePanel.add(nameLabel);
		namePanel.add(courseNameField);

		buttonPanel.add(newRubricButton);
		buttonPanel.add(oldRubricButton);
		buttonPanel.add(rubricList);

		fillPanel.add(numberPanel);
      fillPanel.add(namePanel);
		fillPanel.add(buttonPanel);

		createFrame.add(fillPanel, BorderLayout.CENTER);
		createFrame.add(topPanel, BorderLayout.NORTH);
	}

	private class ButtonClickListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();

			String courseNum = courseNumber.getText();
         String courseName = courseNameField.getText();

         courseTitle = courseNum + " " + courseName;

         if (command.equals("newRubric")) {
				String nameAndNum = courseNum + courseName;
				nameAndNum = nameAndNum.replaceAll(" ","");

            try {
               FileWriter writer = new FileWriter(mainPage.semesterDir, true);

               BufferedWriter buffer = new BufferedWriter(writer);
               buffer.write(courseNum + " " + courseName  + "\n");
               buffer.close();
            }

            catch(IOException creationError){
               creationError.printStackTrace();
            }

				rubricBuilder RubricBuilder = new rubricBuilder();
            createFrame.setVisible(false);
         }

			if (command.equals("pastRubric")) {
				pastRubric PastRubric = new pastRubric();
				createFrame.setVisible(false);
			}

			if (command.equals("home")) {
				mainPage returnHome = new mainPage();
				createFrame.setVisible(false);
			}
      }
   }

	private class ComboBoxListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         JComboBox box = (JComboBox) e.getSource();
         selectedRubric = (String)box.getSelectedItem();
      }
   }

	private String[] getRubricList() {
      try {
         Scanner rubricsFile = new Scanner(new File("rubrics/rubricList.txt"));

         List<String> lines = new ArrayList<String>();
         while (rubricsFile.hasNextLine()) {
            lines.add(rubricsFile.nextLine());
         }
         String[] rubrics = lines.toArray(new String[0]);

         return rubrics;
      }
      catch (FileNotFoundException ex) {
         System.out.println(ex.getMessage());
         String[] rubrics = { "No rubrics created" };

         return rubrics;
      }
   }

}
