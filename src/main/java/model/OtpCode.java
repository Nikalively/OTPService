package model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class OtpCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private String operationId; // Идентификатор операции

    @Column(nullable = false)
    private String code; // Сам OTP-код

    @Column(nullable = false)
    private String status; // ACTIVE, USED, EXPIRED

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    public OtpCode() {}

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public String toString() {
        return "OtpCode{" +
                "id=" + id +
                ", operationId='" + operationId + '\'' +
                ", code='" + code + '\'' +
                ", status='" + status + '\'' +
                ", expiresAt=" + expiresAt +
                '}';
    }
}