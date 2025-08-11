package com.cloud.cloud_rest.corporate;

import com.cloud.cloud_rest.corporate.corporate_tag.QCorporateTag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class CorporateRepositoryImpI implements CorporateRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CorporatePost> findBySearchOption(Pageable pageable, CorporatePostRequestDto.SearchDTO searchDTO) {
        QCorporatePost corporatePost = QCorporatePost.corporatePost;
        QCorporateTag corporateTag = QCorporateTag.corporateTag;

        // 1. 데이터 조회 쿼리
        List<CorporatePost> corporatePosts = queryFactory
                .select(corporatePost).distinct()
                .from(corporatePost)
                .leftJoin(corporatePost.tags, corporateTag)
                .where(
                        // 검색 조건들을 동적으로 조합
                        keywordContains(searchDTO.getKeyword()),
                        tagsIn(searchDTO.getCorporateTags())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(corporatePost.createdAt.desc())
                .fetch();

        // 2. 전체 카운트 쿼리 (페이지네이션용)
        Long total  = queryFactory
                .select(corporatePost.countDistinct())
                .from(corporatePost)
                .leftJoin(corporatePost.tags, corporateTag)
                .where(
                        keywordContains(searchDTO.getKeyword()),
                        tagsIn(searchDTO.getCorporateTags())
                )
                .fetchOne();

        return new PageImpl<>(corporatePosts, pageable, total);
    }

    // 조건문 메서드

    // 키워드(제목,내용) 검색 조건
    private BooleanExpression keywordContains(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return null; // keyword가 없으면 조건 무시
        }
        return QCorporatePost.corporatePost.title.contains(keyword)
                .or(QCorporatePost.corporatePost.content.contains(keyword));
    }

    // 태그 검색 조건
    private BooleanExpression tagsIn(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return null; // tags가 없으면 조건 무시
        }
        return QCorporateTag.corporateTag.tagName.in(tags);
    }
}
