import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class createPage {
	private JFrame createFrame;
	private JPanel createPagePanel;
	private JLabel numberLabel, nameLabel, oldRubricLabel;
	private JTextField courseNumber, courseName;
	private JButton newRubricButton;
	
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
		createPagePanel = new JPanel();
		createPagePanel.setBackground(new Color(188, 86, 86));

		numberLabel = new JLabel("Course #: ");
		nameLabel = new JLabel("Course Name: ");

		courseNumber = new JTextField(20);
		courseName = new JTextField(20);

		newRubricButton = new JButton("Create new grading rubric");
		oldRubricLabel = new JLabel("Work from past rubric");

		newRubricButton.setActionCommand("newRubric");
		newRubricButton.addActionListener(new ButtonClickListener());

		createPagePanel.add(numberLabel);
		createPagePanel.add(courseNumber);
		createPagePanel.add(nameLabel);
		createPagePanel.add(courseName);
		createPagePanel.add(newRubricButton);
		createPagePanel.add(oldRubricLabel);

		createFrame.add(createPagePanel);
	}

	private class ButtonClickListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();

         if (command.equals("newRubric")) {
				rubricBuilder RubricBuilder = new rubricBuilder();
            createFrame.setVisible(false);
         }
      }
   }

}
