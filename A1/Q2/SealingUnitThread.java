import java.util.concurrent.Semaphore;

class SealingUnitThread extends Thread{

    private Semaphore unfinishedTraySemaphore;
    private Semaphore packagingBufferSemaphore;
    private Semaphore sealingBufferSemaphore;
    private Semaphore godownSemaphore;
    private UnfinishedTray unfinishedTray;
    private PackingUnit packingUnit;
    private SealingUnit sealingUnit;
    private Godown godown;
    public static int sealedBottleType;
    public static Constant.Destination sendBottleTo;
    public static int nextWakeUpTime;
    public static Constant.Status status;

    public SealingUnitThread( int sealedBottleType1,Constant.Destination sendBottleTo1,int nextWakeUpTime1,Constant.Status status1,UnfinishedTray unfinishedTray, PackingUnit packingUnit, SealingUnit sealingUnit, Godown godown, Semaphore unfinishedTraySemaphore, Semaphore sealingBufferSemaphore,Semaphore godownSemaphore,Semaphore packagingBufferSemaphore) {
        this.unfinishedTraySemaphore = unfinishedTraySemaphore;
        this.packagingBufferSemaphore = packagingBufferSemaphore;
        this.sealingBufferSemaphore = sealingBufferSemaphore;
        this.godownSemaphore = godownSemaphore;
        this.unfinishedTray = unfinishedTray;
        this.packingUnit = packingUnit;
        this.sealingUnit = sealingUnit;
        this.godown = godown;
        sealedBottleType=sealedBottleType1;
        nextWakeUpTime=nextWakeUpTime1;
        status=status1;
        sendBottleTo=sendBottleTo1;
    }

    public void run(){

        if(status==Constant.Status.IDLE){    
            try{
                sealingBufferSemaphore.acquire();
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            Boolean checkBufferHasBottels = sealingUnit.isBufferNotEmpty();
            if (checkBufferHasBottels) {
                int nextBottelType = sealingUnit.nextBottelTypeFromNonEmptyBuffer();

                if(nextBottelType==1){
                    sealingUnit.setBufferB1TypeBottels( sealingUnit.getBufferB1TypeBottels() - 1);
                    sealingBufferSemaphore.release();
                    status=Constant.Status.WORKING;
                    nextWakeUpTime+=3;
                    sendBottleTo=Constant.Destination.GODOWN;
                    sealedBottleType=1;
                    // try {
                    //     Thread.sleep(3000);
                    // } catch (InterruptedException e) {
                    //     e.printStackTrace();
                    // }
                    // sealingUnit.setDonePackingForB1TypeBottels( 1+ sealingUnit.getDonePackingForB1TypeBottels() );
                    // godownSemaphore.acquire();
                    // godown.setB1TypeBottels( godown.getB1TypeBottels() + 1);
                    // godownSemaphore.release();
                }
                else{
                    sealingUnit.setBufferB2TypeBottels( sealingUnit.getBufferB2TypeBottels() - 1);
                    sealingBufferSemaphore.release();
                    status=Constant.Status.WORKING;
                    nextWakeUpTime+=3;
                    sendBottleTo=Constant.Destination.GODOWN;
                    sealedBottleType=2;
                    // try {
                    //     Thread.sleep(3000);
                    // } catch (InterruptedException e) {
                    //     e.printStackTrace();
                    // }
                    // sealingUnit.setDonePackingForB2TypeBottels(1+ sealingUnit.getDonePackingForB2TypeBottels()  );
                    // godownSemaphore.acquire();
                    // godown.setB2TypeBottels( godown.getB2TypeBottels() + 1);
                    // godownSemaphore.release();
                }
            }
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
                    status=Constant.Status.WORKING;
                    nextWakeUpTime+=3;
                    sendBottleTo=Constant.Destination.PACKING;
                    sealedBottleType=1;
                    // try {
                    //     Thread.sleep(3000);
                    // } catch (InterruptedException e) {
                    //     e.printStackTrace();
                    // }
                    // sealingUnit.setDonePackingForB1TypeBottels( 1+ sealingUnit.getDonePackingForB1TypeBottels() );
                    // packagingBufferSemaphore.acquire();
                    // packingUnit.setBufferB1TypeBottels( 1 + packingUnit.getBufferB1TypeBottels() );
                    // packagingBufferSemaphore.release();
                    
                } else if(nextTypeBottel==2) {
                    unfinishedTray.setB2TypeBottels( unfinishedTray.getB2TypeBottels() -1 );
                    unfinishedTraySemaphore.release();
                    status=Constant.Status.WORKING;
                    nextWakeUpTime+=3;
                    sendBottleTo=Constant.Destination.PACKING;
                    sealedBottleType=2;
                }
                else{
                    unfinishedTraySemaphore.release();
                    nextWakeUpTime=PackingUnitThread.nextWakeUpTime;
                }
            }
        }
        else if(status==Constant.Status.WORKING){
            if(sendBottleTo==Constant.Destination.GODOWN){
                if(sealedBottleType==1){
                    sealingUnit.setDonePackingForB1TypeBottels( 1+ sealingUnit.getDonePackingForB1TypeBottels() );
                    try{
                        godownSemaphore.acquire();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    godown.setB1TypeBottels( godown.getB1TypeBottels() + 1);
                    godownSemaphore.release();
                }
                else if(sealedBottleType==2){
                    sealingUnit.setDonePackingForB2TypeBottels(1+ sealingUnit.getDonePackingForB2TypeBottels()  );
                    try{
                        godownSemaphore.acquire();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }                        godown.setB2TypeBottels( godown.getB2TypeBottels() + 1);
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
                    
                    if(packingUnit.avaliableSpaceInB1TypeBottelsBuffer()){
                        sealingUnit.setDonePackingForB1TypeBottels( 1+ sealingUnit.getDonePackingForB1TypeBottels() );
                        packingUnit.setBufferB1TypeBottels( 1 + packingUnit.getBufferB1TypeBottels() );
                        packagingBufferSemaphore.release();
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
                        sealingUnit.setDonePackingForB2TypeBottels( 1+ sealingUnit.getDonePackingForB2TypeBottels() );
                        packingUnit.setBufferB2TypeBottels( 1 + packingUnit.getBufferB2TypeBottels() );
                        packagingBufferSemaphore.release();
                        status=Constant.Status.IDLE;
                    }
                    else{
                        packagingBufferSemaphore.release();
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
        
        Main.sealedBottleTypeSealing=sealedBottleType;
        Main.sendBottleToSealing=sendBottleTo;
        Main.nextWakeUpTimeSealing=nextWakeUpTime;
        Main.statusSealing=status;
    }
}