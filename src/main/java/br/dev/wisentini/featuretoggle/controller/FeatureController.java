package br.dev.wisentini.featuretoggle.controller;

import br.dev.wisentini.featuretoggle.dto.*;
import br.dev.wisentini.featuretoggle.model.Feature;
import br.dev.wisentini.featuretoggle.service.FeatureService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(value = "/feature")
@RequiredArgsConstructor
public class FeatureController {

    private final FeatureService featureService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public Feature findById(@PathVariable("id") int id) {
        return this.featureService.findById(id);
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public List<Feature> findAll() {
        return this.featureService.findAll();
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void save(@Valid @RequestBody FeatureCreationDTO featureCreationDTO) {
        this.featureService.save(featureCreationDTO);
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void update(@PathVariable("id") int id, @Valid @RequestBody FeatureUpdateDTO featureUpdateDTO) {
        this.featureService.update(id, featureUpdateDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") int id) {
        this.featureService.deleteById(id);
    }
}
