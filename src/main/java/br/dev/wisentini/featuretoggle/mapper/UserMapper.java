package br.dev.wisentini.featuretoggle.mapper;

import br.dev.wisentini.featuretoggle.dto.UserCreationDTO;
import br.dev.wisentini.featuretoggle.dto.UserRetrievalDTO;
import br.dev.wisentini.featuretoggle.model.User;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserRetrievalDTO toUserRetrievalDTO(User user) {
        return new UserRetrievalDTO(
            user.getId(),
            user.getName(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }

    public User toUser(UserCreationDTO userCreationDTO) {
        return new User(
            null,
            userCreationDTO.getName(),
            userCreationDTO.getPassword(),
            null,
            null
        );
    }
}
