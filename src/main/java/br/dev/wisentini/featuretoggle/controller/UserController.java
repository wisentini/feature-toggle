package br.dev.wisentini.featuretoggle.controller;

import br.dev.wisentini.featuretoggle.dto.UserCreationDTO;
import br.dev.wisentini.featuretoggle.dto.UserRetrievalDTO;
import br.dev.wisentini.featuretoggle.dto.UserUpdateDTO;
import br.dev.wisentini.featuretoggle.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserRetrievalDTO findById(@PathVariable("id") int id) {
        return this.userService.findById(id);
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserRetrievalDTO> findAll() {
        return this.userService.findAll();
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void save(@Valid @RequestBody UserCreationDTO userCreationDTO) {
        this.userService.save(userCreationDTO);
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void update(@PathVariable("id") int id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        this.userService.update(id, userUpdateDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void delete(@PathVariable("id") int id) {
        this.userService.deleteById(id);
    }
}
