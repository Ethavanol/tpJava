
public class Main {
    public static void main(String[] args) {
        // Initialisation du service
        Service ticket = new Service(0, 100);

        StrategieFluctuationDiminue strategieFournisseur = new StrategieFluctuationDiminue(570f, 630f);
        StrategieFluctuationDiminue strategieAcheteur = new StrategieFluctuationDiminue(540f, 580f);

        // Initialisation des agents
        AgentFournisseur fournisseur = new AgentFournisseur(0, new Service[]{ticket}, strategieFournisseur);
        AgentAcheteur acheteur = new AgentAcheteur(0, strategieAcheteur);

        // Mise en négociation
        NegociationContratNet negociation = new NegociationContratNet(fournisseur, acheteur);
        negociation.negociate();
    }
}