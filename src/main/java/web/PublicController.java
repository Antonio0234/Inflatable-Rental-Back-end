package com.antonio.napuhanci.web;

import com.antonio.napuhanci.domain.Inflatable;
import com.antonio.napuhanci.service.InflatableService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PublicController {

    private final InflatableService inflatableService;

    public PublicController(InflatableService inflatableService) {
        this.inflatableService = inflatableService;
    }

    @GetMapping("/api/inflatables")
    public List<Inflatable> inflatables() {
        return inflatableService.getActiveInflatables();
    }
}
