package love.huhu.platform.client;

import lombok.RequiredArgsConstructor;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.URI;

//@Component
//@RequiredArgsConstructor
public class WebsocketClient {

    @Value("${api.manager-server}")
    String managerServerApi;

    @Bean("managerWebsocketClient")
    public WebSocketClient managerWebsocketClient() {
        try {
            WebSocketClient webSocketClient = new WebSocketClient(new URI("ws://"+managerServerApi+"/webSocket/1")) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("ws 连接成功");
                }

                @Override
                public void onMessage(String message) {
                    System.out.println("ws 收到消息"+message);

                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("ws 退出");
                }

                @Override
                public void onError(Exception ex) {
                    System.out.println("连接错误"+ex.getMessage());
                }
            };
            webSocketClient.connect();
            return webSocketClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
