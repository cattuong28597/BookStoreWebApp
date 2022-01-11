package com.cg.service.importT;

import com.cg.model.Import;
import com.cg.repository.ImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImportServiceImpl implements ImportService {

    @Autowired
    private ImportRepository importRepository;


    @Override
    public List<Import> findAll() {
        return importRepository.findAll();
    }

    @Override
    public Optional<Import> findById(Long id) {
        return importRepository.findById(id);
    }

    @Override
    public Import getById(Long id) {
        return importRepository.getById(id);
    }

    @Override
    public Import save(Import anImport) {
        return importRepository.save(anImport);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Integer sumQuatityWithIdProduct(Long id_product) {
        return importRepository.sumQuatityWithIdProduct(id_product);
    }
}
