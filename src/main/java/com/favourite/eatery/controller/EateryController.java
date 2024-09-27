package com.favourite.eatery.controller;

import com.favourite.eatery.exception.EateryNotFoundException;
import com.favourite.eatery.model.Eatery;
import com.favourite.eatery.repository.EateryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EateryController {
    private final EateryRepository repository;

    public EateryController(EateryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/eateries")
    List<Eatery> getAll() {
        return repository.findAll();
    }

    @PostMapping("/eatery")
    Eatery create(@RequestBody Eatery newEatery) {
        return repository.save(newEatery);
    }

    @GetMapping("/eatery/{id}")
    Eatery get(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EateryNotFoundException(id));
    }

    @PutMapping("/eatery/{id}")
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

    @DeleteMapping("/eatery/{id}")
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
