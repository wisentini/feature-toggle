package br.dev.wisentini.featuretoggle.repository;

import br.dev.wisentini.featuretoggle.model.Feature;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Integer> {
}
