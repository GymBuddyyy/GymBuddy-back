package java.org.example.gymbuddyback.config;


import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import sun.misc.SignalHandler;

@EnableWebSocket
public class WebRtcConfig implements WebSocketConfigurer {
    private final SignalHandler signalHandler;

    public WebRtcConfig(SignalHandler signalHandler) {
        this.signalHandler = signalHandler;
    }

}
