package dev.arthuroliv.esmaltei.service;

import dev.arthuroliv.esmaltei.domain.Esmalte;

import dev.arthuroliv.esmaltei.dto.request.EsmalteFiltroRequest;
import dev.arthuroliv.esmaltei.dto.request.EsmalteRequest;

import dev.arthuroliv.esmaltei.dto.response.EsmalteResponse;

import dev.arthuroliv.esmaltei.exception.RegraNegocioException;
import dev.arthuroliv.esmaltei.repository.EsmalteRepository;
import dev.arthuroliv.esmaltei.specification.EsmalteSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EsmalteService {

    public final EsmalteRepository esmalteRepository;

    public EsmalteService(EsmalteRepository esmalteRepository) {
        this.esmalteRepository = esmalteRepository;
    }

    public EsmalteResponse cadastrar(EsmalteRequest esmalteRequest){
        if (esmalteRequest.nome() != null && esmalteRequest.marca() != null &&
                esmalteRepository.existsByNomeIgnoreCaseAndMarcaIgnoreCase(
                        esmalteRequest.nome(),
                        esmalteRequest.marca()
                )){
            throw new RegraNegocioException("Já existe um esmalte cadastrado com esse nome e marca.");
        }

        Esmalte esmalte = esmalteRequest.toEntity();
        Esmalte esmalteSalvo = esmalteRepository.save(esmalte);
        return EsmalteResponse.fromEntity(esmalteSalvo);
    }

    public Page<EsmalteResponse> listar(EsmalteFiltroRequest filtro , Pageable pageable){
        return esmalteRepository.findAll(EsmalteSpecification.comFiltros(filtro), pageable).map(EsmalteResponse::fromEntity);
    }

    public EsmalteResponse buscarPorId(Long id){
        Esmalte esmalte = buscarEntidadePorId(id);
        return EsmalteResponse.fromEntity(esmalte);
    }

    public EsmalteResponse atualizar(Long id, EsmalteRequest esmalteRequest){
        if (esmalteRequest.nome() != null && esmalteRequest.marca() != null &&
                esmalteRepository.existsByNomeIgnoreCaseAndMarcaIgnoreCaseAndIdNot(
                        esmalteRequest.nome(),
                        esmalteRequest.marca(),
                        id
                )){
            throw new RegraNegocioException("Já existe um esmalte cadastrado com esse nome e marca.");
        }
        Esmalte esmalte = buscarEntidadePorId(id);
        esmalteRequest.preencher(esmalte);
        Esmalte esmalteAtualizado = esmalteRepository.save(esmalte);
        return EsmalteResponse.fromEntity(esmalteAtualizado);
    }

    public void excluir(Long id){
        Esmalte esmalte = buscarEntidadePorId(id);
        esmalteRepository.delete(esmalte);
    }

    private Esmalte buscarEntidadePorId(Long id){
        return esmalteRepository.findById(id).orElseThrow(()-> new RegraNegocioException("Esmalte não encontrado"));
    }

    public List<Esmalte> buscarListaEntidadePorId(List<Long> ids) {

        List<Esmalte> esmaltes = esmalteRepository.findAllById(ids);

        if (esmaltes.size() != ids.size()) {
            throw new RegraNegocioException("Um ou mais esmaltes informados não existem.");
        }

        return esmaltes;
    }
}
