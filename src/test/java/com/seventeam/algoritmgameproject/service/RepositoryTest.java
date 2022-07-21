package com.seventeam.algoritmgameproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seventeam.algoritmgameproject.domain.QuestionLevel;
import com.seventeam.algoritmgameproject.domain.model.Question;
import com.seventeam.algoritmgameproject.domain.model.TestCase;
import com.seventeam.algoritmgameproject.domain.model.User;
import com.seventeam.algoritmgameproject.domain.repository.QuestionDslRepository;
import com.seventeam.algoritmgameproject.security.repository.UserRepository;
import com.seventeam.algoritmgameproject.web.dto.QuestionDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@ActiveProfiles("testDB")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
@Rollback
public class RepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    QuestionDslRepository repository;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    ObjectMapper mapper;

    @Test
    @DisplayName("승리 처리")
    public void test0() {

        User winUser = userRepository.findByUserId("biolkj28").orElseThrow(() -> new NullPointerException("없는 유저 입니다."));
        log.info("사용자 정보: {}", winUser.toString());
        User save = null;

        winUser.win();
        save = userRepository.save(winUser);

        log.info("저장 승리 처리 사용자:{}", save.toString());
    }

    @Test
    @DisplayName("패배 처리")
    public void test1() {

        User loseUser = userRepository.findByUserId("biolkj28").orElseThrow(() -> new NullPointerException("없는 유저 입니다."));
        log.info("사용자 정보: {}", loseUser.toString());
        User save = null;

        loseUser.lose();
        save = userRepository.save(loseUser);

        log.info("저장 패배 처리 사용자:{}", save.toString());
    }

    @Test
    @DisplayName("문제 데이터 캐시 처리")
    public void test2() throws JsonProcessingException {
        String QUESTION = "QUESTION";
        List<Question> allByQuestionLevel = repository.findAllByQuestionLevel(QuestionLevel.EASY);
        HashOperations<String, Long, String> opsForHash = redisTemplate.opsForHash();
        for (Question question : allByQuestionLevel) {
            String qs = mapper.writeValueAsString(question);
            log.info(qs);
//            레벨로 찾게
//            난이도, id

            opsForHash.put(QUESTION+QuestionLevel.EASY.name(),question.getId(),qs);
            String qsString = opsForHash.get(QUESTION + QuestionLevel.EASY.name(), question.getId());
            log.info(qsString);
        }
    }

//    @Test
//    @DisplayName("문제  캐시 확인")
//    public void test3() throws JsonProcessingException {
//        String QUESTION = "QUESTION";
//        HashOperations<String, Long, String> opsForHash = redisTemplate.opsForHash();
//        String HK = QUESTION + QuestionLevel.EASY.name();
//        Set<Long> keys = opsForHash.keys(HK);
//        List<Long> keyList = new LinkedList<>(keys);
//        Collections.shuffle(keyList);
//        Long qsId = keyList.get(0);
//
//        String s = opsForHash.get(HK, qsId);
//        Question question = mapper.readValue(s, Question.class);
//        log.info(question.toString());
//
//    }
}
