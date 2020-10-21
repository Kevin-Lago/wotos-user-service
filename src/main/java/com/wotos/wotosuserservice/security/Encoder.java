package com.wotos.wotosuserservice.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class Encoder implements PasswordEncoder {

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(
            10,
            new SecureRandom()
    );

    @Override
    public String encode(CharSequence charSequence) {
        String encodedPassword = encoder.encode(charSequence);
        return encodedPassword;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return encoder.matches(charSequence, s);
    }
}
