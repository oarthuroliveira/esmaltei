package dev.arthuroliv.esmaltei.service;

import dev.arthuroliv.esmaltei.exception.RegraNegocioException;
import org.flywaydb.core.api.resource.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImagemService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.base-url}")
    private String baseUrl;

    public String salvar(MultipartFile arquivo, String pasta) {

        if (arquivo == null || arquivo.isEmpty()) {
            return null;
        }

        String contentType = arquivo.getContentType();

        if (contentType == null ||
                !(contentType.equals("image/jpeg")
                        || contentType.equals("image/png")
                        || contentType.equals("image/webp"))) {

            throw new RegraNegocioException(
                    "Formato de imagem inválido. Envie JPG, PNG ou WEBP."
            );
        }

        try {

            Path diretorio = Paths.get(uploadDir, pasta);

            Files.createDirectories(diretorio);

            String extensao = obterExtensao(arquivo.getOriginalFilename());

            String nomeArquivo = UUID.randomUUID() + "." + extensao;

            Path caminho = diretorio.resolve(nomeArquivo);

            Files.copy(
                    arquivo.getInputStream(),
                    caminho,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return pasta + "/" + nomeArquivo;

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar imagem.", e);
        }
    }

    public void excluir(String caminho) {
        if (caminho == null || caminho.isBlank()) {
            return;
        }

        try {
            Path arquivo = Paths.get(uploadDir).resolve(caminho);

            Files.deleteIfExists(arquivo);

        } catch (IOException e) {
            throw new RegraNegocioException("Erro ao excluir imagem.");
        }
    }

    private String obterExtensao(String nomeArquivo) {

        if (nomeArquivo == null || !nomeArquivo.contains(".")) {
            return "jpg";
        }

        return nomeArquivo.substring(nomeArquivo.lastIndexOf('.') + 1);
    }

    public String montarUrl(String caminho) {

        if (caminho == null || caminho.isBlank()) {
            return null;
        }

        return baseUrl + "/uploads/" + caminho;
    }
}