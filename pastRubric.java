import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class pastRubric {
	private JFrame pastFrame;
	private JLabel headerLabel;
	String className = createPage.courseTitle;	

	public pastRubric() {
		pastFrameCreation();
		pastRubricGUI();
	}

	private void pastFrameCreation() {
		pastFrame = new JFrame("Gradebook");
		pastFrame.setSize(1000,700);
      pastFrame.setLayout(new BorderLayout());
      pastFrame.getContentPane().setBackground(new Color(188, 86, 86));


      pastFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
           System.exit(0);
         }
      });

      pastFrame.setVisible(true);
   }

	private void pastRubricGUI() {
		headerLabel = new JLabel(className, JLabel.CENTER);

      headerLabel.setFont(new Font("Georgia", Font.PLAIN, 48));
      headerLabel.setForeground(Color.white);

		pastFrame.add(headerLabel);
	}
}
