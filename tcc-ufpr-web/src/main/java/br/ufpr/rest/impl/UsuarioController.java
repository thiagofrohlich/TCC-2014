package br.ufpr.rest.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.ufpr.domain.Pessoa;
import br.ufpr.model.Usuario;
import br.ufpr.services.CrudService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController extends AbstractPessoaController<Usuario, br.ufpr.domain.Usuario> {

	@Autowired
	public UsuarioController(Mapper mapper, CrudService<br.ufpr.domain.Usuario> usuarioService, CrudService<Pessoa> pessoaService) {
		super(mapper, usuarioService, pessoaService);
	}

	@Override
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST)
	public Usuario create(@RequestBody Usuario model) {
		
		br.ufpr.domain.Usuario domain = mapper.map(model, br.ufpr.domain.Usuario.class);
		domain.setPessoa(createPessoa(model));
		domain = crudService.create(domain);
		
		return mapToModel(domain);
	}

	private Usuario mapToModel(br.ufpr.domain.Usuario usuarioDomain) {
		Usuario usuario = mapper.map(usuarioDomain, Usuario.class);
		mapper.map(usuarioDomain.getPessoa(), usuario);
		return usuario;
	}

}
