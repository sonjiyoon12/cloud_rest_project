package com.cloud.cloud_rest.corporate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CorporateRepositoryCustom {
    Page<CorporatePost> findBySearchOption(Pageable pageable, CorporatePostRequestDto.SearchDTO searchDTO);

}
