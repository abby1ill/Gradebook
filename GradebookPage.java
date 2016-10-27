import java.util.Arrays;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;




public class GradebookPage {
	private JFrame gradebookFrame;
	private JPanel classNamePanel, tablePanel, controlPanel;
	private JScrollPane scrollPane;
	private JTable gradeTable;
	private JButton addAssignment, deleteAssignment, addStudent, deleteStudent;
	private JLabel classLabel, studentLabel, assignmentLabel;

	public GradebookPage() {
		gradebookFrameCreation();
   	initGradebook();
   }
    

	private void gradebookFrameCreation() {
      gradebookFrame = new JFrame("Gradebook");
      gradebookFrame.setSize(1000,700);
      gradebookFrame.setLayout(new BorderLayout());
      gradebookFrame.getContentPane().setBackground(new Color(188, 86, 86));

      gradebookFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
           System.exit(0);
         }
      });

      gradebookFrame.setVisible(true);
	}

	private void initGradebook() {
   	tablePanel = new JPanel();
		controlPanel = new JPanel();	
		classNamePanel = new JPanel();

		classLabel = new JLabel("Class name goes here");
		classLabel.setFont(new Font("Georgia", Font.PLAIN, 30));
      classLabel.setForeground(Color.white);

		Object [][] data = {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
      };

		String [] columnNames = {
                "Last Name", "First Name", "Absent Days", "Assignment 1"
      };

		tablePanel.setBackground(new Color(188, 86, 86));
		controlPanel.setBackground(new Color(188, 86, 86));
		classNamePanel.setBackground(new Color(188, 86, 86));

		gradeTable = new JTable (data, columnNames);
		scrollPane = new JScrollPane(gradeTable);
		gradeTable.setFillsViewportHeight(true);

		classNamePanel.add(classLabel);
		tablePanel.add(scrollPane);
		gradebookFrame.add(classNamePanel, BorderLayout.NORTH);
		gradebookFrame.add(controlPanel, BorderLayout.WEST);
		gradebookFrame.add(tablePanel, BorderLayout.CENTER);
   }
}

