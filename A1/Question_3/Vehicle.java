import java.util.*;
public class Vehicle {
    // var for vehicle number
    private int vehicleNumber;
    // var for vehicle source direction
    private char sourceDirectionSymbol;
    //var for destination direction
    private char destinationDirectionSymbol;
    // var stores remaining time
    private int waitingTimeForCrossRoad;
    // stores status of traffic light when passed
    private int timeLeftForGreenAfterPassed;

    // getter setter for vehicleNumber
    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
    // getter setter sourceDirectionSymbol
    public char getSourceDirectionSymbol() {
        return sourceDirectionSymbol;
    }

    public void setSourceDirectionSymbol(char sourceDirectionSymbol) {
        this.sourceDirectionSymbol = sourceDirectionSymbol;
    }

    // getter setter destinationDirectionSymbol
    public char getDestinationDirectionSymbol() {
        return destinationDirectionSymbol;
    }

    public void setDestinationDirectionSymbol(char destinationDirectionSymbol) {
        this.destinationDirectionSymbol = destinationDirectionSymbol;
    }

    // getter setter waitingTimeForCrossRoad
    public int getWaitingTimeForCrossRoad() {
        return waitingTimeForCrossRoad;
    }

    public void setWaitingTimeForCrossRoad(int waitingTimeForCrossRoad) {
        this.waitingTimeForCrossRoad = waitingTimeForCrossRoad;
    }

    // getter setter timeLeftForGreenAfterPassed
    public int getTimeLeftForGreenAfterPassed() {
        return timeLeftForGreenAfterPassed;
    }

    public void setTimeLeftForGreenAfterPassed(int timeLeftForGreenAfterPassed) {
        this.timeLeftForGreenAfterPassed = timeLeftForGreenAfterPassed;
    }
    //constructor
    public Vehicle(int vehicleNumber, char sourceDirectionSymbol, char destinationDirectionSymbol,
            int waitingTimeForCrossRoad) {
        this.vehicleNumber = vehicleNumber;
        this.sourceDirectionSymbol = sourceDirectionSymbol;
        this.destinationDirectionSymbol = destinationDirectionSymbol;
        this.waitingTimeForCrossRoad = waitingTimeForCrossRoad;
    }
}