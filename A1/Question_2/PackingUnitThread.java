import java.util.concurrent.Semaphore;

class PackingUnitThread extends Thread {
   //Thread to simulate packing unit
    private Semaphore packagingBufferSemaphore;
    private Semaphore sealingBufferSemaphore;
    private Semaphore godownSemaphore;
    private Semaphore unfinishedTraySemaphore;
    private UnfinishedTray unfinishedTray;
    private PackingUnit packingUnit;
    private SealingUnit sealingUnit;
    private Godown godown;
    //Stores the type of bottle being packed by buffer
    public static int packedBottleType;
    // stores destination where the bottle will be sent after packing 
    public static Constant.Destination sendBottleTo;
    // stores nextWakeUpTime. That is time at which thread will be run again
    public static int nextWakeUpTime;
    // Staus tells whether thread is IDLE or WORKING(PACKING A BOTTLE)
    public static Constant.Status status;

    public PackingUnitThread(int packedBottletype,Constant.Destination sendBottleToWhere,int nextWakeupTime,Constant.Status setStatus,UnfinishedTray unfinishedTray, PackingUnit packingUnit, SealingUnit sealingUnit, Godown godown,Semaphore unfinishedTraySemaphore,Semaphore packagingBuffSemaphore,Semaphore godownSemaphore,Semaphore sealingBufferSemaphore) {
        //Constructor
        this.unfinishedTraySemaphore = unfinishedTraySemaphore;
        this.packagingBufferSemaphore = packagingBuffSemaphore;
        this.sealingBufferSemaphore = sealingBufferSemaphore;
        this.godownSemaphore = godownSemaphore;
        this.unfinishedTray = unfinishedTray;
        packedBottleType=packedBottletype;
        this.packingUnit = packingUnit;
        this.sealingUnit = sealingUnit;
        this.godown = godown;
        // Initialise the state values with values from previous run
        nextWakeUpTime=nextWakeupTime;
        status=setStatus;
        sendBottleTo=sendBottleToWhere;
    }
    public void run(){
        while(true){
            if(status==Constant.Status.IDLE)
            {   //IF unit is IDLE then pick a bottle (if available)to pack
                    try{
                    packagingBufferSemaphore.acquire();
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                Boolean checkBufferHasBottels = packingUnit.avaliableAnyTypeBottelsBuffer();
                // check if we have a bottle in packing buffer
                if (checkBufferHasBottels) {
                    int nextBottelType = packingUnit.nextBottelTypeFromNonEmptyBuffer();
                    //check which bottle is picked from buffer
                    if(nextBottelType==1){
                        // reduce bottle count in buffer
                        packingUnit.setBufferB1TypeBottels( packingUnit.getBufferB1TypeBottels() - 1);
                        packagingBufferSemaphore.release();
                        // stores state varuables of machine
                        packedBottleType=1;
                        // It takes to 2 second to pack a bottle so run thread again after 2 seconds from now
                        nextWakeUpTime+=2;
                        // because bottle was picked from buffer it will go to godown after packing
                        sendBottleTo = Constant.Destination.GODOWN;
                        // set state to working as we are packing a bottle
                        status=Constant.Status.WORKING;
                    }
                    else{
                        packingUnit.setBufferB2TypeBottels( packingUnit.getBufferB2TypeBottels() - 1);
                        packagingBufferSemaphore.release();
                        packedBottleType=2;
                        // It takes to 2 second to pack a bottle so run thread again after 2 seconds from now
                        nextWakeUpTime+=2;
                        sendBottleTo=Constant.Destination.GODOWN;
                        status=Constant.Status.WORKING;
                    }
                }
                // check if unfinished tray has a bottle
                else {
                    packagingBufferSemaphore.release();
                    try{
                        unfinishedTraySemaphore.acquire();
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    int nextTypeBottel = unfinishedTray.nextBottelTypeForPackingUnit();
                    
                    if (nextTypeBottel==1) {
                        unfinishedTray.setB1TypeBottels( unfinishedTray.getB1TypeBottels() -1 );
                        unfinishedTraySemaphore.release();
                        packedBottleType=1;
                        // It takes to 2 second to pack a bottle so run thread again after 2 seconds from now
                        nextWakeUpTime+=2;
                        // because bottle was picked from unfinished tray 
                        //it will go to sealing buffer after packing
                        sendBottleTo=Constant.Destination.SEALING;
                        // set state to working as we are packing a bottle
                        status=Constant.Status.WORKING;
                    } else if (nextTypeBottel==2) {
                        unfinishedTray.setB2TypeBottels( unfinishedTray.getB2TypeBottels() -1 );
                        unfinishedTraySemaphore.release();
                        packedBottleType=2;
                        // It takes to 2 second to pack a bottle so run thread
                        // again after 2 seconds from now
                        nextWakeUpTime+=2;
                        // because bottle was picked from unfinished tray it will go 
                        // to sealing buffer after packing
                        sendBottleTo=Constant.Destination.SEALING;
                        // set state to working as we are packing a bottle
                        status=Constant.Status.WORKING;
                    }
                    else{
                        // there is no bottle to pack
                        // we set nextWakeuptime as to time when sealing unit will wake up
                        unfinishedTraySemaphore.release();
                        nextWakeUpTime = SealingUnitThread.nextWakeUpTime;
                        status=Constant.Status.IDLE;
                    }
                    
                }
            }
            // If state is working that means we have a bottle which is packed 
            // and need to discharge it
            else if(status==Constant.Status.WORKING){
                if(sendBottleTo==Constant.Destination.GODOWN){
                    if(packedBottleType==1){
                        // increase packing bottle count by 1
                        packingUnit.setDonePackingForB1TypeBottels(1+ packingUnit.getDonePackingForB1TypeBottels() );
                        try{
                            godownSemaphore.acquire();
                        }
                        catch(InterruptedException e){
                            e.printStackTrace();
                        }

                        // send bottle to godown
                        godown.setB1TypeBottels( godown.getB1TypeBottels() + 1);
                        godownSemaphore.release();
                    }
                    else if(packedBottleType==2){
                        // increase packing bottle count by 1
                        packingUnit.setDonePackingForB2TypeBottels(1+ packingUnit.getDonePackingForB2TypeBottels() );
                        try{
                            godownSemaphore.acquire();
                        }
                        catch(InterruptedException e){
                            e.printStackTrace();
                        }                        
                        // send bottle to godown
                        godown.setB2TypeBottels( godown.getB2TypeBottels() + 1);
                        godownSemaphore.release();
                    }
                    else if(packedBottleType==-1){
                        //ERROR CASES
                        System.out.print("packed bottle type is -1\n FATAL ERROR SYSTEM SHUTDOWN!!");
                        System.exit(1);
                    }
                    else{
                        //ERROR CASES
                        System.out.print("packed bottle type is unknown\n FATAL ERROR SYSTEM SHUTDOWN!!");
                        System.exit(1);
                    }
                    // AS bottle are sent set state to IDLE 
                    status=Constant.Status.IDLE;
                }
                else if(sendBottleTo==Constant.Destination.SEALING){
                    try{
                        sealingBufferSemaphore.acquire();
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    // check if sealing buffer is full or not
                    if(sealingUnit.avaliableSpaceInBuffer()){
                        if(packedBottleType==1){
                        // increase packing bottle count by 1
                            packingUnit.setDonePackingForB1TypeBottels(1+packingUnit.getDonePackingForB1TypeBottels());
                            //increase sealing buffer count
                            sealingUnit.setBufferB1TypeBottels( 1 + sealingUnit.getBufferB1TypeBottels() );
                            sealingBufferSemaphore.release();
                        }
                        else if(packedBottleType==2){
                        // increase packing bottle count by 1
                            packingUnit.setDonePackingForB2TypeBottels(1+packingUnit.getDonePackingForB2TypeBottels());
                            //increase sealing buffer count
                            sealingUnit.setBufferB2TypeBottels( 1 + sealingUnit.getBufferB2TypeBottels() );
                            sealingBufferSemaphore.release();
                        }
                        else if(packedBottleType==-1){
                            System.out.print("packed bottle type is -1\n FATAL ERROR SYSTEM SHUTDOWN!!");
                            System.exit(1);
                        }
                        else{
                            System.out.print("packed bottle type is unknown\n FATAL ERROR SYSTEM SHUTDOWN!!");
                            System.exit(1);
                        }
                        status=Constant.Status.IDLE;
                    }
                    else{
                        // if sealing buffer is full we wait till next run of sealing unit
                        // our state remains WORKING beacause we cant pack new botlte
                        sealingBufferSemaphore.release();
                        nextWakeUpTime=SealingUnitThread.nextWakeUpTime;
                    }
                }
                else{
                    System.out.print("send bottle to staus unknown\n FATAL ERROR SYSTEM SHUTDOWN!!");
                    System.exit(1);
                }
            }
            else{
                System.out.print("Packing unit staus unknown\n FATAL ERROR SYSTEM SHUTDOWN!!");
                System.exit(1);
            }
            break;
        }
        // Store the states of this thread in Main to initialise it with values on next run
        Main.packedBottleTypePacking=packedBottleType;
        Main.sendBottleToPacking=sendBottleTo;
        Main.nextWakeUpTimePacking=nextWakeUpTime;
        Main.statusPacking=status;
    }
}   