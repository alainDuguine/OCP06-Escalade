package com.alain.web;

import com.alain.EntityManagerUtil;
import com.alain.dao.entities.*;
import com.alain.dao.impl.*;
import com.alain.metier.CheckForm;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;


public class Servlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        EntityManagerUtil entityManagerUtil = new EntityManagerUtil();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getServletPath();
        if(path.equals("/index.do")) {
            this.getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
        }
        else if(path.equals("/inscription.do")){
            req.setAttribute("utilisateur", new Utilisateur());
            this.getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(req, resp);
        }
        else if (path.equals("/rechercheSpot.do")){
            SpotDaoImpl spotDao = new SpotDaoImpl();
            List<Spot> spots = spotDao.findAll();
            req.setAttribute("spots", spots);
            this.getServletContext().getRequestDispatcher("/WEB-INF/rechercheSpot.jsp").forward(req, resp);
        }
        else if(path.equals("/connexion.do")){
            this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(req, resp);
        }
        else if(path.equals("/ajoutSpot.do") || path.equals("/saveSpot.do")){
            DepartementDaoImpl departementDao = new DepartementDaoImpl();
            List<Departement> departements = departementDao.findAll();
            req.setAttribute("departements", departements);
            this.getServletContext().getRequestDispatcher("/WEB-INF/ajoutSpot.jsp").forward(req, resp);
        }
        else if(path.equals("/ajoutSecteur.do") || path.equals("/saveSecteur.do")){
            SpotDaoImpl spotDao = new SpotDaoImpl();
            Spot spot = spotDao.findOne(Long.parseLong(req.getParameter("idSpot")));
            req.setAttribute("spot", spot);
            this.getServletContext().getRequestDispatcher("/WEB-INF/ajoutSecteur.jsp").forward(req,resp);
        }
        else if(path.equals("/ajoutVoie.do") || path.equals("/saveVoie.do")){
            SecteurDaoImpl secteurDao = new SecteurDaoImpl();
            Secteur secteur = secteurDao.findOne(Long.parseLong(req.getParameter("idSecteur")));
            CotationDaoImpl cotationDao = new CotationDaoImpl();
            List<Cotation> cotations = cotationDao.findAll();
            req.setAttribute("cotations", cotations);
            req.setAttribute("secteur", secteur);
            this.getServletContext().getRequestDispatcher("/WEB-INF/ajoutVoie.jsp").forward(req, resp);
        }
        else if(path.equals("/dashboard.do")){
            SpotDaoImpl spotDao = new SpotDaoImpl();
            List<Spot> listSpots = spotDao.findAll();
            req.setAttribute("spots", listSpots);
            this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(req, resp);
        }
        else if (path.equals("/choixDepartement.do")){
            Gson gson = new Gson();
            VilleDaoImpl dao = new VilleDaoImpl();
            String codeDep = req.getParameter("codeDep");
            List<Ville> villes = dao.findAllInDep(codeDep);
//            HashMap<Long, String> villesMap = new HashMap<>();
//            for (Ville ville : villes){
//                villesMap.put(ville.getId(), ville.getNom());
//            }
            String villesJsonString = gson.toJson(villes);
            PrintWriter out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(villesJsonString);
            out.flush();
        }
        else if (path.equals("/display.do")){
            SpotDaoImpl spotDao = new SpotDaoImpl();
            Spot spot = spotDao.findOne(Long.parseLong(req.getParameter("idSpot")));
            req.setAttribute("spot",spot);
            this.getServletContext().getRequestDispatcher("/WEB-INF/display.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.equals("/saveUser.do")){
            UtilisateurDaoImpl utilisateurDaoImpl = new UtilisateurDaoImpl();
            CheckForm form = new CheckForm();
            form.checkAndSave(req, "com.alain.dao.entities.Utilisateur", utilisateurDaoImpl);
            req.setAttribute("form", form);
            this.getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(req, resp);
        }
        else if (path.equals("/connexion.do")){
            UtilisateurDaoImpl utilisateurDaoImpl = new UtilisateurDaoImpl();
            CheckForm form = new CheckForm();
            form.checkConnect(req, utilisateurDaoImpl);
            req.setAttribute("form", form);
            this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(req, resp);
        }
        else if (path.equals("/saveSpot.do")){
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
            if (form.getListErreurs().isEmpty()){
                // todo Comment avoir à la fois la requête sql pour récupérer tous les spots
                // Et l'objet form.resultat ? Peut être faire la requête avec Ajax ?
//                resp.sendRedirect("/dashboard.do");
                resp.sendRedirect("/dashboard.do?resultat=true");
            } else {
                doGet(req,resp);
            }
        }else if (path.equals("/saveSecteur.do")){
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
            if (form.getListErreurs().isEmpty()) {
                resp.sendRedirect("/dashboard.do");
            }else {
                doGet(req,resp);
            }
        }else if (path.equals("/saveVoie.do")){
            VoieDaoImpl voieDao = new VoieDaoImpl();
            PhotoVoieDaoImpl photoVoieDao = new PhotoVoieDaoImpl();
            CheckForm form = new CheckForm();
            form.checkAndSave(req, "com.alain.dao.entities.Voie", voieDao);
            if (form.getListErreurs().isEmpty()){
                Long idVoie = ((Voie) form.getEntitie()).getId();
                req.setAttribute("idVoie", idVoie);
                form.checkAndSavePhoto(req, "com.alain.dao.entities.PhotoVoie", photoVoieDao);
            }
            form.setResultat(form.checkResultListErreurs(form.getListErreurs()));
            req.setAttribute("form", form);
            if (form.getListErreurs().isEmpty()){
                resp.sendRedirect("/dashboard.do");
            }else{
                doGet(req,resp);
            }
        }
    }
}
