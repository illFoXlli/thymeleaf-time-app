package org.fox.servlet;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");

        Context context = new Context();

        String baseUrl = req.getScheme() + "://" +
                         req.getServerName() + ":" +
                         req.getServerPort();

        context.setVariable("baseUrl", baseUrl);

        templateEngine.process("index", context, resp.getWriter());
    }
}
