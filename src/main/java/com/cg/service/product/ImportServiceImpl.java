package com.cg.service.product;

import com.cg.model.Import;
import com.cg.model.Product;
import com.cg.repository.ImportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImportServiceImpl implements ImportService{

    @Autowired
    ImportRepository importRepository;

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
    public int SumOfImportQuantityByProduct(Product product) {
        return importRepository.SumOfImportQuantityByProduct(product);
    }
}
