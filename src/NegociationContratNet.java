public class NegociationContratNet {

    private AgentFournisseur fournisseur;
    private AgentAcheteur acheteur;

    public void negociate(){

    }

    public void taskOffer(Float prix){
        acheteur.makeOffer(prix);

    }
}
