package com.alain.web.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RestrictionFilter implements Filter {
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String ACCES_CONNEXION = "/WEB-INF/connexion.jsp";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession();

        if (session.getAttribute(ATT_SESSION_USER) == null){
            req.getRequestDispatcher(ACCES_CONNEXION).forward(req, resp);
        }else{
            //Redirige vers le filtre suivant s'il existe
            chain.doFilter(req,resp);
        }
    }

    @Override
    public void destroy() {

    }
}
