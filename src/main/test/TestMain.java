import org.junit.Assert;
import org.junit.Test;
import sockets.GreetClient;

import java.io.IOException;

public class TestMain {

    @Test
    public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException {

        GreetClient client = new GreetClient();
        client.startConnection("127.0.0.1", 6667);

        String response = client.sendMessage("hello server");
        Assert.assertEquals("hello client", response);
    }
}
