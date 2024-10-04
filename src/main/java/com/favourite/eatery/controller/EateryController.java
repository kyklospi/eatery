package com.favourite.eatery.controller;

import com.favourite.eatery.exception.EateryNotFoundException;
import com.favourite.eatery.model.Eatery;
import com.favourite.eatery.repository.EateryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/eateries")
public class EateryController {
    @Autowired
    private EateryRepository repository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Eatery> getAll() {
        return repository.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Eatery create(@RequestBody Eatery newEatery) {
        return repository.save(newEatery);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Eatery get(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EateryNotFoundException(id));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Eatery replace(@RequestBody Eatery newEatery, @PathVariable Long id) {
        return repository.findById(id)
                .map(eatery -> {
                    eatery.setName(newEatery.getName());
                    eatery.setAddress(newEatery.getAddress());
                    eatery.setEmail(newEatery.getEmail());
                    eatery.setPhoneNumber(newEatery.getPhoneNumber());
                    return repository.save(eatery);
                })
                .orElseGet(() -> repository.save(newEatery));
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
