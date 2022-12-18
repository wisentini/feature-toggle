package br.dev.wisentini.featuretoggle.service;

import br.dev.wisentini.featuretoggle.dto.UserCreationDTO;
import br.dev.wisentini.featuretoggle.dto.UserRetrievalDTO;
import br.dev.wisentini.featuretoggle.dto.UserUpdateDTO;
import br.dev.wisentini.featuretoggle.exception.ResourceNotFoundException;
import br.dev.wisentini.featuretoggle.mapper.UserMapper;
import br.dev.wisentini.featuretoggle.model.User;
import br.dev.wisentini.featuretoggle.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserRetrievalDTO findById(int id) {
        return this.userRepository
            .findById(id)
            .map(UserMapper::toUserRetrievalDTO)
            .orElseThrow(() -> {
                throw new ResourceNotFoundException(String.format("User with ID %d not found.", id));
            }
        );
    }

    public List<UserRetrievalDTO> findAll() {
        return this.userRepository
            .findAll()
            .stream()
            .map(UserMapper::toUserRetrievalDTO)
            .collect(Collectors.toList());
    }

    public void update(int id, UserUpdateDTO userUpdateDTO) {
        User user = this.userRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(String.format("User with ID %d not found.", id));
        });

        user.update(userUpdateDTO);

        this.userRepository.save(user);
    }

    public void save(UserCreationDTO userCreationDTO) {
        userCreationDTO.validate();

        this.userRepository.save(UserMapper.toUser(userCreationDTO));
    }

    public void deleteById(int id) {
        if (!this.userRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("User with ID %d not found.", id));
        }

        this.userRepository.deleteById(id);
    }
}
