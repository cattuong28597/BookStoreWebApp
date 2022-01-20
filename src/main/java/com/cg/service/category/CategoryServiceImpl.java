package com.cg.service.category;

import com.cg.exception.DataInputException;
import com.cg.model.Category;
import com.cg.model.Product;
import com.cg.model.ProductImage;
import com.cg.model.dto.CategoryDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.repository.CategoryRepository;
import com.cg.service.upload.UploadService;
import com.cg.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private UploadUtils uploadUtils;


    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.getById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }


    @Override
    public Boolean existsBySlugEquals(String slug) {
        return categoryRepository.existsBySlugEquals(slug);
    }

    @Override
    public Boolean existsBySlugEqualsAndIdIsNot(String slug, long id) {
        return categoryRepository.existsBySlugEqualsAndIdIsNot(slug, id);
    }

    @Override
    public Iterable<Category> findAllByDeletedIsFalse() {
        return categoryRepository.findAllByDeletedIsFalse();
    }


    @Override
    public Category create(CategoryDTO categoryDTO) {
//        String fileType = categoryDTO.getImageFile().getContentType();
//
//        assert fileType != null;
//
//        fileType = fileType.substring(0, 5);

        Category category = categoryDTO.toCategory();

        uploadAndSaveCategoryImage(categoryDTO,category) ;

        return category;
    }

    @Override
    public void remove(Long id) {
         categoryRepository.deleteById(id);
    }


    private void uploadAndSaveCategoryImage(CategoryDTO categoryDTO, Category category) {
        try {
            Map uploadResult = uploadService.uploadImage(categoryDTO.getImageFile(), uploadUtils.buildImageUploadParamsCategory(categoryDTO));
            String fileUrl = (String) uploadResult.get("secure_url");

            category.setImage(fileUrl);

            if(categoryDTO.getId() != null){
                category.setId(categoryDTO.getId());
                categoryRepository.save(category);
            }
            categoryRepository.save(category);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    private MultipartFile setImageForDTO(String path){
        File file = new File(path);
        MultipartFile multipartFile = (MultipartFile) file;
        return multipartFile;
    }
}
