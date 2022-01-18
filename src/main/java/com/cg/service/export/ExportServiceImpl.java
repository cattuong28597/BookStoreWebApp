package com.cg.service.export;

import com.cg.model.Export;
import com.cg.repository.ExportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExportServiceImpl implements ExportService {

    @Autowired
    private ExportRepository exportRepository;


    @Override
    public List<Export> findAll() {
        return exportRepository.findAll();
    }

    @Override
    public Optional<Export> findById(Long id) {
        return exportRepository.findById(id);
    }

    @Override
    public Export getById(Long id) {
        return exportRepository.getById(id);
    }

    @Override
    public Export save(Export export) {
        return exportRepository.save(export);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public Integer sumQuatityWithIdProduct(Long id) {
        try{
            return exportRepository.sumQuatityWithIdProduct(id);
        }catch (Exception e){
            return 0;
        }
    }
}
