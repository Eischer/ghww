package at.eischer.filter;

import at.eischer.session.CurrentUser;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthentificationFilter")
public class AuthentificationFilter implements javax.servlet.Filter {

    @Inject
    CurrentUser currentUser;

    private static final String AJAX_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<partial-response><redirect url=\"%s\"></redirect></partial-response>";

    @Override
    public void destroy() {
        //Nothing to do here
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;

        String reqURI = httpRequest.getRequestURI();

        if (reqURI.startsWith("/admin") && currentUser.getUser() == null) {
            if ("partial/ajax".equals(httpRequest.getHeader("Faces-Request"))) {
                httpResponse.setContentType("text/xml");
                httpResponse.setCharacterEncoding("UTF-8");
                httpResponse.getWriter().printf(AJAX_REDIRECT_XML, httpRequest.getContextPath() + "/login.xhtml");
            } else {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.xhtml");
            }
        } else {
            chain.doFilter(req, resp);
        }


    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        //Nothing to do here
    }

}
