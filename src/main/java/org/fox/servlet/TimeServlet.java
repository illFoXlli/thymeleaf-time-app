package org.fox.servlet;

import org.fox.service.TimeService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    private final TimeService service = new TimeService();
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

        Context context = new Context();
        context.setVariable("time", result);

        resp.setContentType("text/html;charset=UTF-8");
        templateEngine.process("time", context, resp.getWriter());
    }
}
