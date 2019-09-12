import java.util.*;
public class Main {

  static Scanner scan = new Scanner(System.in);

  public static void BuildUnfinishedTray(UnfinishedTray unfinishedTray){
      int b1TypeBottels = scan.nextInt();
      unfinishedTray.setB1TypeBottels(b1TypeBottels);

      int b2TypeBottels = scan.nextInt();
      unfinishedTray.setB2TypeBottels(b2TypeBottels);
  }

  public static void main(String[] args) throws InterruptedException {
    Semaphore unfinishedTraySemaphore;
    Semaphore packagingBufferSemaphore;
    Semaphore sealingBufferSemaphore;
    Semaphore godownSemaphore;
    UnfinishedTray unfinishedTray = new UnfinishedTray();
    Godown godown = new Godown();
    BuildUnfinishedTray(unfinishedTray);

    int ResultTime = scan.nextInt();

    PackingUnit packingUnit = new PackingUnit();
    SealingUnit sealingUnit = new SealingUnit();    
    PackingUnitThread packingUnitThread = new PackingUnitThread(unfinishedTray, packingUnit, sealingUnit, godown,unfinishedTraySemaphore,packagingBufferSemaphore,godownSemaphore,sealingBufferSemaphore);
    SealingUnitThread sealingUnitThread = new SealingUnitThread(unfinishedTray, packingUnit, sealingUnit, godown,unfinishedTraySemaphore,sealingBufferSemaphore,godownSemaphore,packagingBufferSemaphore);
    packingUnitThread.start();
    sealingUnitThread.start();

    int currentSec=1;
    Thread.sleep(1500);
    while(currentSec<=ResultTime){
      System.out.println("After " + currentSec+ " sec ");
      System.out.println("Unfinished Tray Status: B1-> " + unfinishedTray.getB1TypeBottels()  + " ,B2-> " + unfinishedTray.getB2TypeBottels() );
      System.out.println("Packing Buffer Status: B1-> " + packingUnit.getBufferB1TypeBottels()  + " ,B2-> " + packingUnit.getBufferB2TypeBottels() );
      System.out.println("Sealing Buffer Status: B1-> " + sealingUnit.getBufferB1TypeBottels()  + " ,B2-> " + sealingUnit.getBufferB2TypeBottels() );
      System.out.println( "B1 Packaged : " + packingUnit.getDonePackingForB1TypeBottels());    
      System.out.println( "B1 Sealed : " + sealingUnit.getDonePackingForB1TypeBottels());
      System.out.println( "B1 In Godown : " + godown.getB1TypeBottels());
      System.out.println( "B2 Packaged : " + packingUnit.getDonePackingForB2TypeBottels());    
      System.out.println( "B2 Sealed : " + sealingUnit.getDonePackingForB2TypeBottels());
      System.out.println( "B2 In Godown : " + godown.getB2TypeBottels());
      Thread.sleep(1000);
      System.out.println();
      System.out.println();
      currentSec = currentSec + 1;
    }
  }
}