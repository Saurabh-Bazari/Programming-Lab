import java.util.Timer;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.util.TimerTask;
class TimerTaskHelper extends TimerTask{
    static Object Lock;
    static DefaultTableModel vehicleTableModel;
    TimerTaskHelper(DefaultTableModel vehicleTableModel1,Object Lock1){
        vehicleTableModel=vehicleTableModel1;
        Lock=Lock1;
    }
    @Override
    public void run(){
        synchronized(Lock){
            vehicleTableModel.setRowCount(0);
            for(int i=0;i<Main.vehicleList.size();i++){
                Vehicle v=Main.vehicleList.get(i);
                int time=v.getWaitingTimeForCrossRoad();
                String vehicleTableData[]={Integer.toString(v.getVehicleNumber()),
                Character.toString(v.getSourceDirectionSymbol()),
                Character.toString(v.getDestinationDirectionSymbol()),
                "WAIT",
                "--"
                };
                if(time>1){
                    time--;
                    vehicleTableData[4]=Integer.toString(time);
                    v.setWaitingTimeForCrossRoad(time);
                }
                else if(time<0){
                    System.out.print("Vehicle Time for "+v.getVehicleNumber()+" is Negative");
                    System.exit(1);
                }
                else if(time==1){
                    time--;
                    v.setWaitingTimeForCrossRoad(time);
                    vehicleTableData[3]="PASS";
                }
                else{
                    vehicleTableData[3]="PASS";
                }                
                vehicleTableModel.addRow(vehicleTableData);
                Main.vehicleList.set(i,v);
            
                vehicleTableModel.fireTableDataChanged();
                
                int t=Main.southToEastLane.getLastVehicleRemaingTime();
                Main.southToEastLane.setLastVehicleRemaingTime(t-1 );
                
                t=Main.westToSouthLane.getLastVehicleRemaingTime();
                Main.westToSouthLane.setLastVehicleRemaingTime(t-1 );

                t=Main.eastToWestLane.getLastVehicleRemaingTime();
                Main.eastToWestLane.setLastVehicleRemaingTime(t-1 );  
            } 
        }
    }
}