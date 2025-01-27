import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

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
            put(Arrays.asList(fournisseur, acheteur1, acheteur2, acheteur3), 95);
        }};

        AlgorithmeIP algorithmeIP = new AlgorithmeIP(listAgents);
        List<List<Agent>> coalitionAgents = algorithmeIP.findCoalition();

        // Initialize agents
        List<Agent> agents = new ArrayList<>();
        // Add agents to the list
        agents.add(fournisseur);
        agents.add(acheteur1);
        agents.add(acheteur2);
        agents.add(acheteur3);

        // Initialize CooperativeCoalitionFormation
        CooperativeCoalitionFormation coalitionFormation = new CooperativeCoalitionFormation(agents);

        // Form coalitions
        // coalitionFormation.findBestCoalition();

        // Mise en négociation
        // NegociationContratNet negociation = new NegociationContratNet(fournisseur, acheteur1);
        // negociation.negociate();

        List<AgentAcheteur> acheteurs = Arrays.asList(acheteur1, acheteur2, acheteur3);
        
        // Négociation multi-acheteurs (Offres non connues)
        //NegotiationContratNetMulti negociationMulti = new NegotiationContratNetMulti(fournisseur, acheteurs);
        //negociationMulti.negociate();
        NegotiationCoalition negotiationCoalition = new NegotiationCoalition(coalitionAgents, fournisseur);
        negotiationCoalition.negotiate();

        // Négociation multi-acheteurs (Offres connues)
        NegotiationContratNetMultiKnown negociationMultiKnown = new NegotiationContratNetMultiKnown(fournisseur, acheteurs);
        //negociationMultiKnown.negociate();

        // Négociation multi-fournisseurs
        AgentFournisseur fournisseur2 = new AgentFournisseur(1, new Service[]{ticket}, strategieFournisseur);
        AgentFournisseur fournisseur3 = new AgentFournisseur(2, new Service[]{ticket}, strategieFournisseur);
        List<AgentFournisseur> fournisseurs = Arrays.asList(fournisseur, fournisseur2, fournisseur3);
        NegotiationContratNetMultiSupplier negociationMultiSupplier = new NegotiationContratNetMultiSupplier(acheteur1, fournisseurs);
        negociationMultiSupplier.negociate();

        // Négociation multi-fournisseurs (Offres compétitives)
        List<AgentFournisseur> fournisseursCompetitifs = Arrays.asList(fournisseur, fournisseur2, fournisseur3);
        NegotiationContratNetCompetitiveSuppliers negociationCompetitiveSuppliers = new NegotiationContratNetCompetitiveSuppliers(acheteur1, fournisseursCompetitifs);
        negociationCompetitiveSuppliers.negociate();
        
    }
}