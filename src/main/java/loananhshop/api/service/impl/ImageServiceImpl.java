package loananhshop.api.service.impl;

import loananhshop.api.model.Image;
import loananhshop.api.repository.ImageRepository;
import loananhshop.api.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public void save(Image image) {
        imageRepository.save(image);
    }

    @Override
    public Image findByName(String filename) {
        return imageRepository.findByImageName(filename);
    }

    @Override
    public void deleteById(Long imageId) {
        imageRepository.deleteById(imageId);
    }

    @Override
    public List<Image> findAll() {
        return imageRepository.findAll();
    }
}
