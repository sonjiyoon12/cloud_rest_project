package com.cloud.cloud_rest.board;

import com.cloud.cloud_rest.board.board_tag.QBoardTag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Board> findBySearchOption(Pageable pageable, BoardRequestDto.SearchDTO searchDTO) {
        QBoard board = QBoard.board;
        QBoardTag boardTag = QBoardTag.boardTag;

        // 1. 데이터 조회 쿼리
        List<Board> boards = queryFactory
                .select(board).distinct()
                .from(board)
                .leftJoin(board.tags, boardTag)
                .where(
                        // 검색 조건들을 동적으로 조합
                        keywordContains(searchDTO.getKeyword()),
                        tagsIn(searchDTO.getTags())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(board.createdAt.desc())
                .fetch();

        // 2. 전체 카운트 쿼리 (페이지네이션용)
        Long total = queryFactory
                .select(board.countDistinct())
                .from(board)
                .leftJoin(board.tags, boardTag)
                .where(
                        keywordContains(searchDTO.getKeyword()),
                        tagsIn(searchDTO.getTags())
                )
                .fetchOne();

        return new PageImpl<>(boards, pageable, total);
    }

    // --- 조건문 메서드들 ---

    // 키워드(제목, 내용) 검색 조건
    private BooleanExpression keywordContains(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return null; // keyword가 없으면 조건 무시
        }
        return QBoard.board.title.contains(keyword)
                .or(QBoard.board.content.contains(keyword));
    }

    // 태그 검색 조건
    private BooleanExpression tagsIn(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return null; // tags가 없으면 조건 무시
        }
        return QBoardTag.boardTag.name.in(tags);
    }
}
