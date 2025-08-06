package com.cloud.cloud_rest.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    Page<Board> findBySearchOption(Pageable pageable, BoardRequestDto.SearchDTO searchDTO);
}
