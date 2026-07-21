package dev.arthuroliv.esmaltei.service;

import dev.arthuroliv.esmaltei.domain.Seguidor;
import dev.arthuroliv.esmaltei.domain.Usuario;
import dev.arthuroliv.esmaltei.exception.RegraNegocioException;
import dev.arthuroliv.esmaltei.repository.SeguidorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeguidorService {

    private final SeguidorRepository seguidorRepository;
    private final UsuarioService usuarioService;

    public SeguidorService(SeguidorRepository seguidorRepository,
                           UsuarioService usuarioService) {
        this.seguidorRepository = seguidorRepository;
        this.usuarioService = usuarioService;
    }

    public void seguir(Usuario seguidor, Long seguidoId) {

        if (seguidor.getId().equals(seguidoId)) {
            throw new RegraNegocioException("Você não pode seguir a si mesmo.");
        }

        Usuario seguido =
                usuarioService.buscarEntidadePorId(seguidoId);

        Optional<Seguidor> relacao =
                seguidorRepository.findBySeguidorIdAndSeguidoId(
                        seguidor.getId(),
                        seguido.getId());

        if (relacao.isPresent()) {

            seguidorRepository.delete(relacao.get());

        } else {

            Seguidor novo = new Seguidor();
            novo.setSeguidor(seguidor);
            novo.setSeguido(seguido);

            seguidorRepository.save(novo);
        }
    }

    public long contarSeguidores(Long usuarioId) {
        return seguidorRepository.countBySeguidoId(usuarioId);
    }

    public long contarSeguindo(Long usuarioId) {
        return seguidorRepository.countBySeguidorId(usuarioId);
    }

}
