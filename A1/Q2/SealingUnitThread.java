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

    public SealingUnitThread(UnfinishedTray unfinishedTray, PackingUnit packingUnit, SealingUnit sealingUnit, Godown godown, Semaphore unfinishedTraySemaphore, Semaphore sealingBufferSemaphore,Semaphore godownSemaphore,Semaphore packagingBufferSemaphore) {
        this.unfinishedTraySemaphore = unfinishedTraySemaphore;
        this.packagingBufferSemaphore = packagingBufferSemaphore;
        this.sealingBufferSemaphore = sealingBufferSemaphore;
        this.godownSemaphore = godownSemaphore;
        this.unfinishedTray = unfinishedTray;
        this.packingUnit = packingUnit;
        this.sealingUnit = sealingUnit;
        this.godown = godown;
    }

    public void run(){

        while(true){
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
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sealingUnit.setDonePackingForB1TypeBottels( 1+ sealingUnit.getDonePackingForB1TypeBottels() );
                    godownSemaphore.acquire();
                    godown.setB1TypeBottels( godown.getB1TypeBottels() + 1);
                    godownSemaphore.release();
                }
                else{
                    sealingUnit.setBufferB2TypeBottels( sealingUnit.getBufferB2TypeBottels() - 1);
                    sealingBufferSemaphore.release();
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sealingUnit.setDonePackingForB2TypeBottels(1+ sealingUnit.getDonePackingForB2TypeBottels()  );
                    godownSemaphore.acquire();
                    godown.setB2TypeBottels( godown.getB2TypeBottels() + 1);
                    godownSemaphore.release();
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
                    packagingBufferSemaphore.acquire();
                    if (packingUnit.avaliableSpaceInB1TypeBottelsBuffer()) {
                        unfinishedTray.setB1TypeBottels( unfinishedTray.getB1TypeBottels() -1 );
                        unfinishedTraySemaphore.release();
                        packagingBufferSemaphore.release();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sealingUnit.setDonePackingForB1TypeBottels( 1+ sealingUnit.getDonePackingForB1TypeBottels() );
                        packagingBufferSemaphore.acquire();
                        packingUnit.setBufferB1TypeBottels( 1 + packingUnit.getBufferB1TypeBottels() );
                        packagingBufferSemaphore.release();
                    }
                    else{
                        packagingBufferSemaphore.release();
                        unfinishedTraySemaphore.release();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else if(nextTypeBottel==2) {
                    packagingBufferSemaphore.acquire();
                    if (packingUnit.avaliableSpaceInB2TypeBottelsBuffer()) {
                        unfinishedTray.setB2TypeBottels( unfinishedTray.getB2TypeBottels() -1 );
                        unfinishedTraySemaphore.release();
                        packagingBufferSemaphore.release();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        sealingUnit.setDonePackingForB2TypeBottels( 1+ sealingUnit.getDonePackingForB2TypeBottels() );
                        packagingBufferSemaphore.acquire();
                        packingUnit.setBufferB2TypeBottels( 1 + packingUnit.getBufferB2TypeBottels() );
                        packagingBufferSemaphore.release();
                    } else {
                        packagingBufferSemaphore.release();
                        unfinishedTraySemaphore.release();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    unfinishedTraySemaphore.release();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}