package com.alain.web;


import com.alain.EntityManagerUtil;
import com.alain.dao.entities.Spot;
import com.alain.dao.entities.Utilisateur;
import com.alain.dao.impl.SecteurDaoImpl;
import com.alain.dao.impl.SpotDaoImpl;
import com.alain.dao.impl.UtilisateurDaoImpl;
import com.alain.metier.CheckForm;
import com.alain.metier.CheckFormResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class Servlet extends HttpServlet {
    private UtilisateurDaoImpl utilisateurDaoImpl;

    private String resultat;

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
            this.getServletContext().getRequestDispatcher("/WEB-INF/rechercheSpot.jsp").forward(req, resp);
        }
        else if(path.equals("/connexion.do")){
            this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(req, resp);
        }
        else if(path.equals("/ajoutSpot.do")) {
            this.getServletContext().getRequestDispatcher("/WEB-INF/ajoutSpot.jsp").forward(req, resp);
        }
        else if(path.equals("/ajoutSecteur.do")){
            Long idSpot = Long.parseLong(req.getParameter("idSpot"));
            req.setAttribute("idSpot", idSpot);
            this.getServletContext().getRequestDispatcher("/WEB-INF/ajoutSecteur.jsp").forward(req,resp);
        }
        else if(path.equals("/dashboard.do")) {
            SpotDaoImpl spotDaoImpl;
            spotDaoImpl = new SpotDaoImpl();
            List<Spot> listSpots = spotDaoImpl.findAll();
            req.setAttribute("spots", listSpots);
            this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.equals("/saveUser.do")){
            utilisateurDaoImpl = new UtilisateurDaoImpl();
            CheckFormResult result = CheckForm.checkAndSave(req, "com.alain.dao.entities.Utilisateur", utilisateurDaoImpl);
            Map<String,String> listErreurs = result.getListErreurs();
            if (listErreurs.isEmpty()){
                resultat = "Enregistrement réussi";
                req.setAttribute("resultat", resultat);
                this.getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(req, resp);
            }else{
                resultat = "L'enregistrement a échoué";
                req.setAttribute("resultat", resultat);
                req.setAttribute("listErreurs", listErreurs);
                this.getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(req, resp);
            }
        }
        else if (path.equals("/connexion.do")){
            utilisateurDaoImpl = new UtilisateurDaoImpl();
            Map<String,String> listErreurs = CheckForm.checkConnect(req, utilisateurDaoImpl);
            if (listErreurs.isEmpty()) {
                resultat = "Connexion réussie";
            }else{
                resultat = "Connexion échouée";
                req.setAttribute("listErreurs", listErreurs);
            }
            req.setAttribute("resultat", resultat);
            this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(req, resp);
        }
        else if (path.equals("/saveSpot.do")){
            SpotDaoImpl spotDaoImpl = new SpotDaoImpl();
            CheckFormResult resultForm = null;
            if(req.getParameter("photo").isEmpty()){
               resultForm = CheckForm.checkAndSave(req, "com.alain.dao.entities.Spot", spotDaoImpl);
            }else{
//                CheckFormResult result = CheckFormUpload.checkAndUpload(req, "com.alain.dao.entities.Spot", spotDaoImpl);
            }
            req.setAttribute("form", resultForm);
            if (resultForm.getListErreurs().isEmpty()){
                resp.sendRedirect("/dashboard.do");
            }else{
                this.getServletContext().getRequestDispatcher("/WEB-INF/ajoutSpot.jsp").forward(req, resp);
            }
        }else if (path.equals("/saveSecteur.do")){
            SecteurDaoImpl secteurDaoImpl = new SecteurDaoImpl();
            CheckFormResult resultForm = CheckForm.checkAndSave(req, "com.alain.dao.entities.Secteur", secteurDaoImpl);
            req.setAttribute("form", resultForm);
            if (!resultForm.getListErreurs().isEmpty()) {
                req.setAttribute("idSpot", req.getParameter("idSpot"));
                this.getServletContext().getRequestDispatcher("/WEB-INF/ajoutSecteur.jsp").forward(req, resp);
            }else {
                resp.sendRedirect("/dashboard.do");

            }
        }
    }
}
