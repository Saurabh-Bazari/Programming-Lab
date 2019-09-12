public class SealingUnit{
    private int bufferB1TypeBottels;
    private int bufferB2TypeBottels;
    private int donePackingForB1TypeBottels;
    private int donePackingForB2TypeBottels;

    public SealingUnit(){
        bufferB1TypeBottels=0;
        bufferB2TypeBottels=0;
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

    public boolean isBufferNotEmpty(){
        if(bufferB1TypeBottels+bufferB2TypeBottels>0)
            return true;
        else
            return false;
    }

    public Boolean avaliableSpaceInBuffer(){
        if(bufferB1TypeBottels+bufferB2TypeBottels<2)
            return true;
        else
            return false;
    }

	public int nextBottelTypeFromNonEmptyBuffer() {
        if(bufferB1TypeBottels>0)
            return 1;
        else
            return 2;
	}
}