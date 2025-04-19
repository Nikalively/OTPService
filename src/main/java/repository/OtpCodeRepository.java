package repository;

import model.OtpCode;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpCodeRepository extends JpaRepository<OtpCode, Long> {
    Optional<OtpCode> findByOperationIdAndUserAndStatus(String operationId, User user, String status);
}
