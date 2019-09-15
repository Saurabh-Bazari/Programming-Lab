import java.awt.BorderLayout;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;  
import javax.swing.JOptionPane;
import java.util.concurrent.locks.ReentrantLock; 
class TimerThread extends Thread{
    // This thread runs a timer. The timer periodically runs a task every 1 second
    //This task updates the table showing each car's waiting time
    ReentrantLock lock;
    DefaultTableModel vehicleTableModel;
    TimerThread(DefaultTableModel vehicleTableModel,ReentrantLock lock){
        this.vehicleTableModel=vehicleTableModel;
        this.lock=lock;
    }
    public void run(){
        //create timer
        Timer timer =new Timer();
        //create task instance
        TimerTaskHelper task = new TimerTaskHelper();
        //start the timer;
        timer.schedule(task,0,1000);
    }
}