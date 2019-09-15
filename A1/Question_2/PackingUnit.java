public class PackingUnit{
    // Packing unit
    // number of b1 bottles in packing buffer
    private int bufferB1TypeBottels;
    // number of b2 bottles in packing buffer
    private int bufferB2TypeBottels;
    // returns which bottle to pick from buffer
    private int nextBottelTypeFromBuffer;
    //number of b1 bottles packed by this unit
    private int donePackingForB1TypeBottels;
    //number of b2 bottles packed by this unit
    private int donePackingForB2TypeBottels;

    public PackingUnit() {
        //Constructor
        bufferB1TypeBottels=0;
        bufferB2TypeBottels=0;
        nextBottelTypeFromBuffer=1;
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
        //setter method for b1 type bottles
        this.donePackingForB1TypeBottels = donePackingForB1TypeBottels;
    }

    public int getDonePackingForB2TypeBottels() {
        return donePackingForB2TypeBottels;
    }

    public void setDonePackingForB2TypeBottels(int donePackingForB2TypeBottels) {
        this.donePackingForB2TypeBottels = donePackingForB2TypeBottels;
    }

    public Boolean avaliableAnyTypeBottelsBuffer(){
        // return if there is any bottle available in buffer
        if(bufferB1TypeBottels==0 && bufferB2TypeBottels==0)
            return false;
        else
            return true;
    }

    public Boolean avaliableSpaceInB1TypeBottelsBuffer(){
        // return if there is any space available in buffer for b1 bottles
        if(bufferB1TypeBottels<2)
            return true;
        else
            return false;
    }
    public Boolean avaliableSpaceInB2TypeBottelsBuffer(){
        // return if there is any space available in buffer for b2 bottles
        if(bufferB2TypeBottels<3)
            return true;
        else
            return false;
    }

    public int nextBottelTypeFromNonEmptyBuffer(){
        // method for picking alternative type bottle from buffer
        if(nextBottelTypeFromBuffer==1){
            if(bufferB1TypeBottels>0){
                nextBottelTypeFromBuffer = 2;
                return 1;
            }
            else{
                nextBottelTypeFromBuffer = 1;
                return 2;
            }
        }
        else{
            if(bufferB2TypeBottels>0){
                nextBottelTypeFromBuffer = 1;
                return 2;
            }
            else{
                nextBottelTypeFromBuffer = 2;
                return 1;
            }
        }
    }
}