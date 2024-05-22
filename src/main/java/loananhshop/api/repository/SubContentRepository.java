package loananhshop.api.repository;

import loananhshop.api.model.SubContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubContentRepository extends JpaRepository<SubContent, Long> {
}
