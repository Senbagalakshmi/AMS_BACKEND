package com.bbots.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashGen {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // Change "admin123" to whatever password you want to hash
        String result = encoder.encode("admin123");
        System.out.println("Generated Hash: " + result);
    }
}

