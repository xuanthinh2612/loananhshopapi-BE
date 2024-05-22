package loananhshop.api.repository;

import loananhshop.api.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByImageName(String fileName);
}
