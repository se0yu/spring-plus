package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN t.user " +
            "WHERE t.id = :todoId")
    Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);

    //1. RequestParam 에 조회에 사용할 날씨, 검색 날짜(시작일,종료일) 추가
    //2. TodoService 메서드에 매개변수 추가
    //3. 관련 메서드 repository 에 추가
    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u " +
            "WHERE (:weather IS NULL OR t.weather = :weather)"+
            "AND (:startDate IS NULL OR t.modifiedAt >= :startDate)"+
            "AND (:endDate IS NULL OR t.modifiedAt <= :endDate)"+
            "ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByWeatherAndModifiedAt(String weather, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
