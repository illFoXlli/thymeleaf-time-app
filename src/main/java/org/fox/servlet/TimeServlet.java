package org.fox.servlet;

import org.fox.service.TimeService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.web.servlet.JavaxServletWebApplication;

import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    private final TimeService service = new TimeService();
    private TemplateEngine templateEngine;
    private JavaxServletWebApplication webApplication;

    @Override
    public void init() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);
        webApplication = JavaxServletWebApplication.buildApplication(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String timezoneFromQuery = req.getParameter("timezone");
            String timezone = timezoneFromQuery;

            if (timezone == null || timezone.isEmpty()) {
                Cookie[] cookies = req.getCookies();

                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("lastTimezone".equals(cookie.getName())) {
                            timezone = cookie.getValue();
                            break;
                        }
                    }
                }
            }

            if (timezone == null || timezone.isEmpty()) {
                timezone = "UTC";
            }

            String result = service.getTime(timezone);

            if (timezoneFromQuery != null && !timezoneFromQuery.isEmpty()) {
                Cookie cookie = new Cookie("lastTimezone", timezoneFromQuery);
                cookie.setMaxAge(60 * 60 * 24 * 30);
                cookie.setPath("/");
                resp.addCookie(cookie);
            }

            WebContext context = new WebContext(webApplication.buildExchange(req, resp));
            context.setVariable("time", result);

            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = resp.getWriter();
            templateEngine.process("time", context, writer);
        } catch (Exception e) {
            handleRenderError(resp, e);
        }
    }

    private void handleRenderError(HttpServletResponse resp, Exception e) {
        log("Unable to render time page", e);
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        try {
            resp.getWriter().write("Unable to render page");
        } catch (IOException ioException) {
            log("Unable to write error response", ioException);
        }
    }
}
