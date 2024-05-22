package loananhshop.api.service;

import loananhshop.api.model.Inquiry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InquiryService extends CommonService<Inquiry> {

    List<Inquiry> findAllByStatus(int status);
}
