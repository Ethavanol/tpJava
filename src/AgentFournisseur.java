import Enum.Preference;

import java.util.HashMap;

public class AgentFournisseur {

    private String nom;

    private Service[] services;

    private HashMap<Preference, Object> preferences;

    public AgentFournisseur(){
        this.nom = "";
        this.services = new Service[0];
    }

    public AgentFournisseur(String nom, Service[] services){
        this.nom = nom;
        this.services = services;
    }

    public String getNom() {
        return this.nom;
    }

    public Service[] getServices() {
        return services;
    }

    public Boolean examineOffer(Float prix){
        if (prix < this.preferences.get(Preference.PRIX_MIN)){
            return false;
        }
        if (prix > this.preferences.get(Preference.PRIX_MAX)){
            return false;
        }
        return true;
    }
}
