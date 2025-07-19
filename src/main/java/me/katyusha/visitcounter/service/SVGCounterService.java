package me.katyusha.visitcounter.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

@Service
public class SVGCounterService {

    private final VisitCountService visitCountService;
    private final TemplateEngine templateEngine;

    public SVGCounterService(VisitCountService visitCountService, TemplateEngine templateEngine) {
        this.visitCountService = visitCountService;
        this.templateEngine = templateEngine;
    }

    public String getSVGCounter(String pageKey, String template) {
        Long count = visitCountService.incrementVisit(pageKey);
        Context context = new Context();
        context.setVariable("count", count);
        return templateEngine.process(template, context);
    }
}
