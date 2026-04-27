package org.fox.servlet;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@WebServlet("/")
public class IndexServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @Override
    public void init() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        try {
            resp.setContentType("text/html;charset=UTF-8");

            Context context = new Context();
            String baseUrl = req.getScheme() + "://" +
                             req.getServerName() + ":" +
                             req.getServerPort();
            context.setVariable("baseUrl", baseUrl);

            PrintWriter writer = resp.getWriter();
            templateEngine.process("index", context, writer);
        } catch (Exception e) {
            handleRenderError(resp, e);
        }
    }

    private void handleRenderError(HttpServletResponse resp, Exception e) {
        log("Unable to render index page", e);
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        try {
            resp.getWriter().write("Unable to render page");
        } catch (IOException ioException) {
            log("Unable to write error response", ioException);
        }
    }
}
