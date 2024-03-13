package com.example.board.entity;

// Entity Class: DB의 테이블 역할을 하는 클래스(별도로 DB에 테이블 만들 필요 X)
// Service와 Repsitory에서만 사용한다

import com.example.board.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity // 해당 클래스 Entity로 사용함
@Getter
@Setter
@Table(name = "board_table") // 테이블 이름 지정하고 싶으면 사용
public class BoardEntity extends BaseEntity{
    
    @Id // PK 컬럼 지정(필수)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 설정
    private Long id;


    @Column(length = 20, nullable = false) // 일반 컬럼. 데이터 크기 20, NOT NULL 설정
    private String boardWriter;

    @Column // 일반 컬럼의 기본값은 데이터 크기 255, nullable = true. (unique = true도 가능하다)
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    // DTO 객체에 담긴 값들을 Entity 객체로 옮겨 담는 클래스
    public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        return boardEntity;

    }

    public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());  // save와 달리 update는 Id가 있어야만 update 쿼리가 실행 가능
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        return boardEntity;
    }
}
