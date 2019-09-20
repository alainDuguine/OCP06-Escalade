package com.alain.dao.impl;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.*;
import com.alain.metier.SpotResearchDto;
import com.alain.metier.Utilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class SpotDaoImpl implements EntityRepository<Spot> {

    private EntityManager entityManager = EntityManagerUtil.getEntityManager();
    private static final Logger logger = LogManager.getLogger("SpotDaoImpl");

    /**
     * Enregistre un spot en base de données
     * ajoute les associations avec le département, la ville et l'utilisateur,
     * @param spot à enregistrer
     * @param req requête http
     * @return l'objet spot
     */
    @Override
    public Spot save(Spot spot, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de sauvegarde d'un Spot");
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
            logger.info("Sauvegarde spot réussie  : " + spot.getId());
        } catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Sauvegarde spot échouée : " + Arrays.toString(e.getStackTrace()));
            throw e;
        }
        return spot;
    }

    /**
     * Modifie un spot en base de données
     * @param spot à modifier
     * @param req requête http
     * @return l'objet spot
     */
    @Override
    public Spot update(Spot spot, HttpServletRequest req) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de modification d'un Spot " + spot.getId());
            transaction.begin();
            Departement departement = entityManager.find(Departement.class, req.getParameter("departement"));
            Ville ville = entityManager.find(Ville.class, Long.parseLong(req.getParameter("ville")));
            spot.setDepartement(departement);
            spot.setVille(ville);
            entityManager.merge(spot);
            transaction.commit();
            logger.info("Modification spot réussie " + spot.getId());
        } catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Modification spot échouée : " + Arrays.toString(e.getStackTrace()));
            throw e;
        }
        return spot;
    }

    /**
     * Supprime un spot en base de données
     * supprime les associations avec le département, la ville, l'utilisateur et les topos
     * @param id du secteur
     * @return booléen
     */
    @Override
    public boolean delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            logger.info("Tentative de suppression d'un spot " + id);
            transaction.begin();
            Spot spot = entityManager.find(Spot.class, id);
            spot.getUtilisateur().removeSpot(spot);
            spot.getDepartement().removeSpot(spot);
            spot.getVille().removeSpot(spot);
            spot.removeAllTopos();
            entityManager.remove(spot);
            entityManager.flush();
            transaction.commit();
            logger.info("Suppression spot réussie " + id);
            return true;
        }catch (Exception e){
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
            logger.error("Suppression spot échouée : " + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    @Override
    public List<Spot> findAll() {
        logger.info("Recherche de tous les spots");
        Query query = entityManager.createQuery("select spot from Spot spot order by spot.nom asc");
        return query.getResultList();
    }

    @Override
    public Spot findOne(Long id) {
        logger.info("Recherche du spot " + id);
        return entityManager.find(Spot.class, id);
    }

    /**
     * Retourne l'ensemble des spots enregistrés dans le départeement
     * @return liste des résultats
     */
    public List<Spot> findSpotInDepartement(String nomSpot, String departement) {
        logger.info("Recherche du spot "+ nomSpot +" dans le département " + departement);
        Query query = entityManager.createQuery("select s from Spot s where s.nom= :nom and s.departement.code= :departement");
        query.setParameter("nom", nomSpot );
        query.setParameter("departement", departement);
        return query.getResultList();
    }

    /**
     * Retourne l'ensemble des spots existants sous la forme SpotResearchDto
     * @return liste des résultats
     */
    public List<SpotResearchDto> findAllForResearch(){
        logger.info("Recherche des spots sous la forme SpotResearchDto");
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
        logger.info("Démarrage requête personnalisée");
        List<SpotResearchDto> list = null;
        // nom des attributs dans la base de données
        String[] AttributList = {"spot.nom", "spot.officiel", "spot.departement.code", "spot.ville.nom", "min(cotations.id)", "max(cotations.id)", "spot.secteurs.size", "spot.secteurs.size"};
        // Map qui contiendra les paramètres à injecter (nom et valeur)
        Map <String, Object> paramInReq = new HashMap<>();
        StringBuffer builtQuery = new StringBuffer("select new com.alain.metier.SpotResearchDto(spot.id, spot.nom, spot.departement.code, spot.departement.nom, spot.ville.nom, spot.secteurs.size, min(cotations.id), max(cotations.id), spot.officiel) FROM Spot spot left join spot.secteurs secteurs left join secteurs.voies voies left join voies.cotation cotations");
        logger.info("Base requête : " + builtQuery.toString());

        // Ajout des clauses WHERE
        logger.info("Ajout des clauses WHERE");
        boolean first = true;
        for (int i=0; i <= 3;i++) {
            if ((paramReq.get(Utilities.paramList[i]) != null) && !(paramReq.get(Utilities.paramList[i]).equals(false)) && !(paramReq.get(Utilities.paramList[i]).equals(""))) {
                builtQuery.append(first ? " where " : " and ");
                if (Utilities.paramList[i].contains("nom")){
                    builtQuery.append("lower(" + AttributList[i] + ") like lower(concat('%', :" + Utilities.paramList[i]+", '%'))");
                    logger.info("lower(" + AttributList[i] + ") like lower(concat('%', :" + Utilities.paramList[i]+", '%'))");
                }else {
                    builtQuery.append(AttributList[i] + " = :" + Utilities.paramList[i]);
                    logger.info(AttributList[i] + " = :" + Utilities.paramList[i]);
                }
                paramInReq.put(Utilities.paramList[i], paramReq.get(Utilities.paramList[i]));
                first = false;
            }
        }

        // Ajout des clauses GROUP BY
        builtQuery.append(" group by spot.id, spot.departement.nom, spot.ville.nom");
        logger.info("Ajout des clauses GROUP BY");
        logger.info("group by spot.id, spot.departement.nom, spot.ville.nom");

        // Ajout des clauses HAVING
        logger.info("Ajout des clauses HAVING");
        first = true;
        for (int i = 4; i < Utilities.paramList.length; i++ ){
            if ((paramReq.get(Utilities.paramList[i]) != null) && !(paramReq.get(Utilities.paramList[i]).equals(""))) {
                builtQuery.append(first ? " having " : " and ");
                if (Utilities.paramList[i].contains("Min")){
                    builtQuery.append(AttributList[i] + " >= :" + Utilities.paramList[i]);
                    logger.info(AttributList[i] + " >= :" + Utilities.paramList[i]);
                }else{
                    builtQuery.append(AttributList[i] + " <= :" + Utilities.paramList[i]);
                    logger.info(AttributList[i] + " <= :" + Utilities.paramList[i]);
                }
                paramInReq.put(Utilities.paramList[i], paramReq.get(Utilities.paramList[i]));
                first = false;
            }
        }

        // Ajout des clauses order by
        builtQuery.append(" order by spot.nom");
        logger.info("Ajout des clauses order by");

        Query query = entityManager.createQuery(builtQuery.toString());

        // Injection des paramètres
        logger.info("Injection des paramètres");
        Iterator<String> iter = paramInReq.keySet().iterator();
        while (iter.hasNext()){
            String name = iter.next();
            Object value = paramInReq.get(name);
            query.setParameter(name, value);
            logger.info("Paramètre : " + name + " - " + value);
        }
        logger.info("Résultats " + query.getResultList().size());
        return query.getResultList();
    }

    /**
     * retourne le nom des villes des spots présents dans le département (pour chargement dans la list déroulante du formulaire de recherche
     * @param codeDep code du département
     * @return liste des noms de ville
     */
    public List<String> findVilleInDepHavingSpot(String codeDep) {
        logger.info("Recherche des villes dans un département donné comportant au moins un spot");
        Query query = entityManager.createQuery("select spot.ville.nom from Spot spot where spot.departement.code= :codeDep ORDER BY spot.ville.nom ASC ");
        query.setParameter("codeDep", codeDep);
        return query.getResultList();
    }

    /**
     * Recherche un spot par nom dans un départemment ayant un identifiant différent du secteur
     * (Utile pour la modification d'un spot, afin de s'assurer qu'on ne donne pas le nom d'un spot déjà présent dans le département)
     * @param id identifiant du spot
     * @param nomSpot nom du spot
     * @param departement nom du département
     * @return liste des secteurs
     */
    public List<Spot> findSpotInDepartementForUpdate(Long id, String nomSpot, String departement) {
        logger.info("Recherche des villes dans un département pour modification");
        Query query = entityManager.createQuery("select s from Spot s where s.nom= :nom and s.departement.code= :departement and s.id <> :id");
        query.setParameter("nom", nomSpot );
        query.setParameter("departement", departement);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
