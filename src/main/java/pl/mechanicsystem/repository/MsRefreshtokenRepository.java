package pl.mechanicsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mechanicsystem.entity.MsRefreshtoken;

import java.util.List;
import java.util.Optional;

public interface MsRefreshtokenRepository extends JpaRepository<MsRefreshtoken, Long> {


    Optional<MsRefreshtoken> findByTokenAndStatus(String token, String status);
    List<MsRefreshtoken> findByUsridAndStatus(Long usrId, String status);
}
