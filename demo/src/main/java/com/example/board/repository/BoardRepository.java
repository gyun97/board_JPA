package com.example.board.repository;

import com.example.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// JpaRepository<엔티티 클래스 이름, PK의 데이터 타입>

// Repository는 기본적으로 Entity 클래스만 받아준다.
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    // 조회 수 증가: update board_table set board_hits=board_hits + 1 where id=?
    // => 작업을 수행하는 쿼리 필요

    @Modifying  // @Query 어노테이션을 통해 작성된 변경이 일어나는 쿼리(INSERT, DELETE, UPDATE )에 필수
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits + 1 where b.id=:id")
    // 이 쿼리는 Entity 기준의 쿼리문이지만 쿼리 안에 nativeQuery = true 옵션을 주면 DB 기준 쿼리를 사용할 수 있다.
    // Entity 기준의 쿼리문에서는 테이블 자리에 Entity 이름이 오고  Entity 이름은 Alias가 필수이다.
    //Entity 기준의 쿼리문에서는 DB의 컬럼 자리에 Entity 클래스에서 정의한 컬럼 이름이 온다.
    public void updateHits(@Param("id") Long id); // @Param의 id는 :id와 매칭된다.

}
