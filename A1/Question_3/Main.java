import java.awt.BorderLayout;
import java.util.Timer;
import javax.swing.table.DefaultTableModel;
import java.util.TimerTask;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;  
import javax.swing.JOptionPane;
import java.util.concurrent.locks.ReentrantLock; 
public class Main {
  // lock for synchronize
  static ReentrantLock vehicleListLock;
  public static DefaultTableModel vehicleTableModel;
  // start bool for start timer
  public static Boolean start;
  // Lane object for each way
  public static Lane southToEastLane;
  public static Lane westToSouthLane;
  public static Lane eastToWestLane;
  // list stores each vehicle details
  public static List<Vehicle> vehicleList = new ArrayList<>(); 
  // stores next vehicle number
  private int currentVechileNumber=0;
  // method for add vehicle details in list
  public void inputFromGUI(char sourceDirectionSymbol,char destinatoinDirectionSymbol, int numberOfVehicle){
    String vehicleTableData[]={"","","",
      "WAIT",
      "--"
    };
    // bypass vehicle is directly passed
    if ( (sourceDirectionSymbol=='S' && destinatoinDirectionSymbol=='W') ||
     (sourceDirectionSymbol=='E' && destinatoinDirectionSymbol=='S') || 
     (sourceDirectionSymbol=='W' && destinatoinDirectionSymbol=='E') ) {
      for (int indexOfVehicle = 0; indexOfVehicle < numberOfVehicle; indexOfVehicle++) {
        Vehicle vehicle = new Vehicle(nextVehicleNumberGenerator(),sourceDirectionSymbol,destinatoinDirectionSymbol,-6);
        vehicleListLock.lock();
        vehicleTableData[0]=Integer.toString(vehicle.getVehicleNumber());
        vehicleTableData[1]=Character.toString(vehicle.getSourceDirectionSymbol());
        vehicleTableData[2]=Character.toString(vehicle.getDestinationDirectionSymbol());
        vehicleList.add(vehicle);
        vehicleTableModel.addRow(vehicleTableData);
        vehicleListLock.unlock();
      }
      vehicleTableModel.fireTableDataChanged();
    } 
    // cross road vehicle add to corresponding lane
    else if (sourceDirectionSymbol=='S' && destinatoinDirectionSymbol=='E') {
      for (int indexOfVehicle = 0; indexOfVehicle < numberOfVehicle; indexOfVehicle++) {
        southToEastLane.insertIntoQueue(nextVehicleNumberGenerator());
      }
    } 
    else if (sourceDirectionSymbol=='E' && destinatoinDirectionSymbol=='W') {
      for (int indexOfVehicle = 0; indexOfVehicle < numberOfVehicle; indexOfVehicle++) {
        eastToWestLane.insertIntoQueue(nextVehicleNumberGenerator());
      }
    } 
    else if (sourceDirectionSymbol=='W' && destinatoinDirectionSymbol=='S') {
      for (int indexOfVehicle = 0; indexOfVehicle < numberOfVehicle; indexOfVehicle++) {
        westToSouthLane.insertIntoQueue(nextVehicleNumberGenerator());
      }
    }     
  }
  // get next vehicle number
  int nextVehicleNumberGenerator(){
    currentVechileNumber++;
    return currentVechileNumber;
  }

  public static void main(String[] args) throws InterruptedException{
    // initilize lock for vehicle list
    vehicleListLock = new ReentrantLock();
    // create object for each traffic light
    TrafficLight trafficLightForSouth = new TrafficLight(120,"RED");
    TrafficLight trafficLightForWest = new TrafficLight(60,"RED");
    TrafficLight trafficLightForEast = new TrafficLight(60,"GREEN");
    
    // create lane for each way
    southToEastLane = new Lane('S','E',trafficLightForSouth,vehicleListLock);
    westToSouthLane = new Lane('W','S',trafficLightForWest,vehicleListLock);
    eastToWestLane = new Lane('E','W',trafficLightForEast,vehicleListLock);

    String[] columnIndentifier = {
      "Vehicle",
      "Source",
      "Destination",
      "Status",
      "Time"
    };
    // create frame table and panel 
    JFrame jFrame=new JFrame();
    JTable jTable =new JTable();
    JPanel jPanel =new JPanel();
    // set location and size of panel
    jPanel.setBounds(500,200,1500,2000);
    // connect table model to jTable
    vehicleTableModel =(DefaultTableModel) jTable.getModel();
    // add header to vehicle table model
    vehicleTableModel.setColumnIdentifiers(columnIndentifier);
    vehicleTableModel.setRowCount(0);
    JScrollPane jScrollPane = new JScrollPane(jTable); 
    jPanel.add(jScrollPane,BorderLayout.CENTER);
    // add panel to frame
    jFrame.add(jPanel); 
    // set frame size and then display
    jFrame.setSize(2000,1050);
    jFrame.setLayout(null);
    jFrame.setVisible(true);

    // create object for traffic light controller
    // create object for update vehicle remaining time 
    TrafficLightControllerThread trafficLightControllerThread = new TrafficLightControllerThread(
      trafficLightForSouth,trafficLightForWest,trafficLightForEast,jFrame);
    TimerThread timerThread = new TimerThread(vehicleTableModel,vehicleListLock);
    // input class object for input vehicle
    Input input = new Input(jFrame);
    start=true;
    int forContinue=0;
    // wait for first input then start the clock
    while(start){
      System.out.print(" ");
      forContinue++;
    }
    // start all threads
    System.out.println("start");
    trafficLightControllerThread.start();
    timerThread.start();
  }
}