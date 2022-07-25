package com.seventeam.algoritmgameproject.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GameRoomException {

    //조회 오류
    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> gameRoomFindException(NullPointerException ex) {
        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {IllegalStateException.class})
    public ResponseEntity<Object> gameRoomCRUDException(IllegalStateException ex) {
        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }


}
