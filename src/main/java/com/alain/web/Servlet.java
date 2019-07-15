package com.alain.web;


import com.alain.EntityManagerUtil;
import com.alain.dao.entities.Spot;
import com.alain.dao.entities.Utilisateur;
import com.alain.dao.impl.SecteurDaoImpl;
import com.alain.dao.impl.SpotDaoImpl;
import com.alain.dao.impl.UtilisateurDaoImpl;
import com.alain.metier.CheckForm;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class Servlet extends HttpServlet {

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
            UtilisateurDaoImpl utilisateurDaoImpl;
            utilisateurDaoImpl = new UtilisateurDaoImpl();
            CheckForm form = new CheckForm();
            form.checkAndSave(req, "com.alain.dao.entities.Utilisateur", utilisateurDaoImpl);
            req.setAttribute("form", form);
            this.getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(req, resp);
        }
        else if (path.equals("/connexion.do")){
            UtilisateurDaoImpl utilisateurDaoImpl;
            utilisateurDaoImpl = new UtilisateurDaoImpl();
            CheckForm form = new CheckForm();
            form.checkConnect(req, utilisateurDaoImpl);
            req.setAttribute("form", form);
            this.getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(req, resp);
        }
        else if (path.equals("/saveSpot.do")){
            SpotDaoImpl spotDaoImpl = new SpotDaoImpl();
            CheckForm form = new CheckForm();
            if(req.getParameter("photo").isEmpty()){
               form.checkAndSave(req, "com.alain.dao.entities.Spot", spotDaoImpl);
            }else{
//                CheckForm form = CheckForm.checkAndUpload(req, "com.alain.dao.entities.Spot", spotDaoImpl);
            }
            req.setAttribute("form", form);
            if (form.getListErreurs().isEmpty()){
                resp.sendRedirect("/dashboard.do");
            }else{
                this.getServletContext().getRequestDispatcher("/WEB-INF/ajoutSpot.jsp").forward(req, resp);
            }
        }else if (path.equals("/saveSecteur.do")){
            SecteurDaoImpl secteurDaoImpl = new SecteurDaoImpl();
            CheckForm form = new CheckForm();
            form.checkAndSave(req, "com.alain.dao.entities.Secteur", secteurDaoImpl);
            req.setAttribute("form", form);
            if (form.getListErreurs().isEmpty()) {
                resp.sendRedirect("/dashboard.do");
            }else {
                req.setAttribute("idSpot", req.getParameter("idSpot"));
                this.getServletContext().getRequestDispatcher("/WEB-INF/ajoutSecteur.jsp").forward(req, resp);
            }
        }
    }
}
