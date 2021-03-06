package br.ufpr.services.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.domain.Pessoa;
import br.ufpr.domain.Usuario;
import br.ufpr.exception.NoResultFoundException;
import br.ufpr.repository.UsuarioRepository;
import br.ufpr.services.AbstractCrudService;
import br.ufpr.services.UserService;
import br.ufpr.util.AssertUtils;

@Service
@Transactional
public class UsuarioService extends AbstractCrudService<Usuario, Integer> implements UserService {

	private static final String SECURITY_TYPE = "MD5";

	@Autowired
	public UsuarioService(UsuarioRepository repository) {
		super(repository);
	}

	@Override
	public String encodePassword(String password) {
		MessageDigest md;
		StringBuffer sb = new StringBuffer();
		try {
	        md = MessageDigest.getInstance(SECURITY_TYPE);
	        md.reset();
	        byte[] digested = md.digest(password.getBytes());
	        for(int i = 0; i < digested.length; i++){
	            sb.append(Integer.toHexString(0xff & digested[i]));
	        }
	    } catch (NoSuchAlgorithmException ex) {
	    	throw new RuntimeException(ex);
	    }
		return sb.toString();
	}

	@Override
	public boolean canLogin(String login, String password) {
		if(isEmptyString(login) || isEmptyString(password)) {
			return false;
		}
		
		Usuario usuario = findByLogin(login);
		if(usuario == null) {
			return false;
		}
		
		return encodePassword(password).equals(
				usuario.getSenha());
	}
	
	@Override
	public Usuario findByLogin(String login) {
		return ((UsuarioRepository) repository).findByLogin(login);
	}
	
	@Override
	public Usuario findByPessoa(Pessoa pessoa) throws NoResultFoundException {
		Usuario usuario = getRepository().findByPessoa(pessoa);
		AssertUtils.assertIsFound(usuario);
		return usuario;
	}
	
	private boolean isEmptyString(String parameter) {
		return parameter == null || parameter.trim().equals("");
	}
	
	private UsuarioRepository getRepository() {
		return (UsuarioRepository) repository;
	}

}
