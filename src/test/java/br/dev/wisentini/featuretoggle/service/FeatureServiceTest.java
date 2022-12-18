package br.dev.wisentini.featuretoggle.service;

import br.dev.wisentini.featuretoggle.dto.FeatureCreationDTO;
import br.dev.wisentini.featuretoggle.dto.FeatureUpdateDTO;
import br.dev.wisentini.featuretoggle.exception.MissingFieldsException;
import br.dev.wisentini.featuretoggle.exception.ResourceNotFoundException;
import br.dev.wisentini.featuretoggle.model.Feature;
import br.dev.wisentini.featuretoggle.repository.FeatureRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class FeatureServiceTest {

    @InjectMocks
    private FeatureService featureService;

    @Mock
    private FeatureRepository featureRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindFeatureByIdWhenItExists() {
        int featureId = 11;
        Feature feature = new Feature(featureId, "Teste 1", true, null, LocalDate.now().minusDays(87), LocalDate.now());

        when(this.featureRepository.findById(featureId)).thenReturn(Optional.of(feature));

        assertDoesNotThrow(() -> this.featureService.findById(featureId));
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenFeatureDoesNotExists() {
        int featureId = 332;

        when(this.featureRepository.findById(featureId)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> this.featureService.findById(featureId));
    }

    @Test
    void shouldReturnFeaturesWhenPresent() {
        List<Feature> features = List.of(
            new Feature(6477, "Feature 1", false, null, LocalDate.now(), null),
            new Feature(9751, "Feature 2", false, null, LocalDate.now().minusDays(987), LocalDate.now()),
            new Feature(5542, "Feature 3", true, null, LocalDate.now().minusDays(44), LocalDate.now().minusDays(8))
        );

        when(this.featureRepository.findAll()).thenReturn(features);

        assertDoesNotThrow(() -> this.featureService.findAll());
    }

    @Test
    void shouldCreateFeatureWhenNameAndStatusAreProvided() {
        String featureName = "Feature 6";
        boolean featureStatus = false;

        FeatureCreationDTO featureCreationDTO = new FeatureCreationDTO(featureName, featureStatus);
        Feature feature = new Feature(773, featureName, featureStatus, null, LocalDate.now(), null);

        when(this.featureRepository.save(feature)).thenReturn(feature);

        assertDoesNotThrow(() -> this.featureService.save(featureCreationDTO));
    }

    @Test
    void shouldThrowMissingFieldsExceptionWhenOnlyNameIsProvidedOnCreation() {
        FeatureCreationDTO featureCreationDTO = new FeatureCreationDTO("Feature 03");

        assertThrows(MissingFieldsException.class, () -> this.featureService.save(featureCreationDTO));
    }

    @Test
    void shouldThrowMissingFieldsExceptionWhenOnlyStatusIsProvidedOnCreation() {
        FeatureCreationDTO featureCreationDTO = new FeatureCreationDTO(true);

        assertThrows(MissingFieldsException.class, () -> this.featureService.save(featureCreationDTO));
    }

    @Test
    void shouldThrowMissingFieldsExceptionWhenNameOrStatusAreNotProvidedOnCreation() {
        FeatureCreationDTO featureCreationDTO = new FeatureCreationDTO();

        assertThrows(MissingFieldsException.class, () -> this.featureService.save(featureCreationDTO));
    }

    @Test
    void shouldUpdateFeatureWhenNameAndStatusAreProvided() {
        int featureId = 58;
        Feature feature = new Feature(featureId, "Feature 99", false, null, LocalDate.now(), null);
        FeatureUpdateDTO featureUpdateDTO = new FeatureUpdateDTO("Feature 98", true);

        when(this.featureRepository.findById(featureId)).thenReturn(Optional.of(feature));

        when(this.featureRepository.save(feature)).thenReturn(feature);

        assertDoesNotThrow(() -> this.featureService.update(featureId, featureUpdateDTO));
    }

    @Test
    void shouldUpdateUserWhenOnlyNameIsProvided() {
        int featureId = 58;
        Feature feature = new Feature(featureId, "Feature 005", true, null, LocalDate.now(), null);
        FeatureUpdateDTO featureUpdateDTO = new FeatureUpdateDTO("Feature 5");

        when(this.featureRepository.findById(featureId)).thenReturn(Optional.of(feature));

        when(this.featureRepository.save(feature)).thenReturn(feature);

        assertDoesNotThrow(() -> this.featureService.update(featureId, featureUpdateDTO));
    }

    @Test
    void shouldUpdateUserWhenOnlyStatusIsProvided() {
        int featureId = 60;
        Feature feature = new Feature(featureId, "Feature 5", false, null, LocalDate.now(), null);
        FeatureUpdateDTO featureUpdateDTO = new FeatureUpdateDTO(true);

        when(this.featureRepository.findById(featureId)).thenReturn(Optional.of(feature));

        when(this.featureRepository.save(feature)).thenReturn(feature);

        assertDoesNotThrow(() -> this.featureService.update(featureId, featureUpdateDTO));
    }

    @Test
    void shouldThrowMissingFieldsExceptionWhenNameAndStatusAreNotProvidedOnUpdate() {
        int featureId = 60;
        Feature feature = new Feature(featureId, "Feature 5", false, null, LocalDate.now(), null);
        FeatureUpdateDTO featureUpdateDTO = new FeatureUpdateDTO();

        when(this.featureRepository.findById(featureId)).thenReturn(Optional.of(feature));

        when(this.featureRepository.save(feature)).thenReturn(feature);

        assertThrows(MissingFieldsException.class, () -> this.featureService.update(featureId, featureUpdateDTO));
    }

    @Test
    void shouldDeleteFeatureByIdWhenItExists() {
        int featureId = 33;

        when(this.featureRepository.existsById(featureId)).thenReturn(true);

        doNothing().when(this.featureRepository).deleteById(featureId);

        assertDoesNotThrow(() -> this.featureService.deleteById(featureId));
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenFeatureToDeleteDoesNotExists() {
        int featureId = 646;

        when(this.featureRepository.existsById(featureId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> this.featureService.deleteById(featureId));
    }
}
