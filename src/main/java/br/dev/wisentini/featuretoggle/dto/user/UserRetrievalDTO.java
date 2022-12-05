package br.dev.wisentini.featuretoggle.dto.user;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRetrievalDTO {

    @NotNull(message = "ID must not be null.")
    private Integer id;

    @NotBlank(message = "Name must not be blank.")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters long.")
    private String name;

    @NotNull(message = "Creation date must not be null.")
    private LocalDate createdAt;

    private LocalDate updatedAt;
}
