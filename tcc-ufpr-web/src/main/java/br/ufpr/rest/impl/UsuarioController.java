package br.ufpr.rest.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufpr.domain.Pessoa;
import br.ufpr.exception.MissingIdException;
import br.ufpr.exception.NoResultFoundException;
import br.ufpr.exception.NotDeletedObjectException;
import br.ufpr.exception.NullParameterException;
import br.ufpr.model.Usuario;
import br.ufpr.services.CrudService;
import br.ufpr.services.UserService;
import br.ufpr.util.AssertUtils;

@Controller
@RequestMapping("/usuario")
public class UsuarioController extends AbstractPessoaController<Usuario, br.ufpr.domain.Usuario, Integer> {

	@Autowired
	public UsuarioController(Mapper mapper, CrudService<br.ufpr.domain.Usuario, Integer> usuarioService, CrudService<Pessoa, Integer> pessoaService) {
		super(mapper, usuarioService, pessoaService);
	}

	@Override
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST)
	public Usuario create(@RequestBody Usuario model) throws NullParameterException {
		AssertUtils.assertParameterIsSupplied(model);
		br.ufpr.domain.Usuario domain = mapper.map(model, br.ufpr.domain.Usuario.class);
		domain.setPessoa(createPessoa(model));
		domain = crudService.create(domain);
		
		return mapToModel(domain);
	}

	@Override
	@ResponseBody
	@RequestMapping(method=RequestMethod.PUT)
	public Usuario update(@RequestBody Usuario model) throws MissingIdException, NullParameterException {
		AssertUtils.assertParameterIsSupplied(model);
		br.ufpr.domain.Usuario domain = mapper.map(model, br.ufpr.domain.Usuario.class);
		domain.setPessoa(findPessoa(model.getPessoaId()));
		domain = crudService.update(domain);
		
		return mapToModel(domain);
	}
	
	@Override
	@RequestMapping(method=RequestMethod.DELETE)
	public void delete(@RequestBody Usuario model) throws NullParameterException, NotDeletedObjectException, NoResultFoundException {
		AssertUtils.assertParameterIsSupplied(model);
		br.ufpr.domain.Usuario domain = mapper.map(model, br.ufpr.domain.Usuario.class);
		crudService.delete(domain.getId());
	}
	
	@Override
	@ResponseBody
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Usuario find(@PathVariable final Integer id) throws NullParameterException,
			NoResultFoundException {
		AssertUtils.assertParameterIsSupplied(id);
		br.ufpr.domain.Usuario domain = crudService.find(id);
		AssertUtils.assertIsFound(domain);
		return mapToModel(domain);
	}
	
	@RequestMapping(value="/password/encode/{password}", method=RequestMethod.GET)
	public String encodePassword(@PathVariable final String password) throws NullParameterException {
		AssertUtils.assertParameterIsSupplied(password);
		return getUserService().encodePassword(password);
	}
	
	@RequestMapping(value="/login/{login}/{password}", method=RequestMethod.GET)
	public boolean canLogin(@PathVariable final String login, @PathVariable final String password) {
		return getUserService().canLogin(login, password);
	}
	
	private Usuario mapToModel(br.ufpr.domain.Usuario usuarioDomain) {
		Usuario usuario = mapper.map(usuarioDomain, Usuario.class);
		mapper.map(usuarioDomain.getPessoa(), usuario);
		return usuario;
	}
	
	private UserService getUserService() {
		return (UserService) crudService;
	}

}