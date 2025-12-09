package pl.mechanicsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.mechanicsystem.entity.MsUser;

import java.util.List;
import java.util.Optional;

public interface MsUserRepository extends JpaRepository<MsUser, Long> {

    Optional<MsUser> findByLogin(String login);

    // Select zwraca prawa do funkcji po podaniu userId
    @Query(value = """
        SELECT
            f.code,      -- np. 'CLIENTS'
            f.path,      -- np. '/clients'
            g.access     -- np. 'VIEW;EDIT;DELETE;CREATE'
        FROM ms_user u
            JOIN ms_user2group ug ON ug.usrid = u.gid AND ug.status = 'A'
            JOIN ms_usrfun2usrgrp g ON g.grpid = ug.grpid AND g.status = 'A'
            JOIN ms_userfunction f ON f.gid = g.usrfunid AND f.status = 'A'
                WHERE u.gid = :userId
        """,
        nativeQuery = true)
    List<Object[]> findUserAccess(@Param("userId") Long userId);
}
