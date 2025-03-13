package com.github.brunomndantas.flashscore.api.dataAccess.dtoRepository;

import com.github.brunomndantas.repository4j.IRepository;
import com.github.brunomndantas.repository4j.exception.RepositoryException;

import java.util.Collection;
import java.util.function.Function;

public class DTORepository<K, E, KDTO, EDTO> implements IRepository<K, E> {

    protected IRepository<KDTO, EDTO> sourceRepository;
    protected Function<K, KDTO> keyToDTOMapper;
    protected Function<E, EDTO> entityToDTOMapper;
    protected Function<EDTO, E> dtoToEntityMapper;


    public DTORepository(
            IRepository<KDTO, EDTO> sourceRepository,
            Function<K, KDTO> keyToDTOMapper,
            Function<E, EDTO> entityToDTOMapper,
            Function<EDTO, E> dtoToEntityMapper
    ) {
        this.sourceRepository = sourceRepository;
        this.keyToDTOMapper = keyToDTOMapper;
        this.entityToDTOMapper = entityToDTOMapper;
        this.dtoToEntityMapper = dtoToEntityMapper;
    }


    @Override
    public Collection<E> getAll() throws RepositoryException {
        return sourceRepository.getAll().stream().map(dtoToEntityMapper).toList();
    }

    @Override
    public E get(K key) throws RepositoryException {
        EDTO dto = sourceRepository.get(keyToDTOMapper.apply(key));
        return dto == null ? null : dtoToEntityMapper.apply(dto);
    }

    @Override
    public void insert(E entity) throws RepositoryException {
        EDTO dto = entityToDTOMapper.apply(entity);
        sourceRepository.insert(dto);
    }

    @Override
    public void update(E entity) throws RepositoryException {
        EDTO dto = entityToDTOMapper.apply(entity);
        sourceRepository.update(dto);
    }

    @Override
    public void delete(K key) throws RepositoryException {
        sourceRepository.delete(keyToDTOMapper.apply(key));
    }

}
