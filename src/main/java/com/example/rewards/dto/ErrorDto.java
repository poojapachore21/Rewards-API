package com.example.rewards.dto;

import java.time.LocalDateTime;

public class ErrorDto {
    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ErrorDto() {}

    public ErrorDto(int status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    // getters & setters
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setStatus(int status) { this.status = status; }
    public void setMessage(String message) { this.message = message; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
