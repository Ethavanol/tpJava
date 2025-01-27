import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ConcurrentHashMap;

public class NegotiationCoalition {

    private List<List<Agent>> coalitionAcheteurs;
    private AgentFournisseur agentFournisseur;
    private ConcurrentHashMap<Integer, Float> offresAcheteurs = new ConcurrentHashMap<>(); // Stocke les offres des acheteurs

    public NegotiationCoalition(List<List<Agent>> coalitionAcheteurs, AgentFournisseur agentFournisseur) {
        this.coalitionAcheteurs = coalitionAcheteurs;
        this.agentFournisseur = agentFournisseur;
    }


    public void negotiate() {
        ExecutorService executor = Executors.newFixedThreadPool(coalitionAcheteurs.size());

        for (List<Agent> coalition : coalitionAcheteurs) {
            executor.submit(() -> {
                boolean isAccepted = false;
                int tick = 0;
                // Chaque acheteur négocie indépendamment
                for (Agent acheteur : coalition) {
                    System.out.println("Acheteur " + acheteur.getId() + " commence la négociation.");

                    float prixInitial = 520f;
                    float prixAcheteur = prixInitial; // Le prix proposé par l'acheteur
                    float meilleurPrix = prixAcheteur;
                    int meilleureOffreAcheteurId = acheteur.getId();

                    // Chaque acheteur fait plusieurs tentatives pour négocier une meilleure offre
                    while (!isAccepted && tick < 6) {
                        // L'acheteur propose une offre
                        Offre offreAcheteur = new Offre(prixAcheteur);
                        System.out.println("L'acheteur " + acheteur.getId() + " propose une offre de " + offreAcheteur.getPrix());
                        isAccepted = agentFournisseur.getStrategie().evaluateOffer(offreAcheteur);

                        // Si l'offre est acceptée, on enregistre cette offre comme la meilleure pour la coalition
                        if (isAccepted) {
                            System.out.println("L'acheteur " + acheteur.getId() + " a trouvé une offre acceptable.");
                            if (prixAcheteur < meilleurPrix) {
                                meilleurPrix = prixAcheteur;
                                meilleureOffreAcheteurId = acheteur.getId();
                            }
                            break;
                        }

                        // Sinon, l'acheteur augmente son offre de manière exponentielle à chaque tour
                        float augmentation = (float) Math.pow(2, tick); // Augmentation exponentielle
                        prixAcheteur += augmentation; // Augmenter l'offre de plus en plus à chaque tour
                        tick++;
                    }

                    // Quand l'acheteur a trouvé une meilleure offre, il la partage avec le groupe
                    if (isAccepted && acheteur.getId() == meilleureOffreAcheteurId) {
                        // La coalition profite de la meilleure offre trouvée
                        for (Agent membreCoalition : coalition) {
                            offresAcheteurs.put(membreCoalition.getId(), meilleurPrix);
                        }
                        System.out.println("Le groupe a trouvé une offre finale de " + meilleurPrix + ". Tous les membres bénéficient de cette offre.");
                    }
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Attendre que toutes les négociations soient terminées
        }
    }
}
