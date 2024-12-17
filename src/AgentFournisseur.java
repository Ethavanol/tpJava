
public class AgentFournisseur implements Agent {

    private Integer id;

    private Service[] services;

    private Strategie strategie;

    public AgentFournisseur(){
        this.services = new Service[0];
    }

    public AgentFournisseur(Integer id, Service[] services, Strategie strategie){
        this.id = id;
        this.services = services;
        this.strategie = strategie;
    }

    public Strategie getStrategie(){
        return strategie;
    }

    public Integer getNom() {
        return this.id;
    }

    public Service[] getServices() {
        return services;
    }


}
