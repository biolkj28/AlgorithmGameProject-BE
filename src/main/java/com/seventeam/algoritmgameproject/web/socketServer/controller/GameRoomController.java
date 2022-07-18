package com.seventeam.algoritmgameproject.web.socketServer.controller;

import com.seventeam.algoritmgameproject.config.HttpCode;
import com.seventeam.algoritmgameproject.web.socketServer.Dto.CreateRoomRequestDto;
import com.seventeam.algoritmgameproject.web.socketServer.Dto.EnterRoomRequestDto;
import com.seventeam.algoritmgameproject.web.socketServer.model.GameRoom;
import com.seventeam.algoritmgameproject.web.socketServer.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/game")
public class GameRoomController {

    private final GameService service;

    @GetMapping("/room")
    @Operation(summary = "Test UI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_OK, description = "응답완료"),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_FORBIDDEN, description = "로그인 필요", content = @Content),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_BADREQUEST, description = "요청 데이터 오류", content = @Content),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_SERVERERROR, description = "서버 오류", content = @Content)
    })
    public String rooms() {
        return "/chat/room";
    }


    @GetMapping("/rooms")
    @Operation(summary = "방 목록 가져오기 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_OK, description = "방 목록 반환",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GameRoom.class)))} ),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_FORBIDDEN, description = "로그인 필요", content = @Content),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_BADREQUEST, description = "요청 데이터 오류", content = @Content),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_SERVERERROR, description = "서버 오류", content = @Content)
    })
    @Parameters(value = {
            @Parameter(in = ParameterIn.PATH,name = "langIdx",description ="0:JAVA, 1:JS, 2:PYTHON3" ),
            @Parameter(in = ParameterIn.PATH,name = "levelIdx",description ="0:EASY, 1:NORMAL, 2:HARD" )
    })
    public ResponseEntity<?> getRooms(@RequestParam int langIdx, @RequestParam int levelIdx) {
        return new ResponseEntity<>(service.findRooms(langIdx, levelIdx), HttpStatus.OK);
    }


    @GetMapping("/room/{roomId}")
    @ResponseBody
    @Operation(summary = "방 정보 가져오기 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_OK, description = "응답완료",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameRoom.class))}),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_FORBIDDEN, description = "로그인 필요", content = @Content),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_BADREQUEST, description = "요청 데이터 오류", content = @Content),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_SERVERERROR, description = "서버 오류", content = @Content)
    })
    @Parameters(value = {
            @Parameter(in = ParameterIn.PATH,name = "langIdx",description ="0:JAVA, 1:JS, 2:PYTHON3" ),
            @Parameter(in = ParameterIn.PATH,name = "levelIdx",description ="0:EASY, 1:NORMAL, 2:HARD" ),
            @Parameter(in = ParameterIn.PATH,name = "roomId",description ="URI path에 추가" )
    })
    public GameRoom roomInfo(@RequestParam int langIdx, @RequestParam int levelIdx, @PathVariable String roomId) {
        return service.findRoom(langIdx, levelIdx, roomId);
    }

    // 방 crud
    @GetMapping("/room/enter/{roomId}")
    @Operation(summary = "DetailTest UI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_OK, description = "페이지 반환", content = @Content),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_FORBIDDEN, description = "로그인 필요", content = @Content),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_BADREQUEST, description = "요청 데이터 오류", content = @Content),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_SERVERERROR, description = "서버 오류", content = @Content)
    })
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "chat/roomdetail";
    }

    @PostMapping("/room/create")
    @Operation(summary = "방 생성 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_OK, description = "방 생성",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameRoom.class))}),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_FORBIDDEN, description = "로그인 필요", content = @Content),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_BADREQUEST, description = "요청 데이터 오류", content = @Content),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_SERVERERROR, description = "서버 오류", content = @Content)
    })
    @Parameter(in = ParameterIn.PATH,name = "CreateRoomRequestDto",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CreateRoomRequestDto.class))} )
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomRequestDto dto) {
        log.info(dto.toString());
        return new ResponseEntity<>(service.createRoom(dto.getLangIdx(), dto.getLevelIdx()), HttpStatus.OK);
    }

    @PostMapping("/room/enter")
    @Operation(summary = "방 입장 처리 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_OK, description = "입장 처리: TRUE, 입장 불가: FASLE",content = @Content),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_FORBIDDEN, description = "로그인 필요", content = @Content),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_BADREQUEST, description = "요청 데이터 오류", content = @Content),
            @ApiResponse(responseCode = HttpCode.HTTPSTATUS_SERVERERROR, description = "서버 오류", content = @Content)
    })
    @Parameter(in = ParameterIn.PATH,name = "EnterRoomRequestDto",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = EnterRoomRequestDto.class))} )
    public ResponseEntity<?> enterRoom(@RequestBody EnterRoomRequestDto dto) {
        log.info("Enter:{}", dto.toString());
        if (service.isEnter(dto.getServer(), dto.getRoomId())) {
            service.enterRoom(dto);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}
