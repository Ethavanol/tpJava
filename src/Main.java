public class Main {
    public static void main(String[] args) {

        // Initiatilisation des agents
        AgentFournisseur fournisseur = new AgentFournisseur();
        AgentAcheteur acheteur = new AgentAcheteur();

        // Mise en négociation
        NegociationContratNet negociation = new NegociationContratNet();
        negociation.setAcheteur(acheteur);
    }
}