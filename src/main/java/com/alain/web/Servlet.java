package com.alain.web;

import com.alain.EntityManagerUtil;
import com.alain.dao.entities.*;
import com.alain.dao.impl.*;
import com.alain.metier.CheckForm;
import com.alain.metier.SpotResearchDto;
import com.alain.metier.Utilities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Servlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        EntityManagerUtil entityManagerUtil = new EntityManagerUtil();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getServletPath();
        switch (path) {
            case "/index.do":
                this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
                break;
            case "/inscription.do":
                this.getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(req, resp);
                break;
            case "/connexion.do":
                Cookie[] cookies = req.getCookies();
                if (cookies != null){
                    for (Cookie cookie : cookies){
                        if (cookie.getName().equals("email")){
                            req.setAttribute("cookieEmail", cookie.getValue());
                        }
                    }
                }
                this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(req, resp);
                break;
            case "/ajoutSpot.do":
            case "/saveSpot.do":
                DepartementDaoImpl departementDao = new DepartementDaoImpl();
                List<Departement> departements = departementDao.findAll();
                req.setAttribute("departements", departements);
                this.getServletContext().getRequestDispatcher("/WEB-INF/restricted/ajoutSpot.jsp").forward(req, resp);
                break;
            case "/ajoutSecteur.do":
            case "/saveSecteur.do": {
                SpotDaoImpl spotDao = new SpotDaoImpl();
                Spot spot = spotDao.findOne(Long.parseLong(req.getParameter("idSpot")));
                req.setAttribute("spot", spot);
                this.getServletContext().getRequestDispatcher("/WEB-INF/restricted/ajoutSecteur.jsp").forward(req, resp);
                break;
            }
            case "/ajoutVoie.do":
            case "/saveVoie.do": {
                SecteurDaoImpl secteurDao = new SecteurDaoImpl();
                Secteur secteur = secteurDao.findOne(Long.parseLong(req.getParameter("idSecteur")));
                if (secteur != null) {
                    CotationDaoImpl cotationDao = new CotationDaoImpl();
                    List<Cotation> cotations = cotationDao.findAll();
                    req.setAttribute("cotations", cotations);
                    req.setAttribute("secteur", secteur);
                    this.getServletContext().getRequestDispatcher("/WEB-INF/restricted/ajoutVoie.jsp").forward(req, resp);
                } else {
                    this.getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(req, resp);
                }
                break;
            }
            case "/connexionForm.do":
            case "/dashboard.do": {
                HttpSession session = req.getSession();
                String username = (String) session.getAttribute("sessionUtilisateur");
                if (username != null) {
                    UtilisateurDaoImpl utilisateurDao = new UtilisateurDaoImpl();
                    Utilisateur utilisateur = utilisateurDao.findByUsername(username);
                    req.setAttribute("utilisateur", utilisateur);
                }
                this.getServletContext().getRequestDispatcher("/WEB-INF/restricted/dashboard.jsp").forward(req, resp);
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
            case "/saveUser.do": {
                UtilisateurDaoImpl utilisateurDaoImpl = new UtilisateurDaoImpl();
                CheckForm form = new CheckForm();
                form.checkAndSave(req, "com.alain.dao.entities.Utilisateur", utilisateurDaoImpl);
                form.setResultat(form.checkResultListErreurs(form.getListErreurs()));
                req.setAttribute("form", form);
                this.getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(req, resp);
                break;
            }
            case "/connexionForm.do": {
                UtilisateurDaoImpl utilisateurDaoImpl = new UtilisateurDaoImpl();
                CheckForm form = new CheckForm();
                form.checkConnect(req, utilisateurDaoImpl);
                req.setAttribute("form", form);
                if (form.isResultat()) {
                    if (req.getParameter("cookie") != null){
                        Cookie cookieEmail = new Cookie("email", req.getParameter("email"));
                        cookieEmail.setMaxAge(60*60*24*365);
                        resp.addCookie(cookieEmail);
                    }
                    HttpSession session = req.getSession();
                    String username = ((Utilisateur) form.getEntitie()).getUsername();
                    session.setAttribute("sessionUtilisateur", username);
                    doGet(req,resp);
                } else {
                    this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(req, resp);
                }
                break;
            }
            case "/saveSpot.do": {
                SpotDaoImpl spotDao = new SpotDaoImpl();
                PhotoSpotDaoImpl photoSpotDao = new PhotoSpotDaoImpl();
                CheckForm form = new CheckForm();
                form.checkAndSave(req, "com.alain.dao.entities.Spot", spotDao);
                if (form.getListErreurs().isEmpty()) {
                    Long idSpot = ((Spot) form.getEntitie()).getId();
                    req.setAttribute("idSpot", idSpot);
                    form.checkAndSavePhoto(req, "com.alain.dao.entities.PhotoSpot", photoSpotDao);
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
            case "/saveSecteur.do": {
                SecteurDaoImpl secteurDao = new SecteurDaoImpl();
                PhotoSecteurDaoImpl photoSecteurDao = new PhotoSecteurDaoImpl();
                CheckForm form = new CheckForm();
                form.checkAndSave(req, "com.alain.dao.entities.Secteur", secteurDao);
                if (form.getListErreurs().isEmpty()) {
                    Long idSecteur = ((Secteur) form.getEntitie()).getId();
                    req.setAttribute("idSecteur", idSecteur);
                    form.checkAndSavePhoto(req, "com.alain.dao.entities.PhotoSecteur", photoSecteurDao);
                }
                form.setResultat(form.checkResultListErreurs(form.getListErreurs()));
                req.setAttribute("form", form);
                if (form.isResultat()) {
                    resp.sendRedirect("/display.do?idSpot=" + req.getParameter("idSpot"));
                } else {
                    doGet(req, resp);
                }
                break;
            }
            case "/saveVoie.do": {
                VoieDaoImpl voieDao = new VoieDaoImpl();
                PhotoVoieDaoImpl photoVoieDao = new PhotoVoieDaoImpl();
                CheckForm form = new CheckForm();
                form.checkAndSave(req, "com.alain.dao.entities.Voie", voieDao);
                if (form.getListErreurs().isEmpty()) {
                    Long idVoie = ((Voie) form.getEntitie()).getId();
                    req.setAttribute("idVoie", idVoie);
                    form.checkAndSavePhoto(req, "com.alain.dao.entities.PhotoVoie", photoVoieDao);
                }
                form.setResultat(form.checkResultListErreurs(form.getListErreurs()));
                req.setAttribute("form", form);
                if (form.isResultat()) {
                    Long idSpot = ((Voie) form.getEntitie()).getSecteur().getSpot().getId();
                    String nomSecteur = ((Voie) form.getEntitie()).getSecteur().getNom();
                    req.setAttribute("idSpot", idSpot);
                    resp.sendRedirect("/display.do?idSpot=" + idSpot + "#" + nomSecteur);
                } else {
                    doGet(req, resp);
                }
                break;
            }
            case "/saveCommentaire.do": {
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                CommentaireSpotDaoImpl commentaireDao = new CommentaireSpotDaoImpl();
                String idSpot = req.getParameter("idSpot");
                CheckForm form = new CheckForm();
                form.checkAndSave(req, "com.alain.dao.entities.CommentaireSpot", commentaireDao);
                form.setResultat(form.checkResultListErreurs(form.getListErreurs()));
                if (form.isResultat()) {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    CommentaireSpot commentaire = (CommentaireSpot) form.getEntitie();
                    String json = gson.toJson(commentaire);
                    PrintWriter out = resp.getWriter();
                    out.print(json);
                    out.flush();
                } else {
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    String erreurJson = gson.toJson(form.getListErreurs());
                    PrintWriter out = resp.getWriter();
                    out.print(erreurJson);
                    out.flush();
                }
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
        }
    }
}
