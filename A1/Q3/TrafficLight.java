import java.util.*;
public class TrafficLight {
    private int timeForCurrentState;
    private String colour;

    public TrafficLight(int timeForCurrentState, String colour) {
        this.timeForCurrentState = timeForCurrentState;
        this.colour = colour;
    }

    public int getTimeForCurrentState() {
        return timeForCurrentState;
    }

    public void setTimeForCurrentState(int timeForCurrentState) {
        this.timeForCurrentState = timeForCurrentState;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}