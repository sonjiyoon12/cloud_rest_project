package com.cloud.cloud_rest.corporate;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpRepository;
import com.cloud.cloud_rest.corporate.corporate_tag.CorporateTag;
import com.cloud.cloud_rest.corporate.corporate_tag.CorporateTagRepository;
import com.cloud.cloud_rest.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorporatePostService {

    private final CorporatePostRepository corporatePostRepository;
    private final CorpRepository corpRepository;
    private final CorporateTagRepository corporateTagRepository; 
    private final UserRepository userRepository;

    @Transactional
    public void savePost(CorporatePostRequestDto.SaveDto saveDTO, SessionUser sessionUser) {
        Corp author = corpRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다: " + sessionUser.getId()));

        CorporatePost post = CorporatePost.builder()
                .title(saveDTO.getTitle())
                .content(saveDTO.getContent())
                .author(author)
                .build();
        corporatePostRepository.save(post);

        // 태그 저장
        saveTags(post, saveDTO.getTags());
    }

    public List<CorporatePostResponseDto.ListDto> findAll() {
        return corporatePostRepository.findAllWithAuthorByOrderByCreatedAtDesc().stream()
                .map(CorporatePostResponseDto.ListDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CorporatePostResponseDto.DetailDto findById(Long id) {
        CorporatePost post = findByPostById(id);
        post.increaseViewCount();
        return new CorporatePostResponseDto.DetailDto(post);
    }

    @Transactional
    public void updatePost(Long id, CorporatePostRequestDto.UpdateDto updateDTO, SessionUser sessionUser) {
        CorporatePost post = findByPostById(id);
        checkOwnership(post, sessionUser);

        post.update(updateDTO.getTitle(), updateDTO.getContent());
        // 태그 업데이트
        updateTags(post, updateDTO.getTags());
    }

    @Transactional
    public void deletePost(Long id, SessionUser sessionUser) {
        CorporatePost post = findByPostById(id);
        checkOwnership(post, sessionUser);
        corporatePostRepository.delete(post);
    }

    /**
     * 게시물 조회
     */
    private CorporatePost findByPostById(Long id) {
        return corporatePostRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다: " + id));
    }

    /**
     * 게시물 소유권 확인
     */
    private void checkOwnership(CorporatePost post, SessionUser sessionUser) {
        if (!Objects.equals(post.getAuthor().getCorpId(), sessionUser.getId())) {
            throw new SecurityException("해당 게시물에 대한 권한이 없습니다");
        }
    }

    // 태그 저장 로직
    private void saveTags(CorporatePost post, List<String> tagNames) {
        if (tagNames != null && !tagNames.isEmpty()) {
            for (String tagName : tagNames) {
                CorporateTag tag = CorporateTag.builder()
                        .name(tagName)
                        .corporatePost(post)
                        .build();
                post.addTag(tag);
            }
        }
    }

    // 태그 업데이트 로직
    private void updateTags(CorporatePost post, List<String> newTagNames) {
        post.clearTags();
        // 새로운 태그 저장
        saveTags(post, newTagNames);
    }

    // 태그 및 키워드 검색
    public List<CorporatePostResponseDto.ListDto> searchPosts(CorporatePostRequestDto.SearchDTO searchDTO) {
        String keyword = searchDTO.hasKeyword() ? searchDTO.getKeyword() : null;
        List<String> tags = searchDTO.hasTags() ? searchDTO.getCorporateTags() : null;

        List<CorporatePost> posts = corporatePostRepository.searchPosts(keyword, tags);
        return posts.stream()
                .map(CorporatePostResponseDto.ListDto::new)
                .collect(Collectors.toList());
    }
}