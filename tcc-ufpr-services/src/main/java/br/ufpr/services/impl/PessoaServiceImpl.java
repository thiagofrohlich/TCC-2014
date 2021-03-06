package br.ufpr.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufpr.domain.Pessoa;
import br.ufpr.exception.NoResultFoundException;
import br.ufpr.repository.PessoaRepository;
import br.ufpr.services.AbstractCrudService;
import br.ufpr.services.PessoaService;
import br.ufpr.util.AssertUtils;

@Service
@Transactional
public class PessoaServiceImpl extends AbstractCrudService<Pessoa, Integer> implements PessoaService {

	@Autowired
	public PessoaServiceImpl(PessoaRepository repository) {
		super(repository);
	}

	@Override
	public Pessoa findPessoaByCpf(String cpf) throws NoResultFoundException {
		Pessoa pessoa = getPessoaRepository().findByCpf(cpf);
		AssertUtils.assertIsFound(pessoa);
		return pessoa;
	}

	@Override
	public List<Pessoa> findPessoaByNome(String nome) throws NoResultFoundException {
		List<Pessoa> pessoas = getPessoaRepository().findByNomeContaining(nome);
		AssertUtils.assertIsFound(pessoas);
		return pessoas;
	}
	
	private PessoaRepository getPessoaRepository() {
		return (PessoaRepository) repository;
	}

}
