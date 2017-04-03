package at.eischer.filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthentificationFilter")
public class AuthentificationFilter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("MUHHHHHHHHHH");
//        chain.doFilter(req, resp);
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;
        HttpSession ses = httpServletRequest.getSession(false);

        String reqURI = httpServletRequest.getRequestURI();
        if (reqURI.indexOf("/login.xhtml") >= 0
                || (ses != null && ses.getAttribute("username") != null)
                || reqURI.indexOf("/public/") >= 0
                || reqURI.contains("javax.faces.resource"))
            chain.doFilter(req, resp);
        else
            httpResponse.sendRedirect(httpServletRequest.getContextPath() + "/faces/login.xhtml");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
