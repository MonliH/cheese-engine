package Engine;

public class TranspositionEntry {
    int eval;
    int depth;
    Flag flag;

    enum Flag {
        EXACT, LOWER_BOUND, UPPER_BOUND
    }

    public TranspositionEntry(int eval, int depth, Flag flag) {
        this.eval = eval;
        this.depth = depth;
        this.flag = flag;
    }

    public int getEval() {
        return eval;
    }

    public int getDepth() {
        return depth;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setEval(int eval) {
        this.eval = eval;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }
}
