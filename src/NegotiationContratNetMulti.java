import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NegotiationContratNetMulti {

    private AgentFournisseur fournisseur;
    private List<AgentAcheteur> acheteurs;

    public NegotiationContratNetMulti(AgentFournisseur fournisseur, List<AgentAcheteur> acheteurs) {
        this.fournisseur = fournisseur;
        this.acheteurs = acheteurs;
    }

    public void negociate() {
        ExecutorService executor = Executors.newFixedThreadPool(acheteurs.size());

        for (AgentAcheteur acheteur : acheteurs) {
            executor.submit(() -> {
                System.out.println("Starting negotiation with buyer ID: " + acheteur.getId());
                boolean tourAcheteur = false;
                int tick = 0;
                float prixInitial = fournisseur.getServices()[0].getPrix();

                Offre offreAcheteur = new Offre(acheteur.getStrategie().getPrixAccept());
                Offre offreFournisseur = new Offre(fournisseur.getStrategie().getPrixMax());
                System.out.println("L'acheteur fait une offre de " + offreAcheteur.getPrix());
                boolean isAccepted = fournisseur.getStrategie().evaluateOffer(offreAcheteur);

                while (!isAccepted && tick < 6) {
                    System.out.println("L'offre a été refusée");
                    if (!tourAcheteur) {
                        offreFournisseur = fournisseur.getStrategie().makeOffer(offreFournisseur.getPrix());
                        System.out.println("Le fournisseur fait une offre de " + offreFournisseur.getPrix());
                        isAccepted = acheteur.getStrategie().evaluateOffer(offreFournisseur);
                    } else {
                        offreAcheteur = acheteur.getStrategie().makeOffer(offreAcheteur.getPrix());
                        System.out.println("L'acheteur fait une offre de " + offreAcheteur.getPrix());
                        isAccepted = fournisseur.getStrategie().evaluateOffer(offreAcheteur);
                    }
                    tourAcheteur = !tourAcheteur;
                    tick++;
                }

                Offre offre = isAccepted ? (tourAcheteur ? offreFournisseur : offreAcheteur) : null;
                if (offre != null) {
                    System.out.println("L'offre a été acceptée. La négociation est terminée, le prix final est: " + offre.getPrix());
                } else {
                    System.out.println("La négociation est terminée, aucun achat n'a été effectué");
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Attendre que toutes les négociations soient terminées
        }
    }
}