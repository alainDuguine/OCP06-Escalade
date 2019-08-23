package com.alain.dao.entities;

import com.alain.dao.contract.EntityRepository;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public abstract class Entitie {
    /**
     * Valorise les attributs de l'objet entité depuis la requête http
     * @param req httpServletRequest
     */
    public abstract void hydrate(HttpServletRequest req);

    /**
     * Vérifie les erreurs au sein de l'objet entité
     * @param dao nécessaire pour rechercher les paramètres déjà présents
     * @param req httpServletRequest
     * @return une map d'erreur <Champ, valeur>
     */
    public abstract Map<String, String> checkErreurs(EntityRepository dao, HttpServletRequest req);
}
