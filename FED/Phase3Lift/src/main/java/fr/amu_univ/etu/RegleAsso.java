package fr.amu_univ.etu;

public class RegleAsso{
    Motif X;
    Motif Y;
    Motif YPriveDeX;

    public RegleAsso(Motif X, Motif Y){
        this.X = X;
        this.Y = Y;
        this.YPriveDeX = Y.priveDe(X);
    }

    public double getConfiance(){
        return this.Y.getSupport() / ((double)this.X.getSupport());
    }

    public double getLift(){
        Motif vide = new Motif();
        return getConfiance() / (YPriveDeX.getSupport()/((double)vide.getSupport()));
    }

    public String toString() {
        return X + " => " + YPriveDeX + " : "+ getConfiance() + " : "+ getLift() ;// ddhdh
    }
}