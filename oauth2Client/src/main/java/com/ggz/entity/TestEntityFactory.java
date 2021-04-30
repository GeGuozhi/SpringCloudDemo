package com.ggz.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityFactory  extends JpaRepository<TestEntity,Long> {
    User findByUsername(String username);
}
