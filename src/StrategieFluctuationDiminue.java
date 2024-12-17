import java.util.concurrent.ThreadLocalRandom;

public class StrategieFluctuationDiminue implements Strategie{

    private Float tauxFluctuation;
    private Float tauxMin;
    private Float tauxSmoothness;

    private Float prixAccept;
    private Float prixMax;
    private Float margeAcceptation;
    private int nbTicks;


    public StrategieFluctuationDiminue(Float prixAccept, Float prixMax){
        this.tauxFluctuation = 1f;
        this.prixAccept = prixAccept;
        this.prixMax = prixMax;
        this.nbTicks = 0;
        this.margeAcceptation = prixMax - prixAccept;
        this.tauxMin = 0f;
        this.tauxSmoothness = 0.05f;
    }

    public Float getTaux_fluctuation() {
        return tauxFluctuation;
    }

    public void setTaux_fluctuation(Float tauxFluctuation) {
        this.tauxFluctuation = tauxFluctuation;
    }

    public Offre makeOffer(Float prix){
        int signe = Util.getRandomNegativeOrPositiveSigne();
        Offre offre = new Offre((float) (prix + signe * this.tauxFluctuation * 0.1));
        System.out.println("L'agent fournisseur fait une offre de : " + prix);

        updateStrategie();
        return offre;
    }


    public Boolean evaluateOffer(Offre offre){
        return offre.getPrix() < prixAccept;
    }

    public void updateStrategie(){
        this.tauxFluctuation = Util.lerp(tauxFluctuation, tauxMin, tauxSmoothness);
        this.nbTicks++;
    }

    public Float getPrixAccept(){
        return prixAccept;
    }

}
