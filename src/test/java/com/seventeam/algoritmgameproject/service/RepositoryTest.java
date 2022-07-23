//package com.seventeam.algoritmgameproject.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.seventeam.algoritmgameproject.domain.QuestionLevel;
//import com.seventeam.algoritmgameproject.domain.model.Question;
//import com.seventeam.algoritmgameproject.domain.model.TestCase;
//import com.seventeam.algoritmgameproject.domain.model.User;
//import com.seventeam.algoritmgameproject.domain.repository.QuestionDslRepository;
//import com.seventeam.algoritmgameproject.security.repository.UserRepository;
//import com.seventeam.algoritmgameproject.web.dto.QuestionDto;
//import com.seventeam.algoritmgameproject.web.dto.QuestionRedis;
//import com.seventeam.algoritmgameproject.web.dto.TestCaseRedis;
//import com.seventeam.algoritmgameproject.web.service.compilerService.Language;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.data.redis.core.ListOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.*;
//
////@Slf4j
////@ActiveProfiles("testDB")
////@SpringBootTest
////@ExtendWith(SpringExtension.class)
////@Transactional
////@Rollback
//public class RepositoryTest {
////    @Autowired
////    UserRepository userRepository;
////
////    @Autowired
////    QuestionDslRepository repository;
////
////    @Autowired
////    QuestionDslRepository questionDslRepository;
////
////    @Autowired
////    RedisTemplate<String, Object> redisTemplate;
////
////    @Autowired
////    ObjectMapper mapper;
//
//    @Test
//    @DisplayName("승리 처리")
//    public void test0() {
//
////        User winUser = userRepository.findByUserId("biolkj28").orElseThrow(() -> new NullPointerException("없는 유저 입니다."));
////        log.info("사용자 정보: {}", winUser.toString());
////        User save = null;
////
////        winUser.win();
////        save = userRepository.save(winUser);
////
////        log.info("저장 승리 처리 사용자:{}", save.toString());
//    }
//
//    @Test
//    @DisplayName("패배 처리")
//    public void test1() {
//
////        User loseUser = userRepository.findByUserId("biolkj28").orElseThrow(() -> new NullPointerException("없는 유저 입니다."));
////        log.info("사용자 정보: {}", loseUser.toString());
////        User save = null;
////
////        loseUser.lose();
////        save = userRepository.save(loseUser);
////
////        log.info("저장 패배 처리 사용자:{}", save.toString());
//    }
//
//    @Test
//    @DisplayName("문제 데이터 캐시 처리")
//    public void test2() throws JsonProcessingException {
////        String QUESTION = "QUESTION";
////        String QS_KEYS = "QS_KEYS";
////        List<Question> allByQuestionLevel = repository.findAllByQuestionLevel(QuestionLevel.EASY);
////        ListOperations<String,Object> redisListOperations = redisTemplate.opsForList();
////        String HK = QUESTION + QuestionLevel.EASY.name();
////        for (Question question : allByQuestionLevel) {
////
////            QuestionRedis redis = new QuestionRedis();
////            BeanUtils.copyProperties(question,redis);
////            log.info("테스트케이스 갯수:{}",question.getCases().size());
////            ObjectMapper objectMapper = new ObjectMapper();
////            redisListOperations.rightPush(HK,objectMapper.writeValueAsString(redis));
////        }
//
//
//
//    }
//
//    @Test
//    @DisplayName("문제 랜덤 뽑기")
//    public void test3() throws JsonProcessingException {
//
//
//
//    }
//    @Test
//    @DisplayName("multibag")
//    public void test4() throws JsonProcessingException {
//
////        for (int i = 0; i <20 ; i++) {
////            Question randomQuestionLevel = questionDslRepository.findRandomQuestionLevel(QuestionLevel.EASY);
////        }
//
//    }
//}
