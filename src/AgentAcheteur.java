import java.util.HashMap;

import Enum.*;
import java.util.Random.*;

public class AgentAcheteur {

    private String nom;

    private HashMap<Preference, Object> preferences;

    private HashMap orientationsNegotiations;

    public AgentAcheteur(){
        this.nom = "";
        this.preferences = new HashMap<Preference, Object>();
        this.orientationsNegotiations = new HashMap<OrientationNegociation, Object>();
    }

    public AgentAcheteur(String nom, HashMap<Preference, Object> preferences, HashMap<OrientationNegociation, Object> orientationsNegotiations){
        this.nom = nom;
        this.preferences = preferences;
        this.orientationsNegotiations = orientationsNegotiations;
    }

    public Offre  makeOffer(Float contreProposition){
        Offre offre = new Offre(this, (float) Math.min((Float) preferences.get(OrientationNegociation.PRIX_DEPART), contreProposition * (0.9 + Math.random() * 0.1)));
        return offre;
    }
    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public HashMap getPreferences() {
        return this.preferences;
    }

    public void setPreferences(HashMap preferences) {
        this.preferences = preferences;
    }

    public void addPreference(Preference preference, Object valeur) {
        this.preferences.put(preference, valeur);
    }

    public void removePreference(Preference preference) {
        this.preferences.remove(preference);
    }



}
