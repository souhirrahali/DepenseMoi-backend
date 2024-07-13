package com.example.depenses.dao.entities;
import java.util.List;
import java.util.Collection;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.depenses.dao.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", unique = true, nullable = false)
    private Integer userId;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;
 

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Email should be valid")
    @Pattern(regexp = ".+@.+\\..+", message = "Email should be in the format user@domain.com")
    private String email;

    @Column(name = "password")
    @Size(min = 6, message = "Password should be at least 6 characters long")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role=Role.USER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Depense> depenses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Revenu> revenus;

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
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
     /*    List<GrantedAuthority> authorities = role.getPrivileges().stream()
            .map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.name()))
            .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name())); */
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }


    
}
