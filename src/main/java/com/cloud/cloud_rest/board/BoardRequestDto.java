package com.cloud.cloud_rest.board;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {
    private String title;
    private String content;
    private Long userId;

    @Builder
    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .views(0) // 새 게시물이니까 조회수를 0 으로 초기 설정
                .build();
    }

    /**
     * dto의 기존 데이터를 board 엔티티에 적용하여 수정함
     *
     * @param board
     */
    public void applyTo(Board board) {
        board.setTitle(this.title);
        board.setContent(this.content);
        // userId는 게시글 생성시에만 필요 --> 수정시에는 변경하지않음
        // board.setUserId(this.userId);
    }

}
