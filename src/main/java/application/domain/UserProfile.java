package application.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserProfile implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Integer bonus;
    private Boolean active;
    private String firstName;
    private String lastName;
    @ElementCollection(targetClass = Product.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "UserProfile_basket", joinColumns = @JoinColumn(name = "UserProfile_id"))
    @NonNull
    private List<Product> basket;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_profile_roles", joinColumns = @JoinColumn(name = "user_profile_id"))
    @Enumerated(EnumType.STRING)
    @NonNull
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    public boolean isAdmin() {
        return getRoles().contains(Role.ADMIN);
    }
}
