package com.edaochina.shopping.web.live;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.*;
import org.yeauty.pojo.ParameterMap;
import org.yeauty.pojo.Session;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * LiveWebSocket
 *
 * @author wangpenglei
 * @since 2019/8/21 13:55
 */
@ServerEndpoint(path = "/live", port = 8092)
@Component
public class LiveWebSocket {

    /**
     * 房间id映射SessionIdSet
     */
    private static Map<String, Set<Session>> idSetMap = new ConcurrentHashMap<>(16);

    /**
     * SessionId映射房间id
     */
    private static Map<String, String> roomIdMap = new ConcurrentHashMap<>(32);

    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, ParameterMap parameterMap) {
        String roomId = parameterMap.getParameter("roomId");
        if (roomId == null) {
            session.close();
            return;
        }
        putSession(session, roomId);
    }

    @OnClose
    public void onClose(Session session) {
        removeSession(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        sendTextMessage(session, message);
    }

    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        sendBinaryMessage(session, bytes);
    }

    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    System.out.println("read idle");
                    break;
                case WRITER_IDLE:
                    System.out.println("write idle");
                    break;
                case ALL_IDLE:
                    System.out.println("all idle");
                    break;
                default:
                    break;
            }
            removeSession(session);
        }
    }

    private void putSession(Session session, String roomId) {
        String id = getId(session);
        roomIdMap.put(id, roomId);
        idSetMap.compute(roomId, (s, sessions) -> {
            if (sessions == null) {
                sessions = new CopyOnWriteArraySet<>();
            }
            sessions.add(session);
            return sessions;
        });
    }

    private void removeSession(Session session) {
        String id = getId(session);
        idSetMap.computeIfPresent(roomIdMap.get(id), (s, sessions) -> {
            sessions.remove(session);
            return sessions;
        });
        roomIdMap.remove(id);
    }

    private void sendTextMessage(Session session, String message) {
        idSetMap.get(roomIdMap.get(getId(session)))
                .forEach(s -> s.sendText(message));
    }

    static void sendTextMessage(String message) {
        idSetMap.forEach((s, sessions) -> sessions.forEach(session -> session.sendText(message)));
    }

    private void sendBinaryMessage(Session session, byte[] bytes) {
        idSetMap.get(roomIdMap.get(getId(session)))
                .forEach(s -> s.sendBinary(bytes));
    }

    private String getId(Session session) {
        return session.id().asShortText();
    }

}
