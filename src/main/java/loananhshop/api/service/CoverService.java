package loananhshop.api.service;

import loananhshop.api.model.Cover;

import java.util.List;

public interface CoverService extends CommonService<Cover> {
    List<Cover> getMainCoverList();

    List<Cover> getSubCoverList();

}
