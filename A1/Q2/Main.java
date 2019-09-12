import java.util.*;
import java.util.concurrent.Semaphore;

public class Main {

  public static int packedBottleTypePacking=-1;
  public static Constant.Destination sendBottleToPacking=Constant.Destination.SEALING;
  public static int nextWakeUpTimePacking=0;
  public static Constant.Status statusPacking=Constant.Status.IDLE;
  public static int sealedBottleTypeSealing=-1;
  public static Constant.Destination sendBottleToSealing=Constant.Destination.PACKING;
  public static int nextWakeUpTimeSealing=0;
  public static Constant.Status statusSealing=Constant.Status.IDLE;

  static Scanner scan = new Scanner(System.in);

  public static void BuildUnfinishedTray(UnfinishedTray unfinishedTray){
      int b1TypeBottels = scan.nextInt();
      unfinishedTray.setB1TypeBottels(b1TypeBottels);

      int b2TypeBottels = scan.nextInt();
      unfinishedTray.setB2TypeBottels(b2TypeBottels);
  }

  public static void main(String[] args) throws InterruptedException {
    Semaphore unfinishedTraySemaphore = new Semaphore(1);
    Semaphore packagingBufferSemaphore= new Semaphore(1);
    Semaphore sealingBufferSemaphore = new Semaphore(1);
    Semaphore godownSemaphore = new Semaphore(1);
    UnfinishedTray unfinishedTray = new UnfinishedTray();
    Godown godown = new Godown();
    BuildUnfinishedTray(unfinishedTray);

    int ResultTime = scan.nextInt();
    PackingUnit packingUnit = new PackingUnit();
    SealingUnit sealingUnit = new SealingUnit();  

    int currentSec=0;  
    while(currentSec<=ResultTime){
      PackingUnitThread packingUnitThread = new PackingUnitThread(packedBottleTypePacking,sendBottleToPacking,nextWakeUpTimePacking,statusPacking,unfinishedTray, packingUnit, sealingUnit, godown,unfinishedTraySemaphore,packagingBufferSemaphore,godownSemaphore,sealingBufferSemaphore);
      SealingUnitThread sealingUnitThread = new SealingUnitThread(sealedBottleTypeSealing,sendBottleToSealing,nextWakeUpTimeSealing,statusSealing,unfinishedTray, packingUnit, sealingUnit, godown,unfinishedTraySemaphore,sealingBufferSemaphore,godownSemaphore,packagingBufferSemaphore);
      if(nextWakeUpTimePacking==currentSec){
        packingUnitThread.start();
        packingUnitThread.join();
      }
      if(nextWakeUpTimeSealing==currentSec){
        sealingUnitThread.start();
        sealingUnitThread.join();
      }
      currentSec=Math.min(nextWakeUpTimeSealing,nextWakeUpTimePacking);
    }

   	  System.out.println("After " + ResultTime+ " sec ");
      System.out.println("Unfinished Tray Status: B1-> " + unfinishedTray.getB1TypeBottels()  + " ,B2-> " + unfinishedTray.getB2TypeBottels() );
      System.out.println("Packing Buffer Status: B1-> " + packingUnit.getBufferB1TypeBottels()  + " ,B2-> " + packingUnit.getBufferB2TypeBottels() );
      System.out.println("Sealing Buffer Status: B1-> " + sealingUnit.getBufferB1TypeBottels()  + " ,B2-> " + sealingUnit.getBufferB2TypeBottels() );
      System.out.println( "B1 Packaged : " + packingUnit.getDonePackingForB1TypeBottels());    
      System.out.println( "B1 Sealed : " + sealingUnit.getDonePackingForB1TypeBottels());
      System.out.println( "B1 In Godown : " + godown.getB1TypeBottels());
      System.out.println( "B2 Packaged : " + packingUnit.getDonePackingForB2TypeBottels());    
      System.out.println( "B2 Sealed : " + sealingUnit.getDonePackingForB2TypeBottels());
      System.out.println( "B2 In Godown : " + godown.getB2TypeBottels());
  }
}