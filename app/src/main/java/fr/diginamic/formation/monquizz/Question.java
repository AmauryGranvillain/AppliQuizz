package fr.diginamic.formation.monquizz;

import java.util.ArrayList;

public class Question {

    private int id;
    private String intitule;
    private ArrayList<String> propositions;
    private String bonneReponse;
    private int nbReponse;

    public Question(String intitule, int nbreponse) {
        this.intitule = intitule;
        propositions = new ArrayList<> ();
        nbReponse = nbreponse;
    }
    public boolean verifierReponse(String verifReponse) {
        return bonneReponse.equals(verifReponse);
    }
    public void addProposition(String nouvelleproposition) {
        propositions.add(nouvelleproposition);
    }
    public String getIntitule() {
        return intitule;
    }
    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }
    public ArrayList<String> getPropositions() {
        return propositions;
    }
    public void setPropositions(ArrayList<String> propositions) {
        this.propositions = propositions;
    }
    public String getBonneReponse() {
        return bonneReponse;
    }
    public void setBonneReponse(String bonneReponse) {
        this.bonneReponse = bonneReponse;
    }
    public int getNbReponse(){
        return nbReponse;
    }

}
