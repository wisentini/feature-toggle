package br.dev.wisentini.featuretoggle.model;

import br.dev.wisentini.featuretoggle.dto.FeatureUpdateDTO;

import lombok.*;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "feature")
@Table(name = "feature", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Feature {

    @Id
    @Column(name = "feature_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "active", nullable = false)
    @Getter(value = AccessLevel.NONE)
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible")
    private User responsible;

    @Column(name = "created_at", insertable = false, updatable = false, nullable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    public boolean isActive() {
        return this.active;
    }

    public void update(@NonNull FeatureUpdateDTO featureUpdateDTO) {
        featureUpdateDTO.validate();

        if (StringUtils.isNotBlank(featureUpdateDTO.getName())) {
            this.name = featureUpdateDTO.getName();
        }

        if (Objects.nonNull(featureUpdateDTO.isActive())) {
            this.active = featureUpdateDTO.isActive();
        }
    }
}
