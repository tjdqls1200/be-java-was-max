package was.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import was.common.HttpMethod;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class WebRequestTest {
    @DisplayName("올바른 Request를 보내면 객체가 성공적으로 생성된다.")
    @Test
    void test() throws IOException {
        //given
        String requestBody = "hello";
        final int contentLength = requestBody.length();

        String testRequest = "" +
                "GET /index.html HTTP/1.1\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Content-length: " + contentLength + "\n" +
                "Accept: */*\n" +
                "\n" +
                "hello\n";

        var parseHeaders = Map.of(
                "host", List.of("localhost:8080"),
                "connection", List.of("keep-alive"),
                "content-length", List.of(String.valueOf(contentLength)),
                "accept", List.of("*/*")
        );

        BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(testRequest.getBytes())));

        //when
        HttpRequest request = HttpRequest.parse(br);

        //then
        assertAll(
                () -> assertThat(request.getMethod()).isEqualTo(HttpMethod.GET),
                () -> assertThat(request.getUrl()).isEqualTo("/index.html"),
                () -> assertThat(request.getVersion()).isEqualTo("HTTP/1.1"),
                () -> assertThat(request.getHeaders()).isEqualTo(parseHeaders),
                () -> assertThat(request.getRequestBody()).isEqualTo("hello")
        );

    }
}