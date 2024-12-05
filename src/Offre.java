public class Offre {

    private Agent agent;

    private Float prix;
    private Boolean isValide;

    public Offre(Float prix) {
        this.agent = null;
        this.prix = prix;
    }

    public Offre(Agent agent, Float prix) {
        this.agent = agent;
        this.prix = prix;
    }

    public Float getPrix() {
        return prix;
    }
}
