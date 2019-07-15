package com.alain.metier;

import com.alain.dao.entities.Entitie;

import java.util.Map;

public class CheckFormResult {

    private Entitie entitie;
    private Map<String,String> listErreurs;
    private String resultat;

    public Entitie getEntitie() {
        return entitie;
    }

    public void setEntitie(Entitie entitie) {
        this.entitie = entitie;
    }

    public Map<String, String> getListErreurs() {
        return listErreurs;
    }

    public void setListErreurs(Map<String, String> listErreurs) {
        this.listErreurs = listErreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }
}
