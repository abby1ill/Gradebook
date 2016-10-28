import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class work extends JFrame{
     JTable table;
     JButton button;
         Object row[][] = {{"A1","B1","C1"},{"A2","B2","C2"},{"A3","B3","C3"},{"A4","B4","C4"}
                           ,{"A5","B5","C5"},{"A6","B6","C6"},{"A7","B7","C7"},{"A8","B8","C8"}
                            ,{"A9","B9","C9"},{"A10","B10","C10"}
                      };
    Object column[] = {"column 1","column 2","column 3"};
     public work(){


         super("Export JTable Data To Text File");

         

         //Bind Data In JTable
         table = new JTable(row, column);




         JScrollPane js = new JScrollPane(table);
         js.setBounds(20,20,440,350);
         
         button = new JButton("Write JTable Values In Text File");
         button.setBounds(120, 380, 250, 30);
         
        button.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e){
               try{
                //the file path
               File file = new File("/Users/abbyoneill/JavaPractice/jTable/practice.txt");
               //if the file not exist create one
               if(!file.exists()){
                   file.createNewFile();
               }
               
               FileWriter fw = new FileWriter(file.getAbsoluteFile());
               BufferedWriter bw = new BufferedWriter(fw);
               
               //loop for jtable rows
               for(int i = 0; i < table.getRowCount(); i++){
                   //loop for jtable column
                   for(int j = 0; j < table.getColumnCount(); j++){
                       bw.write(table.getModel().getValueAt(i, j)+" ");
                   }
                   //break line at the begin 
                   //break line at the end 
                   bw.write("\n_________\n");
               }
               //close BufferedWriter
               bw.close();
               //close FileWriter 
               fw.close();
               JOptionPane.showMessageDialog(null, "Data Exported");
               
               }catch(Exception ex){
                   ex.printStackTrace();
               }
           }
        });

         add(button);
         add(js);
         setLayout(null);
         setSize(500, 500);
         getContentPane().setBackground(Color.decode("#bdb76b"));
         setLocationRelativeTo(null);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setVisible(true);
     }
     
     public static void main(String[] args){
         new work();
     }
    }

