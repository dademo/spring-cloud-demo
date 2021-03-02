package fr.dademo.test.springclouddemo.producer;

import org.springframework.http.HttpStatus;

public class APIFetchException extends RuntimeException {

    public APIFetchException(String url, HttpStatus code) {
        super(String.format("Received code [%d] (%s) on url [%s]", code.value(), code.getReasonPhrase(), url));
    }
}
