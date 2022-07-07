package com.seventeam.algoritmgameproject.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.seventeam.algoritmgameproject.domain.model.TestCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.seventeam.algoritmgameproject.domain.model.QTestCase.testCase;

@Repository
@RequiredArgsConstructor
public class TestCaseDslRepository {
    private final JPAQueryFactory query;

    public List<TestCase> getTestCases(Long id) {
        return query
                .selectFrom(testCase)
                .where(testCase.question.id.eq(id))
                .limit(5)
                .fetch();
    }
}
