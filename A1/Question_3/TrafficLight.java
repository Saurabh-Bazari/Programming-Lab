import java.util.*;
public class TrafficLight {
    //Time untill current state changes
    private int timeForCurrentState;
    // Colour of traffic LIGHT
    private String colour;

    public TrafficLight(int timeForCurrentState, String colour) {
        //Constructor
        this.timeForCurrentState = timeForCurrentState;
        this.colour = colour;
    }

    public int getTimeForCurrentState() {
        //getter function for current state time
        return timeForCurrentState;
    }

    public void setTimeForCurrentState(int timeForCurrentState) {
        //setter function for current state time
        this.timeForCurrentState = timeForCurrentState;
    }

    public String getColour() {
        //getter function for colour
        return colour;
    }

    public void setColour(String colour) {
        //setter function for colour
        this.colour = colour;
    }
}