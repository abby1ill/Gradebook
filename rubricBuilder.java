/**
 * Created by Timmy on 10/13/2016.
 */

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class rubricBuilder {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JPanel headerPanel;
    private JPanel listPanel;
    private JPanel addPanel;
    private JTextField typeField;
    private JTextField percentField;

    ArrayList<JLabel> classes = new ArrayList<>();
    ArrayList<JLabel> percents = new ArrayList<>();

    String className = new String("CISC 1600"); //hard-coded for now

    public rubricBuilder(){
        rubricGUI();
    }

    public static void main(String[] args) {
        rubricBuilder RubricBuilder = new rubricBuilder();
        RubricBuilder.showRubricEvent(); //eventually put this in a while loop (i think)
    }

    private void rubricGUI() {
        mainFrame = new JFrame("Rubric Builder");
        mainFrame.setSize(700, 400);
        mainFrame.setLayout(new GridLayout(4, 1));
        mainFrame.getContentPane().setBackground(new Color(188, 86, 86));

        headerLabel = new JLabel("", JLabel.CENTER);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());

        listPanel = new JPanel();
        listPanel.setLayout(new FlowLayout());

        addPanel = new JPanel();
        addPanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(headerPanel);
        mainFrame.add(listPanel);
        mainFrame.add(addPanel);
        mainFrame.setVisible(true);
    }

    private void showRubricEvent() {
        headerLabel.setText(className);
        headerLabel.setFont(new Font("Georgia", Font.PLAIN, 48));
        headerLabel.setForeground(Color.white);

        JLabel typeLabel = new JLabel("Assignment Type: ", JLabel.CENTER);
        typeLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        typeLabel.setForeground(Color.white);
        JLabel percentLabel = new JLabel("Percentage: ", JLabel.CENTER);
        percentLabel.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        percentLabel.setForeground(Color.white);
        typeField = new JTextField(20);
        percentField = new JTextField(3);


        JLabel assignmentHeader = new JLabel("Assignments ", JLabel.CENTER);
        assignmentHeader.setFont(new Font("Sans Serif", Font.BOLD, 15));
        assignmentHeader.setForeground(Color.white);

        JLabel percentHeader = new JLabel("Percent Worth", JLabel.CENTER);
        percentHeader.setFont(new Font("Sans Serif", Font.BOLD, 15));
        percentHeader.setForeground(Color.white);

        JButton addButton = new JButton("Add");

        addButton.setActionCommand("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listPanel.removeAll();
                String type = typeField.getText();
                String percent = percentField.getText();
                typeField.setText("");
                percentField.setText("");

                JLabel assignmentLabel_1 = new JLabel(type, JLabel.CENTER);
                assignmentLabel_1.setFont(new Font("Sans Serif", Font.PLAIN, 14));
                assignmentLabel_1.setForeground(Color.white);

                JLabel percentLabel_2 = new JLabel(percent, JLabel.CENTER);
                percentLabel_2.setFont(new Font("Sans Serif", Font.PLAIN, 14));
                percentLabel_2.setForeground(Color.white);

                classes.add(assignmentLabel_1);
                percents.add(percentLabel_2);

                JPanel assignmentPanel = new JPanel();
                assignmentPanel.setLayout(new GridLayout(classes.size(), 0));

                JPanel percentPanel = new JPanel();
                percentPanel.setLayout(new GridLayout(percents.size(), 0));

                assignmentPanel.setBackground(new Color(188, 86, 86));
                for(JLabel assign : classes) {
                    assignmentPanel.add(assign);
                }

                percentPanel.setBackground(new Color(188, 86, 86));
                for(JLabel percent1 : percents) {
                    percentPanel.add(percent1);
                }

                //listPanel.setBackground(new Color(188, 86, 86));

                listPanel.add(assignmentPanel);
                listPanel.add(new JLabel("                                  "));
                listPanel.add(percentPanel);

                mainFrame.setVisible(true);
            }
        });

        listPanel.setBackground(new Color(188, 86, 86));

        headerPanel.setBackground(new Color(188, 86, 86));
        headerPanel.add(assignmentHeader);
        headerPanel.add(new JLabel("            "));
        headerPanel.add(percentHeader);


        addPanel.setBackground(new Color(188, 86, 86));
        addPanel.add(typeLabel);
        addPanel.add(typeField);
        addPanel.add(percentLabel);
        addPanel.add(percentField);
        addPanel.add(addButton);

        mainFrame.setVisible(true);
    }

}

