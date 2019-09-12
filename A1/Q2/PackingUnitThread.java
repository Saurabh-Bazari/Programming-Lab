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

    public PackingUnitThread(UnfinishedTray unfinishedTray, PackingUnit packingUnit, SealingUnit sealingUnit, Godown godown,Semaphore unfinishedTraySemaphore,Semaphore packagingBuffSemaphore,Semaphore godownSemaphore,Semaphore sealingBufferSemaphore) {
        this.unfinishedTraySemaphore = unfinishedTraySemaphore;
        this.packagingBufferSemaphore = packagingBuffSemaphore;
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
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    packingUnit.setDonePackingForB1TypeBottels(1+ packingUnit.getDonePackingForB1TypeBottels() );
                    godownSemaphore.acquire();
                    godown.setB1TypeBottels( godown.getB1TypeBottels() + 1);
                    godownSemaphore.release();
                }
                else{
                    packingUnit.setBufferB2TypeBottels( packingUnit.getBufferB2TypeBottels() - 1);
                    packagingBufferSemaphore.release();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    packingUnit.setDonePackingForB2TypeBottels(1+ packingUnit.getDonePackingForB2TypeBottels() );
                    godownSemaphore.acquire();
                    godown.setB2TypeBottels( godown.getB2TypeBottels() + 1);
                    godownSemaphore.release();
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
                sealingBufferSemaphore.acquire();
                int nextTypeBottel = unfinishedTray.nextBottelTypeForPackingUnit();
                if( sealingUnit.avaliableSpaceInBuffer() ){
                    if (nextTypeBottel==1) {
                        unfinishedTray.setB1TypeBottels( unfinishedTray.getB1TypeBottels() -1 );
                        unfinishedTraySemaphore.release();
                        sealingBufferSemaphore.release();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        packingUnit.setDonePackingForB1TypeBottels(1+ packingUnit.getDonePackingForB1TypeBottels() );
                        sealingBufferSemaphore.acquire();
                        sealingUnit.setBufferB1TypeBottels( 1 + sealingUnit.getBufferB1TypeBottels() );
                        sealingBufferSemaphore.release();
                    } else if (nextTypeBottel==2) {
                        unfinishedTray.setB2TypeBottels( unfinishedTray.getB2TypeBottels() -1 );
                        unfinishedTraySemaphore.release();
                        sealingBufferSemaphore.release();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        packingUnit.setDonePackingForB2TypeBottels(1+packingUnit.getDonePackingForB2TypeBottels());
                        sealingBufferSemaphore.acquire();
                        sealingUnit.setBufferB2TypeBottels( 1 + sealingUnit.getBufferB2TypeBottels() );
                        sealingBufferSemaphore.release();
                    }
                    else{
                        sealingBufferSemaphore.release();
                        unfinishedTraySemaphore.release();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    sealingBufferSemaphore.release();
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