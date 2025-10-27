package basic.main.repository;

import basic.main.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c WHERE (c.firstUser.id = :firstId AND c.secondUser.id = :secondId) OR (c.firstUser.id = :secondId AND c.secondUser.id = :firstId)")
    Optional<Chat> findByUsersIds(@Param("firstId") Long firstId, @Param("secondId") Long secondId);
}
