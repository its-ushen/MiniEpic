package com.example.authentication;

public interface IAuthentication {
    boolean signup(String username, String password) throws Exception;
    boolean check(String username, String password) throws Exception;
}
