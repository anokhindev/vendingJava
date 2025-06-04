package com.anokhin.vending.common.dto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ApiResponse<T> {
    private HttpStatus status;
    private String message;
    private T body;

    public ApiResponse(HttpStatus status, String message, T body) {
        this.status = status;
        this.message = message;
        this.body = body;
    }

}