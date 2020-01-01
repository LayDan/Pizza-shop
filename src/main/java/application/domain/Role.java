package application.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, Manager, USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
