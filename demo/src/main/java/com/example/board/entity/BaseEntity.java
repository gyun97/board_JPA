package com.example.board.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// 작성 시간, 수정 시간 등 시간에 대한 데이터를 별도로 다루는 Entity 클래스


/*
Audit은 감시하다, 감사하다라는 뜻으로 Spring Data JPA에서 시간에 대해서 자동으로 값을 넣어주는 기능입니다.
도메인을 영속성 컨텍스트에 저장하거나 조회를 수행한 후에 update를 하는 경우 매번 시간 데이터를 입력하여 주어야 하는데,
audit을 이용하면 자동으로 시간을 매핑하여 데이터베이스의 테이블에 넣어주게 됩니다.
 */

@MappedSuperclass   // 해당 클래스를 상속받는 클래스에게 매핑 정보만 제공하고 실제 테이블은 만들지 않는다.
@EntityListeners(AuditingEntityListener.class)  // JPA Entity에서 이벤트가 발생할 때마다 특정 로직을 실행
@Getter
public class BaseEntity {

    @CreationTimestamp // 생성 시간
    @Column(updatable = false) // update(수정)시에는 해당 컬럼은 관여하지 않는다.
    private LocalDateTime createdTime;

    @UpdateTimestamp // 업데이트(수정) 시간
    @Column(insertable = false) // insert(입력(삽입))시에는 해당 컬럼은 관여하지 않는다.
    private LocalDateTime updatedTime;
}
