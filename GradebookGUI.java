import java.util.Arrays;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Object;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.lang.Object;
import javax.swing.table.*;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.lang.Object;
import javax.swing.JComboBox;

public class GradebookGUI extends JFrame{

    private JFrame gradebookFrame;
    private JPanel classNamePanel, tablePanel, controlPanel;
    private JScrollPane scrollPane;
    private JTable gradeTable, gt;
    private JLabel classLabel, studentLabel, assignmentLabel;
    private JButton homeButton;
    String className = createPage.courseTitle;
    private int numAssignment;
    public int selectedRow = 0;
    private int totalRows;
    private DefaultTableModel modeltable;
    boolean sAvg = false;
    boolean aAvg = false;
    private ArrayList<String> points;
    private int numColumns, numRows;

    //Within MenuBar Tabs
    private JMenu fileTab, editTab, calculateTab, Edit_StudentTab;
    private JMenu Edit_AssignmentTab;
    private JMenuBar MenuBar;
    private JMenuItem Edit_Student_AddTab, Edit_Student_RemoveTab;
    private JMenuItem Edit_Assignment_AddTab, Edit_Assignment_RemoveTab;
    private JMenuItem File_SaveTab;
    private JRadioButtonMenuItem studentAverage, assignmentAverage, finalAverage;

    public JTextField FNameEdit, LNameEdit, IDEdit;
    public JFrame changeStudentFrame;

    public JComboBox nameList, assignmentList, assignmentAddList;


    public Assignments [] assignmentArr = setAssignments();
    public String [] columnNames = getColumns();

    private ArrayList<Object> col;


    public GradebookGUI() {
        gradebookFrameCreation();
        initGradebook();
        numRows = 0;
    }



    private void gradebookFrameCreation() {
        gradebookFrame = new JFrame("Gradebook");
        gradebookFrame.setSize(1000,700);
        gradebookFrame.setLayout(new BorderLayout(5,5));
        gradebookFrame.getContentPane().setBackground(new Color(188, 86, 86));

        MenuBar = new JMenuBar();
        fileTab = new JMenu();
        editTab = new JMenu();
        calculateTab = new JMenu();
        studentAverage = new JRadioButtonMenuItem();
        assignmentAverage = new JRadioButtonMenuItem();
        Edit_StudentTab = new JMenu();
        Edit_Student_AddTab = new JMenuItem();
        Edit_Student_RemoveTab = new JMenuItem();
        Edit_AssignmentTab = new JMenu();
        Edit_Assignment_AddTab = new JMenuItem();
        Edit_Assignment_RemoveTab = new JMenuItem();
	File_SaveTab = new JMenuItem();

        assignmentList = new JComboBox();
        nameList = new JComboBox();
        assignmentAddList = new JComboBox();

        fileTab.setText("File");


	File_SaveTab.setText("Save");
	fileTab.add(File_SaveTab);
        MenuBar.add(fileTab);

	File_SaveTab.setActionCommand("FileSave");
	File_SaveTab.addActionListener(new ButtonClickListener()); 

        editTab.setText("Edit");
        editTab.add(Edit_StudentTab);

        calculateTab.setText("Calculate");
        calculateTab.add(studentAverage);
        studentAverage.setActionCommand("StudentAverage");
        studentAverage.addActionListener(new ButtonClickListener());

        calculateTab.add(assignmentAverage);

        studentAverage.setText("Student Average");
        assignmentAverage.setText("Assignment Average");
        assignmentAverage.setActionCommand("AssignmentAverage");
        assignmentAverage.addActionListener(new ButtonClickListener());



        MenuBar.add(calculateTab);

        Edit_StudentTab.setText("Student");


        Edit_Student_AddTab.setText("Add");
        Edit_StudentTab.add(Edit_Student_AddTab);
        Edit_Student_AddTab.setActionCommand("AddStudent");
        Edit_Student_AddTab.addActionListener(new ButtonClickListener());

        Edit_Student_RemoveTab.setText("Remove");
        Edit_StudentTab.add(Edit_Student_RemoveTab);
        Edit_Student_RemoveTab.setActionCommand("RemoveStudent");
        Edit_Student_RemoveTab.addActionListener(new ButtonClickListener());

        Edit_AssignmentTab.setText("Assignment");


        Edit_Assignment_AddTab.setText("Add");
        Edit_AssignmentTab.add(Edit_Assignment_AddTab);
        Edit_Assignment_AddTab.setActionCommand("AddAssignment");
        Edit_Assignment_AddTab.addActionListener(new ButtonClickListener());

        Edit_Assignment_RemoveTab.setText("Remove");
        Edit_AssignmentTab.add(Edit_Assignment_RemoveTab);
        Edit_Assignment_RemoveTab.setActionCommand("RemoveAssignment");
        Edit_Assignment_RemoveTab.addActionListener(new ButtonClickListener());

        editTab.add(Edit_AssignmentTab);
        MenuBar.add(editTab);

        gradebookFrame.add(MenuBar,BorderLayout.PAGE_START);


        gradebookFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

    }



    private void initGradebook() {
        tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(0,1,1,1));
        controlPanel = new JPanel();
        classNamePanel = new JPanel();
        String[][] content;

        classLabel = new JLabel(className);
        classLabel.setFont(new Font("Georgia", Font.PLAIN, 30));
        classLabel.setForeground(Color.white);

        homeButton = new JButton("Home");
        homeButton.setActionCommand("home");
        homeButton.addActionListener(new ButtonClickListener());

        tablePanel.setBackground(new Color(188, 86, 86));
        controlPanel.setBackground(new Color(188, 86, 86));

        gradeTable = new JTable (new DefaultTableModel());

        classNamePanel.setLayout(new BoxLayout(classNamePanel, BoxLayout.Y_AXIS));
        classNamePanel.setBackground(new Color(188, 86, 86));

        //Opens csv file

        try {
            File csvFile = new File(mainPage.GradebookFP);
            System.out.println(mainPage.GradebookFP);
            if (!csvFile.exists()) {
                csvFile.createNewFile();
            }

				BufferedReader rCounter = new BufferedReader(new FileReader(csvFile));
      		int lc = 0;

				while (rCounter.readLine() != null)
					lc++;

				rCounter.close();

				String [][] elements = new String[lc][columnNames.length];
		
		      BufferedReader br = new BufferedReader(new FileReader(csvFile));

            String line = null;
				int rIndex = 0;
            while((line=br.readLine())!=null) {
                String[] splitted = line.split(",");
					 for (int j=0; j<splitted.length; j++) {
					    elements[rIndex][j] = splitted[j];
					 }
                rIndex++;
            }
            br.close();

            gradeTable.setModel(new DefaultTableModel(elements,columnNames));


        } catch (Exception ex) {
            ex.printStackTrace();
        }


        scrollPane = new JScrollPane(gradeTable);
        gradeTable.setFillsViewportHeight(true);

        classNamePanel.add(homeButton);
        classNamePanel.add(classLabel);
        tablePanel.add(scrollPane);

        gradebookFrame.add(classNamePanel, BorderLayout.WEST);
        gradebookFrame.add(tablePanel,BorderLayout.CENTER);
        gradebookFrame.setVisible(true);
    }


    public class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String command = e.getActionCommand();
            if (command.equals("home")) {
                writeGradebookFile();
                mainPage Home = new mainPage();
                gradebookFrame.setVisible(false);
            }

				if (command.equals("FileSave")){
					writeGradebookFile();
				}

            if(command.equals("AddStudent")){

                ((DefaultTableModel)gradeTable.getModel()).addRow(new Object[]{"","","",""});
            }

            if (command.equals("AddAssignment")){
                changeStudentFrame = new JFrame("Add Assignment");
                changeStudentFrame.setSize(200,200);
                changeStudentFrame.setLayout(new BorderLayout(5,5));

                assignmentAddList = new JComboBox();

                JButton saveButton = new JButton("save");

                Object [] list;

                list = new Object[assignmentArr.length];

                for (int i = 0; i< assignmentArr.length; i++){
                    list[i] = assignmentArr[i].getAssignment();

                    assignmentAddList.addItem(list[i]);

                }


                saveButton.setActionCommand("saveAddAssignmentButton");
                saveButton.addActionListener(new ButtonClickListener());

                changeStudentFrame.add(assignmentAddList,BorderLayout.NORTH);
                changeStudentFrame.add(saveButton, BorderLayout.SOUTH);
                changeStudentFrame.setVisible(true);
            }

            if (command.equals("saveAddAssignmentButton")){
                int col = gradeTable.getColumnCount();
                int columnIndex= 0;
					 String addedCol = assignmentAddList.getSelectedItem().toString();

                for (int i = 0; i< col; i++){
                    if (assignmentAddList.getSelectedItem() == gradeTable.getModel().getColumnName(i))
                                          columnIndex = i;
                }
                ((DefaultTableModel)gradeTable.getModel()).addColumn(assignmentAddList.getSelectedItem());

					 saveAssignmentFile(addedCol);
            }



            if (command.equals("RemoveStudent")){
                changeStudentFrame = new JFrame("Remove Student");
                changeStudentFrame.setSize(200,200);
                changeStudentFrame.setLayout(new BorderLayout(5,5));
                //create an array that has a list of student names


                JButton saveButton = new JButton("save");

                nameList = new JComboBox();

                System.out.println(gradeTable.getModel().getValueAt(0, 2));

                Object [] list;

                int row = gradeTable.getRowCount();
                list = new Object[row];
                for (int i = 0; i< row; i++){
                    System.out.println(gradeTable.getModel().getValueAt(i,2));
                    list[i] = gradeTable.getModel().getValueAt(i,2);
                    nameList.addItem(list[i]);

                }

                saveButton.setActionCommand("saveRemoveButton");
                saveButton.addActionListener(new ButtonClickListener());


                System.out.println(nameList.getSelectedItem());
                changeStudentFrame.add(nameList,BorderLayout.NORTH);
                changeStudentFrame.add(saveButton, BorderLayout.SOUTH);
                changeStudentFrame.setVisible(true);
            }

            if(command.equals("saveRemoveButton")){
                int row = gradeTable.getRowCount();
                int selectedRow= 0;
                for (int i = 0; i< row; i++){
                    if (nameList.getSelectedItem() == gradeTable.getModel().getValueAt(i,2))
                        selectedRow = i;
                }

                ((DefaultTableModel)gradeTable.getModel()).removeRow(selectedRow);

            }

            if (command.equals("RemoveAssignment")){
                changeStudentFrame = new JFrame("Remove Assignment");
                changeStudentFrame.setSize(200,200);
                changeStudentFrame.setLayout(new BorderLayout(5,5));
                //create an array that has a list of student names


                JButton saveButton = new JButton("save");

                Object [] list;

                int column = gradeTable.getColumnCount() - 3;
                list = new Object[column];

                for (int i = 0; i< column; i++){
                    list[i] = gradeTable.getColumnName(i+3);

                    assignmentList.addItem(list[i]);

                }


                saveButton.setActionCommand("saveRemoveAssignment");
                saveButton.addActionListener(new ButtonClickListener());

                System.out.println(nameList.getSelectedItem());
                changeStudentFrame.add(assignmentList,BorderLayout.NORTH);
                changeStudentFrame.add(saveButton, BorderLayout.SOUTH);
                changeStudentFrame.setVisible(true);


            }

            if(command.equals("saveRemoveAssignment")){

                int col = gradeTable.getColumnCount();
                int columnIndex= 0;
                for (int i = 0; i< col; i++){
                    if (assignmentList.getSelectedItem() == gradeTable.getModel().getColumnName(i))
                        columnIndex = i;
                }

					 int removeCol = columnIndex;

                String[] identifiers = new String[gradeTable.getColumnCount() - 1];
                int k = 0;
                for(int i = 0; i < gradeTable.getColumnCount(); i++) {
                    if(i != columnIndex) {
                        identifiers[k++] = gradeTable.getColumnName(i);
                    }
                }


                String[][] data = new String[gradeTable.getRowCount()][gradeTable.getColumnCount() - 1];
                for(int i = 0; i < gradeTable.getRowCount(); i++) {
                    for(int j = 0; j < gradeTable.getColumnCount(); j++) {
                        if(j != columnIndex) {
                            if(gradeTable.getValueAt(i, j) == null) {
                                gradeTable.setValueAt("", i, j);
                            }
                            if(j < columnIndex) {
                                data[i][j] = gradeTable.getValueAt(i, j).toString();
                            } else {
                                data[i][j - 1] = gradeTable.getValueAt(i, j).toString();
                            }
                        }
                    }
                }
                modeltable = new DefaultTableModel(data, identifiers);
                gradeTable.setModel(modeltable);

					 fileRemoveAssignment(removeCol);
            }



            if (command.equals("AssignmentAverage")){
                aAvg = true;

                int rowsCount = gradeTable.getRowCount();
                int colCount = gradeTable.getColumnCount();

                if (sAvg)
                    colCount = colCount-1;
                Object[] av;
                av =  new Object[gradeTable.getColumnCount()];
                for (int j = 0; j < colCount; j++){
                    int sum = 0;

                    if (j <3)
                        av[j] = "";
                    else{
                        for(int i = 0; i < rowsCount; i++){
                            sum += Integer.parseInt(gradeTable.getValueAt(i, j).toString());
                        }
                        float average = sum/rowsCount;
                        av[j] = average;
                    }
                }

                ((DefaultTableModel)gradeTable.getModel()).addRow(av);
            }

            if (command.equals("StudentAverage")){
                sAvg = true;
                int rowCount = gradeTable.getRowCount();
                int colCount = gradeTable.getColumnCount();

                if (aAvg)
                    rowCount = rowCount-1;

                Object [] studentAvg;
                studentAvg = new Object[rowCount];

                for (int k=0; k<rowCount; k++) {
			float average = 0;
			for (int i=0; i<assignmentArr.length; i++) {
				int matches = 0;
				String rubricMetric = assignmentArr[i].getAssignment();
				int percentage = assignmentArr[i].getPercentage();
				int grade = 0; 
				double avg = 0;
				
                    		for (int j=3; j<colCount; j++) {
					String assignName = gradeTable.getColumnName(j);

					if (assignName.equals(rubricMetric)) {
						matches++;
						grade += Integer.parseInt(gradeTable.getValueAt(k,j).toString());
						avg = grade/matches;
					}	
                    		}
				
				double weight = ((double) percentage)/100;
                     		double weightGrade = weight * avg;
                     		average += weightGrade;
                	}
			studentAvg[k] = average;
		}

                ((DefaultTableModel)gradeTable.getModel()).addColumn("Student Average", studentAvg);
            }
        }
    }

    private Assignments[] setAssignments() {
        String [] rubric = getRubric();
        int len = rubric.length;

        Assignments[] assignmentArr = new Assignments[len];
        String [] assignments = new String[len];
        String [] percentages = new String[len];
        int [] percents = new int[len];

        for (int i=0; i<len; i++) {
            String [] splitArr = rubric[i].split(",");
            assignments[i] = splitArr[0];
            percentages[i] = splitArr[1];
        }

        for (int j=0; j<len; j++) {
            percents[j] = Integer.parseInt(percentages[j]);
            assignmentArr[j] = new Assignments(assignments[j], percents[j]);
        }

        return assignmentArr;
    }

    public String[] getColumns () {
		  try {
            Scanner assignFile = new Scanner(new File(mainPage.AssignFP));

            List<String> lines = new ArrayList<String>();
            while (assignFile.hasNextLine()) {
                lines.add(assignFile.nextLine());
            }
            String[] assign = lines.toArray(new String[0]);

            return assign;
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            String[] assign = { "" };

            return assign;
        }
    }

    private String[] getRubric() {
        try {
            Scanner rubricFile = new Scanner(new File(mainPage.RubricFP));

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

    private void writeGradebookFile() {
        File dir = new File(mainPage.GradebookFP);
        int row = gradeTable.getRowCount();
        int col = gradeTable.getColumnCount();
		
		  if (sAvg)
		     col = col-1;

		  if (aAvg)
			  row = row-1;

        try{
            FileWriter writer = new FileWriter(dir, false);
            BufferedWriter buffer = new BufferedWriter(writer);

            for (int i = 0; i < row; i++){
                for (int j = 0; j < col; j++){
                    if(j == col-1)
                        buffer.write(gradeTable.getValueAt(i, j) + "\n");
                    else {
                        buffer.write(gradeTable.getValueAt(i, j) + ",");
                    }
                }
            }
				buffer.close();

        }catch (IOException creationError) {
            creationError.printStackTrace();
        }
    }

	 private void saveAssignmentFile (String column) {
	 	 File dir = new File(mainPage.AssignFP);

		 try{
       	FileWriter writer = new FileWriter(dir, true);
         BufferedWriter buffer = new BufferedWriter(writer);
 
			buffer.write(column + "\n");
         buffer.close();
 
         }catch (IOException creationError) {
             creationError.printStackTrace();
         }
	 }

	 private void fileRemoveAssignment (int columnNum) {

		 try {
	 	 	File assignFile = new File(mainPage.AssignFP);
		 	File tempFile = new File ("tempFile.txt");

		 	BufferedReader reader = new BufferedReader (new FileReader(assignFile));
		 	BufferedWriter writer = new BufferedWriter (new FileWriter(tempFile));

			String cur;

		 	for (int i=0; i<columnNum; i++) {
				cur = reader.readLine();
				writer.write(cur + "\n");
			}

			cur = reader.readLine();

			while ((cur = reader.readLine()) != null) {
				writer.write(cur + "\n");
			}

		 	writer.close();
		 	reader.close();

		 	tempFile.renameTo(assignFile);
	 	} catch (IOException error) {
			error.printStackTrace();
		}
	}
}
