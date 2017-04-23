public class RegleAssoLift {
    MotifPhase3 X;
    MotifPhase3 Y;
    MotifPhase3 YPriveDeX;

    public RegleAssoLift(MotifPhase3 X, MotifPhase3 Y){
        this.X = X;
        this.Y = Y;
        this.YPriveDeX = Y.priveDe(X);
    }

    public double getConfiance(){
        return this.Y.getSupport() / ((double)this.X.getSupport());
    }

    public double getLift(){
        MotifPhase3 vide = new MotifPhase3();
        return getConfiance() / (YPriveDeX.getSupport()/((double)vide.getSupport()));
    }

    public String toString() {
        return X + " => " + YPriveDeX + " : "+ getConfiance() + " : "+ getLift() ;// ddhdh
    }
}