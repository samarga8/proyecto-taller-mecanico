package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Rol;
import com.gestortallermecanico.model.Usuario;
import com.gestortallermecanico.model.dao.UsuarioRegistroDTO;
import com.gestortallermecanico.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private UsuarioRepository repoUsuario;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Usuario registrarUsuario(UsuarioRegistroDTO dto) throws Exception {
        Optional<Usuario> usuario = repoUsuario.findByEmail(dto.getEmail());
        if (usuario.isPresent()) {
            throw new Exception("Estas reguistrado");
        } else {
            Usuario user = new Usuario();
            user.setEmail(dto.getEmail());
            user.setUsername(dto.getNombre());
            user.setLastname(dto.getApellido());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            List.of(new Rol("ROLE_USER"));

            return repoUsuario.save(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repoUsuario.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario o password inválido"));

        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                mapearAutoridadesRoles(usuario.getRoles()));

    }

    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }
}
