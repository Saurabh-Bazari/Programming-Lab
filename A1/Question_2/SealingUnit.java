public class SealingUnit{
    // number of b1 bottles in sealing buffer
    private int bufferB1TypeBottels;
    // number of b2 bottles in sealing buffer
    private int bufferB2TypeBottels;
    //number of b1 bottles packed by this unit
    private int donePackingForB1TypeBottels;
    //number of b2 bottles packed by this unit
    private int donePackingForB2TypeBottels;

    public SealingUnit(){
        //Constructor
        bufferB1TypeBottels=0;
        bufferB2TypeBottels=0;
        donePackingForB1TypeBottels=0;
        donePackingForB2TypeBottels=0;
    }

    public int getBufferB1TypeBottels() {
        //getter function for b1 bottles
        return bufferB1TypeBottels;
    }

    public void setBufferB1TypeBottels(int bufferB1TypeBottels) {
        //setter function for b1 bottles
        this.bufferB1TypeBottels = bufferB1TypeBottels;
    }

    public int getBufferB2TypeBottels() {
        //getter function for b2 bottles
        return bufferB2TypeBottels;
    }

    public void setBufferB2TypeBottels(int bufferB2TypeBottels) {
        //setter function for b2 bottles
        this.bufferB2TypeBottels = bufferB2TypeBottels;
    }

    public int getDonePackingForB1TypeBottels() {
        //getter method for b1 type bottles
        return donePackingForB1TypeBottels;
    }

    public void setDonePackingForB1TypeBottels(int donePackingForB1TypeBottels) {
        this.donePackingForB1TypeBottels = donePackingForB1TypeBottels;
    }

    // getter Setter for var donePackingForB2TypeBottels
    public int getDonePackingForB2TypeBottels() {
        return donePackingForB2TypeBottels;
    }

    public void setDonePackingForB2TypeBottels(int donePackingForB2TypeBottels) {
        this.donePackingForB2TypeBottels = donePackingForB2TypeBottels;
    }

    public boolean isBufferNotEmpty(){
        //check if buffer is empty
        if(bufferB1TypeBottels+bufferB2TypeBottels>0)
            return true;
        else
            return false;
    }

    public Boolean avaliableSpaceInBuffer(){
        // check if buffer has free space
        if(bufferB1TypeBottels+bufferB2TypeBottels<2)
            return true;
        else
            return false;
    }

	public int nextBottelTypeFromNonEmptyBuffer() {
        // get next bottle type from buffer
        if(bufferB1TypeBottels>0)
            return 1;
        else
            return 2;
	}
}