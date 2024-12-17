import java.util.HashMap;

public class AgentAcheteur implements Agent {

    private Integer id;

    private Strategie strategie;

    public AgentAcheteur(Integer id, Strategie strategie){
        this.id = id;
        this.strategie = strategie;
    }

    public Integer getId() {
        return this.id;
    }


    public Strategie getStrategie(){
        return strategie;
    }


}
