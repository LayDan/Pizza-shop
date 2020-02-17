package application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ApiModel(value = "class User")
public class UserProfile implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;


    @ApiModelProperty(value = "username")
    @Size(min = 2, max = 50)
    private String username;

    @ApiModelProperty(value = "password")
    @NotBlank()
    private String password;

    @ApiModelProperty(value = "bonus")
    private Integer bonus;

    @ApiModelProperty(value = "active")
    private Boolean active;

    @ApiModelProperty(value = "firstName")
    @NotBlank()
    private String firstName;

    @ApiModelProperty(value = "lastName")
    @NotBlank()
    private String lastName;
    @Email
    @NotBlank()
    @ApiModelProperty(value = "mail")
    private String mail;
    @ApiModelProperty(value = "activationCode")
    private String activationCode;

    @ElementCollection(targetClass = Basket.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "UserProfile_basket", joinColumns = @JoinColumn(name = "UserProfile_id"))
    @NonNull
    @ApiModelProperty(value = "basket")
    private List<Basket> basket;

    @NonNull
    @ApiModelProperty(value = "role")
    @ElementCollection(targetClass = Role.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "user_profile_roles", joinColumns = @JoinColumn(name = "user_profile_id"))
    @Enumerated(EnumType.STRING)
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
