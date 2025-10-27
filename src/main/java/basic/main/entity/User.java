package basic.main.entity;

import basic.main.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public final class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @CreationTimestamp
    private LocalDateTime createdAt;

    protected User() {
    }

    public User(String userName, String email, String rawPassword, Role role, BCryptPasswordEncoder encoder) {
        this.userName = userName;
        this.email = email;
        setPassword(rawPassword, encoder);
        this.role = role;
    }

    public void setPassword(String rawPassword, BCryptPasswordEncoder encoder) {
        this.passwordHash = encoder.encode(rawPassword);
    }

    public boolean validatePassword(String rawPassword, BCryptPasswordEncoder encoder) {
        return encoder.matches(rawPassword, this.passwordHash);
    }


    public Long getId() { return id; }
    public String getUserName() { return userName; }
    public String getEmail() { return email; }
    public Role getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setEmail(String email) { this.email = email; }
    public void setRole(Role role) { this.role = role; }
}
