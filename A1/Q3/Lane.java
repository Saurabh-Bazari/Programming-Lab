import java.util.*;
public class Lane {

    private Queue<Vehicle> vehiclesQueue = new LinkedList<>();
    private char sourceDirectionSymbol;
    private char destinationDirectionSymbol;
    TrafficLight trafficLight;

    public Lane(TrafficLight trafficLight){
        this.trafficLight = trafficLight;
    }

    public Queue<Vehicle> getVehiclesQueue() {
        return vehiclesQueue;
    }

    public void setVehiclesQueue(Queue<Vehicle> vehiclesQueue) {
        this.vehiclesQueue = vehiclesQueue;
    }

    public void insertIntoQueue(int vehicleNumber){
        // Vehicle vehicle = new Vehicle(vehicleNumber,sourceDirectionSymbol,destinationDirectionSymbol);     
        System.out.println(vehicleNumber);   
    }    
}