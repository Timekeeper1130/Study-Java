package socket;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SocketTest {

    EchoClient client;

    /**
     * 单次通信
     */
    @Test
    public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException {

        GreetClient client = new GreetClient();
        client.startConnection("127.0.0.1", 6667);

        String response = client.sendMessage("hello server");
        assertEquals("hello client", response);
    }

    /**
     * 任意一个Test方法执行前都会执行@Before方法
     */
    @Before
    public void setup() throws IOException {
        client = new EchoClient();
        client.startConnection("127.0.0.1", 4444);
    }

    /**
     * 任意一个Test方法执行完毕后都会执行@After方法
     */
    @After
    public void tearDown() throws IOException {
        client.stopConnection();
    }

    /**
     * 单个客户端，服务端循环等待客户端输入
     */
    @Test
    public void givenClient_whenServerEchosMessage_thenCorrect() throws IOException {
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        String resp3 = client.sendMessage("!");
        String resp4 = client.sendMessage(".");

        assertEquals("hello", resp1);
        assertEquals("world", resp2);
        assertEquals("!", resp3);
        assertEquals("bye", resp4);
    }
}
