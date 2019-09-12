public class Order {
  private int orderNumber;
  private int orderQuantity;
  private char orderType;

  /**
   * @return the orderNumber
   */
  public int getOrderNumber() {
    return orderNumber;
  }

  /**
   * @param orderNumber the orderNumber to set
   */
  public void setOrderNumber(int orderNumber) {
    this.orderNumber = orderNumber;
  }

  /**
   * @return the orderQuantity
   */
  public int getOrderQuantity() {
    return orderQuantity;
  }

  /**
   * @param orderQuantity the orderQuantity to set
   */
  public void setOrderQuantity(int orderQuantity) {
    this.orderQuantity = orderQuantity;
  }

  /**
   * @return the orderType
   */
  public char getOrderType() {
    return orderType;
  }

  /**
   * @param orderType the orderType to set
   */
  public void setOrderType(char orderType) {
    this.orderType = orderType;
  }

  public Order(int number,char type, int quantity){
    orderNumber=number;
    orderType = type;
    orderQuantity = quantity;
  }
}