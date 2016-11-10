import java.util.*;
import java.util.List;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class pastRubric {
	private JFrame pastFrame;
	private JPanel headerPanel, listPanel, assignmentPanel, percentPanel, createPanel;
	private JLabel headerLabel, pastRubricLabel;
	private JButton createButton;
	List<JTextField> assignmentField = new ArrayList<JTextField>();
   List<JTextField> percentField = new ArrayList<JTextField>();
	String className = createPage.courseTitle;	
	String pastRubric = createPage.selectedRubric;
	String rubricDir, fileName;
	String [] assignments;
	String [] percents;
	String[] assignmentArray;
   String[] percentArray;

	public pastRubric() {
		pastFrameCreation();
		pastRubricGUI();
	}

	private void pastFrameCreation() {
		pastFrame = new JFrame("Gradebook");
		pastFrame.setSize(1000,700);
	   pastFrame.setLayout(new GridLayout(0,1));
      pastFrame.getContentPane().setBackground(new Color(188, 86, 86));

      pastFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
           System.exit(0);
         }
      });

      pastFrame.setVisible(true);
   }

	private void pastRubricGUI() {
		headerPanel = new JPanel();
		headerPanel.setLayout(new FlowLayout());
//      headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

		listPanel = new JPanel();
      listPanel.setLayout(new GridLayout(0,2));

      assignmentPanel = new JPanel();
      assignmentPanel.setLayout(new BoxLayout(assignmentPanel, BoxLayout.Y_AXIS));

      percentPanel = new JPanel();
      percentPanel.setLayout(new BoxLayout(percentPanel, BoxLayout.Y_AXIS));

		createPanel = new JPanel();
		createPanel.setLayout(new FlowLayout());

		createPanel.setBackground(new Color(188, 86, 86));
		assignmentPanel.setBackground(new Color(188, 86, 86));
      percentPanel.setBackground(new Color(188, 86, 86));
      headerPanel.setBackground(new Color(188, 86, 86));
		listPanel.setBackground(new Color(188, 86, 86));

		String[] splitName = pastRubric.split(":");
		rubricDir = splitName[0];
		fileName = splitName[1];

		rubricDir = rubricDir.replaceAll(" ", "");
		fileName = fileName.replaceAll(" ", "");

		headerLabel = new JLabel(className, JLabel.CENTER);
		pastRubricLabel = new JLabel("Working from : " + pastRubric, JLabel.CENTER);

		String [] rubric = getRubric();
		splitRubricArray(rubric);
		int len = rubric.length;

		for (int i=0; i<len; i++) {
      	assignmentField.add(new JTextField(assignments[i], 20));
         assignmentPanel.add(assignmentField.get(i));
      	percentField.add(new JTextField(percents[i], 3));
      	percentPanel.add(percentField.get(i));
      }

		createButton = new JButton ("Create class");
		createButton.setActionCommand("createClass");
      createButton.addActionListener(new ButtonClickListener());

      headerLabel.setFont(new Font("Georgia", Font.PLAIN, 48));
      headerLabel.setForeground(Color.white);

		pastRubricLabel.setFont(new Font("Georgia", Font.PLAIN, 30));
      pastRubricLabel.setForeground(Color.white);

		createPanel.add(createButton);
		headerPanel.add(headerLabel);
      headerPanel.add(pastRubricLabel);
		listPanel.add(assignmentPanel);
		listPanel.add(percentPanel);
		pastFrame.add(headerPanel);
		pastFrame.add(listPanel);
		pastFrame.add(createPanel);
	}

	private String[] getRubric() {
      try {
         Scanner rubricFile = new Scanner(new File("rubrics/"+rubricDir + "/" + fileName + "Rubric.txt"));

         List<String> lines = new ArrayList<String>();
         while (rubricFile.hasNextLine()) {
            lines.add(rubricFile.nextLine());
         }
         String[] rubric = lines.toArray(new String[0]);

         return rubric;
      }
      catch (IOException ex) {
         System.out.println(ex.getMessage());
         String[] rubric = { "" };

         return rubric;
      }
   }

	private void splitRubricArray(String [] rubric) {
		int len = rubric.length;

		assignments = new String[len];
		percents = new String[len];

		for (int i=0; i<len; i++) {
			String [] splitArr = rubric[i].split(",");
			assignments[i] = splitArr[0];
			percents[i] = splitArr[1];
		}
	}

	private class ButtonClickListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();

         if (command.equals("createClass")) {
            boolean percentageCheck = verifyPercentages();

            if (percentageCheck) {
               writeRubricFile();
               GradebookPage gradebookPage = new GradebookPage();
               pastFrame.setVisible(false);
            }
            else {
               JOptionPane.showMessageDialog(pastFrame, "Percentages must add to 100%", "Error", JOptionPane.ERROR_MESSAGE);
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
}

