class Constant{
  //States for both machine working or not
  public static enum Status{
    IDLE,WORKING;
  }
  // States for bottel what is next destination 
  // any buffer or godown
  public static enum Destination{
    SEALING,GODOWN,PACKING;
  }
}