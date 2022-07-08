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
    //이미 만들어진 테스트 케이스 출력문이 들어간  문자열과 답변의 순서 보장을 위해 정렬 후 데이터 가져오기
    public List<TestCase> getTestCases(Long id) {
        return query
                .selectFrom(testCase)
                .where(testCase.question.id.eq(id))
                .orderBy(testCase.id.asc())
                .limit(5)
                .fetch();
    }
}
