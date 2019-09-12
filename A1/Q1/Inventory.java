public class Inventory {
  
  private int caps;
  private int smallTShirt;
  private int mediumTShirt;
  private int largeTShirt;
  
  public final Object lockCap = new Object();
  public final Object lockS = new Object();
  public final Object lockM = new Object();
  public final Object lockL = new Object();

  /**
   * @return the caps
   */
  public int getCaps() {
    return caps;
  }

  /**
   * @param caps the caps to set
   */
  public void setCaps(int caps) {
    this.caps = caps;
  }

  /**
   * @return the largeTShirt
   */
  public int getLargeTShirt() {
    return largeTShirt;
  }

  /**
   * @param largeTShirt the largeTShirt to set
   */
  public void setLargeTShirt(int largeTShirt) {
    this.largeTShirt = largeTShirt;
  }

  /**
   * @return the mediumTShirt
   */
  public int getMediumTShirt() {
    return mediumTShirt;
  }

  /**
   * @param mediumTShirt the mediumTShirt to set
   */
  public void setMediumTShirt(int mediumTShirt) {
    this.mediumTShirt = mediumTShirt;
  }

  /**
   * @return the smallTShirt
   */
  public int getSmallTShirt() {
    return smallTShirt;
  }

  /**
   * @param smallTShirt the smallTShirt to set
   */
  public void setSmallTShirt(int smallTShirt) {
    this.smallTShirt = smallTShirt;
  }
}