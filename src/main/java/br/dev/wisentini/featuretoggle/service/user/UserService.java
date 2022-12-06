package br.dev.wisentini.featuretoggle.service.user;

import br.dev.wisentini.featuretoggle.dto.user.UserCreationDTO;
import br.dev.wisentini.featuretoggle.dto.user.UserRetrievalDTO;
import br.dev.wisentini.featuretoggle.dto.user.UserUpdateDTO;
import br.dev.wisentini.featuretoggle.exception.MissingAttributesException;
import br.dev.wisentini.featuretoggle.exception.ResourceNotFoundException;
import br.dev.wisentini.featuretoggle.mapper.user.UserMapper;
import br.dev.wisentini.featuretoggle.model.user.User;
import br.dev.wisentini.featuretoggle.repository.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

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
            throw new MissingAttributesException(message);
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
