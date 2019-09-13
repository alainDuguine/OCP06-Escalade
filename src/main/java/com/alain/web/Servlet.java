package com.alain.web;

import com.alain.EntityManagerUtil;
import com.alain.dao.contract.EntityRepository;
import com.alain.dao.entities.*;
import com.alain.dao.impl.*;
import com.alain.metier.CheckForm;
import com.alain.metier.SpotResearchDto;
import com.alain.metier.Utilities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Servlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger("Servlet");

    @Override
    public void init(){
        logger.info("Initialisation EntityManager");
        EntityManagerUtil entityManagerUtil = new EntityManagerUtil();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getServletPath();
        switch (path) {

            /* *********************************************************************************************
             **** Index      *******************************************************************************
             *********************************************************************************************** */
            case "/index.do": {
                this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
                break;
            }

            /* *********************************************************************************************
             **** Gestion Utilisateur      *****************************************************************
             *********************************************************************************************** */

            case "/inscription.do": {
                this.getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(req, resp);
                break;
            }
            case "/connexion.do": {
                Cookie[] cookies = req.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("email")) {
                            logger.info("Récupération cookie : " + cookie.toString());
                            req.setAttribute("cookieEmail", cookie.getValue());
                        }
                    }
                }
                this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(req, resp);
                break;
            }
            case "/connexionForm.do":
            case "/dashboard.do": {
                HttpSession session = req.getSession();
                String username = (String) session.getAttribute("sessionUtilisateur");
                if (username == null) {
                    this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(req, resp);
                }if(session.getAttribute("admin").equals(true)){

                    logger.info("Chargement dashboard Administrateur");
                    // Si l'utilisateur est admin on charge chaque élément séparément, pour avoir accès à tout.

                    UtilisateurDaoImpl utilisateurDao = new UtilisateurDaoImpl();
                    List<Utilisateur> listUtilisateur = utilisateurDao.findAll();

                    SpotDaoImpl spotDao = new SpotDaoImpl();
                    List<Spot> listSpot = spotDao.findAll();

                    SecteurDaoImpl secteurDao = new SecteurDaoImpl();
                    List<Secteur> listSecteur = secteurDao.findAll();

                    VoieDaoImpl voieDao = new VoieDaoImpl();
                    List<Voie> listVoie = voieDao.findAll();

                    TopoDaoImpl topoDao = new TopoDaoImpl();
                    List<Topo> listTopo = topoDao.findAll();

                    ReservationDaoImpl reservationDao = new ReservationDaoImpl();
                    List<Reservation> listReservations = reservationDao.findAll();

                    req.setAttribute("listUtilisateur", listUtilisateur);
                    req.setAttribute("listSpot", listSpot);
                    req.setAttribute("listSecteur", listSecteur);
                    req.setAttribute("listVoie", listVoie);
                    req.setAttribute("listTopo", listTopo);
                    req.setAttribute("listReservations", listReservations);
                }else{
                    // Si l'utilisateur n'est pas admin, on charge simplement l'utilisateur.
                    // Tous les objets le concernant seront dans son graphe d'objets;
                    UtilisateurDaoImpl utilisateurDao = new UtilisateurDaoImpl();
                    Utilisateur utilisateur = utilisateurDao.findByUsername(username);
                    req.setAttribute("utilisateur", utilisateur);
                }
                this.getServletContext().getRequestDispatcher("/WEB-INF/restricted/dashboard.jsp").forward(req, resp);
                break;
            }
            case "/deconnexion.do":{
                logger.info("Déconnexion : " + req.getSession().getAttribute("sessionUtilisateur"));
                HttpSession session = req.getSession();
                session.invalidate();
                resp.sendRedirect("index.do");
                break;
            }

            /* *********************************************************************************************
             **** Gestion Entités Spots, secteurs, voies    ************************************************
             *********************************************************************************************** */

            case "/modifierSpot.do":
            case "/updateSpot.do":
            case "/ajoutSpot.do":
            case "/saveSpot.do": {
                DepartementDaoImpl departementDao = new DepartementDaoImpl();
                List<Departement> departements = departementDao.findAll();
                req.setAttribute("departements", departements);
                if (path.equals("/modifierSpot.do") || path.equals("/updateSpot.do")){
                    SpotDaoImpl spotDao = new SpotDaoImpl();
                    Spot spot = spotDao.findOne(Long.parseLong(req.getParameter("idSpot")));
                    // Si le spot n'existe pas, ou si l'utilisateur n'a pas créé le spot et qu'il n'est pas admin, on envoie une page d'erreur
                    if (spot == null || (!spot.getUtilisateur().getUsername().equals(req.getSession().getAttribute("sessionUtilisateur"))) && (req.getSession().getAttribute("admin").equals(false))) {
                        logger.warn("Tentative de modification non autorisée : " + req.getSession().getAttribute("sessionUtilisateur"));
                        this.getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(req, resp);
                    }else{
                        req.setAttribute("spot", spot);
                        this.setNoCache(resp);
                        this.getServletContext().getRequestDispatcher("/WEB-INF/restricted/modifierSpot.jsp").forward(req, resp);
                    }
                }else {
                    this.getServletContext().getRequestDispatcher("/WEB-INF/restricted/ajoutSpot.jsp").forward(req, resp);
                }
                break;
            }
            case "/ajoutSecteur.do":
            case "/saveSecteur.do": {
                SpotDaoImpl spotDao = new SpotDaoImpl();
                Spot spot = spotDao.findOne(Long.parseLong(req.getParameter("idSpot")));
                if (spot == null){
                    this.getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(req, resp);
                }else{
                    req.setAttribute("spot", spot);
                    this.getServletContext().getRequestDispatcher("/WEB-INF/restricted/ajoutSecteur.jsp").forward(req, resp);
                }
                break;
            }
            case "/ajoutVoie.do":
            case "/saveVoie.do": {
                SecteurDaoImpl secteurDao = new SecteurDaoImpl();
                Secteur secteur = secteurDao.findOne(Long.parseLong(req.getParameter("idSecteur")));
                if (secteur == null) {
                    this.getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(req, resp);
                } else {
                    CotationDaoImpl cotationDao = new CotationDaoImpl();
                    List<Cotation> cotations = cotationDao.findAll();
                    req.setAttribute("cotations", cotations);
                    req.setAttribute("secteur", secteur);
                    this.getServletContext().getRequestDispatcher("/WEB-INF/restricted/ajoutVoie.jsp").forward(req, resp);
                }
                break;
            }
            case "/ajoutTopo.do":
            case "/saveTopo.do":{
                this.getServletContext().getRequestDispatcher("/WEB-INF/restricted/ajoutTopo.jsp").forward(req, resp);
                break;
            }
            case "/modifierSecteur.do":
            case "/updateSecteur.do":{
                SecteurDaoImpl secteurDao = new SecteurDaoImpl();
                Secteur secteur = secteurDao.findOne(Long.parseLong(req.getParameter("idSecteur")));
                if (secteur == null || (!secteur.getUtilisateur().getUsername().equals(req.getSession().getAttribute("sessionUtilisateur"))) && (req.getSession().getAttribute("admin").equals(false))) {
                    logger.warn("Tentative de modification non autorisée : " + req.getSession().getAttribute("sessionUtilisateur"));
                    this.getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(req, resp);
                }else {
                    req.setAttribute("secteur", secteur);
                    this.setNoCache(resp);
                    this.getServletContext().getRequestDispatcher("/WEB-INF/restricted/modifierSecteur.jsp").forward(req, resp);
                }
                break;
            }
            case "/modifierVoie.do":
            case "/updateVoie.do":{
                VoieDaoImpl voieDao = new VoieDaoImpl();
                Voie voie = voieDao.findOne(Long.parseLong(req.getParameter("idVoie")));
                if (voie == null ||(!voie.getUtilisateur().getUsername().equals(req.getSession().getAttribute("sessionUtilisateur"))) && (req.getSession().getAttribute("admin").equals(false))){
                    logger.warn("Tentative de modification non autorisée : " + req.getSession().getAttribute("sessionUtilisateur"));
                    this.getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(req, resp);
                }else {
                    CotationDaoImpl cotationDao = new CotationDaoImpl();
                    List<Cotation> cotations = cotationDao.findAll();
                    req.setAttribute("cotations", cotations);
                    req.setAttribute("voie", voie);
                    this.setNoCache(resp);
                    this.getServletContext().getRequestDispatcher("/WEB-INF/restricted/modifierVoie.jsp").forward(req, resp);
                }
                break;
            }


            /* *********************************************************************************************
             **** Gestion Topo *****************************************************************************
             *********************************************************************************************** */

            case "/saveTopoSpot.do":
            case "/ajoutTopoSpot.do":{
                HttpSession session = req.getSession();
                String username = (String) session.getAttribute("sessionUtilisateur");
                SpotDaoImpl spotDao = new SpotDaoImpl();
                Spot spot = spotDao.findOne(Long.parseLong(req.getParameter("idSpot")));
                if (spot == null || username == null){
                    this.getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(req, resp);
                }else{
                    if(session.getAttribute("admin").equals(true)){
                        logger.info("Ajout topo demandé par administrateur");
                        TopoDaoImpl topoDao = new TopoDaoImpl();
                        List<Topo> listTopo = topoDao.findAll();
                        req.setAttribute("listTopo", listTopo);
                    }else {
                        req.setAttribute("spot", spot);
                        UtilisateurDaoImpl utilisateurDao = new UtilisateurDaoImpl();
                        Utilisateur utilisateur = utilisateurDao.findByUsername(username);
                        req.setAttribute("utilisateur", utilisateur);
                    }
                    this.getServletContext().getRequestDispatcher("/WEB-INF/restricted/selectionTopo.jsp").forward(req, resp);
                }
                break;
            }
            case "/modifierTopo.do":{
                TopoDaoImpl topoDao = new TopoDaoImpl();
                Topo topo = topoDao.findOne(Long.parseLong(req.getParameter("idTopo")));
                // Seul l'administrateur ou le propriétaire du topo peut le modifier
                if (topo == null ||(!topo.getUtilisateur().getUsername().equals(req.getSession().getAttribute("sessionUtilisateur"))) && (req.getSession().getAttribute("admin").equals(false))){
                    this.getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(req, resp);
                }else {
                    req.setAttribute("topo", topo);
                    this.setNoCache(resp);
                    this.getServletContext().getRequestDispatcher("/WEB-INF/restricted/modifierTopo.jsp").forward(req, resp);
                }
                break;
            }

            /* *********************************************************************************************
             **** Affichage Spots, Topos *******************************************************************
             *********************************************************************************************** */

            case "/listeSpot.do":
            case "/rechercheSpot.do": {
                SpotDaoImpl spotDao = new SpotDaoImpl();
                List<SpotResearchDto> spots = spotDao.findAllForResearch();
                TreeMap<String, String> listDepartementSort = Utilities.getDepartementSortedFromList(spots);
                CotationDaoImpl cotationDao = new CotationDaoImpl();
                List<Cotation> cotations = cotationDao.findAll();
                req.setAttribute("cotations", cotations);
                req.setAttribute("listDepartement", listDepartementSort);
                if (path.equals("/listeSpot.do")) {
                    req.setAttribute("spots", spots);
                }
                this.getServletContext().getRequestDispatcher("/WEB-INF/rechercheSpot.jsp").forward(req, resp);
                break;
            }
            case "/display.do": {
                SpotDaoImpl spotDao = new SpotDaoImpl();
                Spot spot = spotDao.findOne(Long.parseLong(req.getParameter("idSpot")));
                if (spot != null) {
                    CommentaireSpotDaoImpl commentaireSpotDao = new CommentaireSpotDaoImpl();
                    spot.setCommentaires(commentaireSpotDao.findAllInSpot(spot.getId()));
                    req.setAttribute("spot", spot);
                    this.getServletContext().getRequestDispatcher("/WEB-INF/display.jsp").forward(req, resp);
                } else {
                    this.getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(req, resp);
                }
                break;
            }
            case "/listeTopo.do":{
                TopoDaoImpl topoDao = new TopoDaoImpl();
                List<Topo> topos = topoDao.findAll();
                req.setAttribute("topos", topos);
                this.getServletContext().getRequestDispatcher("/WEB-INF/restricted/listeTopo.jsp").forward(req, resp);
            }

            /* *********************************************************************************************
             **** Appels AJAX  *****************************************************************************
             *********************************************************************************************** */

            case "/choixDepartement.do": {
                Gson gson = new Gson();
                VilleDaoImpl dao = new VilleDaoImpl();
                String codeDep = req.getParameter("codeDep");
                List<Ville> villes = dao.findAllInDep(codeDep);
                String villesJsonString = gson.toJson(villes);
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(villesJsonString);
                out.flush();
                break;
            }
            case "/choixVille.do": {
                Gson gson = new Gson();
                SpotDaoImpl spotDao = new SpotDaoImpl();
                String codeDep = req.getParameter("codeDep");
                List<String> villes = spotDao.findVilleInDepHavingSpot(codeDep);
                String villesJsonString = gson.toJson(villes);
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(villesJsonString);
                out.flush();
                break;
            }
        }
    }



    /* =================================================================================================================
    ========  POST  ====================================================================================================
    ================================================================================================================= */


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {

            /* *********************************************************************************************
             **** Gestion Utilisateur      *****************************************************************
             *********************************************************************************************** */
            case "/connexionForm.do": {
                logger.info("Connexion nouvel utilisateur");
                UtilisateurDaoImpl utilisateurDaoImpl = new UtilisateurDaoImpl();
                CheckForm form = new CheckForm();
                form.checkConnect(req, utilisateurDaoImpl);
                req.setAttribute("form", form);
                if (form.isResultat()) {
                    if (req.getParameter("cookie") != null){
                        Cookie cookieEmail = new Cookie("email", req.getParameter("email"));
                        cookieEmail.setMaxAge(60*60*24*30);
                        resp.addCookie(cookieEmail);
                        logger.info("Création de cookie : " + cookieEmail.toString());
                    }
                    HttpSession session = req.getSession();
                    String username = ((Utilisateur) form.getEntitie()).getUsername();
                    Boolean admin = ((Utilisateur) form.getEntitie()).isAdmin();
                    session.setAttribute("sessionUtilisateur", username);
                    session.setAttribute("admin", admin);
                    logger.info("Connexion réussie avec variable de session : " + username);
                    doGet(req,resp);
                } else {
                    this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(req, resp);
                }
                break;
            }
            case "/saveUser.do": {
                logger.info("Création nouvel utilisateur");
                UtilisateurDaoImpl utilisateurDaoImpl = new UtilisateurDaoImpl();
                CheckForm form = new CheckForm();
                form.checkAndSave(req, "com.alain.dao.entities.Utilisateur", utilisateurDaoImpl);
                req.setAttribute("form", form);
                this.getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(req, resp);
                break;
            }
            // switch des droits administrateurs pour un utilisateur
            case "/toggleAdmin.do":{
                Long idUser = Long.parseLong(req.getParameter("idUser"));
                UtilisateurDaoImpl utilisateurDao = new UtilisateurDaoImpl();
                Utilisateur utilisateur = utilisateurDao.findOne(idUser);
                logger.info("Changement droits administrateurs utilisateur : " + utilisateur.getId());
                utilisateur.setAdmin(!utilisateur.isAdmin());
                utilisateurDao.update(utilisateur, req);
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(utilisateur.isAdmin());
                out.flush();
                break;
            }

            /* *********************************************************************************************
             **** Gestion Entités Spots, secteurs, voies, topos  *******************************************
             *********************************************************************************************** */

            case "/saveSpot.do":
            case "/saveSecteur.do":
            case "/saveVoie.do":
            case "/saveTopo.do":{
                EntityRepository dao, daoPhoto = null;
                String className, photoClassName = null;
                if (path.contains("Spot")){
                    dao = new SpotDaoImpl();
                    daoPhoto = new PhotoSpotDaoImpl();
                    className = "com.alain.dao.entities.Spot";
                    photoClassName = "com.alain.dao.entities.PhotoSpot";
                } else if (path.contains("Secteur")) {
                    dao = new SecteurDaoImpl();
                    daoPhoto = new PhotoSecteurDaoImpl();
                    className = "com.alain.dao.entities.Secteur";
                    photoClassName = "com.alain.dao.entities.PhotoSecteur";
                } else if (path.contains("Voie")){
                    dao = new VoieDaoImpl();
                    daoPhoto = new PhotoVoieDaoImpl();
                    className = "com.alain.dao.entities.Voie";
                    photoClassName = "com.alain.dao.entities.PhotoVoie";
                } else{
                    dao = new TopoDaoImpl();
                    className = "com.alain.dao.entities.Topo";
                }
                logger.info("Requête sauvegarde nouvel objet : " + className);
                CheckForm form = new CheckForm();
                form.checkAndSave(req, className, dao);
                if (form.isResultat() && !path.contains("Topo")){
                    Long id = form.getEntitie().getId();
                    req.setAttribute("idElement", id);
                    System.out.println(req.getAttribute("idElement"));
                    form.checkAndSavePhoto(req, photoClassName, daoPhoto);
                }
                req.setAttribute("form", form);
                if (form.isResultat()) {
                    resp.sendRedirect("/dashboard.do?resultat=true");
                } else {
                    logger.info("Sauvegarde échouée : " + form.getListErreurs().toString());
                    doGet(req, resp);
                }
                break;
            }
            case "/updateSpot.do":
            case "/updateSecteur.do":
            case "/updateVoie.do":
            case "/updateTopo.do":{
                Long idElement = Long.parseLong(req.getParameter("idElement"));
                EntityRepository dao, daoPhoto = null;
                String photoClassName = null;
                if (path.contains("Spot")){
                    dao = new SpotDaoImpl();
                    daoPhoto = new PhotoSpotDaoImpl();
                    photoClassName = "com.alain.dao.entities.PhotoSpot";
                } else if (path.contains("Secteur")) {
                    dao = new SecteurDaoImpl();
                    daoPhoto = new PhotoSecteurDaoImpl();
                    photoClassName = "com.alain.dao.entities.PhotoSecteur";
                }else if (path.contains("Voie")){
                    dao = new VoieDaoImpl();
                    daoPhoto = new PhotoVoieDaoImpl();
                    photoClassName = "com.alain.dao.entities.PhotoVoie";
                }else{
                    dao = new TopoDaoImpl();
                }
                logger.info("Requête modification objet : " + dao + ", " + daoPhoto + ", " + idElement);
                CheckForm form = new CheckForm();
                form.checkAndUpdate(req, dao, idElement);
                if (form.isResultat() && !path.contains("Topo")){
                    req.setAttribute("idElement", idElement);
                    form.checkAndSavePhoto(req, photoClassName, daoPhoto);
                }
                req.setAttribute("form", form);
                if (form.isResultat()) {
                    resp.sendRedirect("/dashboard.do?resultat=true");
                } else {
                    logger.info("Modification échouée : "+ form.getListErreurs().toString());
                    doGet(req, resp);
                }
                break;
            }
            case "/supprimerSpot.do":
            case "/supprimerSecteur.do":
            case "/supprimerVoie.do": {
                Long idElement = Long.parseLong(req.getParameter("idElement"));
                EntityRepository dao;
                if (path.contains("Spot")) {
                    dao = new SpotDaoImpl();
                } else if (path.contains("Secteur")) {
                    dao = new SecteurDaoImpl();
                } else {
                    dao = new VoieDaoImpl();
                }
                logger.info("Requête suppression objet : "+ dao + ", id : " + idElement);
                Boolean result = dao.delete(idElement);
                this.sendAjaxBooleanResponse(result, resp);
                break;
            }
            // Recherche multi-critères
            case "/rechercheSpot.do": {
                SpotDaoImpl spotDao = new SpotDaoImpl();
                logger.info("Recherche multi-critères de spots demandée");
                Map<String, Object> paramMap = Utilities.getParameterMapFromReq(req);
                List<SpotResearchDto> spots = spotDao.findSpotPersonalResearch(paramMap);
                logger.info("Nombre de Résultats : " + spots.size());
                req.setAttribute("spots", spots);
                doGet(req, resp);
                break;
            }

            /* *********************************************************************************************
             **** Suppression Eléments Ajax ****************************************************************
             *********************************************************************************************** */

            case "/supprimerUser.do":
            case "/supprimerCommentaire.do":
            case "/supprimerPhoto.do":
            case "/supprimerTopo.do":
            case "/supprimerReservation.do":{
                Long idElement = Long.parseLong(req.getParameter("idElement"));
                EntityRepository dao;
                if (path.contains("User")) {
                    dao = new UtilisateurDaoImpl();
                } else if (path.contains("Commentaire")) {
                    dao = new CommentaireSpotDaoImpl();
                } else if (path.contains("Photo")){
                    String url = req.getParameter("jspUrl");
                    if (url.contains("Spot")){
                        dao = new PhotoSpotDaoImpl();
                    }else if (url.contains("Secteur")){
                        dao = new PhotoSecteurDaoImpl();
                    }else{
                        dao = new PhotoVoieDaoImpl();
                    }
                } else if (path.contains("Topo")){
                    dao = new TopoDaoImpl();
                } else {
                    dao = new ReservationDaoImpl();
                }
                logger.info("Requête suppression Ajax objet : "+ dao + ", id : " + idElement);
                Boolean result = dao.delete(idElement);
                this.sendAjaxBooleanResponse(result, resp);
                break;
            }

            /* *********************************************************************************************
             **** Gestion commentaires *********************************************************************
             *********************************************************************************************** */

            case "/saveCommentaire.do": {
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                CommentaireSpotDaoImpl commentaireDao = new CommentaireSpotDaoImpl();
                logger.info("Requête AJAX sauvegarde nouveau commentaire");
                CheckForm form = new CheckForm();
                form.checkAndSave(req, "com.alain.dao.entities.CommentaireSpot", commentaireDao);
                if (form.isResultat()) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    CommentaireSpot commentaire = (CommentaireSpot) form.getEntitie();
                    logger.info("Sauvegarde commentaire réussie : " + commentaire.getId());
                    String json = gson.toJson(commentaire);
                    PrintWriter out = resp.getWriter();
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    out.print(json);
                    out.flush();
                }
                break;
            }
            case "/updateCommentaire.do":{
                CommentaireSpotDaoImpl commentaireSpotDao = new CommentaireSpotDaoImpl();
                logger.info("Requête AJAX modification commentaire : " + req.getParameter("idCommentaire"));
                CheckForm form = new CheckForm();
                form.checkAndUpdate(req, commentaireSpotDao, Long.parseLong(req.getParameter("idCommentaire")));
                Map<String, String> ajaxReturn = new HashMap<>();
                ajaxReturn.put("resultat", String.valueOf(form.isResultat()));
                if (!form.isResultat()){
                    logger.info("Modification commentaire échouée : " + form.getListErreurs().toString());
                    ajaxReturn.put("erreur",form.getListErreurs().toString());
                }
                logger.info("Modification commentaire réussie");
                Gson gson = new Gson();
                String ajaxReturnString = gson.toJson(ajaxReturn);
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(ajaxReturnString);
                out.flush();
                break;
            }

            /* *********************************************************************************************
             **** Gestion des Réservations de topo  ********************************************************
             *********************************************************************************************** */

            case "/reserverTopo.do":{
                ReservationDaoImpl reservationDao = new ReservationDaoImpl();
                CheckForm form = new CheckForm();
                logger.info("Nouvelle demande de réservation topo");
                form.checkAndSave(req, "com.alain.dao.entities.Reservation", reservationDao);
                if (form.isResultat()) {
                    logger.info("Création demande de réservation réussie");
                    sendAjaxBooleanResponse(true, resp);
                }else{
                    logger.info("Création demande de réservation échouée : " + form.getListErreurs().toString());
                    Map<String, String> ajaxReturn = new HashMap<>();
                    ajaxReturn.put("erreur", String.valueOf(form.getListErreurs().get("erreur")));
                    Gson gson = new Gson();
                    String ajaxReturnString = gson.toJson(ajaxReturn);
                    PrintWriter out = resp.getWriter();
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    out.print(ajaxReturnString);
                    out.flush();
                }
                break;
            }
            case "/terminerReservation.do":
            case "/refuserPret.do":
            case "/accepterPret.do":{
                ReservationDaoImpl reservationDao = new ReservationDaoImpl();
                Reservation reservation = reservationDao.findOne(Long.parseLong(req.getParameter("idReservation")));
                boolean result;
                try {
                    logger.info("Modification statut réservation topo : " + reservation.getId());
                    reservationDao.update(reservation, req);
                    result = true;
                }catch (Exception e){
                    result = false;
                    logger.warn("Modification statut réservation échouée : " + e.getMessage());
                }
                this.sendAjaxBooleanResponse(result, resp);
                break;
            }
            // Switcher disponibilité des topos
            case "/toggleTopo.do":{
                Long idTopo = Long.parseLong(req.getParameter("idTopo"));
                logger.info("Modification disponibilité topo : " + idTopo);
                TopoDaoImpl topoDao = new TopoDaoImpl();
                Topo topo = topoDao.findOne(idTopo);
                topo.setDisponible(!topo.isDisponible());
                topoDao.update(topo, req);
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(topo.isDisponible());
                out.flush();
                break;
            }
            /* *********************************************************************************************
             **** Gestion des associations entre spot et topo  *********************************************
             *********************************************************************************************** */

            case "/saveTopoSpot.do":{
                Boolean result = false;
                TopoDaoImpl topoDao = new TopoDaoImpl();
                String[] idTopos = req.getParameterValues("checkTopo");
                Long idSpot = Long.parseLong(req.getParameter("idSpot"));
                logger.info("Demande d'association des topos : " + Arrays.toString(idTopos) + " au spot : " + idSpot);
                if (idTopos.length != 0) {
                    for (String idTopo : idTopos) {
                        result = topoDao.addSpotInTopo(Long.parseLong(idTopo), idSpot);
                    }
                }
                if (result) {
                    logger.info("Associations réussies");
                    resp.sendRedirect("/display.do?idSpot="+idSpot+"&resultat=true");
                } else {
                    logger.info("Associations échouées.");
                    doGet(req, resp);
                }
                break;
            }
            case "/supprimerSpotInTopo.do":{
                TopoDaoImpl topoDao = new TopoDaoImpl();
                Long idTopo = Long.parseLong(req.getParameter("idTopo"));
                Long idSpot = Long.parseLong(req.getParameter("idSpot"));
                logger.info("Demande de suppression d'association du topo : " + idTopo+ " au spot : " + idSpot);
                Boolean result = topoDao.deleteSpotFromTopo(idTopo, idSpot);
                this.sendAjaxBooleanResponse(result, resp);
                break;
            }
        }
    }

    /**
     * Renvoie une réponse booléenne AJAX
     * @param result
     * @param resp
     * @throws IOException
     */
    private void sendAjaxBooleanResponse(Boolean result, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(result);
        out.flush();
    }

    /**
     * Ajoute des en-tête no cache à une jsp
     * @param resp
     */
    private void setNoCache(HttpServletResponse resp) {
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
    }
}
