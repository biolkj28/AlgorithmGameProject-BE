package com.seventeam.algoritmgameproject.domain.repository;

import com.seventeam.algoritmgameproject.domain.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
}
