package dev.stratospheric.todo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
  List<Todo> findAllByOwnerEmailOrderByIdAsc(String email);
}
