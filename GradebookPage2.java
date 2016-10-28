import java.util.Arrays;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class GradebookPage2 {
        private JFrame gradebookFrame;
        private JPanel classNamePanel, tablePanel, controlPanel;
        private JScrollPane scrollPane;
        private JTable gradeTable;
        private JButton addAssignment, deleteAssignment, addStudent, deleteStudent;
        private JLabel classLabel, studentLabel, assignmentLabel;


        private JMenu fileTab, editTab,Edit_StudentTab;
        private JMenu Edit_AssignmentTab;
        private JMenuBar jMenuBar1;
        private JMenuItem Edit_Student_ChangeTab;
        private JMenuItem Edit_Student_RemoveTab;
        private JMenuItem jMenuItem3;
        private JMenuItem Edit_Student_AddTab;
        private JMenuItem Edit_Assignment_ChangeTab;
        private JMenuItem Edit_Assignment_AddTab;
        private JMenuItem Edit_Assignment_RemoveTab;

        public static String [][] data = {
                {"Doe", "Jane", "1", "5"},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
                 };


        public GradebookPage2() {

                gradebookFrameCreation();
                initGradebook();
         }


        private void gradebookFrameCreation() {
                gradebookFrame = new JFrame("Gradebook");
                gradebookFrame.setSize(1000,700);
                gradebookFrame.setLayout(new BorderLayout(5,5));
                gradebookFrame.getContentPane().setBackground(new Color(188, 86, 86));


                jMenuItem3 = new JMenuItem();
                jMenuBar1 = new JMenuBar();
                fileTab = new JMenu();
                editTab = new JMenu();
                Edit_StudentTab = new JMenu();
                Edit_Student_ChangeTab = new JMenuItem();
                                Edit_Student_AddTab = new JMenuItem();
                Edit_Student_RemoveTab = new JMenuItem();
                Edit_AssignmentTab = new JMenu();
                Edit_Assignment_ChangeTab = new JMenuItem();
                Edit_Assignment_AddTab = new JMenuItem();
                Edit_Assignment_RemoveTab = new JMenuItem();


        jMenuItem3.setText("jMenuItem3");

        fileTab.setText("File");


        jMenuBar1.add(fileTab);

        editTab.setText("Edit");

        Edit_StudentTab.setText("Student");

        Edit_Student_ChangeTab.setText("Change");
        Edit_StudentTab.add(Edit_Student_ChangeTab);

        Edit_Student_ChangeTab.setActionCommand("ChangeStudent");
        Edit_Student_ChangeTab.addActionListener(new ButtonClickListener());


        Edit_Student_AddTab.setText("Add");
        Edit_StudentTab.add(Edit_Student_AddTab);

        Edit_Student_RemoveTab.setText("Remove");
        Edit_StudentTab.add(Edit_Student_RemoveTab);


        editTab.add(Edit_StudentTab);

        Edit_AssignmentTab.setText("Assignment");

        Edit_Assignment_ChangeTab.setText("Change");
        Edit_AssignmentTab.add(Edit_Assignment_ChangeTab);

        Edit_Assignment_AddTab.setText("Add");
        Edit_AssignmentTab.add(Edit_Assignment_AddTab);

        Edit_Assignment_RemoveTab.setText("Remove");
        Edit_AssignmentTab.add(Edit_Assignment_RemoveTab);

        editTab.add(Edit_AssignmentTab);

        jMenuBar1.add(editTab);



        gradebookFrame.add(jMenuBar1,BorderLayout.PAGE_START);
        gradebookFrame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent windowEvent){
           System.exit(0);
         }
      });

   //   gradebookFrame.setVisible(true);
        }

        private void initGradebook() {
        tablePanel = new JPanel();
tablePanel.setLayout(new GridLayout(0,1,1,1));
                controlPanel = new JPanel();
                classNamePanel = new JPanel();

                classLabel = new JLabel("Class name goes here");
                classLabel.setFont(new Font("Georgia", Font.PLAIN, 30));
      classLabel.setForeground(Color.white);


                String [] columnNames = {
                "Last Name", "First Name", "Absent Days", "Assignment 1"
      };


                tablePanel.setBackground(new Color(188, 86, 86));
                controlPanel.setBackground(new Color(188, 86, 86));
                classNamePanel.setBackground(new Color(188, 86, 86));

                gradeTable = new JTable (GradebookPage2.data, columnNames);

                scrollPane = new JScrollPane(gradeTable);
                gradeTable.setFillsViewportHeight(true);

                classNamePanel.add(classLabel);
                tablePanel.add(scrollPane);

                gradebookFrame.add(classNamePanel, BorderLayout.WEST);
//              gradebookFrame.add(controlPanel, BorderLayout.EAST);
                gradebookFrame.add(tablePanel,BorderLayout.CENTER);
                gradebookFrame.setVisible(true);
        }


        public class ButtonClickListener implements ActionListener{
                public void actionPerformed(ActionEvent e){
                        String command = e.getActionCommand();
                        if(command.equals("ChangeStudent")) {
                                                        JFrame changeStudentFrame = new JFrame("Change Student");
                                  changeStudentFrame.setSize(300,400);
                                  changeStudentFrame.setLayout(new BorderLayout(5,5));
                                //create an array that has a list of student names
                                String names[] = new String[4];
                                for (int i = 0; i<4; i++){
                                        names[i]=data[i][0]+","+data[i][1];
                                }

                                JComboBox nameList = new JComboBox(names);
                                changeStudentFrame.add(nameList,BorderLayout.NORTH);
                                  changeStudentFrame.setVisible(true);

                        }

                }


        }
}
