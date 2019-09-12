public class UnfinishedTray {
    private int b1TypeBottels;
    private int b2TypeBottels;
    private int nextBottelInPackingUnit=1;
    private int nextBottelInSealingUnit=2;

    public UnfinishedTray(){
        b1TypeBottels=0;
        b2TypeBottels=0;
        nextBottelInPackingUnit=1;
        nextBottelInSealingUnit=2;
    }

    public int getB1TypeBottels() {
        return b1TypeBottels;
    }

    public void setB1TypeBottels(int b1TypeBottels) {
        this.b1TypeBottels = b1TypeBottels;
    }

    public int getB2TypeBottels() {
        return b2TypeBottels;
    }

    public void setB2TypeBottels(int b2TypeBottels) {
        this.b2TypeBottels = b2TypeBottels;
    }

    public Boolean avaliableB1TypeBottels() {
        if(b1TypeBottels>0)
            return true;
        else
            return false;
    }

    public Boolean avaliableB2TypeBottels(){
        if(b2TypeBottels>0)
            return true;
        else
            return false;
    }

    public int nextBottelTypeForPackingUnit(){
        if(nextBottelInPackingUnit==1){
            if(b1TypeBottels>0){
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
                nextBottelInPackingUnit=1;
                return 2;
            }
            else if(b1TypeBottels>0){
                nextBottelInPackingUnit=2;
                return 1;
            }
            else{
                return 0;
            }
        }       
    }

    public int nextBottelTypeForSealingUnit(){
        if(nextBottelInSealingUnit==1){
            if(b1TypeBottels>0){
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
                nextBottelInSealingUnit=1;
                return 2;
            }
            else if(b1TypeBottels>0){
                nextBottelInSealingUnit=2;
                return 1;
            }
            else{
                return 0;
            }
        }             
    }
}