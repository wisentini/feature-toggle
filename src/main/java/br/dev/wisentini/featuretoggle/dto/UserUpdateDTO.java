package br.dev.wisentini.featuretoggle.dto;

import br.dev.wisentini.featuretoggle.exception.MissingFieldsException;

import lombok.*;

import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters long.")
    private String name;

    @Size(min = 1, max = 255, message = "Password must be between 1 and 255 characters long.")
    private String password;

    public void validate() {
        if (StringUtils.isBlank(this.name) && StringUtils.isBlank(this.password)) {
            throw new MissingFieldsException("A name, password or both must be provided in order to update a user.");
        }
    }
}
