import java.util.HashMap;

import Enum.*;

public class AgentAcheteur implements Agent {

    private Integer id;

    private HashMap<Preference, Object> preferences;


    private Strategie strategie;

    public AgentAcheteur(Integer id, HashMap<Preference, Object> preferences, Strategie strategie){
        this.id = id;
        this.preferences = preferences;
        this.strategie = strategie;
    }

    public Offre makeOffer(Float prix){
        Offre offre = new Offre(this, prix);
        System.out.println("L'agent acheteur fait une offre de : " + prix);
//      (float) Math.min((Float) preferences.get(OrientationNegociation.PRIX_DEPART), contreProposition * (0.9 + Math.random() * 0.1))
        return offre;
    }

    public Boolean evaluateOffer(Offre offre){
        if(offre.getPrix() > (float) preferences.get(Preference.PRIX_MAX)) {
            System.out.println("L'agent acheteur rejette l'offre");
            return false;
        }
        System.out.println("L'agent acheteur accepte l'offre");
        return true;
    }

    public Integer getId() {
        return this.id;
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

    public Strategie getStrategie(){
        return strategie;
    }


}
