package com.alain.web;


import com.alain.dao.entities.Utilisateur;
import com.alain.dao.impl.UtilisateurDaoImpl;
import com.alain.metier.CheckForm;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


public class Servlet extends HttpServlet {
    private UtilisateurDaoImpl metier;
    String resultat;

    @Override
    public void init() throws ServletException {
        metier = new UtilisateurDaoImpl();
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
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.equals("/saveUser.do")){
            Map<String,String> listErreur = CheckForm.checkAndSave(req, "com.alain.dao.entities.Utilisateur", metier);
            if (listErreur.isEmpty()){
                resultat = "Enregistrement réussi";
            }else{
                resultat = "L'enregistrement a échoué";
                req.setAttribute("listErreur", listErreur);
            }
            req.setAttribute("resultat", resultat);
            this.getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(req, resp);
        }
        else if (path.equals("/connexion.do")){
            Map<String,String> listErreur = CheckForm.checkConnect(req, metier);
            if (listErreur.isEmpty()) {
                resultat = "Connexion réussie";
            }else{
                resultat = "Connexion échouée";
                req.setAttribute("listErreur", listErreur);
            }
            req.setAttribute("resultat", resultat);
            this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(req, resp);
        }
    }
}
