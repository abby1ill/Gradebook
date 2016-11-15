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
    private JMenuItem Edit_Student_ChangeTab, Edit_Student_AddTab, Edit_Student_RemoveTab;
    private JMenuItem Edit_Assignment_ChangeTab, Edit_Assignment_AddTab, Edit_Assignment_RemoveTab;
    private JRadioButtonMenuItem studentAverage, assignmentAverage, finalAverage;

    public JTextField FNameEdit, LNameEdit, IDEdit;
    public JFrame changeStudentFrame;

    public JComboBox nameList, assignmentList, assignmentAddList;


    //       public static String[][] content;
    public Assignments [] assignmentArr = setAssignments();
    public String [] columnNames = getColumns(assignmentArr);

    //	private Object[] columnNames;
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
        finalAverage = new JRadioButtonMenuItem();
        Edit_StudentTab = new JMenu();
        Edit_Student_ChangeTab = new JMenuItem();
        Edit_Student_AddTab = new JMenuItem();
        Edit_Student_RemoveTab = new JMenuItem();
        Edit_AssignmentTab = new JMenu();
        Edit_Assignment_ChangeTab = new JMenuItem();
        Edit_Assignment_AddTab = new JMenuItem();
        Edit_Assignment_RemoveTab = new JMenuItem();


        assignmentList = new JComboBox();
        nameList = new JComboBox();
        assignmentAddList = new JComboBox();

        fileTab.setText("File");

        MenuBar.add(fileTab);

        editTab.setText("Edit");
        editTab.add(Edit_StudentTab);

        calculateTab.setText("Calculate");
        calculateTab.add(studentAverage);
        studentAverage.setActionCommand("StudentAverage");
        studentAverage.addActionListener(new ButtonClickListener());

        calculateTab.add(assignmentAverage);
        calculateTab.add(finalAverage);

        studentAverage.setText("Student Average");
        assignmentAverage.setText("Assignment Average");
        assignmentAverage.setActionCommand("AssignmentAverage");
        assignmentAverage.addActionListener(new ButtonClickListener());


        finalAverage.setText("Final Average");


        MenuBar.add(calculateTab);

        Edit_StudentTab.setText("Student");

        //Edit_Student_ChangeTab.setText("Change");
        //Edit_StudentTab.add(Edit_Student_ChangeTab);

        //Edit_Student_ChangeTab.setActionCommand("ChangeStudent");
        //Edit_Student_ChangeTab.addActionListener(new ButtonClickListener());

        Edit_Student_AddTab.setText("Add");
        Edit_StudentTab.add(Edit_Student_AddTab);
        Edit_Student_AddTab.setActionCommand("AddStudent");
        Edit_Student_AddTab.addActionListener(new ButtonClickListener());

        Edit_Student_RemoveTab.setText("Remove");
        Edit_StudentTab.add(Edit_Student_RemoveTab);
        Edit_Student_RemoveTab.setActionCommand("RemoveStudent");
        Edit_Student_RemoveTab.addActionListener(new ButtonClickListener());

        Edit_AssignmentTab.setText("Assignment");

        Edit_Assignment_ChangeTab.setText("Change");
        Edit_AssignmentTab.add(Edit_Assignment_ChangeTab);
        Edit_Assignment_ChangeTab.setActionCommand("ChangeAssignment");
        Edit_Assignment_ChangeTab.addActionListener(new ButtonClickListener());

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

//                gradeTable.getColumn("ID").setCellEditor(
//                new ButtonEditor(new JCheckBox()));

        gradeTable = new JTable (new DefaultTableModel());

        classNamePanel.setLayout(new BoxLayout(classNamePanel, BoxLayout.Y_AXIS));
        classNamePanel.setBackground(new Color(188, 86, 86));

        //Opens cvs file

        try {
            File csvFile = new File(mainPage.GradebookFP);
            System.out.println(mainPage.GradebookFP);
            if (!csvFile.exists()) {
                csvFile.createNewFile();
            }

            BufferedReader br = new BufferedReader(new FileReader(csvFile));

            List<String[]> elements = new ArrayList<String[]>();
            String line = null;
            while((line = br.readLine())!=null) {
                String[] splitted = line.split(",");
                elements.add(splitted);
                numRows++;
            }
            br.close();

            content = new String[elements.size()][numRows];
            TableCellRenderer buttonRenderer = new ButtonRenderer();

            for (int i =0; i<numRows; i++){
                content[i][0] = elements.get(i)[0];
            }


            System.out.println(elements.size());
            System.out.println(numRows);
            for(int i=0; i<numRows-1; i++) {
                for (int j = 0; j<elements.size()-1; j++){
                    content[i][j] = elements.get(i)[j];
                }
            }


            totalRows = elements.size();

            //		initGradeBook(content);
            gradeTable.setModel(new DefaultTableModel(content,columnNames));

//			setWidthAsPercentages(gradeTable, 0.05, 0.20, 0.25, 0.25, 0.25);
//            		System.out.println(gradeTable.getModel().getValueAt(1, 1));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

//		gradeTable.getColumn("ID").setCellRenderer(new ButtonRenderer());
//    		gradeTable.getColumn("ID").setCellEditor(
//        	new ButtonEditor(new JCheckBox()));

        scrollPane = new JScrollPane(gradeTable);
        gradeTable.setFillsViewportHeight(true);

//                classNamePanel.add(classLabel);
        classNamePanel.add(homeButton);
        classNamePanel.add(classLabel);
        tablePanel.add(scrollPane);

        gradebookFrame.add(classNamePanel, BorderLayout.WEST);
        gradebookFrame.add(tablePanel,BorderLayout.CENTER);
        gradebookFrame.setVisible(true);
    }


/*
	public void initGradeBook(String [][] c){
//		initColumns();
		students = new ArrayList<Student>();
                        grades = new ArrayList<Grades>();
                        for(int i= 0; i<numRows-1; i++){
                                Student s = new Student(c[i][2], c[i][1], c[i][0]);
                                students.add(s);
                        }
                        for (int k = 3; k < totalRows-1; k++){
                                        points = new ArrayList<String>();
                                          for (int j = 0; j<totalRows-1; j++){
                                                for(int i=0; i<numRows-1; i++) {
                                                        points.add(c[i][j]);
                                                }
                                        }
                                        Grades g = new Grades(null, null, points);
                                        grades.add(g);
                        }
                        gradeTable.setModel(new DefaultTableModel(c,columnNames));
	}*/


///INIT COLUMNS
/*	private void initColumns(){
               try{
	BufferedReader br = new BufferedReader(new FileReader(new File("/Users/mauraodonnell/cols.csv")));
                        List<String[]> elements = new ArrayList<String[]>();
                        String line = null;
			numAssignment = 0;
                        while((line = br.readLine())!=null) {
                                String[] splitted = line.split(",");
                                elements.add(splitted);
                                numAssignment++;
                        }
                        br.close();
                 //       content = new String[elements.size()][numRows];
                        TableCellRenderer buttonRenderer = new ButtonRenderer();
			col = new ArrayList<Object>();
                        for (int i =0; i<numAssignment; i++){
                                col.add(elements.get(i)[0]);
                        }
			System.out.println(elements.size());
			System.out.println(col);
                        for(int i=0; i<numRows-1; i++) {
                                for (int j = 0; j<elements.size()-1; j++){
                                        content[i][j] = elements.get(i)[j];
                                }
                        }
			columnNames = new Object[col.size()];
			col = col.toArray(columnNames);
			  System.out.println(columnNames);
                        totalRows = elements.size();
                } catch (Exception ex) {
                        ex.printStackTrace();
                }
	}*/


    private static void setWidthAsPercentages(JTable table, double... percentages) {
        final double factor = 10000;

        TableColumnModel model = table.getColumnModel();
        for (int columnIndex = 0; columnIndex < percentages.length; columnIndex++) {
            TableColumn column = model.getColumn(columnIndex);
            column.setPreferredWidth((int) (percentages[columnIndex] * factor));
        }
    }


    public class ButtonClickListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String command = e.getActionCommand();
            if (command.equals("home")) {
                writeGradebookFile();
                mainPage Home = new mainPage();
                gradebookFrame.setVisible(false);
            }
            if(command.equals("AddStudent")){

                ((DefaultTableModel)gradeTable.getModel()).addRow(new Object[]{"","","",""});
            }

            if (command.equals("AddAssignment")){
                changeStudentFrame = new JFrame("Remove Assignment");
                changeStudentFrame.setSize(200,200);
                changeStudentFrame.setLayout(new BorderLayout(5,5));
                //create an array that has a list of student names

                assignmentAddList = new JComboBox();

                JButton saveButton = new JButton("save");


                //      System.out.println(gradeTable.getModel().getValueAt(0, 2));

                System.out.println("THISS");
                System.out.println(gradeTable.getColumnName(3));

                Object [] list;

                int column = gradeTable.getColumnCount() - 3;
                list = new Object[column];

                for (int i = 0; i< column; i++){
                    //System.out.println(gradeTable.getModel().getValueAt(i,2));
                    list[i] = gradeTable.getColumnName(i+3);//getModel().getValueAt(2,i);

                    assignmentAddList.addItem(list[i]);

                }


                saveButton.setActionCommand("saveAddAssignmentButton");
                saveButton.addActionListener(new ButtonClickListener());

//
                System.out.println(nameList.getSelectedItem());
                changeStudentFrame.add(assignmentAddList,BorderLayout.NORTH);
                changeStudentFrame.add(saveButton, BorderLayout.SOUTH);
                changeStudentFrame.setVisible(true);
                //    ((DefaultTableModel)gradeTable.getModel()).addColumn(new Object[]{"Assignment"});
            }

            if (command.equals("saveAddAssignmentButton")){
                int col = gradeTable.getColumnCount();
                int columnIndex= 0;
                for (int i = 0; i< col; i++){
                    if (assignmentAddList.getSelectedItem() == gradeTable.getModel().getColumnName(i))                                          columnIndex = i;
                }
                ((DefaultTableModel)gradeTable.getModel()).addColumn(assignmentAddList.getSelectedItem());
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

                // changeStudentFrame.setVisible(false);
            }

            if (command.equals("RemoveAssignment")){
                changeStudentFrame = new JFrame("Remove Assignment");
                changeStudentFrame.setSize(200,200);
                changeStudentFrame.setLayout(new BorderLayout(5,5));
                //create an array that has a list of student names

                //		assignmentList = new JComboBox();

                JButton saveButton = new JButton("save");


                //      System.out.println(gradeTable.getModel().getValueAt(0, 2));

                System.out.println("THISS");
                System.out.println(gradeTable.getColumnName(3));

                Object [] list;

                int column = gradeTable.getColumnCount() - 3;
                list = new Object[column];

                for (int i = 0; i< column; i++){
                    //System.out.println(gradeTable.getModel().getValueAt(i,2));
                    list[i] = gradeTable.getColumnName(i+3);//getModel().getValueAt(2,i);

                    assignmentList.addItem(list[i]);

                }


                saveButton.setActionCommand("saveRemoveAssignment");
                saveButton.addActionListener(new ButtonClickListener());

//
                System.out.println(nameList.getSelectedItem());
                changeStudentFrame.add(assignmentList,BorderLayout.NORTH);
                changeStudentFrame.add(saveButton, BorderLayout.SOUTH);
                changeStudentFrame.setVisible(true);


            }

            if(command.equals("saveRemoveAssignment")){

//System.out.println(assignmentList.getSelectedItem());
                int col = gradeTable.getColumnCount();
                int columnIndex= 0;
                for (int i = 0; i< col; i++){
                    if (assignmentList.getSelectedItem() == gradeTable.getModel().getColumnName(i))
                        columnIndex = i;
                }

                String[] identifiers = new String[gradeTable.getColumnCount() - 1];
                int k = 0;
                for(int i = 0; i < gradeTable.getColumnCount(); i++) {
                    if(i != columnIndex) {
                        identifiers[k++] = gradeTable.getColumnName(i);
                    }
                }


//                              TableColumn tcol = gradeTable.getColumnModel().getColumn(selectedColumn);
//                              gradeTable.getColumnModel().removeColumn
//                              ((DefaultTableModel)gradeTable.getModel()).removeColumn(selectedColumn);


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

                // changeStudentFrame.setVisible(false);
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

                for (int i=0; i<rowCount; i++) {
                    float average = 0;
                    for (int j=3; j<colCount; j++) {
                        int percentage = (assignmentArr[j-3].getPercentage());
                        double weight = ((double) percentage)/100;
                        int grade = Integer.parseInt(gradeTable.getValueAt(i, j).toString());
                        double weightGrade = weight * grade;
                        average += weightGrade;
                    }
                    studentAvg[i] = average;
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

    public String[] getColumns (Assignments[] assignArr) {
        int len = assignArr.length+3;

        String[] columns = new String[len];

        columns[0] = "ID";
        columns[1] = "Last Name";
        columns[2] = "First Name";

        for (int i=3; i<len; i++) {
            columns[i] = assignArr[i-3].getAssignment();
        }
        return columns;
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
        try{
            FileWriter writer = new FileWriter(dir.getAbsolutePath(), true);
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
}

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(UIManager.getColor("Button.background"));
        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}


class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed) {

            JOptionPane.showMessageDialog(button, label);
            // System.out.println(label + ": Ouch!");
        }
        isPushed = false;
        return new String(label);
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}

/*
 public void exportTable(JTable table, File file) throws IOException {
        TableModel model = table.getModel();
        FileWriter out = new FileWriter(file);
        for(int i=0; i < model.getColumnCount(); i++) {
            out.write(model.getColumnName(i) + "\t");
        }
        out.write("\n");
        for(int i=0; i< model.getRowCount(); i++) {
            for(int j=0; j < model.getColumnCount(); j++) {
                out.write(model.getValueAt(i,j).toString()+"\t");
            }
            out.write("\n");
        }
        out.close();
        System.out.println("write out to: " + file);
    }*/
