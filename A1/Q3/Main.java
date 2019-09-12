import java.awt.BorderLayout;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;  
import javax.swing.JOptionPane;
public class Main {

  static Object Lock;
  static DefaultTableModel vehicleTableModel;
  public static Lane southToEastLane;
  public static Boolean start;
  public static Lane westToSouthLane;
  public static Lane eastToWestLane;

  public static List<Vehicle> vehicleList = new ArrayList<>(); 

  private int currentVechileNumber=0;

  public void inputFromGUI(char sourceDirectionSymbol,char destinatoinDirectionSymbol, int numberOfVehicle){

    if (sourceDirectionSymbol=='S' && destinatoinDirectionSymbol=='W') {
      for (int indexOfVehicle = 0; indexOfVehicle < numberOfVehicle; indexOfVehicle++) {
        Vehicle vehicle = new Vehicle(nextVehicleNumberGenerator(),sourceDirectionSymbol,destinatoinDirectionSymbol,0);
        synchronized(Lock){
          vehicleList.add(vehicle);
        }
      }
    } 
    else if (sourceDirectionSymbol=='E' && destinatoinDirectionSymbol=='S') {
      for (int indexOfVehicle = 0; indexOfVehicle < numberOfVehicle; indexOfVehicle++) {
        Vehicle vehicle = new Vehicle(nextVehicleNumberGenerator(),sourceDirectionSymbol,destinatoinDirectionSymbol,0);
        synchronized(Lock){
          vehicleList.add(vehicle);
        }
      }
    } 
    else if (sourceDirectionSymbol=='W' && destinatoinDirectionSymbol=='E') {
      for (int indexOfVehicle = 0; indexOfVehicle < numberOfVehicle; indexOfVehicle++) {
        Vehicle vehicle = new Vehicle(nextVehicleNumberGenerator(),sourceDirectionSymbol,destinatoinDirectionSymbol,0);
        synchronized(Lock){
          vehicleList.add(vehicle);
        }
      }
    } 
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

  int nextVehicleNumberGenerator(){
    currentVechileNumber++;
    return currentVechileNumber;
  }




  public static void main(String[] args) throws InterruptedException{
    Lock =new Object();
    TrafficLight trafficLightForSouth = new TrafficLight(120,"RED");
    TrafficLight trafficLightForWest = new TrafficLight(60,"RED");
    TrafficLight trafficLightForEast = new TrafficLight(60,"GREEN");

    southToEastLane = new Lane('S','E',trafficLightForSouth,Lock);
    westToSouthLane = new Lane('W','S',trafficLightForWest,Lock);
    eastToWestLane = new Lane('E','W',trafficLightForEast,Lock);
    String[] columnIndentifier = {
      "Vehicle Number",
      "Source Direction",
      "Destination Direction",
      "Status",
      "Time"
    };
    JFrame jFrame=new JFrame();
    JTable jTable =new JTable();
    JPanel jPanel =new JPanel();
    jPanel.setBounds(100,400,2000,1000);
    vehicleTableModel =(DefaultTableModel) jTable.getModel();
    vehicleTableModel.setColumnIdentifiers(columnIndentifier);
    JScrollPane jScrollPane = new JScrollPane(jTable); 
    jPanel.add(jScrollPane,BorderLayout.CENTER);
    jFrame.add(jPanel); 
    jFrame.setSize(2000,1050);
    jFrame.setLayout(null);
    jFrame.setVisible(true);
    TrafficLightControllerThread trafficLightControllerThread = new TrafficLightControllerThread(trafficLightForSouth,trafficLightForWest,trafficLightForEast,jFrame);
    TimerThread timerThread = new TimerThread(vehicleTableModel,Lock);
    Input input = new Input(jFrame);
    start=true;
    while(start){
      System.out.println(start);
    }
    trafficLightControllerThread.start();
    timerThread.start();
  }
}