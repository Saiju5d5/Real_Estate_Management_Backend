package com.realestate.rems.model;

/**
 * Standard API response structure
 */
public class ApiResponse {

    private boolean success;
    private String message;

    // ✅ NO-ARG CONSTRUCTOR
    public ApiResponse() {
    }

    // ✅ REQUIRED CONSTRUCTOR (THIS FIXES YOUR ERROR)
    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // ===== GETTERS & SETTERS =====

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
