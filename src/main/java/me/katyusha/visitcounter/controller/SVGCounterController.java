package me.katyusha.visitcounter.controller;

import me.katyusha.visitcounter.service.SVGCounterService;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/count/svg/")
public class SVGCounterController {

    private final SVGCounterService svgCounterService;

    public SVGCounterController(SVGCounterService svgCounterService) {
        this.svgCounterService = svgCounterService;
    }

    @GetMapping(value = "/{pageKey}/{template}.svg", produces = "image/svg+xml")
    public ResponseEntity<String> getSVGCounter(@PathVariable("pageKey") String pageKey, @PathVariable("template") String template) {
        return ResponseEntity.ok()
                .header("Cache-Control", "max-age=0, no-cache, no-store, must-revalidate")
                .body(svgCounterService.getSVGCounter(pageKey, template));
    }
}
