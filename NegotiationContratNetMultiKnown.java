import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ConcurrentHashMap;

public class NegotiationContratNetMultiKnown {

    private AgentFournisseur fournisseur;
    private List<AgentAcheteur> acheteurs;
    private ConcurrentHashMap<Integer, Float> offresAcheteurs = new ConcurrentHashMap<>();

    public NegotiationContratNetMultiKnown(AgentFournisseur fournisseur, List<AgentAcheteur> acheteurs) {
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

                offresAcheteurs.put(acheteur.getId(), offreAcheteur.getPrix());

                while (!isAccepted && tick < 6) {
                    System.out.println("L'offre a été refusée");
                    if (!tourAcheteur) {
                        offreFournisseur = fournisseur.getStrategie().makeOffer(offreFournisseur.getPrix());
                        System.out.println("Le fournisseur fait une offre de " + offreFournisseur.getPrix());
                        isAccepted = acheteur.getStrategie().evaluateOffer(offreFournisseur);
                    } else {
                        final float currentOfferPrice = offreAcheteur.getPrix();
                        // Get the best offer from other buyers
                        float bestOtherOffer = offresAcheteurs.values().stream()
                            .filter(price -> !price.equals(currentOfferPrice))
                            .max(Float::compare)
                            .orElse(0.0f);

                        // Make sure the new offer is better (higher) than the best other offer
                        float newOfferPrice = Math.max(offreAcheteur.getPrix(), bestOtherOffer + 1);
                        offreAcheteur = acheteur.getStrategie().makeOffer(newOfferPrice);
                        System.out.println("L'acheteur fait une offre de " + offreAcheteur.getPrix());

                        // Update the offer in the shared map
                        offresAcheteurs.put(acheteur.getId(), offreAcheteur.getPrix());
                        System.out.println("Offres actuelles des acheteurs: " + offresAcheteurs);

                        // Evaluate the new offer
                        isAccepted = fournisseur.getStrategie().evaluateOffer(offreAcheteur);
                    }
                    if (tourAcheteur) {
                        offresAcheteurs.put(acheteur.getId(), offreAcheteur.getPrix());
                        System.out.println("Offres actuelles des acheteurs: " + offresAcheteurs);
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