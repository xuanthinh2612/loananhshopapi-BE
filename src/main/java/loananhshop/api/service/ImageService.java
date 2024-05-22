package loananhshop.api.service;

import loananhshop.api.model.Image;

import java.util.List;

public interface ImageService {

    void save(Image image);

    Image findByName(String filename);

    void deleteById(Long imageId);

    List<Image> findAll();
}
