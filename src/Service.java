import java.util.Date;

public class Service {

    private String lieu_depart;
    private String lieu_arrive;
    private Date date_depart;
    private Date date_arrivee;

    private Boolean enVente;

    public Service(){
        this.lieu_depart = "";
        this.lieu_arrive = "";
        this.date_depart = new Date();
        this.date_arrivee = new Date();
        this.enVente = false;
    }

    public Date getDate_arrivee() {
        return date_arrivee;
    }

    public Date getDate_depart() {
        return date_depart;
    }

    public String getLieu_arrive() {
        return lieu_arrive;
    }

    public String getLieu_depart() {
        return lieu_depart;
    }
    public Boolean getEnVente() {
        return enVente;
    }

    public void setEnVente(Boolean enVente) {
        this.enVente = enVente;
    }
}
