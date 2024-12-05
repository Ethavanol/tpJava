import java.util.HashMap;
import Enum.*;

public class Main {
    public static void main(String[] args) {
        // Initialisation du service
        Service ticket = new Service(0, 100);

        // Initialisation des preferences
        HashMap<Preference, Object> mapFournisseur = new HashMap<Preference, Object>();
        mapFournisseur.put(Preference.PRIX_MIN, 450f);

        HashMap<Preference, Object> mapAcheteur = new HashMap<Preference, Object>();
        mapAcheteur.put(Preference.PRIX_MAX, 500f);

        StrategieFluctuationDiminue strategieFournisseur = new StrategieFluctuationDiminue(570f, 630f);
        StrategieFluctuationDiminue strategieAcheteur = new StrategieFluctuationDiminue(540f, 580f);

        // Initialisation des agents
        AgentFournisseur fournisseur = new AgentFournisseur(0, new Service[]{ticket}, mapFournisseur, strategieFournisseur);
        AgentAcheteur acheteur = new AgentAcheteur(0, mapAcheteur, strategieAcheteur);

        // Mise en négociation
        NegociationContratNet negociation = new NegociationContratNet(fournisseur, acheteur);
        negociation.negociate();
    }
}