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

public class mainPage {
	private JFrame mainFrame;
	private JPanel topPanel, leftPanel, classPagePanel, classListPanel;
	private JButton testClass, createSemester, createClass;
	private JButton[] classButton;
	private JLabel semesterLabel, classList;
	private String[] semesters;
	private String[] classes;
	private JComboBox<String> semesterList;
	public static String semesterDir;
	public static String selectedSemester;
	public static String GradebookFP, RubricFP, AssignFP;
	int classListSize;

	public mainPage(){
		mainFrameCreation();
		classPageGUI();
		loadSemesters();
		if (!"No semesters created".equals(semesters[0])){
			loadButtons(semesters[0]);
			semesterList.setSelectedItem(semesters[0]);
		}
	}

	private void mainFrameCreation(){
		mainFrame = new JFrame("Gradebook");
      mainFrame.setSize(1000,700);
      mainFrame.setLayout(new BorderLayout());
      mainFrame.getContentPane().setBackground(new Color(188, 86, 86));

      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
           System.exit(0);
         }
      });

		mainFrame.setVisible(true);
   }

	private void loadSemesters() {
		semesters = getSemesterList();
      semesterList = new JComboBox<>(semesters);
      semesterList.addActionListener(new ComboBoxListener());
	}

   public void classPageGUI(){
		classPagePanel = new JPanel();
      classPagePanel.setLayout(new BorderLayout());

      topPanel = new JPanel();
      leftPanel = new JPanel();
      classListPanel = new JPanel();
      topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 5));
      leftPanel.setLayout(new FlowLayout());
      classListPanel.setLayout(new BoxLayout(classListPanel, BoxLayout.Y_AXIS));

      classPagePanel.setBackground(new Color(188, 86, 86));
      topPanel.setBackground(new Color(188, 86, 86));
      leftPanel.setBackground(new Color(188, 86, 86));
      classListPanel.setBackground(new Color(188, 86, 86));

      createSemester = new JButton("Create new semester");
      createClass = new JButton("Create new class");

      createSemester.setActionCommand("createSemester");
      createSemester.addActionListener(new ButtonClickListener());

		createClass.setActionCommand("createClass");
		createClass.addActionListener(new ButtonClickListener());

      semesterLabel = new JLabel("Current Semester:");
      semesterLabel.setFont(new Font("Georgia", Font.PLAIN, 30));
      semesterLabel.setForeground(Color.white);

      semesters = getSemesterList();
      semesterList = new JComboBox<>(semesters);
		semesterList.addActionListener(new ComboBoxListener());

      classList = new JLabel("Classes: ");
      classList.setFont(new Font("Georgia", Font.PLAIN, 25));
      classList.setForeground(Color.white);

      classPagePanel.setBackground(new Color(188, 86, 86));
      topPanel.setBackground(new Color(188, 86, 86));
      leftPanel.setBackground(new Color(188, 86, 86));
      classListPanel.setBackground(new Color(188, 86, 86));

      topPanel.add(semesterLabel);
      topPanel.add(semesterList);
      topPanel.add(createSemester);
      leftPanel.add(createClass);
      classListPanel.add(classList);

      classPagePanel.add(topPanel, BorderLayout.NORTH);
      classPagePanel.add(leftPanel, BorderLayout.WEST);
      classPagePanel.setVisible(true);

      mainFrame.add(classPagePanel);
   }

   private String[] getSemesterList() {
      try {
         Scanner semesterFile = new Scanner(new File(new File("semesterList.txt").getAbsolutePath()));

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

   private class ButtonClickListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();
	      if (command.equals("createSemester")) {
		String newSemester = JOptionPane.showInputDialog(mainFrame, "Enter semester and year: ");
		semesterDir = newSemester.replaceAll(" ","");

		File dir = new File(new File("semesters/" + semesterDir).getAbsolutePath());
            	dir.mkdirs();
				
            	try {
              		File semesterFile = new File(new File("semesterList.txt").getAbsolutePath());
			File classList = new File (dir, semesterDir + "classList.txt");

               		if(!semesterFile.exists()){
                  		semesterFile.createNewFile();
               		}
			if(!classList.exists()){
   	            		classList.createNewFile();
	            	}

               		FileWriter writer = new FileWriter(semesterFile.getName(),true);
               		BufferedWriter buffer = new BufferedWriter(writer);
               		buffer.write(newSemester + "\n");
               		buffer.close();

			destroyClassPage();
			classPageGUI();
            	}

            	catch(IOException creationError){
               		creationError.printStackTrace();
		}
	      }

	 if (command.equals("createClass")) {
		if (selectedSemester.equals("No semesters created")) {
			JOptionPane.showMessageDialog(mainFrame, "Please create a semester", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else {
			destroyClassPage();
			createPage CreatePage = new createPage();			
			mainFrame.setVisible(false);
		}
	}

	for (int i=0; i<classListSize; i++) {
		if (command.equals(classes[i])) {
			createPage.courseTitle = classes[i];
			String courseName = classes[i].replaceAll(" ","");
			String semDir = selectedSemester.replaceAll(" ","");

			GradebookFP = "semesters/" + semDir + "/" + courseName + ".csv";
			RubricFP = "rubrics/" + semDir + "/" + courseName + "Rubric.txt";
			AssignFP = "assignments/" + semDir + "/" + courseName + "Assignments.txt";

			GradebookGUI gradePage = new GradebookGUI();
			mainFrame.setVisible(false);
		}
	}
	
      }
    }

	private class ComboBoxListener implements ActionListener{
      		public void actionPerformed(ActionEvent e) {
			JComboBox box = (JComboBox) e.getSource();
			selectedSemester = (String)box.getSelectedItem();
			semesterDir = getSelectedSemester(selectedSemester);
			destroyButtons();
			classListPanel.setVisible(false);
			loadButtons(selectedSemester);
		}
	}

	private void destroyClassPage() {
		topPanel.setVisible(false);
		leftPanel.setVisible(false);
		classPagePanel.setVisible(false);
		classListPanel.setVisible(false);
	}

	private void destroyButtons() {
		for (int j=0; j<classListSize; j++) {
         classListPanel.remove(classButton[j]);
      }
	}

	private String getSelectedSemester(String selected) {
		selected = selected.replaceAll(" ","");
		semesterDir = ("semesters/" + selected + "/" + selected + "classList.txt");
		return semesterDir;
	}

	private void loadButtons(String curSemester) {
		classes = getClassList(curSemester);
      		classListSize = classes.length;
      		classButton = new JButton[classListSize];

      		for (int i=0; i<classListSize; i++) {
         		classButton[i] = new JButton(classes[i]);
         		classButton[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			classButton[i].setActionCommand(classes[i]);
		   	classButton[i].addActionListener(new ButtonClickListener());
      		}

		for (int j=0; j<classListSize; j++) {
         		classListPanel.add(classButton[j]);
      		}

		classListPanel.setVisible(true);
		classPagePanel.add(classListPanel, BorderLayout.CENTER);
	}

	private String[] getClassList(String semester) {
		String semesterDirectory = getSelectedSemester(semester);

      try {
         Scanner classFile = new Scanner(new File(semesterDirectory));

         List<String> lines = new ArrayList<String>();
         while (classFile.hasNextLine()) {
            lines.add(classFile.nextLine());
         }
         String[] listOfClasses = lines.toArray(new String[0]);

         return listOfClasses;
      }
      catch (IOException ex) {
         System.out.println(ex.getMessage());
			String[] listOfClasses = {"No classes"};
         return listOfClasses;
      }
   }
}	
