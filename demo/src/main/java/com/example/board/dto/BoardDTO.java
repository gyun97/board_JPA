package com.example.board.dto;


import com.example.board.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;


//DTO: Data Transfer Object
/*
데이터 전송을 위한 객체이다.
주로 데이터를 저장하고 전송하는 용도로 사용되며, 데이터베이스나 서비스와의 데이터 교환을 간단하게 하기 위해 사용된다.
DTO는 데이터의 속성을 저장하는데 중점을 둡니다.

데이터들을 전송할 때 따로 보내기 여의치 않을 때 하나의 객체에 데이터들을 담아 전송하는데 사용된다.
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;  // 게시글의 조회 수
    private LocalDateTime boardCreatedTime;  // 게시글 작성 시간
    private LocalDateTime boardUpdatedTime;  // 게시글 수정 시간

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
        return boardDTO;

    }

    
}
