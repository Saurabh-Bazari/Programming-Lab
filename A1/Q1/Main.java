import java.util.*;
public class Main {

  static Scanner scan = new Scanner(System.in);
  public static void BuildInventory( Inventory inventory) {

    System.out.println("Inventory Data");
    System.out.print("Number Of Caps: ");
    int caps = scan.nextInt();
    inventory.setCaps(caps);

    System.out.print("Number Of Small size TShirt: ");
    int smallTShirt = scan.nextInt();
    inventory.setSmallTShirt(smallTShirt);

    System.out.print("Number Of Medium size TShirt: ");
    int mediumTShirt = scan.nextInt();
    inventory.setMediumTShirt(mediumTShirt);

    System.out.print("Number Of Large size TShirt: ");
    int largeTShirt = scan.nextInt();
    inventory.setLargeTShirt(largeTShirt);
  }

  public static void main(String[] args) {
    
    int poolSize=8;
    Inventory inventory = new Inventory();
    BuildInventory(inventory);

    System.out.println();
    System.out.print("Number of students ordering: ");
    int numberOfOrder = scan.nextInt();
    System.out.println("Orders: ");

    ArrayList<Order>[] orderList = new ArrayList[poolSize];
    OrderThread threadPool[] = new OrderThread[poolSize];

    for(int indexPool=0; indexPool<poolSize; indexPool++){
      orderList[indexPool]=new ArrayList<Order>();
    }

    for(int indexOrder=0; indexOrder<numberOfOrder; indexOrder++){
      int orderNumber,orderQuantity;
      char orderType;
      orderNumber=scan.nextInt();
      orderType=scan.next().charAt(0);
      orderQuantity=scan.nextInt();
      Order order = new Order(orderNumber,orderType,orderQuantity);
      orderList[indexOrder%poolSize].add(order);
    }

    for(int indexOfPool=0;indexOfPool<poolSize;indexOfPool++){
      threadPool[indexOfPool]=new OrderThread(inventory,orderList[indexOfPool]);
    }

    System.out.print("You gave Following Orders\n");
    
    for(int indexOfPool=0;indexOfPool<poolSize;indexOfPool++){
      threadPool[indexOfPool].start();
    }
  }
}