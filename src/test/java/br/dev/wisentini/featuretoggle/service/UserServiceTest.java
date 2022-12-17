package br.dev.wisentini.featuretoggle.service;

import br.dev.wisentini.featuretoggle.dto.UserCreationDTO;
import br.dev.wisentini.featuretoggle.dto.UserUpdateDTO;
import br.dev.wisentini.featuretoggle.exception.MissingFieldsException;
import br.dev.wisentini.featuretoggle.exception.ResourceNotFoundException;
import br.dev.wisentini.featuretoggle.model.User;
import br.dev.wisentini.featuretoggle.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldFindUserByIdWhenItExists() {
        int userId = 21;
        User user = new User(userId, "Marcos", "abacaxi", LocalDate.now(), null);

        when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> this.userService.findById(userId));
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUserDoesNotExists() {
        int userId = 88;

        when(this.userRepository.findById(userId)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> this.userService.findById(userId));
    }

    @Test
    void shouldReturnUsersWhenPresent() {
        List<User> users = List.of(
            new User(5341, "João", "banana", LocalDate.now(), null),
            new User(2434, "Maria", "uva", LocalDate.now().minusDays(6741), LocalDate.now()),
            new User(6968, "Pedro", "maçã", LocalDate.now().minusDays(457), LocalDate.now().minusDays(8))
        );

        when(this.userRepository.findAll()).thenReturn(users);

        assertDoesNotThrow(() -> this.userService.findAll());
    }

    @Test
    void shouldReturnEmptyListWhenNoUserIsFound() {
        List<User> users = new ArrayList<>();

        when(this.userRepository.findAll()).thenReturn(users);

        assertDoesNotThrow(() -> this.userService.findAll());
    }

    @Test
    void shouldCreateUserWhenNameAndPasswordAreProvided() {
        String userName = "Fernanda";
        String userPassword = "banana";

        UserCreationDTO userCreationDTO = new UserCreationDTO(userName, userPassword);
        User user = new User(777, userName, userPassword, LocalDate.now(), null);

        when(this.userRepository.save(user)).thenReturn(user);

        assertDoesNotThrow(() -> this.userService.save(userCreationDTO));
    }

    @Test
    void shouldThrowMissingFieldsExceptionWhenOnlyNameIsProvidedOnCreation() {
        UserCreationDTO userCreationDTO = new UserCreationDTO("Fernanda", null);

        assertThrows(MissingFieldsException.class, () -> this.userService.save(userCreationDTO));
    }

    @Test
    void shouldThrowMissingFieldsExceptionWhenOnlyPasswordIsProvidedOnCreation() {
        UserCreationDTO userCreationDTO = new UserCreationDTO(null, "banana");

        assertThrows(MissingFieldsException.class, () -> this.userService.save(userCreationDTO));
    }

    @Test
    void shouldThrowMissingFieldsExceptionWhenNameOrPasswordAreNotProvidedOnCreation() {
        UserCreationDTO userCreationDTO = new UserCreationDTO();

        assertThrows(MissingFieldsException.class, () -> this.userService.save(userCreationDTO));
    }

    @Test
    void shouldUpdateUserWhenNameAndPasswordAreProvided() {
        int userId = 684;
        User user = new User(userId, "Marcos", "polenta", LocalDate.now(), null);
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO("Marcos Visentini", "limão");

        when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(this.userRepository.save(user)).thenReturn(user);

        assertDoesNotThrow(() -> this.userService.update(userId, userUpdateDTO));
    }

    @Test
    void shouldUpdateUserWhenOnlyNameIsProvided() {
        int userId = 914;
        User user = new User(userId, "Marcos", "polenta", LocalDate.now(), null);
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO("Marcos Visentini", null);

        when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(this.userRepository.save(user)).thenReturn(user);

        assertDoesNotThrow(() -> this.userService.update(userId, userUpdateDTO));
    }

    @Test
    void shouldUpdateUserWhenOnlyPasswordIsProvided() {
        int userId = 684;
        User user = new User(userId, "Marcos", "polenta", LocalDate.now(), null);
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(null, "limão");

        when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(this.userRepository.save(user)).thenReturn(user);

        assertDoesNotThrow(() -> this.userService.update(userId, userUpdateDTO));
    }

    @Test
    void shouldThrowMissingFieldsExceptionWhenNameAndPasswordAreNotProvidedOnUpdate() {
        int userId = 34;
        User user = new User(userId, "Marcos", "polenta", LocalDate.now(), null);
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();

        when(this.userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(this.userRepository.save(user)).thenReturn(user);

        assertThrows(MissingFieldsException.class, () -> this.userService.update(userId, userUpdateDTO));
    }

    @Test
    void shouldDeleteUserByIdWhenItExists() {
        int userId = 33;

        when(this.userRepository.existsById(userId)).thenReturn(true);

        doNothing().when(this.userRepository).deleteById(userId);

        assertDoesNotThrow(() -> this.userService.deleteById(userId));
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUserToDeleteDoesNotExists() {
        int userId = 646;

        when(this.userRepository.existsById(userId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> this.userService.deleteById(userId));
    }
}
