package me.katyusha.visitcounter.controller;

import me.katyusha.visitcounter.service.SVGCounterService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/count/svg/")
public class SVGCounterController {

    private final SVGCounterService svgCounterService;

    public SVGCounterController(SVGCounterService svgCounterService) {
        this.svgCounterService = svgCounterService;
    }

    @GetMapping(value = "/{pageKey}/{template}.svg", produces = "image/svg+xml")
    @ResponseBody
    public String getSVGCounter(@PathVariable("pageKey") String pageKey, @PathVariable("template") String template) {
        return svgCounterService.getSVGCounter(pageKey, template);
    }
}
