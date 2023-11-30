package com.example;
import com.example.database.DBAuthentication;
import com.example.gui.LoginGUI;

public class Driver {
    public static void main(String[] args) {
        new LoginGUI(new DBAuthentication());
    }
}
