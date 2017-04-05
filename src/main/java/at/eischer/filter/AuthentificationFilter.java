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

    @Override
    public void destroy() {
        //Nothing to do here
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;

        String reqURI = httpServletRequest.getRequestURI();

        if (reqURI.startsWith("/ghww/protected") && currentUser.getUser() == null) {
            httpResponse.sendRedirect(httpServletRequest.getContextPath() + "/public/login.xhtml");
        } else{
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        //Nothing to do here
    }

}
