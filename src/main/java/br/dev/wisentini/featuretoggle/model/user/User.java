package br.dev.wisentini.featuretoggle.model.user;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

import java.sql.Date;

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

    @CreatedDate
    @Column(name = "created_at", insertable = false, updatable = false, nullable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedAt;
}
