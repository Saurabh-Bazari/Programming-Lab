import java.util.ArrayList;
class OrderThread extends Thread{

    // Each thread run orderThread parallel
    // orderList is arraylist of order for this thread only
    private ArrayList<Order> orderList;
    private Inventory inventory;
   
    // assign inventory to common inventory
    public OrderThread(Inventory inventory, ArrayList<Order> orderList){
        this.orderList = orderList;
        this.inventory=inventory;
    }

    // run method for each order and update inventory
    public void run(){

        // call corresponding method for each order according to order type
        for(int index = 0;index<orderList.size();index++){
            switch(orderList.get(index).getOrderType()){
                case 'C':
                        executeCaps(orderList.get(index));
                        break;
                case 'S':
                        executeSmallTShirt(orderList.get(index));
                        break;
                case 'M':
                        executeMediumTShirt(orderList.get(index));
                        break;
                case 'L':
                        executeLargeTShirt(orderList.get(index));
                        break;
            }
        }
    }
    
    // print inventory before order execute
    // it get synchronized block and execute or wait for block
    // compare order quantity and avaliable quantity of cap
    // print order status
    // print updated inventory
    public void executeCaps(Order order){
        synchronized(inventory.lockCap){
            int orderSize=order.getOrderQuantity();
            int currentSize = inventory.getCaps();

            if(orderSize > currentSize){
                System.out.println( "Caps:" + inventory.getCaps() 
                + " Small T-Shirt:" + inventory.getSmallTShirt() 
                + " Medium T-Shirt:" + inventory.getMediumTShirt() 
                + " Large T-Shirt:" + inventory.getLargeTShirt() + "\n\n" 
                + "Order "+order.getOrderNumber()+" FAILED!" + "\n\n" 
                + "Caps:" + inventory.getCaps() 
                + " Small T-Shirt:" + inventory.getSmallTShirt() 
                + " Medium T-Shirt:" + inventory.getMediumTShirt() 
                + " Large T-Shirt:" + inventory.getLargeTShirt() 
                + "\n-------------------------------------\n");
            }
            else{  
                System.out.println( "Caps:" + inventory.getCaps() 
                + " Small T-Shirt:" + inventory.getSmallTShirt() 
                + " Medium T-Shirt:" + inventory.getMediumTShirt() 
                + " Large T-Shirt:" + inventory.getLargeTShirt() + "\n\n" 
                + "Order "+order.getOrderNumber()+" SUCCESS!"+ "\n\n"
                + "Caps:" + (currentSize-orderSize) 
                + " Small T-Shirt:" + inventory.getSmallTShirt() 
                + " Medium T-Shirt:" + inventory.getMediumTShirt() 
                + " Large T-Shirt:" + inventory.getLargeTShirt() 
                + "\n-------------------------------------\n");
                inventory.setCaps(currentSize-orderSize);
            }
        }
    }

    // print inventory before order execute
    // it get synchronized block and execute or wait for block
    // compare order quantity and avaliable quantity of small Tshirt
    // print order status
    // print updated inventory
    public void executeSmallTShirt(Order order){
        synchronized(inventory.lockS){
            int orderSize=order.getOrderQuantity();
            int currentSize = inventory.getSmallTShirt();
            if(orderSize > currentSize){
                System.out.println( "Caps:" + inventory.getCaps() 
                + " Small T-Shirt:" + inventory.getSmallTShirt() 
                + " Medium T-Shirt:" + inventory.getMediumTShirt() 
                + " Large T-Shirt:" + inventory.getLargeTShirt() + "\n\n" 
                + " Order "+order.getOrderNumber()+" FAILED!"+ "\n\n"
                + " Caps:" + inventory.getCaps() 
                + " Small T-Shirt:" + inventory.getSmallTShirt() 
                + " Medium T-Shirt:" + inventory.getMediumTShirt() 
                + " Large T-Shirt:" + inventory.getLargeTShirt() 
                + "\n-------------------------------------\n");
            }
            else{      
                System.out.println( "Caps:" + inventory.getCaps() 
                + " Small T-Shirt:" + inventory.getSmallTShirt() 
                + " Medium T-Shirt:" + inventory.getMediumTShirt() 
                + " Large T-Shirt:" + inventory.getLargeTShirt() + "\n\n" 
                + "Order "+order.getOrderNumber()+" SUCCESS!"+ "\n\n" 
                + "Caps:" + inventory.getCaps() 
                + " Small T-Shirt:" + (currentSize-orderSize) 
                + " Medium T-Shirt:" + inventory.getMediumTShirt() 
                + " Large T-Shirt:" + inventory.getLargeTShirt() 
                + "\n-------------------------------------\n");
                inventory.setSmallTShirt(currentSize-orderSize);
            }
        }
    }

    // print inventory before order execute
    // it get synchronized block and execute or wait for block
    // compare order quantity and avaliable quantity of medium Tshirt
    // print order status
    // print updated inventory
    public void executeMediumTShirt(Order order){
        synchronized(inventory.lockM){
            int orderSize=order.getOrderQuantity();
            int currentSize = inventory.getMediumTShirt();
            if(orderSize > currentSize){
                System.out.println( "Caps:" + inventory.getCaps() 
                + " Small T-Shirt:" + inventory.getSmallTShirt() 
                + " Medium T-Shirt:" + inventory.getMediumTShirt() 
                + " Large T-Shirt:" + inventory.getLargeTShirt() + "\n\n" 
                + "Order "+order.getOrderNumber()+" FAILED!"+ "\n\n" 
                + "Caps:" + inventory.getCaps() 
                + " Small T-Shirt:" + inventory.getSmallTShirt() 
                + " Medium T-Shirt:" + inventory.getMediumTShirt()
                + " Large T-Shirt:" + inventory.getLargeTShirt()
                + "\n-------------------------------------\n");
            }
            else{      
                System.out.println( "Caps:" + inventory.getCaps() 
                + " Small T-Shirt:" + inventory.getSmallTShirt() 
                + " Medium T-Shirt:" + inventory.getMediumTShirt() 
                + " Large T-Shirt:" + inventory.getLargeTShirt() + "\n\n" 
                + "Order "+order.getOrderNumber()+" SUCCESS!"+ "\n\n" 
                + "Caps:" + inventory.getCaps() 
                + " Small T-Shirt:" + inventory.getSmallTShirt() 
                + " Medium T-Shirt:" + (currentSize-orderSize) 
                + " Large T-Shirt:" + inventory.getLargeTShirt() 
                + "\n-------------------------------------\n");
                inventory.setMediumTShirt(currentSize-orderSize);
            }
        }
    }

    // print inventory before order execute
    // it get synchronized block and execute or wait for block
    // compare order quantity and avaliable quantity of large Tshirt
    // print order status
    // print updated inventory
    public void executeLargeTShirt(Order order){
        synchronized(inventory.lockL){
            int orderSize=order.getOrderQuantity();
            int currentSize = inventory.getLargeTShirt();
            if(orderSize > currentSize){
                System.out.println( "Caps:" + inventory.getCaps() 
                + " Small T-Shirt:" + inventory.getSmallTShirt() 
                + " Medium T-Shirt:" + inventory.getMediumTShirt() 
                + " Large T-Shirt:" + inventory.getLargeTShirt() + "\n\n" 
                + "Order "+order.getOrderNumber()+" FAILED!"+ "\n\n" 
                + "Caps:" + inventory.getCaps() 
                + " Small T-Shirt:" + inventory.getSmallTShirt() 
                + " Medium T-Shirt:" + inventory.getMediumTShirt() 
                +  " Large T-Shirt:" + inventory.getLargeTShirt() 
                + "\n-------------------------------------\n");
            }
            else{      
                System.out.println( "Caps:" + inventory.getCaps() 
                + " Small T-Shirt:" + inventory.getSmallTShirt() 
                + " Medium T-Shirt:" + inventory.getMediumTShirt() 
                + " Large T-Shirt:" + inventory.getLargeTShirt() + "\n\n" 
                + "Order "+order.getOrderNumber()+" SUCCESS!"+ "\n\n" 
                + "Caps:" + inventory.getCaps() 
                + " Small T-Shirt:" + inventory.getSmallTShirt() 
                + " Medium T-Shirt:" + inventory.getMediumTShirt() 
                + " Large T-Shirt:" + (currentSize-orderSize) 
                + "\n-------------------------------------\n");
                inventory.setLargeTShirt(currentSize-orderSize);
            }
        }
    }
}