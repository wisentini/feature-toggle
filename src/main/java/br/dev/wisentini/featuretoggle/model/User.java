package br.dev.wisentini.featuretoggle.model;

import br.dev.wisentini.featuretoggle.dto.UserUpdateDTO;

import lombok.*;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

import java.time.LocalDate;

@Entity(name = "user")
@Table(name = "user", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @Column(name = "user_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", insertable = false, updatable = false, nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    public void update(@NonNull UserUpdateDTO userUpdateDTO) {
        userUpdateDTO.validate();

        if (StringUtils.isNotBlank(userUpdateDTO.getName())) {
            this.name = userUpdateDTO.getName();
        }

        if (StringUtils.isNotBlank(userUpdateDTO.getPassword())) {
            this.password = userUpdateDTO.getPassword();
        }
    }
}
