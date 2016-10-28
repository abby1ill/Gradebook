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
	private JComboBox<String> semesterList;
	String semesterDir;

	public mainPage(){
		mainFrameCreation();
		classPageGUI();
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

      classList = new JLabel("Classes: ");
      classList.setFont(new Font("Georgia", Font.PLAIN, 25));
      classList.setForeground(Color.white);

     /* classButton = new JButton[size];
		
		String [] classes = getClassList();
		int classListSize = classes.length()

      for (int i=0; i<classListSize; i++) {
         classButton[i] = new JButton("Class " + i);
         classButton[i].setAlignmentX(Component.CENTER_ALIGNMENT);
      }*/

      classPagePanel.setBackground(new Color(188, 86, 86));
      topPanel.setBackground(new Color(188, 86, 86));
      leftPanel.setBackground(new Color(188, 86, 86));
      classListPanel.setBackground(new Color(188, 86, 86));

      topPanel.add(semesterLabel);
      topPanel.add(semesterList);
      topPanel.add(createSemester);
      leftPanel.add(createClass);
      classListPanel.add(classList);

      /*for (int j=0; j<size; j++) {
         classListPanel.add(classButton[j]);
      }*/

      classPagePanel.add(topPanel, BorderLayout.NORTH);
      classPagePanel.add(leftPanel, BorderLayout.WEST);
      classPagePanel.add(classListPanel, BorderLayout.CENTER);
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

               if(!semesterFile.exists()){
                  semesterFile.createNewFile();
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
				destroyClassPage();
				createPage CreatePage = new createPage();			
				mainFrame.setVisible(false);
			}
		}
	}

	private void destroyClassPage() {
		topPanel.setVisible(false);
		leftPanel.setVisible(false);
		classPagePanel.setVisible(false);
		classListPanel.setVisible(false);
	}

	/*private String[] getClassList(semesterDir) {
      try {
         Scanner classFile = new Scanner(new File(new File(semesters/semesterDir/semesterDir + "Classes.txt").getAbsolutePath()));

         List<String> lines = new ArrayList<String>();
         while (classFile.hasNextLine()) {
            lines.add(classFile.nextLine());
         }
         String[] classList = lines.toArray(new String[0]);

         return classList;
      }
      catch (FileNotFoundException ex) {
         System.out.println(ex.getMessage());
         return classList;
      }
   }*/

}	
