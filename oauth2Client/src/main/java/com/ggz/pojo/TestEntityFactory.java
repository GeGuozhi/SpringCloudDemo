package com.ggz.pojo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityFactory  extends JpaRepository<TestEntity,Long> {
    User findByName(String name);
}
