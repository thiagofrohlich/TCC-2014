package br.ufpr.rest;

import java.io.Serializable;

import org.dozer.Mapper;

import br.ufpr.domain.DomainObject;
import br.ufpr.model.BusinessModel;
import br.ufpr.services.CrudService;

public abstract class AbstractRestController<M extends BusinessModel, D extends DomainObject, ID extends Serializable> implements RestController<M, ID> {

	protected Mapper mapper;
	protected CrudService<D, ID> service;

	public AbstractRestController(Mapper mapper, CrudService<D, ID> crudService) {
		this.mapper = mapper;
		this.service = crudService;
		
	}
	
}
