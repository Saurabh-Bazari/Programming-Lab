import java.awt.BorderLayout;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;  
import javax.swing.JOptionPane;
public class Main {

  private int currentVechileNumber=0;

  static Lane southToWestLane;
  static Lane southToEastLane;
  static Lane westToSouthLane;
  static Lane westToEastLane;
  static Lane eastToSouthLane;
  static Lane eastToWestLane;

  int nextVehicleNumberGenerator(){
    currentVechileNumber++;
    return currentVechileNumber;
  }

  void inputFromGUI(char sourceDirectionSymbol,char destinatoinDirectionSymbol, int numberOfVehicle){

    if (sourceDirectionSymbol=='S' && destinatoinDirectionSymbol=='W') {
      for (int indexOfVehicle = 0; indexOfVehicle < numberOfVehicle; indexOfVehicle++) {
        southToWestLane.insertIntoQueue(nextVehicleNumberGenerator());
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
    else if (sourceDirectionSymbol=='E' && destinatoinDirectionSymbol=='S') {
      for (int indexOfVehicle = 0; indexOfVehicle < numberOfVehicle; indexOfVehicle++) {
        eastToSouthLane.insertIntoQueue(nextVehicleNumberGenerator());
      }
    } 
    else if (sourceDirectionSymbol=='W' && destinatoinDirectionSymbol=='E') {
      for (int indexOfVehicle = 0; indexOfVehicle < numberOfVehicle; indexOfVehicle++) {
        westToEastLane.insertIntoQueue(nextVehicleNumberGenerator());
      }
    } 
    else if (sourceDirectionSymbol=='W' && destinatoinDirectionSymbol=='S') {
      for (int indexOfVehicle = 0; indexOfVehicle < numberOfVehicle; indexOfVehicle++) {
        eastToSouthLane.insertIntoQueue(nextVehicleNumberGenerator());
      }
    }     
  }

  public static void main(String[] args) throws InterruptedException{

    TrafficLight trafficLightForSouth = new TrafficLight(120,"RED");
    TrafficLight trafficLightForWest = new TrafficLight(60,"RED");
    TrafficLight trafficLightForEast = new TrafficLight(60,"GREEN");

    southToWestLane = new Lane();
    southToEastLane = new Lane();
    westToSouthLane = new Lane();
    westToEastLane = new Lane();
    eastToSouthLane = new Lane();
    eastToWestLane = new Lane();

    JFrame jFrame=new JFrame();
    jFrame.setSize(2000,1050);
    jFrame.setLayout(null);
    jFrame.setVisible(true);

    TrafficLightControllerThread trafficLightControllerThread = new TrafficLightControllerThread(trafficLightForSouth,trafficLightForWest,trafficLightForEast,jFrame);
    trafficLightControllerThread.start();
    Input input = new Input(jFrame);
  }
}