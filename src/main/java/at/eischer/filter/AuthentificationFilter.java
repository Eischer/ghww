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

    @Override
    public void destroy() {
        //Nothing to do here
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;
        HttpSession ses = httpServletRequest.getSession(false);

        String reqURI = httpServletRequest.getRequestURI();

        if (reqURI.startsWith("/ghww/protected") && (ses == null || ses.getAttribute("userName") == null)) {
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
