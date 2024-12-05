import Enum.*;
import java.util.HashMap;

public class AgentFournisseur implements Agent {

    private Integer id;

    private Service[] services;

    private HashMap<Preference, Object> preferences;

    private Strategie strategie;

    public AgentFournisseur(){
        this.services = new Service[0];
    }

    public AgentFournisseur(Integer id, Service[] services, HashMap<Preference, Object> preferences, Strategie strategie){
        this.id = id;
        this.services = services;
        this.preferences = preferences;
        this.strategie = strategie;
    }

    public Strategie getStrategie(){
        return strategie;
    }

    public Integer getNom() {
        return this.id;
    }

    public Service[] getServices() {
        return services;
    }










    public Offre makeOffer(Float prix) {
        Offre offre = new Offre(this, prix);
        System.out.println("L'agent fournisseur fait une offre de : " + prix);
        return offre;
    }

    public Boolean evaluateOffer(Offre offre){
        float prix = offre.getPrix();
        if (prix < (Float) this.preferences.get(Preference.PRIX_MIN) || prix > (Float) this.preferences.get(Preference.PRIX_MAX)){
            System.out.println("L'agent fournisseur rejette l'offre");
            return false;
        }
        System.out.println("L'agent fournisseur accepte l'offre");
        return true;
    }
}
