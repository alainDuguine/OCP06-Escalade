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

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Servlet extends HttpServlet {

    private static final String CONTEXT_PATH_PHOTO = "/imagesUsers/";
    private static final String REAL_PATH_PHOTO = "D:\\fichiers";

    @Override
    public void init() throws ServletException {
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
                    UtilisateurDaoImpl utilisateurDao = new UtilisateurDaoImpl();
                    List<Utilisateur> utilisateurs = utilisateurDao.findAll();
                    req.setAttribute("listUtilisateur", utilisateurs);
                    SpotDaoImpl spotDao = new SpotDaoImpl();
                    List<Spot> listSpot = spotDao.findAll();
                    SecteurDaoImpl secteurDao = new SecteurDaoImpl();
                    List<Secteur> listSecteur = secteurDao.findAll();
                    VoieDaoImpl voieDao = new VoieDaoImpl();
                    List<Voie> listVoie = voieDao.findAll();
                    req.setAttribute("listSpot", listSpot);
                    req.setAttribute("listSecteur", listSecteur);
                    req.setAttribute("listVoie", listVoie);
                }else{
                    UtilisateurDaoImpl utilisateurDao = new UtilisateurDaoImpl();
                    Utilisateur utilisateur = utilisateurDao.findByUsername(username);
                    req.setAttribute("utilisateur", utilisateur);
                }
                this.getServletContext().getRequestDispatcher("/WEB-INF/restricted/dashboard.jsp").forward(req, resp);
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
            case "/modifierSecteur.do":
            case "/updateSecteur.do":{
                SecteurDaoImpl secteurDao = new SecteurDaoImpl();
                Secteur secteur = secteurDao.findOne(Long.parseLong(req.getParameter("idSecteur")));
                if (secteur == null || (!secteur.getUtilisateur().getUsername().equals(req.getSession().getAttribute("sessionUtilisateur"))) && (req.getSession().getAttribute("admin").equals(false))) {
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
            case "/deconnexion.do":{
                HttpSession session = req.getSession();
                session.invalidate();
                resp.sendRedirect("index.do");
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            /* *********************************************************************************************
             **** Gestion Utilisateur      *****************************************************************
             *********************************************************************************************** */
            case "/connexionForm.do": {
                UtilisateurDaoImpl utilisateurDaoImpl = new UtilisateurDaoImpl();
                CheckForm form = new CheckForm();
                form.checkConnect(req, utilisateurDaoImpl);
                req.setAttribute("form", form);
                if (form.isResultat()) {
                    if (req.getParameter("cookie") != null){
                        Cookie cookieEmail = new Cookie("email", req.getParameter("email"));
                        cookieEmail.setMaxAge(60*60*24*30);
                        resp.addCookie(cookieEmail);
                    }
                    HttpSession session = req.getSession();
                    String username = ((Utilisateur) form.getEntitie()).getUsername();
                    Boolean admin = ((Utilisateur) form.getEntitie()).isAdmin();
                    session.setAttribute("sessionUtilisateur", username);
                    session.setAttribute("admin", admin);
                    doGet(req,resp);
                } else {
                    this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(req, resp);
                }
                break;
            }
            case "/saveUser.do": {
                UtilisateurDaoImpl utilisateurDaoImpl = new UtilisateurDaoImpl();
                CheckForm form = new CheckForm();
                form.checkAndSave(req, "com.alain.dao.entities.Utilisateur", utilisateurDaoImpl);
                req.setAttribute("form", form);
                this.getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(req, resp);
                break;
            }
            case "/toggleAdmin.do":{
                Long idUser = Long.parseLong(req.getParameter("idUser"));
                UtilisateurDaoImpl utilisateurDao = new UtilisateurDaoImpl();
                Utilisateur utilisateur = utilisateurDao.findOne(idUser);
                utilisateur.setAdmin(!utilisateur.isAdmin());
                utilisateurDao.update(utilisateur, req);
                PrintWriter out = resp.getWriter();
                out.print(utilisateur.isAdmin());
                out.flush();
                break;
            }
            /* *********************************************************************************************
             **** Gestion Entités Spots, secteurs, voies    ************************************************
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
                CheckForm form = new CheckForm();
                form.checkAndSave(req, className, dao);
                if (form.isResultat() && !path.contains("Topo")){
                    Long id = form.getEntitie().getId();
                    req.setAttribute("idElement", id);
                    System.out.println(req.getAttribute("idElement"));
                    form.checkAndSavePhoto(req, photoClassName, daoPhoto);
                }
                form.setResultat(form.checkResultListErreurs(form.getListErreurs()));
                req.setAttribute("form", form);
                if (form.isResultat()) {
                    resp.sendRedirect("/dashboard.do?resultat=true");
                } else {
                    doGet(req, resp);
                }
                break;
            }
            case "/updateSpot.do":
            case "/updateSecteur.do":
            case "/updateVoie.do":{
                Long idElement = Long.parseLong(req.getParameter("idElement"));
                EntityRepository dao, daoPhoto;
                String photoClassName;
                if (path.contains("Spot")){
                    dao = new SpotDaoImpl();
                    daoPhoto = new PhotoSpotDaoImpl();
                    photoClassName = "com.alain.dao.entities.PhotoSpot";
                } else if (path.contains("Secteur")) {
                    dao = new SecteurDaoImpl();
                    daoPhoto = new PhotoSecteurDaoImpl();
                    photoClassName = "com.alain.dao.entities.PhotoSecteur";
                }else{
                    dao = new VoieDaoImpl();
                    daoPhoto = new PhotoVoieDaoImpl();
                    photoClassName = "com.alain.dao.entities.PhotoVoie";
                }
                CheckForm form = new CheckForm();
                form.checkAndUpdate(req, dao, idElement);
                if (form.isResultat()){
                    req.setAttribute("idElement", idElement);
                    form.checkAndSavePhoto(req, photoClassName, daoPhoto);
                }
                req.setAttribute("form", form);
                if (form.isResultat()) {
                    resp.sendRedirect("/dashboard.do?resultat=true");
                } else {
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
                Boolean result = dao.delete(idElement);
                this.sendAjaxBooleanResponse(result, resp);
                break;
            }

            /* *********************************************************************************************
             **** Suppression recherche et droits admins   ************************************************
             *********************************************************************************************** */

            case "/supprimerUser.do":
            case "/supprimerCommentaire.do":
            case "/supprimerPhoto.do":{
                Long idElement = Long.parseLong(req.getParameter("idElement"));
                EntityRepository dao;
                if (path.contains("User")) {
                    dao = new UtilisateurDaoImpl();
                } else if (path.contains("Commentaire")) {
                    dao = new CommentaireSpotDaoImpl();
                } else{
                    String url = req.getParameter("jspUrl");
                    if (url.contains("Spot")){
                        dao = new PhotoSpotDaoImpl();
                    }else if (url.contains("Secteur")){
                        dao = new PhotoSecteurDaoImpl();
                    }else{
                        dao = new PhotoVoieDaoImpl();
                    }
                }
                Boolean result = dao.delete(idElement);
                this.sendAjaxBooleanResponse(result, resp);
                break;
            }
            case "/rechercheSpot.do": {
                SpotDaoImpl spotDao = new SpotDaoImpl();
                Map<String, Object> paramMap = Utilities.getParameterMapFromReq(req);
                List<SpotResearchDto> spots = spotDao.findSpotPersonalResearch(paramMap);
                req.setAttribute("spots", spots);
                doGet(req, resp);
                break;
            }
            case "/saveCommentaire.do": {
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                CommentaireSpotDaoImpl commentaireDao = new CommentaireSpotDaoImpl();
                String idSpot = req.getParameter("idSpot");
                CheckForm form = new CheckForm();
                form.checkAndSave(req, "com.alain.dao.entities.CommentaireSpot", commentaireDao);
                if (form.isResultat()) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    CommentaireSpot commentaire = (CommentaireSpot) form.getEntitie();
                    String json = gson.toJson(commentaire);
                    PrintWriter out = resp.getWriter();
                    out.print(json);
                    out.flush();
                }
                break;
            }
            case "/updateCommentaire.do":{
                CommentaireSpotDaoImpl commentaireSpotDao = new CommentaireSpotDaoImpl();
                CheckForm form = new CheckForm();
                form.checkAndUpdate(req, commentaireSpotDao, Long.parseLong(req.getParameter("idCommentaire")));
                Map<String, String> ajaxReturn = new HashMap<>();
                ajaxReturn.put("resultat", String.valueOf(form.isResultat()));
                if (!form.isResultat()){
                    ajaxReturn.put("erreur",form.getListErreurs().toString());
                }
                Gson gson = new Gson();
                String ajaxReturnString = gson.toJson(ajaxReturn);
                PrintWriter out = resp.getWriter();
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(ajaxReturnString);
                out.flush();
                break;
            }
        }
    }

    private void sendAjaxBooleanResponse(Boolean result, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.print(result);
        out.flush();
    }

    private void setNoCache(HttpServletResponse resp) {
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
    }
}
