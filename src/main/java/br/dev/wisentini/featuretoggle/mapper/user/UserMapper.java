package br.dev.wisentini.featuretoggle.mapper.user;

import br.dev.wisentini.featuretoggle.dto.user.UserCreationDTO;
import br.dev.wisentini.featuretoggle.dto.user.UserRetrievalDTO;
import br.dev.wisentini.featuretoggle.model.user.User;

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
