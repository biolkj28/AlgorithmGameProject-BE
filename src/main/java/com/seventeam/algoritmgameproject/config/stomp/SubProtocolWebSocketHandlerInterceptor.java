package com.seventeam.algoritmgameproject.config.stomp;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import org.springframework.web.socket.WebSocketSession;

public class SubProtocolWebSocketHandlerInterceptor extends DelegatingIntroductionInterceptor {
    @Override
    protected Object doProceed(MethodInvocation mi) throws Throwable {
        if (mi.getMethod().getName().equals("afterConnectionEstablished")){
            WebSocketSession session = (WebSocketSession) mi.getArguments()[0];
            session.setTextMessageSizeLimit(64*1024*2);//128kb
        }
        return super.doProceed(mi);
    }
}
