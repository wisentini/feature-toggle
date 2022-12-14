package br.dev.wisentini.featuretoggle.service;

import br.dev.wisentini.featuretoggle.dto.UserCreationDTO;
import br.dev.wisentini.featuretoggle.dto.UserRetrievalDTO;
import br.dev.wisentini.featuretoggle.dto.UserUpdateDTO;
import br.dev.wisentini.featuretoggle.exception.MissingFieldsException;
import br.dev.wisentini.featuretoggle.exception.ResourceNotFoundException;
import br.dev.wisentini.featuretoggle.mapper.UserMapper;
import br.dev.wisentini.featuretoggle.model.User;
import br.dev.wisentini.featuretoggle.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserRetrievalDTO findById(int id) {
        Optional<User> response = this.userRepository.findById(id);

        if (response.isEmpty()) {
            String message = String.format("User with ID %d not found.", id);
            throw new ResourceNotFoundException(message);
        }

        return this.mapper.toUserRetrievalDTO(response.get());
    }

    public List<UserRetrievalDTO> findAll() {
        return this.userRepository
            .findAll()
            .stream()
            .map(this.mapper::toUserRetrievalDTO)
            .collect(Collectors.toList());
    }

    public void update(int id, UserUpdateDTO userUpdateDTO) {
        Optional<User> response = this.userRepository.findById(id);

        if (response.isEmpty()) {
            String message = String.format("User with ID %d not found.", id);
            throw new ResourceNotFoundException(message);
        }

        if (userUpdateDTO.getName() == null && userUpdateDTO.getPassword() == null) {
            String message = "A name, password or both must be provided in order to update a user.";
            throw new MissingFieldsException(message);
        }

        User oldUser = response.get();

        if (userUpdateDTO.getName() != null) {
            oldUser.setName(userUpdateDTO.getName());
        }

        if (userUpdateDTO.getPassword() != null) {
            oldUser.setPassword(userUpdateDTO.getPassword());
        }

        this.userRepository.save(oldUser);
    }

    public void save(UserCreationDTO userCreationDTO) {
        this.userRepository.save(this.mapper.toUser(userCreationDTO));
    }

    public void deleteById(int id) {
        if (!this.userRepository.existsById(id)) {
            String message = String.format("User with ID %d not found.", id);
            throw new ResourceNotFoundException(message);
        }

        this.userRepository.deleteById(id);
    }
}
