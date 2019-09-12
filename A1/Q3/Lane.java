class Lane{
    int lastVehicleRemaingTime;
    int lastVehiclePassedThenGreenLightTime;
    TrafficLight trafficLight;
    char sourceDirection;
    char destinationDirection;
    Object Lock;

    public Lane(char sourceDirection, char destinationDirection, TrafficLight trafficLight,Object Lock) {
        this.lastVehicleRemaingTime = -10;
        this.sourceDirection = sourceDirection;
        this.destinationDirection = destinationDirection;
        this.trafficLight = trafficLight;
        this.Lock = Lock;
    }
    
    public void insertIntoQueue(int vehicleNumber){
        calculateRemaingTime();
        Vehicle vehicle = new Vehicle(vehicleNumber,sourceDirection,destinationDirection,lastVehicleRemaingTime);
        synchronized(Lock){
            Main.vehicleList.add(vehicle);
        }
    }   

    private void calculateRemaingTime(){
        if (lastVehicleRemaingTime>0) {
            if (lastVehiclePassedThenGreenLightTime<6) {
                lastVehicleRemaingTime = 120 + lastVehiclePassedThenGreenLightTime;
                lastVehiclePassedThenGreenLightTime=54;
            } else {
                lastVehiclePassedThenGreenLightTime -=6;
                lastVehicleRemaingTime += 6;                
            }
        } else if (lastVehicleRemaingTime<=-6) {

            if (trafficLight.getColour()=="RED") {
                lastVehicleRemaingTime= trafficLight.getTimeForCurrentState();
                lastVehiclePassedThenGreenLightTime = 54;            
            } else {
                if (trafficLight.getTimeForCurrentState() >= 6) {
                    lastVehicleRemaingTime= 0;
                    lastVehiclePassedThenGreenLightTime = trafficLight.getTimeForCurrentState() - 6;             
                } else {
                    lastVehicleRemaingTime = 120 + trafficLight.getTimeForCurrentState();
                    lastVehiclePassedThenGreenLightTime=54;
                }   
            }
        } else {
            int newtime  = trafficLight.getTimeForCurrentState();
            newtime = newtime - (6+lastVehicleRemaingTime);
            if (newtime >= 6) {
                lastVehicleRemaingTime= 6+lastVehicleRemaingTime;
                lastVehiclePassedThenGreenLightTime =newtime-6;             
            } else {
                lastVehicleRemaingTime = 120 + trafficLight.getTimeForCurrentState();
                lastVehiclePassedThenGreenLightTime=54;
            }
        }
    }

    public int getLastVehicleRemaingTime() {
        return lastVehicleRemaingTime;
    }

    public void setLastVehicleRemaingTime(int lastVehicleRemaingTime) {
        this.lastVehicleRemaingTime = lastVehicleRemaingTime;
    }
}