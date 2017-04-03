package at.eischer.filter;

import javax.servlet.Filter;
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

@WebFilter(urlPatterns = "/protected/*.xhtml")
public class AuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest reqt = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession ses = reqt.getSession(false);

        String reqURI = reqt.getRequestURI();
        if (reqURI.indexOf("/public/login.xhtml") >= 0
                || (ses != null && ses.getAttribute("username") != null)
                || reqURI.indexOf("/public/") >= 0
                || reqURI.contains("javax.faces.resource")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            resp.sendRedirect(reqt.getContextPath() + "/public/login.xhtml");
        }

    }

    @Override
    public void destroy() {

    }
}
