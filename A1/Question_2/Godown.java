public class Godown{
    // number of b1 bottles in godown
    private int b1TypeBottels;
    // number of b2 bottles in godown
    private int b2TypeBottels;

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
        //setter function for b1 bottles
        this.b2TypeBottels = b2TypeBottels;
    }

    public Godown() {
        // constrcutor
        this.b1TypeBottels = 0;
        this.b2TypeBottels = 0;
    }
}