import java.util.Date;

public class Service {

    private Integer id;
    private Integer length;
    private Float prix;

    private Boolean enVente;

    public Service(Integer id, Integer length) {
        this.id = id;
        this.length = length;
        this.prix = (float) (((double) this.length / 5) * 0.8 + (0.4 * Math.random()));
        this.enVente = true;
    }


    public float getPrix() {
        return prix;
    }

    public Boolean getEnVente() {
        return enVente;
    }

    public void setEnVente(Boolean enVente) {
        this.enVente = enVente;
    }
}
