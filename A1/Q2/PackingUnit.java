public class PackingUnit{
    private int bufferB1TypeBottels;
    private int bufferB2TypeBottels;
    private int nextBottelTypeFromBuffer;
    private int donePackingForB1TypeBottels;
    private int donePackingForB2TypeBottels;

    public PackingUnit() {
        bufferB1TypeBottels=0;
        bufferB2TypeBottels=0;
        nextBottelTypeFromBuffer=1;
        donePackingForB1TypeBottels=0;
        donePackingForB2TypeBottels=0;
    }

    public int getBufferB1TypeBottels() {
        return bufferB1TypeBottels;
    }

    public void setBufferB1TypeBottels(int bufferB1TypeBottels) {
        this.bufferB1TypeBottels = bufferB1TypeBottels;
    }

    public int getBufferB2TypeBottels() {
        return bufferB2TypeBottels;
    }

    public void setBufferB2TypeBottels(int bufferB2TypeBottels) {
        this.bufferB2TypeBottels = bufferB2TypeBottels;
    }

    public int getDonePackingForB1TypeBottels() {
        return donePackingForB1TypeBottels;
    }

    public void setDonePackingForB1TypeBottels(int donePackingForB1TypeBottels) {
        this.donePackingForB1TypeBottels = donePackingForB1TypeBottels;
    }

    public int getDonePackingForB2TypeBottels() {
        return donePackingForB2TypeBottels;
    }

    public void setDonePackingForB2TypeBottels(int donePackingForB2TypeBottels) {
        this.donePackingForB2TypeBottels = donePackingForB2TypeBottels;
    }

    public Boolean avaliableAnyTypeBottelsBuffer(){
        if(bufferB1TypeBottels==0 && bufferB2TypeBottels==0)
            return false;
        else
            return true;
    }

    public Boolean avaliableSpaceInB1TypeBottelsBuffer(){
        if(bufferB1TypeBottels<2)
            return true;
        else
            return false;
    }
    public Boolean avaliableSpaceInB2TypeBottelsBuffer(){
        if(bufferB2TypeBottels<3)
            return true;
        else
            return false;
    }

    public int nextBottelTypeFromNonEmptyBuffer(){
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