import java.util.*;
public class Vehicle {

    private int vehicleNumber;
    private char sourceDirectionSymbol;
    private char destinationDirectionSymbol;
    private int waitingTimeForCrossRoad;
    private int timeLeftForGreenAfterPassed;

    public int getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(int vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public char getSourceDirectionSymbol() {
        return sourceDirectionSymbol;
    }

    public void setSourceDirectionSymbol(char sourceDirectionSymbol) {
        this.sourceDirectionSymbol = sourceDirectionSymbol;
    }

    public char getDestinationDirectionSymbol() {
        return destinationDirectionSymbol;
    }

    public void setDestinationDirectionSymbol(char destinationDirectionSymbol) {
        this.destinationDirectionSymbol = destinationDirectionSymbol;
    }

    public int getWaitingTimeForCrossRoad() {
        return waitingTimeForCrossRoad;
    }

    public void setWaitingTimeForCrossRoad(int waitingTimeForCrossRoad) {
        this.waitingTimeForCrossRoad = waitingTimeForCrossRoad;
    }

    public int getTimeLeftForGreenAfterPassed() {
        return timeLeftForGreenAfterPassed;
    }

    public void setTimeLeftForGreenAfterPassed(int timeLeftForGreenAfterPassed) {
        this.timeLeftForGreenAfterPassed = timeLeftForGreenAfterPassed;
    }
}