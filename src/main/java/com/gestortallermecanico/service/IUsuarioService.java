package com.gestortallermecanico.service;

import com.gestortallermecanico.model.Usuario;
import com.gestortallermecanico.model.dao.UsuarioRegistroDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUsuarioService extends UserDetailsService {

    Usuario registrarUsuario(UsuarioRegistroDTO dto) throws Exception;
}
