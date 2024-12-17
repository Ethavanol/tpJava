public interface Strategie {

    public Offre makeOffer(Float prix);

    public Boolean evaluateOfferAcheteur(Offre offre);

    public Boolean evaluateOfferFournisseur(Offre offre);

    public Float getPrixAccept();
}
