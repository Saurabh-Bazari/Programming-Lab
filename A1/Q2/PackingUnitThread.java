import java.util.concurrent.Semaphore;

class PackingUnitThread extends Thread {
   
    private Semaphore unfinishedTraySemaphore;
    private Semaphore packagingBufferSemaphore;
    private Semaphore sealingBufferSemaphore;
    private Semaphore godownSemaphore;
    private UnfinishedTray unfinishedTray;
    private PackingUnit packingUnit;
    private SealingUnit sealingUnit;
    private Godown godown;
    public static int packedBottleType;
    public static Constant.Destination sendBottleTo;
    public static int nextWakeUpTime;
    public static Constant.Status status;

    public PackingUnitThread(int packedBottleType1,Constant.Destination sendBottleTo1,int nextWakeUpTime1,Constant.Status status1,UnfinishedTray unfinishedTray, PackingUnit packingUnit, SealingUnit sealingUnit, Godown godown,Semaphore unfinishedTraySemaphore,Semaphore packagingBuffSemaphore,Semaphore godownSemaphore,Semaphore sealingBufferSemaphore) {
        this.unfinishedTraySemaphore = unfinishedTraySemaphore;
        this.packagingBufferSemaphore = packagingBuffSemaphore;
        this.sealingBufferSemaphore = sealingBufferSemaphore;
        this.godownSemaphore = godownSemaphore;
        this.unfinishedTray = unfinishedTray;
        packedBottleType=packedBottleType1;
        this.packingUnit = packingUnit;
        this.sealingUnit = sealingUnit;
        this.godown = godown;
        nextWakeUpTime=nextWakeUpTime1;
        status=status1;
        sendBottleTo=sendBottleTo1;
    }
    public void run(){
        while(true){
            if(status==Constant.Status.IDLE)
            {    try{
                    packagingBufferSemaphore.acquire();
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                Boolean checkBufferHasBottels = packingUnit.avaliableAnyTypeBottelsBuffer();
                if (checkBufferHasBottels) {
                    int nextBottelType = packingUnit.nextBottelTypeFromNonEmptyBuffer();

                    if(nextBottelType==1){
                        packingUnit.setBufferB1TypeBottels( packingUnit.getBufferB1TypeBottels() - 1);
                        packagingBufferSemaphore.release();
                        packedBottleType=1;
                        nextWakeUpTime+=2;
                        sendBottleTo = Constant.Destination.GODOWN;
                        status=Constant.Status.WORKING;
                        // try {
                        //     Thread.sleep(2000);
                        // } catch (InterruptedException e) {
                        //     e.printStackTrace();
                        // }
                        // packingUnit.setDonePackingForB1TypeBottels(1+ packingUnit.getDonePackingForB1TypeBottels() );
                        // godownSemaphore.acquire();
                        // godown.setB1TypeBottels( godown.getB1TypeBottels() + 1);
                        // godownSemaphore.release();
                    }
                    else{
                        packingUnit.setBufferB2TypeBottels( packingUnit.getBufferB2TypeBottels() - 1);
                        packagingBufferSemaphore.release();
                        packedBottleType=2;
                        nextWakeUpTime+=2;
                        sendBottleTo=Constant.Destination.GODOWN;
                        status=Constant.Status.WORKING;
                        // try {
                        //     Thread.sleep(2000);
                        // } catch (InterruptedException e) {
                        //     e.printStackTrace();
                        // }
                        // packingUnit.setDonePackingForB2TypeBottels(1+ packingUnit.getDonePackingForB2TypeBottels() );
                        // godownSemaphore.acquire();
                        // godown.setB2TypeBottels( godown.getB2TypeBottels() + 1);
                        // godownSemaphore.release();
                    }
                }
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
                        nextWakeUpTime+=2;
                        sendBottleTo=Constant.Destination.SEALING;
                        status=Constant.Status.WORKING;
                        // try {
                        //     Thread.sleep(2000);
                        // } catch (InterruptedException e) {
                        //     e.printStackTrace();
                        // }
                        // packingUnit.setDonePackingForB1TypeBottels(1+ packingUnit.getDonePackingForB1TypeBottels() );
                        // sealingBufferSemaphore.acquire();
                        // sealingUnit.setBufferB1TypeBottels( 1 + sealingUnit.getBufferB1TypeBottels() );
                        // sealingBufferSemaphore.release();
                    } else if (nextTypeBottel==2) {
                        unfinishedTray.setB2TypeBottels( unfinishedTray.getB2TypeBottels() -1 );
                        unfinishedTraySemaphore.release();
                        packedBottleType=2;
                        nextWakeUpTime+=2;
                        sendBottleTo=Constant.Destination.SEALING;
                        status=Constant.Status.WORKING;
                        // try {
                        //     Thread.sleep(2000);
                        // } catch (InterruptedException e) {
                        //     e.printStackTrace();
                        // }
                        // packingUnit.setDonePackingForB2TypeBottels(1+packingUnit.getDonePackingForB2TypeBottels());
                        // sealingBufferSemaphore.acquire();
                        // sealingUnit.setBufferB2TypeBottels( 1 + sealingUnit.getBufferB2TypeBottels() );
                        // sealingBufferSemaphore.release();
                    }
                    else{
                        unfinishedTraySemaphore.release();
                        nextWakeUpTime = SealingUnitThread.nextWakeUpTime;
                        status=Constant.Status.IDLE;
                    }
                    
                }
            }
            else if(status==Constant.Status.WORKING){
                if(sendBottleTo==Constant.Destination.GODOWN){
                    if(packedBottleType==1){
                        packingUnit.setDonePackingForB1TypeBottels(1+ packingUnit.getDonePackingForB1TypeBottels() );
                        try{
                            godownSemaphore.acquire();
                        }
                        catch(InterruptedException e){
                            e.printStackTrace();
                        }

                        godown.setB1TypeBottels( godown.getB1TypeBottels() + 1);
                        godownSemaphore.release();
                    }
                    else if(packedBottleType==2){
                        packingUnit.setDonePackingForB2TypeBottels(1+ packingUnit.getDonePackingForB2TypeBottels() );
                        try{
                            godownSemaphore.acquire();
                        }
                        catch(InterruptedException e){
                            e.printStackTrace();
                        }                        godown.setB2TypeBottels( godown.getB2TypeBottels() + 1);
                        godownSemaphore.release();
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
                else if(sendBottleTo==Constant.Destination.SEALING){
                    try{
                        sealingBufferSemaphore.acquire();
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    
                    if(sealingUnit.avaliableSpaceInBuffer()){
                        //DEADLOCK MAY HAPPEN
                        if(packedBottleType==1){
                            packingUnit.setDonePackingForB1TypeBottels(1+packingUnit.getDonePackingForB1TypeBottels());
                            sealingUnit.setBufferB1TypeBottels( 1 + sealingUnit.getBufferB1TypeBottels() );
                            sealingBufferSemaphore.release();
                        }
                        else if(packedBottleType==2){
                            packingUnit.setDonePackingForB2TypeBottels(1+packingUnit.getDonePackingForB2TypeBottels());
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
        Main.packedBottleTypePacking=packedBottleType;
        Main.sendBottleToPacking=sendBottleTo;
        Main.nextWakeUpTimePacking=nextWakeUpTime;
        Main.statusPacking=status;
    }
}   