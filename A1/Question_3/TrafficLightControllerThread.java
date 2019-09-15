import java.util.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;

class TrafficLightControllerThread extends Thread {
    // All traffic light variables
    TrafficLight trafficLightForSouth;
    TrafficLight trafficLightForWest;
    TrafficLight trafficLightForEast;
    // GUI frame window variable
    JFrame jFrame;
    // button to show traffic light colour
    JButton buttonForT1,buttonForT2,buttonForT3;
    // current time Clock and time var
    int currentSecond;
    static JLabel jLabelClock;
    // status of each traffic light
    JLabel labelForT1,labelForT2,labelForT3;
    

    public TrafficLightControllerThread(TrafficLight trafficLightForSouth, TrafficLight trafficLightForWest, TrafficLight trafficLightForEast,JFrame jFrame) {
        // constructor
        this.trafficLightForEast = trafficLightForEast;
        this.trafficLightForSouth = trafficLightForSouth;
        this.trafficLightForWest = trafficLightForWest;
        this.jFrame = jFrame;
        currentSecond=0;
        // current clock status 
        // initilize and set location and size
        // add status to frame
        jLabelClock = new JLabel("Clock : 0 sec");
        jLabelClock.setBounds(300, 250, 100, 20);
        jFrame.add(jLabelClock);
        jFrame.setVisible(true);
        // traffic light T1 status 
        // initilize and set location and size
        // add status to frame
        labelForT1 = new JLabel("T1 (S->E) : 120");
        labelForT1.setBounds(100, 300, 150, 50);
        jFrame.add(labelForT1);
        // traffic light T2 status 
        // initilize and set location and size
        // add status to frame
        labelForT2 = new JLabel("T2 (W->S) : 60") ;
        labelForT2.setBounds(100, 450, 150, 50);
        jFrame.add(labelForT2);
        // traffic light T3 status 
        // initilize and set location and size
        // add status to frame
        labelForT3 = new JLabel("T3 (E->W) : 60");
        labelForT3.setBounds(100, 600, 150, 50);
        jFrame.add(labelForT3);
        // traffic light T1 button 
        // initilize and set location and size
        // add light to frame
        buttonForT1 = new JButton();
        buttonForT1 = new RoundButton("");
        buttonForT1.setBounds(300,300,70,70);
        buttonForT1.setBackground(Color.RED);
        jFrame.add(buttonForT1);
        // traffic light T2 button 
        // initilize and set location and size
        // add light to frame
        buttonForT2 = new JButton();
        buttonForT2 = new RoundButton("");
        buttonForT2.setBounds(300,450,70,70);
        buttonForT2.setBackground(Color.RED);
        jFrame.add(buttonForT2);
        // traffic light T3 button 
        // initilize and set location and size
        // add light to frame
        buttonForT3 = new JButton();
        buttonForT3 = new RoundButton("");
        buttonForT3.setBounds(300,600,70,70);
        buttonForT3.setBackground(Color.GREEN);
        jFrame.add(buttonForT3);
    }
    public void run() { 
        // each iteration run for 180 seconds
        // 60 seconds for each traffic light
        // it follow round robin method
        while(true){
            trafficLightForEast.setColour("GREEN");
            trafficLightForEast.setTimeForCurrentState(60);
            trafficLightForSouth.setColour("RED");
            trafficLightForSouth.setTimeForCurrentState(120);            
            trafficLightForWest.setColour("RED");
            trafficLightForWest.setTimeForCurrentState(60);
            // update colour of traffic light
            updateTrafficLightColour(trafficLightForSouth.getColour(), trafficLightForWest.getColour() , trafficLightForEast.getColour());
            // update time of traffic light for 60 second
            continueFor60Seconds();
    
            trafficLightForEast.setColour("RED");
            trafficLightForEast.setTimeForCurrentState(120);
            trafficLightForSouth.setColour("RED");
            trafficLightForSouth.setTimeForCurrentState(60);
            trafficLightForWest.setColour("GREEN");
            trafficLightForWest.setTimeForCurrentState(60);
            // update colour of traffic light
            updateTrafficLightColour(trafficLightForSouth.getColour(), trafficLightForWest.getColour() , trafficLightForEast.getColour());
            // update time of traffic light for 60 second
            continueFor60Seconds();
    
            trafficLightForEast.setColour("RED");
            trafficLightForEast.setTimeForCurrentState(60);
            trafficLightForSouth.setColour("GREEN");
            trafficLightForSouth.setTimeForCurrentState(60);
            trafficLightForWest.setColour("RED");
            trafficLightForWest.setTimeForCurrentState(120);
            // update colour of traffic light
            updateTrafficLightColour(trafficLightForSouth.getColour(), trafficLightForWest.getColour() , trafficLightForEast.getColour());
            // update time of traffic light for 60 second
            continueFor60Seconds();
        }
    }

    // method continues for 60 seconds
    // In each second updates traffic light status and currentSecond
    void continueFor60Seconds(){
        int currentTimer=60;
        while(currentTimer>0){
            jLabelClock.setText("Clock : " + Integer.toString(currentSecond) + " sec");
            updateTrafficLightTime( trafficLightForSouth.getTimeForCurrentState(),
            trafficLightForWest.getTimeForCurrentState(), trafficLightForEast.getTimeForCurrentState());
            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            currentSecond++;
            trafficLightForEast.setTimeForCurrentState( trafficLightForEast.getTimeForCurrentState() -1);
            trafficLightForSouth.setTimeForCurrentState( trafficLightForSouth.getTimeForCurrentState() -1);
            trafficLightForWest.setTimeForCurrentState( trafficLightForWest.getTimeForCurrentState() -1);
            currentTimer--;
        }
    }

    // update time of traffic light
    void updateTrafficLightTime(int time1,int time2,int time3){
        labelForT1.setText("T1 (S->E) : " + Integer.toString(time1) );
        labelForT2.setText("T2 (W->S) : " + Integer.toString(time2) );
        labelForT3.setText("T3 (E->W) : " + Integer.toString(time3) );
    }

    // update colour of traffic light
    // after 60 seconds
    void updateTrafficLightColour(String color1,String color2,String color3){

        if (color1=="RED")
            buttonForT1.setBackground(Color.RED);
        else
            buttonForT1.setBackground(Color.GREEN);

        if (color2=="RED") 
            buttonForT2.setBackground(Color.RED);
        else 
            buttonForT2.setBackground(Color.GREEN);

        if (color3=="RED")
            buttonForT3.setBackground(Color.RED);
        else
            buttonForT3.setBackground(Color.GREEN);
    }
}
