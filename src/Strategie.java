public interface Strategie {

    public Offre makeOffer(Float prix);

    public Boolean evaluateOffer(Offre offre);

    public Float getPrixAccept();

    public Float getPrixMax();

    public int getValue();
}
