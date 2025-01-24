import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Initialisation du service
        Service ticket = new Service(0, 100);

        StrategieFluctuationDiminue strategieFournisseur = new StrategieFluctuationDiminue(570f, 630f, false);
        StrategieFluctuationDiminue strategieAcheteur = new StrategieFluctuationDiminue(540f, 580f, true);

        // Initialisation des agents
        AgentFournisseur fournisseur = new AgentFournisseur(0, new Service[]{ticket}, strategieFournisseur);
        AgentAcheteur acheteur1 = new AgentAcheteur(0, strategieAcheteur);
        AgentAcheteur acheteur2 = new AgentAcheteur(0, strategieAcheteur);
        AgentAcheteur acheteur3 = new AgentAcheteur(0, strategieAcheteur);

        HashMap<List<Agent>, Integer> listAgents = new HashMap<List<Agent>, Integer>(){{
            put(Arrays.asList(acheteur1), 10);
            put(Arrays.asList(acheteur2), 15);
            put(Arrays.asList(acheteur3), 20);
            put(Arrays.asList(fournisseur), 10);
            put(Arrays.asList(acheteur1, acheteur2), 30);
            put(Arrays.asList(acheteur1, acheteur3), 45);
            put(Arrays.asList(acheteur2, acheteur3), 20);
            put(Arrays.asList(fournisseur, acheteur1), 25);
            put(Arrays.asList(fournisseur, acheteur2), 40);
            put(Arrays.asList(fournisseur, acheteur3), 30);
            put(Arrays.asList(acheteur1, acheteur2, acheteur3), 50);
            put(Arrays.asList(fournisseur, acheteur1, acheteur2), 80);
            put(Arrays.asList(fournisseur, acheteur1, acheteur3), 85);
            put(Arrays.asList(fournisseur, acheteur2, acheteur3), 65);
            put(Arrays.asList(fournisseur, acheteur1, acheteur2, acheteur3), 100);
        }};

        AlgorithmeIP algorithmeIP = new AlgorithmeIP(listAgents);
        algorithmeIP.findCoalition();


        // Mise en négociation
        // NegociationContratNet negociation = new NegociationContratNet(fournisseur, acheteur1);
        // negociation.negociate();

        List<AgentAcheteur> acheteurs = Arrays.asList(acheteur1, acheteur2, acheteur3);
        
        // Négociation multi-acheteurs (Offres non connues)
        //NegotiationContratNetMulti negociationMulti = new NegotiationContratNetMulti(fournisseur, acheteurs);
        //negociationMulti.negociate();

        // Négociation multi-acheteurs (Offres connues)
        NegotiationContratNetMultiKnown negociationMultiKnown = new NegotiationContratNetMultiKnown(fournisseur, acheteurs);
        negociationMultiKnown.negociate();

    }
}