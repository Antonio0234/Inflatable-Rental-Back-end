package com.antonio.napuhanci.web;

import com.antonio.napuhanci.domain.Inflatable;
import com.antonio.napuhanci.service.AvailabilityService;
import com.antonio.napuhanci.service.InflatableService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PublicController {

    private final InflatableService inflatableService;
    private final AvailabilityService availabilityService;

    public PublicController(InflatableService inflatableService, AvailabilityService availabilityService) {
        this.inflatableService = inflatableService;
        this.availabilityService = availabilityService;
    }

    @GetMapping("/inflatables")
    public List<Inflatable> inflatables() {
        return inflatableService.getActiveInflatables();
    }

    @GetMapping("/inflatables/{id}/availability")
    public boolean availability(@PathVariable Long id, @RequestParam LocalDate date) {
        return availabilityService.isAvailable(id, date);
    }
}
