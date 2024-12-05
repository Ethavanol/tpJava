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
        boolean isAccepted = fournisseur.getStrategie().evaluateOffer(offre);
        while(!isAccepted && tick < 6){
            if(tourAcheteur){
                offre = fournisseur.getStrategie().makeOffer(offre.getPrix());
                isAccepted = acheteur.getStrategie().evaluateOffer(offre);
            } else {
                offre = acheteur.getStrategie().makeOffer(offre.getPrix());
                isAccepted = fournisseur.getStrategie().evaluateOffer(offre);
            }
            tourAcheteur = !tourAcheteur;
            tick++;
        }

        return isAccepted? offre:null;
    }

    public void taskOffer(Float prix){
        acheteur.makeOffer(prix);
    }
}
