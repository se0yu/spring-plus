package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.entity.QTodo;

import java.util.Optional;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        QTodo todo = QTodo.todo;
        // n+1 문제 방지용 fetchJoin 사용
        Todo result = jpaQueryFactory.selectFrom(todo)
                .join(todo.user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
