package br.dev.wisentini.featuretoggle.dto;

import br.dev.wisentini.featuretoggle.exception.MissingFieldsException;

import lombok.*;

import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeatureCreationDTO {

    @NotBlank(message = "Name must not be blank.")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters long.")
    private String name;

    @NotNull(message = "Status must not be null.")
    @Getter(value = AccessLevel.NONE)
    private Boolean active;

    public FeatureCreationDTO(String name) {
        this(name, null);
    }

    public FeatureCreationDTO(Boolean active) {
        this(null, active);
    }

    public Boolean isActive() {
        return this.active;
    }

    public void validate() {
        if (StringUtils.isBlank(this.name) || Objects.isNull(this.active)) {
            throw new MissingFieldsException("A name and status must be provided in order to create a feature.");
        }
    }
}
