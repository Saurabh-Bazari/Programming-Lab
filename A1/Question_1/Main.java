import java.util.*;
public class Main {

  static Scanner scan = new Scanner(System.in);

  //Function for read input from user for inventory Data
  // store data into inventory object
  public static void BuildInventory( Inventory inventory) {

    // For Cap data
    System.out.println("Inventory Data");
    System.out.print("Number Of Caps: ");
    int caps = scan.nextInt();
    inventory.setCaps(caps);

    // for small Tshirt data
    System.out.print("Number Of Small size TShirt: ");
    int smallTShirt = scan.nextInt();
    inventory.setSmallTShirt(smallTShirt);

    // For medium Tshirt data
    System.out.print("Number Of Medium size TShirt: ");
    int mediumTShirt = scan.nextInt();
    inventory.setMediumTShirt(mediumTShirt);

    // For large Tshirt data
    System.out.print("Number Of Large size TShirt: ");
    int largeTShirt = scan.nextInt();
    inventory.setLargeTShirt(largeTShirt);
  }

  public static void main(String[] args) {
    
    //set poolSize equal to 8(Number of processor present)
    int poolSize=8;

    //Create and Build Inventory
    Inventory inventory = new Inventory();
    BuildInventory(inventory);

    // scan number of order and 
    // every order in specific format: Number Type Quantity
    System.out.println();
    System.out.print("Number of students ordering: ");
    int numberOfOrder = scan.nextInt();
    System.out.println("Orders: ");

    // create array of arraylist store order for each thread
    ArrayList<Order>[] orderList = new ArrayList[poolSize];
    OrderThread threadPool[] = new OrderThread[poolSize];

    for(int indexPool=0; indexPool<poolSize; indexPool++){
      orderList[indexPool]=new ArrayList<Order>();
    }

    // for each order read all data and add into corresponding thread
    for(int indexOrder=0; indexOrder<numberOfOrder; indexOrder++){
      int orderNumber,orderQuantity;
      char orderType;
      orderNumber=scan.nextInt();
      orderType=scan.next().charAt(0);
      orderQuantity=scan.nextInt();
      Order order = new Order(orderNumber,orderType,orderQuantity);
      orderList[indexOrder%poolSize].add(order);
    }

    // create OrderThread object for each thread
    for(int indexOfPool=0;indexOfPool<poolSize;indexOfPool++){
      threadPool[indexOfPool]=new OrderThread(inventory,orderList[indexOfPool]);
    }

    // start each thread
    System.out.print("You gave Following Orders\n"); 
    for(int indexOfPool=0;indexOfPool<poolSize;indexOfPool++){
      threadPool[indexOfPool].start();
    }
  }
}