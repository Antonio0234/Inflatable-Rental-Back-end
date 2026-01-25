package com.antonio.napuhanci.service;

import com.antonio.napuhanci.domain.Inflatable;
import com.antonio.napuhanci.repository.InflatableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InflatableService {

    private final InflatableRepository inflatableRepository;

    public InflatableService(InflatableRepository inflatableRepository) {
        this.inflatableRepository = inflatableRepository;
    }

    public List<Inflatable> getActiveInflatables() {
        return inflatableRepository.findByActiveTrueOrderByNameAsc();
    }
}
