package com.montanha.gerenciador.controller;

import com.montanha.gerenciador.dtos.ViagemDto;
import com.montanha.gerenciador.entities.Viagem;
import com.montanha.gerenciador.responses.Response;
import com.montanha.gerenciador.services.ViagemServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.awt.*;
import java.net.URI;
import java.util.List;

//import org.apache.coyote.Response;

@RestController
@RequestMapping("/api/viagens")
public class GerenciadorViagensController {

    @Autowired
    private ViagemServices viagemServices;

    //POST para cadastramento
    @PostMapping(path = "/new")
    public ResponseEntity<Response<Viagem>> cadastrar(@Valid @RequestBody ViagemDto viagemDto, BindingResult result){
        Response<Viagem> response = new Response<Viagem>();

        if(result.hasErrors()){
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Viagem viagemSalva = this.viagemServices.salvar(viagemDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(viagemDto.getId()).toUri();
        response.setData(viagemSalva);
        return ResponseEntity.created(location).body(response);
    }

    //GET para listagem
    @GetMapping
    public ResponseEntity<List<Viagem>> listar(){
        List<Viagem> viagens = viagemServices.listar();
        return ResponseEntity.status(HttpStatus.OK).body(viagens);
    }

    //GET by Id
    @GetMapping(path = "/{id}")
    public ResponseEntity<Response<Viagem>> buscar(@PathVariable("id") Long id) throws Exception {
        Viagem viagem = viagemServices.buscar(id);
        Response<Viagem> response = new Response<Viagem>();
        response.setData(viagem);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //DELETE by id
    @DeleteMapping(path = "/{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) throws Exception {
        viagemServices.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Response<Viagem>> atualizar(@RequestBody ViagemDto viagemDto, @PathVariable("id") Long id) throws Exception {
        viagemDto.setId(id);
        Viagem viagem = viagemServices.atualizar(viagemDto);
        Response<Viagem> response = new Response<Viagem>();
        response.setData(viagem);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
