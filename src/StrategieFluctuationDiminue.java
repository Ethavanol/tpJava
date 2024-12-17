
public class StrategieFluctuationDiminue implements Strategie{

    private Float tauxFluctuation;
    private Float tauxMin;
    private Float tauxSmoothness;

    private Float prixAccept;
    private Float prixMax;
    private Float margeAcceptation;
    private int nbTicks;

    private Boolean isAcheteur;


    public StrategieFluctuationDiminue(Float prixAccept, Float prixMax, Boolean isAcheteur){
        this.tauxFluctuation = 1f;
        this.prixAccept = prixAccept;
        this.prixMax = prixMax;
        this.nbTicks = 0;
        this.margeAcceptation = prixMax - prixAccept;
        this.tauxMin = 0f;
        this.tauxSmoothness = 0.05f;
        this.isAcheteur = isAcheteur;
    }

    public Offre makeOffer(Float prix){
        // l'acheteur propose un prix plus haut à supposer quel'offre précédente a été refusée
        // le fournisseur lui propose un prix plus bas en supposant que l'offre précédente a été refusée
        int signe = this.isAcheteur ? 1 : -1;
        Offre offre = new Offre((float) (prix + signe * this.tauxFluctuation * 0.02 * prix));

        updateStrategie();
        return offre;
    }

    public Boolean evaluateOffer(Offre offre){
        return this.isAcheteur ? evaluateOfferAcheteur(offre) : evaluateOfferFournisseur(offre);
    }

    public Boolean evaluateOfferAcheteur(Offre offre){
        return offre.getPrix() < this.prixAccept;
    }

    public Boolean evaluateOfferFournisseur(Offre offre){
        return offre.getPrix() > this.prixAccept;
    }

    public void updateStrategie(){
        this.tauxFluctuation = Util.lerp(tauxFluctuation, tauxMin, tauxSmoothness);
        this.nbTicks++;
    }

    public Float getPrixAccept(){
        return this.prixAccept;
    }

    public Float getPrixMax(){
        return this.prixMax;
    }

}
