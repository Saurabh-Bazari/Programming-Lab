public class UnfinishedTray {
    // number of b1 bottles in unfinished
    private int b1TypeBottels;
    // number of b2 bottles in unfinished
    private int b2TypeBottels;
    // next bottle to be sent to packing unit
    private int nextBottelInPackingUnit=1;
    // next bottle to be sent to sealing unit
    private int nextBottelInSealingUnit=2;

    public UnfinishedTray(){
        //Constructor
        b1TypeBottels=0;
        b2TypeBottels=0;
        nextBottelInPackingUnit=1;
        nextBottelInSealingUnit=2;
    }

    public int getB1TypeBottels() {
        //getter function for b1 bottles
        return b1TypeBottels;
    }

    public void setB1TypeBottels(int b1TypeBottels) {
        //setter function for b1 bottles
        this.b1TypeBottels = b1TypeBottels;
    }

    public int getB2TypeBottels() {
        //getter function for b2 bottles
        return b2TypeBottels;
    }

    public void setB2TypeBottels(int b2TypeBottels) {
        //setter function for b2 bottles
        this.b2TypeBottels = b2TypeBottels;
    }

    public Boolean avaliableB1TypeBottels() {
        // check whether b1 bottle are available
        if(b1TypeBottels>0)
            return true;
        else
            return false;
    }

    public Boolean avaliableB2TypeBottels(){
        // check whether b2 bottle are available
        if(b2TypeBottels>0)
            return true;
        else
            return false;
    }

    public int nextBottelTypeForPackingUnit(){
        // tell which type to send and update it by altenate bottle rule
        if(nextBottelInPackingUnit==1){
            if(b1TypeBottels>0){
                // if next bottle is b1 return it and update it as b2
                nextBottelInPackingUnit=2;
                return 1;
            }
            else if(b2TypeBottels>0){
                nextBottelInPackingUnit=1;
                return 2;
            }
            else{
                return 0;
            }
        } 
        else{
            if(b2TypeBottels>0){
                // if next bottle is b2 return it and update it as b1
                nextBottelInPackingUnit=1;
                return 2;
            }
            else if(b1TypeBottels>0){
                // if next bottle is b1 return it and update it as 2
                nextBottelInPackingUnit=2;
                return 1;
            }
            else{
                return 0;
            }
        }       
    }

    public int nextBottelTypeForSealingUnit(){
        // tell which type to send and update it by altenate bottle rule
        if(nextBottelInSealingUnit==1){
            if(b1TypeBottels>0){
                // if next bottle is b1 return it and update it as b2
                nextBottelInSealingUnit=2;
                return 1;
            }
            else if(b2TypeBottels>0){
                nextBottelInSealingUnit=1;
                return 2;
            }
            else{
                return 0;
            }
        }  
        else{
            if(b2TypeBottels>0){
                // if next bottle is b2 return it and update it as b1
                nextBottelInSealingUnit=1;
                return 2;
            }
            else if(b1TypeBottels>0){
                // if next bottle is b1 return it and update it as 2
                nextBottelInSealingUnit=2;
                return 1;
            }
            else{
                return 0;
            }
        }             
    }
}