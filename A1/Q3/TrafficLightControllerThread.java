import java.util.*;
import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
class TrafficLightControllerThread extends Thread {

    TrafficLight trafficLightForSouth;
    TrafficLight trafficLightForWest;
    TrafficLight trafficLightForEast;
    JFrame jFrame;
    int currentTimer;
    JTable jTable;
    DefaultTableModel trafficLightTableModel;

    public TrafficLightControllerThread(TrafficLight trafficLightForSouth, TrafficLight trafficLightForWest, TrafficLight trafficLightForEast,JFrame jFrame) {
        this.trafficLightForEast = trafficLightForEast;
        this.trafficLightForSouth = trafficLightForSouth;
        this.trafficLightForWest = trafficLightForWest;
        this.jFrame = jFrame;

        String [] columnIndentifier = {"Traffic Light" , "Status", "Time"};
    
        jTable = new JTable(); 
        JPanel jPanel = new JPanel();
        jPanel.setBounds(1200, 40, 500, 72); 
        trafficLightTableModel = (DefaultTableModel) jTable.getModel();
        trafficLightTableModel.setColumnIdentifiers(columnIndentifier);
        JScrollPane jScrollPane = new JScrollPane(jTable); 
        jPanel.add(jScrollPane,BorderLayout.CENTER);
        jFrame.add(jPanel); 
        jFrame.setVisible(true);
        trafficLightTableModel.setRowCount(0); 
        String data[]={"T1","RED","--"};
        trafficLightTableModel.addRow(data);
        data[0]="T2";
        trafficLightTableModel.addRow(data);
        data[0]="T3";
        trafficLightTableModel.addRow(data);
        trafficLightTableModel.fireTableDataChanged();
    }
    public void run() { 
        
        while(true){
            trafficLightForEast.setColour("Green");
            trafficLightForEast.setTimeForCurrentState(60);
            trafficLightForSouth.setColour("RED");
            trafficLightForSouth.setTimeForCurrentState(120);            
            trafficLightForWest.setColour("RED");
            trafficLightForWest.setTimeForCurrentState(60);
            updateTrafficLightColour("Green","RED","RED");
            continueFor60Seconds();
    
            trafficLightForEast.setColour("RED");
            trafficLightForEast.setTimeForCurrentState(120);
            trafficLightForSouth.setColour("RED");
            trafficLightForSouth.setTimeForCurrentState(60);
            trafficLightForWest.setColour("Green");
            trafficLightForWest.setTimeForCurrentState(60);
            updateTrafficLightColour("RED","RED","GREEN");
            continueFor60Seconds();
    
            trafficLightForEast.setColour("RED");
            trafficLightForEast.setTimeForCurrentState(60);
            trafficLightForSouth.setColour("Green");
            trafficLightForSouth.setTimeForCurrentState(60);
            trafficLightForWest.setColour("RED");
            trafficLightForWest.setTimeForCurrentState(120);
            updateTrafficLightColour("RED","GREEN","RED");
            continueFor60Seconds();
        }
    }

    void continueFor60Seconds(){
        currentTimer=60;
        while(currentTimer>0){
            updateTrafficLightTime(trafficLightForEast.getTimeForCurrentState(), trafficLightForSouth.getTimeForCurrentState(), trafficLightForWest.getTimeForCurrentState());
            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            trafficLightForEast.setTimeForCurrentState( trafficLightForEast.getTimeForCurrentState() -1);
            trafficLightForSouth.setTimeForCurrentState( trafficLightForSouth.getTimeForCurrentState() -1);
            trafficLightForWest.setTimeForCurrentState( trafficLightForWest.getTimeForCurrentState() -1);
            currentTimer--;
            }
    }
    void updateTrafficLightTime(int time1,int time2,int time3){
        trafficLightTableModel.setValueAt(time1,0,2);
        trafficLightTableModel.setValueAt(time2,1,2);
        trafficLightTableModel.setValueAt(time3,2,2);
        trafficLightTableModel.fireTableDataChanged();
    }
    void updateTrafficLightColour(String color1,String color2,String color3){
        trafficLightTableModel.setValueAt(color1,0,1);
        trafficLightTableModel.setValueAt(color2,1,1);
        trafficLightTableModel.setValueAt(color3,2,1);
        trafficLightTableModel.fireTableDataChanged();
    }
}