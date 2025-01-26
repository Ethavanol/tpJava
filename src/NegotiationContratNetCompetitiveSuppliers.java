import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class NegotiationContratNetCompetitiveSuppliers {

    private AgentAcheteur acheteur;
    private List<AgentFournisseur> fournisseurs;
    private AtomicReference<Offre> bestOffer;

    public NegotiationContratNetCompetitiveSuppliers(AgentAcheteur acheteur, List<AgentFournisseur> fournisseurs) {
        this.acheteur = acheteur;
        this.fournisseurs = fournisseurs;
        this.bestOffer = new AtomicReference<>(null);
    }

    public void negociate() {
        ExecutorService executor = Executors.newFixedThreadPool(fournisseurs.size());

        for (AgentFournisseur fournisseur : fournisseurs) {
            executor.submit(() -> {
                System.out.println("Starting negotiation with supplier ID: " + fournisseur.getNom());
                boolean tourFournisseur = false;
                int tick = 0;
                float prixInitial = acheteur.getStrategie().getPrixAccept();

                final AtomicReference<Offre> currentOffreFournisseur = new AtomicReference<>(new Offre(fournisseur.getStrategie().getPrixMax()));

                Offre offreFournisseur = currentOffreFournisseur.get();
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
                        // Update the best offer if the current offer is better
                        bestOffer.updateAndGet(currentBest -> {
                            if (currentBest == null || currentOffreFournisseur.get().getPrix() < currentBest.getPrix()) {
                                return currentOffreFournisseur.get();
                            }
                            return currentBest;
                        });

                        // Adjust the supplier's offer based on the best offer
                        if (bestOffer.get() != null && bestOffer.get().getPrix() < currentOffreFournisseur.get().getPrix()) {
                            currentOffreFournisseur.set(fournisseur.getStrategie().makeOffer(bestOffer.get().getPrix()));
                        } else {
                            currentOffreFournisseur.set(fournisseur.getStrategie().makeOffer(currentOffreFournisseur.get().getPrix()));
                        }

                        System.out.println("Le fournisseur fait une offre de " + currentOffreFournisseur.get().getPrix());
                        isAccepted = acheteur.getStrategie().evaluateOffer(currentOffreFournisseur.get());
                    }
                    tourFournisseur = !tourFournisseur;
                    tick++;
                }

                Offre offre = isAccepted ? (tourFournisseur ? offreAcheteur : currentOffreFournisseur.get()) : null;
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