package pl.mechanicsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.mechanicsystem.entity.MsClient;

import java.util.Optional;

public interface MsClientRepository extends JpaRepository<MsClient, Integer> {

    @Query("""
        SELECT c FROM MsClient c
            WHERE c.gid = :id AND c.status = 'A'
    """)
    Optional<MsClient> findActiveById(@Param("id") Integer clntid);

    @Query("""
    SELECT c FROM MsClient c
        WHERE c.status = 'A'
          AND (
               :search IS NULL OR :search = '' OR
               LOWER(c.clnam1) LIKE LOWER(CONCAT('%', :search, '%')) OR
               LOWER(COALESCE(c.clnam2, '')) LIKE LOWER(CONCAT('%', :search, '%')) OR
               LOWER(COALESCE(c.phone, '')) LIKE LOWER(CONCAT('%', :search, '%')) OR
               LOWER(COALESCE(c.email, '')) LIKE LOWER(CONCAT('%', :search, '%'))
          )
    """)
    Page<MsClient> findActiveClients(@Param("search") String search, Pageable pageable);
}
