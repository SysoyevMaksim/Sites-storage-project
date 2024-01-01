package com.sitesstorageproject.security;

public record CustomErrorMessage(String timestamp, int status, String error, String message, String path) {
}
