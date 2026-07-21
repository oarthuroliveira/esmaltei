package dev.arthuroliv.esmaltei.security;

import dev.arthuroliv.esmaltei.domain.Usuario;
import dev.arthuroliv.esmaltei.dto.request.LoginRequest;
import dev.arthuroliv.esmaltei.dto.response.LoginResponse;
import dev.arthuroliv.esmaltei.exception.RegraNegocioException;
import dev.arthuroliv.esmaltei.repository.UsuarioRepository;
import dev.arthuroliv.esmaltei.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByEmailOrUsername(request.login(), request.login())
                .orElseThrow(() -> new RegraNegocioException("Login ou senha inválidos"));

        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw new RegraNegocioException("Email ou senha inválidos");
        }

        String token = jwtService.gerarToken(usuario);

        return new LoginResponse(token);
    }
}