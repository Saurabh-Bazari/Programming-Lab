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
    JFrame jFrame;
    int currentTimer;
    // static JTextArea ;
    static JLabel SWLabel;
    static JTextArea SWinput;
    static JTextArea SEinput;
    static JTextArea WEinput;
    static DefaultTableModel inputTableModel;
    static JTextArea WSinput;
    static JTextArea ESinput;
    static JTextArea EWinput;
    static JButton submitButton;
    JTable jTable;


    public Input(JFrame jFrame) {
        Main mainObject = new Main();
        this.jFrame = jFrame;  
        JPanel jPanel = new JPanel();
        jTable = new JTable();
        submitButton = new JButton("SUBMIT");
        submitButton.setActionCommand("ENTER");
        submitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                for(int i=0;i<6;i++){
                    mainObject.inputFromGUI(((String)inputTableModel.getValueAt(i,0)).charAt(0),
                    ((String)inputTableModel.getValueAt(i,1)).charAt(0),
                    Integer.parseInt(((String)inputTableModel.getValueAt(i,2))));
                    inputTableModel.setValueAt("0",i,2);
                }
                inputTableModel.fireTableDataChanged();
            }
        });
        jPanel.setBounds(40, 50, 500, 120); 
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(220,175,100,100);
        buttonPanel.add(submitButton);
        // submitButton.setBounds(500,500,10,10);
        JScrollPane jScrollPane = new JScrollPane(jTable); 
        jPanel.add(jScrollPane,BorderLayout.CENTER);
        // jPanel.add(submitButton);
        jFrame.add(jPanel); 
        jFrame.add(buttonPanel);
        jFrame.setVisible(true);
        inputTableModel = (DefaultTableModel) jTable.getModel();
        inputTableModel.setRowCount(0);
        String columnIndentifier[]={"Initial Directions","Final Direction","Cars"};
        inputTableModel.setColumnIdentifiers(columnIndentifier);
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