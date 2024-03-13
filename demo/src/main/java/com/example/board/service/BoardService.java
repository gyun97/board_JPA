package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) {
     /*
     boardRepository의 "save" 메서드 호출해서 DB에 insert한다.
     save는 기본적으로 entity 타입의 파라미터만 받고 리턴 타입도 entity이다.
     따라서 Service 에서는 곧잘 DTO -> Entity 또는 Entity -> DTO 변환 작업이 필요하다
     Controller로부터 호출되어 데이터 받을 때에는 DTO로 받고 Repository에 넘길 때에는 Entity로 넘겨준다.
     반대로 DB에서 데이터 조회할 때에는 Repository로부터 데이터를 Entity로 받고 DTO로 변환해서 Controller에 넘긴다.
     */

    // DTO -> Entity 변환 과정은 Entity 클래스에서 수행
    // Entity -> DTO 변환 과정은 DTO 클래스에서 수행

        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);

    }


    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();  // 해당 리포지토리의 모든 엔티티를 조회하는 메소드
        List<BoardDTO> boardDTOList = new ArrayList<>();
        /* Repository로 데이터를 가져올 때에는 데이터가 Entity로 넘어오는데
           이러한 Entity를 DTO 객체에 옮겨담아서 컨트롤러에 리턴해야 한다.*/
        // => BoardEntity의 데이터를 BoardDTO에 옮겨 담는다.

        // BoardEntity 데이터들 하나씩 꺼내서 DTO로 변환
        for (BoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));

        }

        return boardDTOList; // DTO를 컨트롤러에 반환한다. -> 컨트롤러는 Model에 담아서 list.html 뷰에 넘긴다.

    }


    @Transactional // JPA에서 제공하는 메서드가 아니라 이용자가 별도로 정의한 메서드라면 붙여야 에러가 나지 않는다.
    // 데이터베이스 트랜잭션을 관리 및 메서드 내에서 발생하는 작업이 모두 성공하거나 실패할 경우 트랜잭션을 커밋 또는 롤백
    public void updateHits(Long id) {
        // 조회 수 증가같은 특수한 목적의 메서드는 JPA에서 제공하는 메서드가 없어 개발자가 별도로 정의해야 한다.
        boardRepository.updateHits(id);

    }


    /*
    Optional은 자바에서 null을 다루는 데 사용되는 컨테이너 클래스입니다.
    Optional 클래스는 값이 있을 수도 있고 없을 수도 있는 상황에서, 명시적으로 값이 없음을 표현하고자 할 때 유용하게 사용됩니다.
    이를 통해 null을 다루는 데 발생하는 일반적인 문제 중 하나인 NullPointerException을 방지하고 코드를 안전하게 작성할 수 있습니다.
     isPresent(): Not Null인 경우
     isEmpty(): null인 경우
     */

    public BoardDTO findByID(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {
        /*
        JPA에서는 JpaRepository의 save()로 insert뿐만 아니라 update도 수행한다!
        insert와 update를 구분하는 방법은 id 값의 유무이다.
        insert할 때에는 id 값이 존재하지 않고, update할 때에는 id 값이 존재한다.
         */

        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);

        // Optional 처리해주는 findById 메서드 재사용
        return findByID(boardDTO.getId()); // 해당 게시글의 상세 정보 조회 정보 컨트롤러에 ㅑ넘겨준다
    }
}
