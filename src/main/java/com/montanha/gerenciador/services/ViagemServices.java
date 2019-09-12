package com.montanha.gerenciador.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.montanha.gerenciador.dtos.ViagemDto;
import com.montanha.gerenciador.entities.Viagem;
import com.montanha.gerenciador.repositories.ViagemRepository;

@Service
public class ViagemServices {

    @Autowired
    private ViagemRepository viagemRepository;

    public List<Viagem> listar(){
            return viagemRepository.findAll();
    }

    public Viagem salvar(ViagemDto viagemDto){
        Viagem viagem = new Viagem();

        viagem.setLocalDeDestino(viagemDto.getLocalDeDestino());
        viagem.setDataPartida(viagemDto.getDataPartida());
        viagem.setDataRetorno(viagemDto.getDataRetorno());
        viagem.setAcompanhante(viagemDto.getAcompanhante());
        viagem.setAcompanhante(viagemDto.getAcompanhante());
        return viagemRepository.save(viagem);
    }

    public Viagem buscar(Long id) throws Exception {
        Viagem viagem = viagemRepository.findOne(id);

        if(viagem == null){
            throw new Exception("Não existe esta viagem cadastrada");
        }
        return viagem;
    }

    public void deletar(Long id) throws Exception {
        Viagem viagem = viagemRepository.findOne(id);

        if (viagem == null) {
            throw new Exception("Não existe esta viagem cadastrada");
        }
        viagemRepository.delete(viagem);
    }

    public Viagem atualizar(ViagemDto viagemDto) throws Exception {
        Viagem viagem = viagemRepository.findOne(viagemDto.getId());
        if (viagem == null){
            throw new Exception("Não existe esta viagem cadastrada");
        }
        viagem.setLocalDeDestino(viagemDto.getLocalDeDestino());
        viagem.setDataPartida(viagemDto.getDataPartida());
        viagem.setDataRetorno(viagemDto.getDataRetorno());
        viagem.setAcompanhante(viagemDto.getAcompanhante());
        viagem.setAcompanhante(viagemDto.getAcompanhante());

        return viagemRepository.save(viagem);
    }
}
