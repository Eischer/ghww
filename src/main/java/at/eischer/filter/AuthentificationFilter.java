package at.eischer.filter;

import at.eischer.session.CurrentUser;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Checks if the User is logged in and so authorized to access the admin area.
 */
@WebFilter(filterName = "AuthentificationFilter")
public class AuthentificationFilter implements jakarta.servlet.Filter {

    @Inject
    private CurrentUser currentUser;

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
    public void init(FilterConfig config) {
        //Nothing to do here
    }

}
