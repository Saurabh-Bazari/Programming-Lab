import java.awt.BorderLayout;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;  
import javax.swing.JOptionPane;
class TimerThread extends Thread{
    Object Lock;
    DefaultTableModel vehicleTableModel;
    TimerThread(DefaultTableModel vehicleTableModel,Object Lock){
        this.vehicleTableModel=vehicleTableModel;
        this.Lock=Lock;
    }
    public void run(){
        Timer timer =new Timer();
        TimerTask task = new TimerTaskHelper(vehicleTableModel,Lock);
        timer.schedule(task,0,1000);
    }
}