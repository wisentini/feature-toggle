package br.dev.wisentini.featuretoggle.dto;

import br.dev.wisentini.featuretoggle.exception.MissingFieldsException;

import lombok.*;

import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeatureUpdateDTO {

    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters long.")
    private String name;

    @Getter(value = AccessLevel.NONE)
    private Boolean active;

    public FeatureUpdateDTO(String name) {
        this(name, null);
    }

    public FeatureUpdateDTO(Boolean active) {
        this(null, active);
    }

    public Boolean isActive() {
        return this.active;
    }

    public void validate() {
        if (StringUtils.isBlank(this.name) && Objects.isNull(this.active)) {
            throw new MissingFieldsException("A name, status or both must be provided in order to update a feature.");
        }
    }
}
