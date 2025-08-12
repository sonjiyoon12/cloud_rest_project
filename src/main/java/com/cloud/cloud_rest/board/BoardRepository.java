package com.cloud.cloud_rest.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b JOIN FETCH b.user u LEFT JOIN FETCH b.tags t ORDER BY b.createdAt DESC")
    List<Board> findAllWithUserAndTags();

    @Query("SELECT b FROM Board b JOIN FETCH b.user u LEFT JOIN FETCH b.tags t LEFT JOIN FETCH b.comments c WHERE b.boardId = :id")
    Optional<Board> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT DISTINCT b FROM Board b JOIN FETCH b.user u LEFT JOIN b.tags t " +
            "WHERE (:keyword IS NULL OR b.title LIKE %:keyword% OR b.content LIKE %:keyword%) " +
            "AND (:tags IS NULL OR t.name IN :tags) ORDER BY b.createdAt DESC")
    List<Board> search(@Param("keyword") String keyword, @Param("tags") List<String> tags);
}