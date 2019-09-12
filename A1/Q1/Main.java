import java.util.*;
// import java.util.ArrayList;
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
    
    int poolSize =8;
    Inventory inventory = new Inventory();
    BuildInventory(inventory);

    System.out.println();
    System.out.print("Number of students ordering: ");
    int numberOfOrder = scan.nextInt();
    ArrayList<Order>[] orderList = new ArrayList[poolSize];
    OrderThread threadpool[] = new OrderThread[poolSize];

    for(int i=0;i<poolSize;i++){
      orderList[i]=new ArrayList<Order>();
    }

    for(int i=0;i<numberOfOrder;i++){
      int number,quantity;
      char type;
      number=scan.nextInt();
      type=scan.next().charAt(0);
      quantity=scan.nextInt();
      Order order = new Order(number,type,quantity);
      orderList[i%poolSize].add(order);
    }

    for(int i=0;i<poolSize;i++){
      threadpool[i]=new OrderThread(inventory,orderList[i]);
    }

    System.out.print("You gave Following Orders\n");
    
    for(int i=0;i<poolSize;i++){
      threadpool[i].start();
    }
  }
}