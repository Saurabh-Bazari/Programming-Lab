import java.util.Timer;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock; 

//This task updates the table showing each car's waiting time
class TimerTaskHelper extends TimerTask{
    @Override
    public void run(){
        //Acquire lock while updating the table.
        Main.vehicleListLock.lock();
        for(int i=0;i<Main.vehicleList.size();i++){
            // iterate thorugh list of all the vehicles 
            Vehicle v=Main.vehicleList.get(i);
            //for each vehicle get currrent waiting time
            int time=v.getWaitingTimeForCrossRoad();
            Main.vehicleTableModel.setValueAt("WAIT",i,3);
            Main.vehicleTableModel.setValueAt("--",i,4);
            if(time>=1){
                // If waiting time is positive then decrease it by 1 second
                Main.vehicleTableModel.setValueAt(Integer.toString(time),i,4);
            }
            else if(time>-6){
                // If wiaitng time is between 0 to -5 print passing
                Main.vehicleTableModel.setValueAt("PASSING..",i,3);
            }
            else{
                // Print status as PASSED
                Main.vehicleTableModel.setValueAt("PASSED!!",i,3);
            }  

            time--;  
            // update waiting time in vehicleList
            v.setWaitingTimeForCrossRoad(time);

            Main.vehicleList.set(i,v);
        } 
        // update GUI Vehicle Table
        Main.vehicleTableModel.fireTableDataChanged();
        // Release Lock
        Main.vehicleListLock.unlock();
        // update remaining time of last vehicle in each lane
        Main.southToEastLane.setLastVehicleRemaingTime(Main.southToEastLane.getLastVehicleRemaingTime()-1 );
        Main.westToSouthLane.setLastVehicleRemaingTime(Main.westToSouthLane.getLastVehicleRemaingTime()-1 );
        Main.eastToWestLane.setLastVehicleRemaingTime(Main.eastToWestLane.getLastVehicleRemaingTime()-1 );  
    }
}