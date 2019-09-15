import java.util.*;
import java.util.concurrent.Semaphore;

public class Main {
  //Packing Thread Status 
  public static int packedBottleTypePacking=-1;
  public static Constant.Destination sendBottleToPacking=Constant.Destination.SEALING;
  public static int nextWakeUpTimePacking=0;
  public static Constant.Status statusPacking=Constant.Status.IDLE;
  //Sealing Thread Status
  public static int sealedBottleTypeSealing=-1;
  public static Constant.Destination sendBottleToSealing=Constant.Destination.PACKING;
  public static int nextWakeUpTimeSealing=0;
  public static Constant.Status statusSealing=Constant.Status.IDLE;

  static Scanner scan = new Scanner(System.in);

  public static void BuildUnfinishedTray(UnfinishedTray unfinishedTray){
    // Fills the Unifinished Tray accrrding to User Input
    System.out.println("Unfinished Try");
    System.out.print("Enter number of B1 bottels: ");
    int b1TypeBottels = scan.nextInt();
    unfinishedTray.setB1TypeBottels(b1TypeBottels);
    System.out.print("Enter number of B2 bottels: ");
    int b2TypeBottels = scan.nextInt();
    unfinishedTray.setB2TypeBottels(b2TypeBottels);
  }

  public static void main(String[] args) throws InterruptedException {
    // Semaphore for unfinished Tray. So that picking bottle from unifinished tray is synchronized
    Semaphore unfinishedTraySemaphore = new Semaphore(1);
    // packing buffer semaphore provides mutual exclusion 
    Semaphore packagingBufferSemaphore= new Semaphore(1);
    // semaphore for sealing buffer
    Semaphore sealingBufferSemaphore = new Semaphore(1);
    // seamphore for godown
    Semaphore godownSemaphore = new Semaphore(1);

    UnfinishedTray unfinishedTray = new UnfinishedTray();
    Godown godown = new Godown();
    BuildUnfinishedTray(unfinishedTray);

    System.out.print("Enter Time in seconds: ");
    int ResultTime = scan.nextInt();
    PackingUnit packingUnit = new PackingUnit();
    SealingUnit sealingUnit = new SealingUnit(); 

    int totalBottels = unfinishedTray.getB1TypeBottels()+ unfinishedTray.getB2TypeBottels(); 

    int currentSecond=0;  
    while(currentSecond<=ResultTime){
      // simulate the packing unit and sealing unit till Result time
    	if ( (godown.getB1TypeBottels()+godown.getB2TypeBottels())==(totalBottels) ) {
        break;    		
        // if all bottles are processed stop simulation
      }
      // packing thread object with all parameters
      PackingUnitThread packingUnitThread = new PackingUnitThread(packedBottleTypePacking,sendBottleToPacking,      nextWakeUpTimePacking,statusPacking,unfinishedTray, packingUnit, sealingUnit, godown,      unfinishedTraySemaphore,packagingBufferSemaphore,godownSemaphore,sealingBufferSemaphore);

      // sealing thread object with all parameters
      SealingUnitThread sealingUnitThread = new SealingUnitThread(sealedBottleTypeSealing,sendBottleToSealing,      nextWakeUpTimeSealing,statusSealing,unfinishedTray, packingUnit, sealingUnit, godown,      unfinishedTraySemaphore,sealingBufferSemaphore,godownSemaphore,packagingBufferSemaphore);

      if(nextWakeUpTimePacking==currentSecond){
        // Start packing unit by checking current time
        packingUnitThread.start();
        packingUnitThread.join();
      }
      if(nextWakeUpTimeSealing==currentSecond){
        // Start sealing unit by checking current time
        sealingUnitThread.start();
        sealingUnitThread.join();
      }
      // set current time to minimum wakeup time of both
      currentSecond=Math.min(nextWakeUpTimeSealing,nextWakeUpTimePacking);
    }

    // Results after given time
    System.out.println();
    System.out.println("Result After " + ResultTime+ " sec:");
    System.out.println("Unfinished Tray Status: B1-> " + unfinishedTray.getB1TypeBottels()  + 
    ", B2-> " + unfinishedTray.getB2TypeBottels() );
    System.out.println("Packing Buffer Status: B1-> " + packingUnit.getBufferB1TypeBottels()  + 
    ", B2-> " + packingUnit.getBufferB2TypeBottels() );
    System.out.println("Sealing Buffer Status: B1-> " + sealingUnit.getBufferB1TypeBottels()  + 
    ", B2-> " + sealingUnit.getBufferB2TypeBottels() );
    System.out.println();
    System.out.println( "B1 Packaged : " + packingUnit.getDonePackingForB1TypeBottels());    
    System.out.println( "B1 Sealed : " + sealingUnit.getDonePackingForB1TypeBottels());
    System.out.println( "B1 In Godown : " + godown.getB1TypeBottels());
    System.out.println();
    System.out.println( "B2 Packaged : " + packingUnit.getDonePackingForB2TypeBottels());    
    System.out.println( "B2 Sealed : " + sealingUnit.getDonePackingForB2TypeBottels());
    System.out.println( "B2 In Godown : " + godown.getB2TypeBottels());
  }
}