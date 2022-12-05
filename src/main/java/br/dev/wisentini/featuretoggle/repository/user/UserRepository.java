package br.dev.wisentini.featuretoggle.repository.user;

import br.dev.wisentini.featuretoggle.model.user.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
