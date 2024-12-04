public class Offre {

    private AgentAcheteur agentAcheteur;
    private Float prix;

    private Boolean accepted;

    public Offre(AgentAcheteur agent, Float prix){
        this.agentAcheteur = agent;
        this.prix = prix;
        this.accepted = false;
    }




}
