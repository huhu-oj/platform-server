/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package love.huhu.platform.websocket;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import love.huhu.platform.authorization.AuthorizationRequired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * @author ZhangHouYing
 * @date 2019-08-10 15:46
 */
@ServerEndpoint("/test/{id}")
@Slf4j
@Component
public class TestServer {

	/**
	 * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	 */
	private static CopyOnWriteArraySet<TestServer> webSocketSet = new CopyOnWriteArraySet<TestServer>();

	/**
	 * 与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	private Session session;

	private Long id;

	public List<TestServer> getTestSession(Long testId) {
		return webSocketSet.stream().filter(ws-> Objects.equals(ws.id, testId)).collect(Collectors.toList());
	}

	/**
	 * 连接建立成功调用的方法
	 * */
	@OnOpen
	@AuthorizationRequired
	public void onOpen(Session session, @PathParam("id") Long id) {
		this.session = session;
		this.id = id;
		//如果存在就先删除一个，防止重复推送消息
//		for (TestServer webSocket:webSocketSet) {
//			if (webSocket.id.equals(id)) {
//				webSocketSet.remove(webSocket);
//			}
//		}
		webSocketSet.add(this);
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		webSocketSet.remove(this);
	}

	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息*/
	@OnMessage
	public void onMessage(String message, Session session) {
		log.info("收到的信息:"+message);
		//群发消息
		for (TestServer item : webSocketSet) {
			item.sendMessage(message);
		}
	}

	@OnError
	public void onError(Session session, Throwable error) {
		log.error("发生错误");
		error.printStackTrace();
	}
	/**
	 * 实现服务器主动推送
	 */
	public void sendMessage(String message) {
		try {
			this.session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * 群发自定义消息
	 * */
	public static void sendInfo(SocketMsg socketMsg) throws IOException {
		String message = JSONUtil.toJsonStr(socketMsg);
		log.info("推送消息到，推送内容:"+message);
		for (TestServer item : webSocketSet) {
			item.sendMessage(message);
		}
	}
}
