public class NegociationContratNet {

    private AgentFournisseur fournisseur;
    private AgentAcheteur acheteur;

    public NegociationContratNet(AgentFournisseur fournisseur, AgentAcheteur acheteur) {
        this.fournisseur = fournisseur;
        this.acheteur = acheteur;
    }
    public Offre negociate(){
        boolean tourAcheteur = true;

        int tick = 0;
        float prixInitial = fournisseur.getServices()[0].getPrix();

        Offre offre = acheteur.getStrategie().makeOffer(acheteur.getStrategie().getPrixAccept());
        System.out.println("L'acheteur fait une offre de " + offre.getPrix());
        boolean isAccepted = fournisseur.getStrategie().evaluateOfferFournisseur(offre);
        while(!isAccepted && tick < 6){
            System.out.println("L'offre a été refusée");
            if(tourAcheteur){
                offre = fournisseur.getStrategie().makeOffer(offre.getPrix());
                System.out.println("Le fournisseur fait une offre de " + offre.getPrix());
                isAccepted = acheteur.getStrategie().evaluateOfferAcheteur(offre);
            } else {
                offre = acheteur.getStrategie().makeOffer(offre.getPrix());
                System.out.println("L'acheteur fait une offre de " + offre.getPrix());
                isAccepted = fournisseur.getStrategie().evaluateOfferFournisseur(offre);
            }
            tourAcheteur = !tourAcheteur;
            tick++;
        }

        System.out.println("La négociation est terminée, le prix final est: " + offre.getPrix());
        return isAccepted? offre:null;
    }

    public void taskOffer(Float prix){
        acheteur.getStrategie().makeOffer(prix);
    }
}
