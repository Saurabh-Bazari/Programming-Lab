import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.JLabel;

class Input {
    
    // variable for GUI window
    JFrame jFrame;
    // Table model for input all vehicles from any direction
    static DefaultTableModel inputTableModel;
    // submit button for launch vehicles
    static JButton submitButton;
    // jtable var for table model
    JTable jTable;

    public Input(JFrame jFrame) {
        // constructor
        this.jFrame = jFrame; 
        Main mainObject = new Main(); 

        // create new panel and table for input table
        JPanel jPanel = new JPanel();
        jTable = new JTable();
        inputTableModel = (DefaultTableModel) jTable.getModel();

        // create submit button with enter litsener
        submitButton = new JButton("RUN");
        submitButton.setActionCommand("ENTER");
        submitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // whenever run button is clicked
                Main.start=false;
                // for each direction read data 
                // pass into backend code
                // again initilize to 0
                for(int i=0;i<6;i++){
                    mainObject.inputFromGUI(((String)inputTableModel.getValueAt(i,0)).charAt(0),
                    ((String)inputTableModel.getValueAt(i,1)).charAt(0),
                    Integer.parseInt(((String)inputTableModel.getValueAt(i,2))));
                    inputTableModel.setValueAt("0",i,2);
                }
                // update table
                inputTableModel.fireTableDataChanged();
            }
        });
        // set panel size and location
        jPanel.setBounds(80, 50, 500, 120); 
        JPanel buttonPanel = new JPanel();
        // set and add button size and location
        buttonPanel.setBounds(250,175,100,100);
        buttonPanel.add(submitButton);
        // add table to panel
        JScrollPane jScrollPane = new JScrollPane(jTable); 
        jPanel.add(jScrollPane,BorderLayout.CENTER);
        // add panel to frame
        jFrame.add(jPanel); 
        jFrame.add(buttonPanel);
        jFrame.setVisible(true);

        // insert heading data into table
        inputTableModel.setRowCount(0);
        String columnIndentifier[]={"Initial Directions","Final Direction","Cars"};
        inputTableModel.setColumnIdentifiers(columnIndentifier);

        // insert all rows into table
        String rowData [] ={"S","W","0"};
        inputTableModel.addRow(rowData);
        rowData[1]="E";
        inputTableModel.addRow(rowData);
        rowData[0]="E";
        rowData[1]="W";
        inputTableModel.addRow(rowData);
        rowData[0]="W";
        rowData[1]="E";
        inputTableModel.addRow(rowData);
        rowData[0]="E";
        rowData[1]="S";
        inputTableModel.addRow(rowData);
        rowData[0]="W";
        rowData[1]="S";
        inputTableModel.addRow(rowData);
    }
}