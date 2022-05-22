package com.example.lovehotelcleaningservice.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    MODERATOR,
    CLEANER;

    @Override
    public String getAuthority() {
        return name();
    }
}
