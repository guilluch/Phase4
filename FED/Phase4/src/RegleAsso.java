/**
 * Created by p14001575 on 07/04/17.
 */
public class RegleAsso {
    MotifPhase2 X;
    MotifPhase2 Y;
    MotifPhase2 YPriveDeX;

    public RegleAsso(MotifPhase2 X, MotifPhase2 Y) {
        this.X = X;
        this.Y = Y;
        this.YPriveDeX = Y.priveDe(X);

    }

    public double getConfiance(){
        return this.Y.getSupport() / ((double)this.X.getSupport());
    }

    public double getLift(){
        return getConfiance()/YPriveDeX.getSupport();
    }

    @Override
    public String toString() {
        return X + "=>" + YPriveDeX;
    }
}
