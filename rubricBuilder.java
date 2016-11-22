import java.util.List;
import java.util.*;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class rubricBuilder {
    private JFrame rubricFrame;
    private JLabel headerLabel, assignmentHeader, percentHeader;
    private JPanel addPanel, listPanel, labelPanel, assignmentPanel, percentPanel;
    private JTextField typeField;
	 private JButton addButton, createButton;
	 String [] assignmentArray;
	 String [] percentArray;

    List<String> assignments = new ArrayList<String>();
    List<String> percents = new ArrayList<String>();
	 List<JTextField> assignmentField = new ArrayList<JTextField>();
	 List<JTextField> percentField = new ArrayList<JTextField>();

    String className = createPage.courseTitle;

    public rubricBuilder(){
		  rubricFrameCreation();
        rubricGUI();
    }

    private void rubricFrameCreation() {
        rubricFrame = new JFrame("Gradebook");
        rubricFrame.setSize(1000, 700);
        rubricFrame.setLayout(new GridLayout(0, 1));
        rubricFrame.getContentPane().setBackground(new Color(188, 86, 86));

        headerLabel = new JLabel("", JLabel.CENTER);

        rubricFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        rubricFrame.setVisible(true);
	 }

	 private void rubricGUI(){
        addPanel = new JPanel();
        addPanel.setLayout(new FlowLayout());

        listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(0,2));

        assignmentPanel = new JPanel();
        assignmentPanel.setLayout(new BoxLayout(assignmentPanel, BoxLayout.Y_AXIS));

		  percentPanel = new JPanel();
        percentPanel.setLayout(new BoxLayout(percentPanel, BoxLayout.Y_AXIS));

        rubricFrame.add(headerLabel);
        rubricFrame.add(listPanel);
        listPanel.add(assignmentPanel);
		  listPanel.add(percentPanel);
		  rubricFrame.add(addPanel);
        rubricFrame.setVisible(true);

        headerLabel.setText(className);
        headerLabel.setFont(new Font("Georgia", Font.PLAIN, 48));
        headerLabel.setForeground(Color.white);

        assignmentHeader = new JLabel("Assignments", SwingConstants.CENTER);
        assignmentHeader.setFont(new Font("Sans Serif", Font.BOLD, 15));
        assignmentHeader.setForeground(Color.white);

        percentHeader = new JLabel("Percent Worth", SwingConstants.CENTER);
        percentHeader.setFont(new Font("Sans Serif", Font.BOLD, 15));
        percentHeader.setForeground(Color.white);

		  createButton = new JButton ("Create class");
		  addButton = new JButton("Add assignment");

		  createButton.setActionCommand("createClass");
		  createButton.addActionListener(new ButtonClickListener());
        
		  addButton.setActionCommand("addAssignment");
		  addButton.addActionListener(new ButtonClickListener());

		  listPanel.setBackground(new Color(188, 86, 86));
		  addPanel.setBackground(new Color(188, 86, 86));
        assignmentPanel.setBackground(new Color(188, 86, 86));
        percentPanel.setBackground(new Color(188, 86, 86));
        addPanel.add(createButton);

		  int numOfFields = getNumOfFields();

		  assignmentPanel.add(assignmentHeader);
		  percentPanel.add(percentHeader);

        for (int i=0; i<numOfFields; i++) {
            assignmentField.add(new JTextField(20));
				assignmentPanel.add(assignmentField.get(i));
        }

        for (int j=0; j<numOfFields; j++) {
            percentField.add(new JTextField(3));
				percentPanel.add(percentField.get(j));
        }

        rubricFrame.setVisible(true);
    }

	 private int getNumOfFields() {
	     int assignNum = Integer.parseInt(JOptionPane.showInputDialog(rubricFrame, "How many assignments will students be graded on?"));

		  return assignNum;
	 }	

	 private class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();

			if (command.equals("createClass")) {
				boolean percentageCheck = verifyPercentages();
	
				boolean emptyFieldCheck = emptyFields();
 
				if (percentageCheck && emptyFieldCheck) {
					writeRubricFile();
					writeAssignmentFile();
					GradebookGUI gradebookPage = new GradebookGUI();
					rubricFrame.setVisible(false);
				}
				else {
					JOptionPane.showMessageDialog(rubricFrame, "Fields must be filled and percentages must add to 100%", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		 }
	 }

    private void writeRubricFile() {
		 int numOfAssign = assignmentField.size();
		 String rubricDir = mainPage.selectedSemester.replaceAll(" ", "");
   	 File dir = new File("rubrics/" + rubricDir);
		 dir.mkdirs();

		 try {
		 	String rubricName = mainPage.selectedSemester + " : " + className;
			String classRubric = className.replaceAll(" ", "");

			File rubricList = new File ("rubrics/rubricList.txt");
			File rubricFile = new File (dir, classRubric + "Rubric.txt");

			if(!rubricList.exists()) {
				rubricList.createNewFile();
			}

			FileWriter listWriter = new FileWriter(rubricList.getAbsolutePath(), true);
			BufferedWriter listBuffer = new BufferedWriter(listWriter);

			listBuffer.write(rubricName + "\n");
			listBuffer.close();

			if(!rubricFile.exists()) {
				rubricFile.createNewFile();
			}

			FileWriter writer = new FileWriter(rubricFile.getAbsolutePath(), true);
			BufferedWriter buffer = new BufferedWriter(writer);

			for (int k=0; k<numOfAssign; k++) {
				buffer.write(assignmentArray[k] + "," + percentArray[k] + "\n");
			}
			buffer.close();
	 	}

	 	catch(IOException creationError){
         creationError.printStackTrace();
     	}
	 }

	 private void writeAssignmentFile() {
		 int numOfAssign = assignmentField.size();
	 	 String assignDir = mainPage.selectedSemester.replaceAll(" ", "");
		 File dir = new File("assignments/" + assignDir);
       dir.mkdirs();

       try {
         String classAssign = className.replaceAll(" ", "");

         File assignFile = new File (dir, classAssign + "Assignments.txt");

         if(!assignFile.exists()) {
            assignFile.createNewFile();
         }

         FileWriter writer = new FileWriter(assignFile.getAbsolutePath(), true);
         BufferedWriter buffer = new BufferedWriter(writer);

			buffer.write("ID\n");
			buffer.write("Last Name\n");
			buffer.write("First Name\n");

         for (int k=0; k<numOfAssign; k++) {
            buffer.write(assignmentArray[k] + "\n");
         }
         buffer.close();
      }

      catch(IOException creationError){
         creationError.printStackTrace();
      }
	 }

	 private boolean verifyPercentages() {
		 int numOfAssign = assignmentField.size();
  
       assignmentArray = new String[numOfAssign];
       percentArray = new String[numOfAssign];

       for (int i=0; i<numOfAssign; i++) {
           assignmentArray[i] = (assignmentField.get(i)).getText();
           percentArray[i] = (percentField.get(i)).getText();
       }

		 int total = 0;

	 	 for (int i=0; i<numOfAssign; i++) {
			 int percentage = Integer.parseInt(percentArray[i]);
		    total += percentage;
		 }

		 if (total == 100)
		    return true;
		 else
          return false;
	 }

	 private boolean emptyFields() {        
       int numOfAssign = assignmentField.size();
 
       for (int i=0; i<numOfAssign; i++) {
          if((assignmentField.get(i)).getText().equals("") || (percentField.get(i)).getText().equals(""))
          return(false);
       }
       return(true);
    }
}
