package br.dev.wisentini.featuretoggle.mapper;

import br.dev.wisentini.featuretoggle.dto.FeatureCreationDTO;
import br.dev.wisentini.featuretoggle.model.Feature;

public class FeatureMapper {

    public static Feature toFeature(FeatureCreationDTO featureCreationDTO) {
        return new Feature(
            null,
            featureCreationDTO.getName(),
            featureCreationDTO.isActive(),
            null,
            null,
            null
        );
    }
}
