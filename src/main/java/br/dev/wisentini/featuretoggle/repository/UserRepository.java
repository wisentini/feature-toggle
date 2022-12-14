package br.dev.wisentini.featuretoggle.repository;

import br.dev.wisentini.featuretoggle.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
