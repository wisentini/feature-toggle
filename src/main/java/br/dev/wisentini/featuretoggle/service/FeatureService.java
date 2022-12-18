package br.dev.wisentini.featuretoggle.service;

import br.dev.wisentini.featuretoggle.dto.FeatureCreationDTO;
import br.dev.wisentini.featuretoggle.dto.FeatureUpdateDTO;
import br.dev.wisentini.featuretoggle.exception.ResourceNotFoundException;
import br.dev.wisentini.featuretoggle.mapper.FeatureMapper;
import br.dev.wisentini.featuretoggle.model.Feature;
import br.dev.wisentini.featuretoggle.repository.FeatureRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeatureService {

    private final FeatureRepository featureRepository;

    public Feature findById(int id) {
        return this.featureRepository
            .findById(id)
            .orElseThrow(() -> {
                throw new ResourceNotFoundException(String.format("Feature with ID %d not found.", id));
            }
        );
    }

    public List<Feature> findAll() {
        return this.featureRepository.findAll();
    }

    public void update(int id, FeatureUpdateDTO featureUpdateDTO) {
        Feature feature = this.featureRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(String.format("Feature with ID %d not found.", id));
        });

        feature.update(featureUpdateDTO);

        this.featureRepository.save(feature);
    }

    public void save(FeatureCreationDTO featureCreationDTO) {
        featureCreationDTO.validate();

        this.featureRepository.save(FeatureMapper.toFeature(featureCreationDTO));
    }

    public void deleteById(int id) {
        if (!this.featureRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("Feature with ID %d not found.", id));
        }

        this.featureRepository.deleteById(id);
    }
}
