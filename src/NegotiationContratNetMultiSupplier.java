import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NegotiationContratNetMultiSupplier {

    private AgentAcheteur acheteur;
    private List<AgentFournisseur> fournisseurs;

    public NegotiationContratNetMultiSupplier(AgentAcheteur acheteur, List<AgentFournisseur> fournisseurs) {
        this.acheteur = acheteur;
        this.fournisseurs = fournisseurs;
    }

    public void negociate() {
        ExecutorService executor = Executors.newFixedThreadPool(fournisseurs.size());

        for (AgentFournisseur fournisseur : fournisseurs) {
            executor.submit(() -> {
                System.out.println("Starting negotiation with supplier ID: " + fournisseur.getNom());
                boolean tourFournisseur = false;
                int tick = 0;
                float prixInitial = acheteur.getStrategie().getPrixAccept();

                Offre offreFournisseur = new Offre(fournisseur.getStrategie().getPrixMax());
                Offre offreAcheteur = new Offre(acheteur.getStrategie().getPrixAccept());
                System.out.println("Le fournisseur fait une offre de " + offreFournisseur.getPrix());
                boolean isAccepted = acheteur.getStrategie().evaluateOffer(offreFournisseur);

                while (!isAccepted && tick < 6) {
                    System.out.println("L'offre a été refusée");
                    if (!tourFournisseur) {
                        offreAcheteur = acheteur.getStrategie().makeOffer(offreAcheteur.getPrix());
                        System.out.println("L'acheteur fait une offre de " + offreAcheteur.getPrix());
                        isAccepted = fournisseur.getStrategie().evaluateOffer(offreAcheteur);
                    } else {
                        offreFournisseur = fournisseur.getStrategie().makeOffer(offreFournisseur.getPrix());
                        System.out.println("Le fournisseur fait une offre de " + offreFournisseur.getPrix());
                        isAccepted = acheteur.getStrategie().evaluateOffer(offreFournisseur);
                    }
                    tourFournisseur = !tourFournisseur;
                    tick++;
                }

                Offre offre = isAccepted ? (tourFournisseur ? offreAcheteur : offreFournisseur) : null;
                if (offre != null) {
                    System.out.println("L'offre a été acceptée. La négociation est terminée, le prix final est: " + offre.getPrix());
                } else {
                    System.out.println("La négociation est terminée, aucun achat n'a été effectué");
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all negotiations to complete
        }
    }
} 