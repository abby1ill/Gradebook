import java.util.Arrays;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Object;
import java.io.FileReader;
import java.io.FileWriter;
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




public class GradebookPage2 extends JFrame{

        private JFrame gradebookFrame;
        private JPanel classNamePanel, tablePanel, controlPanel;
        private JScrollPane scrollPane;
        private JTable gradeTable;
        private JLabel classLabel, studentLabel, assignmentLabel;
 
	//Within MenuBar Tabs
        private JMenu fileTab, editTab, calculateTab, Edit_StudentTab;
        private JMenu Edit_AssignmentTab;
        private JMenuBar MenuBar;
        private JMenuItem Edit_Student_ChangeTab, Edit_Student_AddTab, Edit_Student_RemoveTab;
        private JMenuItem Edit_Assignment_ChangeTab, Edit_Assignment_AddTab, Edit_Assignment_RemoveTab;
        private JRadioButtonMenuItem studentAverage, assignmentAverage, finalAverage;

        public JTextField FNameEdit, LNameEdit;
        public JFrame changeStudentFrame;

	//Used in grade table, needs to be changed to open a file
        public static String[][] content;
        public static String [] columnNames = {"ID", "Last Name", "First Name", "Absent Days", "Assignment 1"};



        public GradebookPage2() {
                gradebookFrameCreation();
                initGradebook();
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

                fileTab.setText("File");
               MenuBar.add(fileTab);

                editTab.setText("Edit");
                editTab.add(Edit_StudentTab);

      		calculateTab.setText("Calculate");
       	 	calculateTab.add(studentAverage);
        	studentAverage.setActionCommand("Student Grades");
        	studentAverage.addActionListener(new ButtonClickListener());

        	calculateTab.add(assignmentAverage);
        	calculateTab.add(finalAverage);

        	studentAverage.setText("Student Average");
       	 	assignmentAverage.setText("Assignment Average");
        	finalAverage.setText("Final Average");

       		MenuBar.add(calculateTab);

        	Edit_StudentTab.setText("Student");

        	Edit_Student_ChangeTab.setText("Change");
        	Edit_StudentTab.add(Edit_Student_ChangeTab);

        	Edit_Student_ChangeTab.setActionCommand("ChangeStudent");
        	Edit_Student_ChangeTab.addActionListener(new ButtonClickListener());

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

                classLabel = new JLabel("Class name goes here");
                classLabel.setFont(new Font("Georgia", Font.PLAIN, 30));
                classLabel.setForeground(Color.white);


                tablePanel.setBackground(new Color(188, 86, 86));
                controlPanel.setBackground(new Color(188, 86, 86));

        	gradeTable = new JTable (new DefaultTableModel());

                classNamePanel.setBackground(new Color(188, 86, 86));
			
		//Opens cvs file
		try{
           		BufferedReader br = new BufferedReader(new FileReader(new File("/Users/mauraodonnell/test.csv")));
            		List<String[]> elements = new ArrayList<String[]>();
            		String line = null;
            		while((line = br.readLine())!=null) {
                		String[] splitted = line.split(",");
                		elements.add(splitted);
            		}
            		br.close();

 	           	content = new String[elements.size()][5];
        	   	TableCellRenderer buttonRenderer = new ButtonRenderer();

           		for (int i =0; i<5; i++){
                		content[i][0] = "+";
        		}

           		for(int i=0; i<4; i++) {
                		content[i][1] = elements.get(i)[1];
               	 		content[i][2] = elements.get(i)[2];
                		content[i][3] = elements.get(i)[3];
            		}

            		gradeTable.setModel(new DefaultTableModel(content,columnNames));

                	setWidthAsPercentages(gradeTable, 0.05, 0.20, 0.25, 0.25, 0.25);
            		System.out.println(gradeTable.getModel().getValueAt(1, 1));

        	} catch (Exception ex) {
            		ex.printStackTrace();
        	}

		gradeTable.getColumn("ID").setCellRenderer(new ButtonRenderer());
    		gradeTable.getColumn("ID").setCellEditor(
        	new ButtonEditor(new JCheckBox()));

                scrollPane = new JScrollPane(gradeTable);
                gradeTable.setFillsViewportHeight(true);

                classNamePanel.add(classLabel);
                tablePanel.add(scrollPane);

                gradebookFrame.add(classNamePanel, BorderLayout.WEST);
                gradebookFrame.add(tablePanel,BorderLayout.CENTER);
                gradebookFrame.setVisible(true);
        }


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
                        if(command.equals("ChangeStudent")) {
                                 changeStudentFrame = new JFrame("Change Student");
                                  changeStudentFrame.setSize(200,200);
                                  changeStudentFrame.setLayout(new BorderLayout(5,5));
                             
	 			  //create an array that has a list of student names
                                String names[] = new String[4];

                                JLabel editFName = new JLabel("First Name");
                                JLabel editLName = new JLabel("Last Name");
                                JLabel editID = new JLabel("Student ID #");
                                JLabel editDays = new JLabel("Days Missed");
                                FNameEdit = new JTextField(content[0][1]);
                                LNameEdit = new JTextField(content[0][0]);
                                JTextField IDEdit = new JTextField("1231431");
                                JTextField DaysEdit = new JTextField(content[0][3]);
                                JButton saveButton = new JButton("save");
                                saveButton.setActionCommand("saveButton");
                                saveButton.addActionListener(new ButtonClickListener());


                                JComboBox nameList = new JComboBox(names);
                                JPanel infoList = new JPanel();
                                infoList.setLayout(new GridLayout(4,4));
                                infoList.add(editFName);
                                infoList.add(FNameEdit);
                                infoList.add(editLName);
                                infoList.add(LNameEdit);
                                infoList.add(editID);
                                infoList.add(IDEdit);

                                infoList.add(editDays);
                                infoList.add(DaysEdit);

                                changeStudentFrame.add(nameList,BorderLayout.NORTH);
                                changeStudentFrame.add(infoList,BorderLayout.CENTER);
                                changeStudentFrame.add(saveButton, BorderLayout.SOUTH);

                                  changeStudentFrame.setVisible(true);

                        }
                        if (command.equals("saveButton")){
                                 content[0][1] = FNameEdit.getText();
                                 content[0][0] = LNameEdit.getText();

                                 gradeTable.setModel(new DefaultTableModel(content,columnNames));                                        				  gradeTable.setModel(new DefaultTableModel(content,columnNames));

                        }

                        if(command.equals("AddStudent")){

                                changeStudentFrame = new JFrame("Add Student");
                                changeStudentFrame.setSize(200,200);
                                changeStudentFrame.setLayout(new BorderLayout(5,5));
                                //create an array that has a list of student names
                                String names[] = new String[4];

                                JLabel editFName = new JLabel("First Name");
                                JLabel editLName = new JLabel("Last Name");
                                JLabel editID = new JLabel("Student ID #");
                                JLabel editDays = new JLabel("Days Missed");
                                FNameEdit = new JTextField();
                                LNameEdit = new JTextField();
                                JTextField IDEdit = new JTextField();
                                JTextField DaysEdit = new JTextField();
                                JButton saveButton = new JButton("save");
                                saveButton.setActionCommand("saveAddButton");

                                saveButton.addActionListener(new ButtonClickListener());


                                JComboBox nameList = new JComboBox(names);
                                JPanel infoList = new JPanel();
                                infoList.setLayout(new GridLayout(4,4));
                                infoList.add(editFName);
                                infoList.add(FNameEdit);
                                infoList.add(editLName);
                                infoList.add(LNameEdit);
                                infoList.add(editID);
                                infoList.add(IDEdit);
                                infoList.add(editDays);
                                infoList.add(DaysEdit);

                                changeStudentFrame.add(nameList,BorderLayout.NORTH);
                                changeStudentFrame.add(infoList,BorderLayout.CENTER);
                                changeStudentFrame.add(saveButton, BorderLayout.SOUTH);

                                  changeStudentFrame.setVisible(true);
                        }

                         if(command.equals("saveAddButton")){
                               String[][] newsr = Arrays.copyOf(content, content.length+1);
				newsr[content.length] = new String[content.length]; // Initializing the new row

				//Needs to be changed to actual input
				String[] d = {FNameEdit.getText(),LNameEdit.getText(), "87", "5"};

				for (int i =0; i<6; i++){
                			newsr[i][0] = "+";
        			}

				// Copying data from d array to the newsr array
				for (int i = 1; i < 4; i++) {
    					newsr[5][i] = String.valueOf(d[i]);
				}

				setWidthAsPercentages(gradeTable, 0.05, 0.20, 0.25, 0.25, 0.25);

				gradeTable.setModel(new DefaultTableModel(newsr,columnNames));
				gradeTable.getColumn("ID").setCellRenderer(new ButtonRenderer());
    				gradeTable.getColumn("ID").setCellEditor(new ButtonEditor(new JCheckBox()));

                    		gradeTable.setModel(new DefaultTableModel(newsr,columnNames));

                                }

                     	  if (command.equals("RemoveStudent")){
                                changeStudentFrame = new JFrame("Add Student");
                                changeStudentFrame.setSize(200,200);
                                changeStudentFrame.setLayout(new BorderLayout(5,5));
                                //create an array that has a list of student names

                                String names[] = new String[4];
                                JButton saveButton = new JButton("save");
                                saveButton.setActionCommand("saveRemoveButton");
                                saveButton.addActionListener(new ButtonClickListener());

                                JComboBox nameList = new JComboBox(names);
                                changeStudentFrame.add(nameList,BorderLayout.NORTH);
                                changeStudentFrame.add(saveButton, BorderLayout.SOUTH);

                                  changeStudentFrame.setVisible(true);

                        ((DefaultTableModel)gradeTable.getModel()).removeRow(2);

                        }


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
     
      		JOptionPane.showMessageDialog(button, label + ": Ouch!");
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

