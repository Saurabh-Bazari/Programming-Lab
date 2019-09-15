import java.util.concurrent.Semaphore;

class SealingUnitThread extends Thread{
   //Thread to simulate sealing unit
    private Semaphore unfinishedTraySemaphore;
    private Semaphore packagingBufferSemaphore;
    private Semaphore sealingBufferSemaphore;
    private Semaphore godownSemaphore;
    private UnfinishedTray unfinishedTray;
    private PackingUnit packingUnit;
    private SealingUnit sealingUnit;
    private Godown godown;
    //Stores the type of bottle being sealed by buffer
    public static int sealedBottleType;
    // stores destination where the bottle will be sent after sealing 
    public static Constant.Destination sendBottleTo;
    // stores nextWakeUpTime. That is time at which thread will be run again
    public static int nextWakeUpTime;
    // Staus tells whether thread is IDLE or WORKING(SEALING A BOTTLE)
    public static Constant.Status status;

    public SealingUnitThread( int sealedBottletype,Constant.Destination sendBottleToWhere,int nextWakeupTime,Constant.Status setStatus,UnfinishedTray unfinishedTray, PackingUnit packingUnit, SealingUnit sealingUnit, Godown godown, Semaphore unfinishedTraySemaphore, Semaphore sealingBufferSemaphore,Semaphore godownSemaphore,Semaphore packagingBufferSemaphore) {
        //Constructor
        this.unfinishedTraySemaphore = unfinishedTraySemaphore;
        this.packagingBufferSemaphore = packagingBufferSemaphore;
        this.sealingBufferSemaphore = sealingBufferSemaphore;
        this.godownSemaphore = godownSemaphore;
        this.unfinishedTray = unfinishedTray;
        this.packingUnit = packingUnit;
        this.sealingUnit = sealingUnit;
        this.godown = godown;
        sealedBottleType=sealedBottletype;
        // Initialise the state values with values from previous run
        nextWakeUpTime=nextWakeupTime;
        status=setStatus;
        sendBottleTo=sendBottleToWhere;
    }

    public void run(){

        if(status==Constant.Status.IDLE){    
              //IF unit is IDLE then pick a bottle (if available)to seal
            try{
                sealingBufferSemaphore.acquire();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            Boolean checkBufferHasBottels = sealingUnit.isBufferNotEmpty();
            if (checkBufferHasBottels) {
                // check if we have a bottle in sealing buffer
                int nextBottelType = sealingUnit.nextBottelTypeFromNonEmptyBuffer();
                //check which bottle is picked from buffer
                if(nextBottelType==1){
                    // reduce bottle count in buffer
                    sealingUnit.setBufferB1TypeBottels( sealingUnit.getBufferB1TypeBottels() - 1);
                    sealingBufferSemaphore.release();
                    // set state to working as we are packing a bottle
                    status=Constant.Status.WORKING;
                    // It takes to 3 second to seal a bottle so run thread again after 3 seconds from now
                    nextWakeUpTime+=3;
                    // because bottle was picked from buffer it will go to godown after sealing
                    sendBottleTo=Constant.Destination.GODOWN;
                    sealedBottleType=1;
                }
                else{
                    // reduce bottle count in buffer
                    sealingUnit.setBufferB2TypeBottels( sealingUnit.getBufferB2TypeBottels() - 1);
                    sealingBufferSemaphore.release();
                    // set state to working as we are packing a bottle
                    status=Constant.Status.WORKING;
                    // It takes to 3 second to seal a bottle so run thread again after 3 seconds from now
                    nextWakeUpTime+=3;
                    // because bottle was picked from buffer it will go to godown after sealing
                    sendBottleTo=Constant.Destination.GODOWN;
                    sealedBottleType=2;
                }
            }
                // check if unfinished tray has a bottle
            else {
                sealingBufferSemaphore.release();
                try{
                    unfinishedTraySemaphore.acquire();
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                int nextTypeBottel = unfinishedTray.nextBottelTypeForSealingUnit();

                if (nextTypeBottel==1) {
                    unfinishedTray.setB1TypeBottels( unfinishedTray.getB1TypeBottels() -1 );
                    unfinishedTraySemaphore.release();
                    // set state to working as we are packing a bottle
                    status=Constant.Status.WORKING;
                    // It takes to 3 second to seal a bottle so run thread again after 3 seconds from now
                    nextWakeUpTime+=3;
                    // because bottle was picked from unfinished it will go to packing after sealing
                    sendBottleTo=Constant.Destination.PACKING;
                    sealedBottleType=1;
                    
                } else if(nextTypeBottel==2) {
                    unfinishedTray.setB2TypeBottels( unfinishedTray.getB2TypeBottels() -1 );
                    unfinishedTraySemaphore.release();
                    // set state to working as we are packing a bottle
                    status=Constant.Status.WORKING;
                    // It takes to 3 second to seal a bottle so run thread again after 3 seconds from now
                    nextWakeUpTime+=3;
                    // because bottle was picked from unfinished it will go to packing after sealing
                    sendBottleTo=Constant.Destination.PACKING;
                    sealedBottleType=2;
                }
                else{
                    // there is no bottle to pack
                    // we set nextWakeuptime as to time when sealing unit will wake up
                    unfinishedTraySemaphore.release();
                    nextWakeUpTime=PackingUnitThread.nextWakeUpTime;
                }
            }
        }
        // If state is working that means we have a bottle which is packed and need to discharge it
        else if(status==Constant.Status.WORKING){
            if(sendBottleTo==Constant.Destination.GODOWN){
                if(sealedBottleType==1){
                    // increase sealed bottle count by 1
                    sealingUnit.setDonePackingForB1TypeBottels( 1+ sealingUnit.getDonePackingForB1TypeBottels() );
                    try{
                        godownSemaphore.acquire();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    // send bottle to godown
                    godown.setB1TypeBottels( godown.getB1TypeBottels() + 1);
                    godownSemaphore.release();
                }
                else if(sealedBottleType==2){
                    // increase sealed bottle count by 1
                    sealingUnit.setDonePackingForB2TypeBottels(1+ sealingUnit.getDonePackingForB2TypeBottels()  );
                    try{
                        godownSemaphore.acquire();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }                        
                    // send bottle to godown
                    godown.setB2TypeBottels( godown.getB2TypeBottels() + 1);
                    godownSemaphore.release();
                }
                else if(sealedBottleType==-1){
                    System.out.print("sealed bottle type is -1\n FATAL ERROR SYSTEM SHUTDOWN!!");
                    System.exit(1);
                }
                else{
                    System.out.print("sealed bottle type is unknown\n FATAL ERROR SYSTEM SHUTDOWN!!");
                    System.exit(1);
                }
                // AS bottle are sent set state to IDLE 
                status=Constant.Status.IDLE;
            }
            else if(sendBottleTo==Constant.Destination.PACKING){
                if(sealedBottleType==1){
                    try{
                        packagingBufferSemaphore.acquire();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    // check if packing buffer is full or not                    
                    if(packingUnit.avaliableSpaceInB1TypeBottelsBuffer()){
                        // increase sealed bottle count by 1
                        sealingUnit.setDonePackingForB1TypeBottels( 1+ sealingUnit.getDonePackingForB1TypeBottels() );
                        // incease packing buffer count by 1
                        packingUnit.setBufferB1TypeBottels( 1 + packingUnit.getBufferB1TypeBottels() );
                        packagingBufferSemaphore.release();
                        //As bottle is sent set status to IDLE
                        status=Constant.Status.IDLE;
                    }
                    else{
                        packagingBufferSemaphore.release();
                        nextWakeUpTime = PackingUnitThread.nextWakeUpTime;
                    }
                }
                else if(sealedBottleType==2){
                    try{
                        packagingBufferSemaphore.acquire();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    if(packingUnit.avaliableSpaceInB2TypeBottelsBuffer()){
                        // increase sealed bottle count by 1
                        sealingUnit.setDonePackingForB2TypeBottels( 1+ sealingUnit.getDonePackingForB2TypeBottels() );
                        // incease packing buffer count by 1
                        packingUnit.setBufferB2TypeBottels( 1 + packingUnit.getBufferB2TypeBottels() );
                        packagingBufferSemaphore.release();
                        //As bottle is sent set status to IDLE
                        status=Constant.Status.IDLE;
                    }
                    else{
                        packagingBufferSemaphore.release();
                        // IF bottle cant be sent wakeup and try when packing unit runs
                        nextWakeUpTime = PackingUnitThread.nextWakeUpTime;
                    }
                }
                else if(sealedBottleType==-1){
                    System.out.print("sealed bottle type is -1\n FATAL ERROR SYSTEM SHUTDOWN!!");
                    System.exit(1);
                }
                else{
                    System.out.print("sealed bottle type is unknown\n FATAL ERROR SYSTEM SHUTDOWN!!");
                    System.exit(1);
                }
            }
            else{
                System.out.print("sealing send bottle to staus unknown\n FATAL ERROR SYSTEM SHUTDOWN!!");
                System.exit(1);
            }
        }
        else{
            System.out.print("Sealing unit staus unknown\n FATAL ERROR SYSTEM SHUTDOWN!!");
            System.exit(1);
        }
        
        // Store the states of this thread in Main to initialise it with values on next run
        Main.sealedBottleTypeSealing=sealedBottleType;
        Main.sendBottleToSealing=sendBottleTo;
        Main.nextWakeUpTimeSealing=nextWakeUpTime;
        Main.statusSealing=status;
    }
}
