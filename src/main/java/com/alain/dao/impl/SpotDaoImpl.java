package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.*;
import com.alain.metier.SpotResearchDto;
import com.alain.metier.Utilities;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SpotDaoImpl implements EntityRepository<Spot> {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();

    @Override
    public Spot save(Spot spot, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            UtilisateurDaoImpl utilisateurDao = new UtilisateurDaoImpl();
            Utilisateur utilisateur = utilisateurDao.findByUsername((String) req.getSession().getAttribute("sessionUtilisateur"));
            Departement departement = entityManager.find(Departement.class, req.getParameter("departement"));
            Ville ville = entityManager.find(Ville.class, Long.parseLong(req.getParameter("ville")));
            spot.setDepartement(departement);
            spot.setVille(ville);
            spot.setUtilisateur(utilisateur);
            entityManager.persist(spot);
            transaction.commit();
        } catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return spot;
    }

    @Override
    public Spot update(Spot spot, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Departement departement = entityManager.find(Departement.class, req.getParameter("departement"));
            Ville ville = entityManager.find(Ville.class, Long.parseLong(req.getParameter("ville")));
            spot.setDepartement(departement);
            spot.setVille(ville);
            entityManager.merge(spot);
            transaction.commit();
        } catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
        return spot;
    }

    @Override
    public boolean delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Spot spot = entityManager.find(Spot.class, id);
            spot.removeAllTopos();
            entityManager.remove(spot);
            entityManager.flush();
            transaction.commit();
            return true;
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Spot> findAll() {
        Query query = entityManager.createQuery("select spot from Spot spot order by spot.nom asc");
        return query.getResultList();
    }

    @Override
    public Spot findOne(Long id) {
        return entityManager.find(Spot.class, id);
    }

    public List<Spot> findSpotInDepartement(String nomSpot, String departement) {
        Query query = entityManager.createQuery("select s from Spot s where s.nom= :nom and s.departement.code= :departement");
        query.setParameter("nom", nomSpot );
        query.setParameter("departement", departement);
        return query.getResultList();
    }

    /**
     * Retourne l'ensemble des spots existants sous la forme SpotResearchDto
     * @return
     */
    public List<SpotResearchDto> findAllForResearch(){
        Query query = entityManager.createQuery("select new com.alain.metier.SpotResearchDto(spot.id, spot.nom, spot.departement.code, spot.departement.nom, spot.ville.nom, spot.secteurs.size, min(cotations.id), max(cotations.id), spot.officiel)" +
                "        FROM Spot spot\n" +
                "        left join spot.secteurs secteurs\n" +
                "        left join secteurs.voies voies\n" +
                "        left join voies.cotation cotations\n" +
                "        group by spot.id, spot.departement.nom, spot.ville.nom" +
                "        order by spot.nom");
        return query.getResultList();
    }

    /**
     * permet d'exécuter une requete personnnalisé via le formulaire de recherche rechercheSpot.do
     * @param paramReq map des paramètres de la requête (générée par Utilities.getParameterMapFromReq(HttpServletRequest req))
     * @return le resultSet de la requête sous forme d'objets SpotResearchDto
     */
    public List<SpotResearchDto> findSpotPersonalResearch(Map<String, Object> paramReq){
        List<SpotResearchDto> list = null;
        // nom des attributs dans la base de données
        String[] AttributList = {"spot.nom", "spot.officiel", "spot.departement.code", "spot.ville.nom", "min(cotations.id)", "max(cotations.id)", "spot.secteurs.size", "spot.secteurs.size"};
        // Map qui contiendra les paramètres à injecter (nom et valeur)
        Map <String, Object> paramInReq = new HashMap<>();
        StringBuffer builtQuery = new StringBuffer("select new com.alain.metier.SpotResearchDto(spot.id, spot.nom, spot.departement.code, spot.departement.nom, spot.ville.nom, spot.secteurs.size, min(cotations.id), max(cotations.id), spot.officiel) FROM Spot spot left join spot.secteurs secteurs left join secteurs.voies voies left join voies.cotation cotations");

        // Adding the WHERE clauses
        boolean first = true;
        for (int i=0; i <= 3;i++) {
            if ((paramReq.get(Utilities.paramList[i]) != null) && !(paramReq.get(Utilities.paramList[i]).equals(false)) && !(paramReq.get(Utilities.paramList[i]).equals(""))) {
                builtQuery.append(first ? " where " : " and ");
                if (Utilities.paramList[i].contains("nom")){
                    builtQuery.append("lower(" + AttributList[i] + ") like lower(concat('%', :" + Utilities.paramList[i]+", '%'))");
                }else {
                    builtQuery.append(AttributList[i] + " = :" + Utilities.paramList[i]);
                }
                paramInReq.put(Utilities.paramList[i], paramReq.get(Utilities.paramList[i]));
                first = false;
            }
        }

        // Adding GROUP BY clause
        builtQuery.append(" group by spot.id, spot.departement.nom, spot.ville.nom");

        // Adding HAVING clauses
        first = true;
        for (int i = 4; i < Utilities.paramList.length; i++ ){
            if ((paramReq.get(Utilities.paramList[i]) != null) && !(paramReq.get(Utilities.paramList[i]).equals(""))) {
                builtQuery.append(first ? " having " : " and ");
                if (Utilities.paramList[i].contains("Min")){
                    builtQuery.append(AttributList[i] + " >= :" + Utilities.paramList[i]);
                }else{
                    builtQuery.append(AttributList[i] + " <= :" + Utilities.paramList[i]);
                }
                paramInReq.put(Utilities.paramList[i], paramReq.get(Utilities.paramList[i]));
                first = false;
            }
        }

        // Adding order by
        builtQuery.append(" order by spot.nom");

        Query query = entityManager.createQuery(builtQuery.toString());

        // Injecting parameters
        Iterator<String> iter = paramInReq.keySet().iterator();
        while (iter.hasNext()){
            String name = iter.next();
            Object value = paramInReq.get(name);
            query.setParameter(name, value);
        }
        return query.getResultList();
    }

    /**
     * retourne le nom des villes des spots présents dans le département (pour chargement dans la list déroulante du formulaire de recherche
     * @param codeDep code du département
     * @return liste des noms de ville
     */
    public List<String> findVilleInDepHavingSpot(String codeDep) {
        Query query = entityManager.createQuery("select spot.ville.nom from Spot spot where spot.departement.code= :codeDep ORDER BY spot.ville.nom ASC ");
        query.setParameter("codeDep", codeDep);
        return query.getResultList();
    }

    public List<Spot> findSpotInDepartementForUpdate(Long id, String nomSpot, String departement) {
        Query query = entityManager.createQuery("select s from Spot s where s.nom= :nom and s.departement.code= :departement and s.id <> :id");
        query.setParameter("nom", nomSpot );
        query.setParameter("departement", departement);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
