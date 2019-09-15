import javax.swing.table.DefaultTableModel;
import java.util.concurrent.locks.ReentrantLock; 
class Lane{
    // var stores last vehicle remainging time
    int lastVehicleRemaingTime;
    // var stores traffic light status when last car just passed
    int lastVehiclePassedThenGreenLightTime;
    // Traffic to corresponding lan
    TrafficLight trafficLight;
    // source direction of lane
    char sourceDirection;
    // destination direction of lane
    char destinationDirection;
    private int waitingTimeforAgainGreen =120;

    ReentrantLock vehicleListLock;

    public Lane(char sourceDirection, char destinationDirection, TrafficLight trafficLight,ReentrantLock lock) {
        // constructor
        this.lastVehicleRemaingTime = -10;
        this.sourceDirection = sourceDirection;
        this.destinationDirection = destinationDirection;
        this.trafficLight = trafficLight;
        this.vehicleListLock = lock;
    }
    // when any new vehicle insert to lane
    public void insertIntoQueue(int vehicleNumber){
        calculateRemaingTime();
        String vehicleTableData[]={"","","",
        "WAIT",
        "--"
        };
        // create vehicle object
        Vehicle vehicle = new Vehicle(vehicleNumber,sourceDirection,destinationDirection,lastVehicleRemaingTime);

        vehicleListLock.lock();
            // add to vehicle list and then add to GUI Table
        Main.vehicleList.add(vehicle);
        vehicleListLock.unlock();
        vehicleTableData[0]=Integer.toString(vehicle.getVehicleNumber());
        vehicleTableData[1]=Character.toString(vehicle.getSourceDirectionSymbol());
        vehicleTableData[2]=Character.toString(vehicle.getDestinationDirectionSymbol());
        Main.vehicleTableModel.addRow(vehicleTableData);
        Main.vehicleTableModel.fireTableDataChanged();
    }   
    // calculate remaining time for new vehicle using last vehicle details
    private void calculateRemaingTime(){
        // if last vehicle is in lane
        if (lastVehicleRemaingTime>0) {
            if (lastVehiclePassedThenGreenLightTime<6) {
                lastVehicleRemaingTime = waitingTimeforAgainGreen + lastVehiclePassedThenGreenLightTime+lastVehicleRemaingTime+6;
                lastVehiclePassedThenGreenLightTime=54;
            } else {
                lastVehiclePassedThenGreenLightTime -=6;
                lastVehicleRemaingTime += 6;                
            }
        } else if (lastVehicleRemaingTime<=-6) {
            // last vehicle is passed
            if (trafficLight.getColour()=="RED") {
                lastVehicleRemaingTime= trafficLight.getTimeForCurrentState();
                lastVehiclePassedThenGreenLightTime = 54;            
            } else {
                if (trafficLight.getTimeForCurrentState() >= 6) {
                    lastVehicleRemaingTime= 0;
                    lastVehiclePassedThenGreenLightTime = trafficLight.getTimeForCurrentState() - 6;             
                } else {
                    lastVehicleRemaingTime = waitingTimeforAgainGreen + trafficLight.getTimeForCurrentState();
                    lastVehiclePassedThenGreenLightTime=54;
                }   
            }
        } else {
            // last vehicle id passing...
            int newtime  = trafficLight.getTimeForCurrentState();
            newtime = newtime - (6+lastVehicleRemaingTime);
            if (newtime >= 6) {
                lastVehicleRemaingTime= 6+lastVehicleRemaingTime;
                lastVehiclePassedThenGreenLightTime =newtime-6;             
            } else {
                lastVehicleRemaingTime = waitingTimeforAgainGreen + trafficLight.getTimeForCurrentState();
                lastVehiclePassedThenGreenLightTime=54;
            }
        }
    }
    // getter setter of lastVehicleRemaingTime
    public int getLastVehicleRemaingTime() {
        return lastVehicleRemaingTime;
    }

    public void setLastVehicleRemaingTime(int lastVehicleRemaingTime) {
        this.lastVehicleRemaingTime = lastVehicleRemaingTime;
    }
}