package pl.mechanicsystem.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
import org.springframework.data.repository.Repository;
import pl.mechanicsystem.entity.MsClient;


import java.util.List;

public interface ClientCarRepository extends Repository<MsClient, Integer> {

    @Query(value = """
        SELECT
            c2c.carid      AS carid,
            cb.brandname   AS brand,
            cm.modelname   AS model,
            c.caryear      AS caryear,
            c.register     AS register,
            cf.name        AS fuel
        FROM ms_clnt2car c2c
            left JOIN ms_car c ON c.gid = c2c.carid AND c.status = 'A'
            left JOIN ms_carbrand cb ON cb.gid = c.carbrandid AND cb.status = 'A'
            left JOIN ms_carmodel cm ON cm.gid = c.carmodelid AND cm.status = 'A'
            left JOIN ms_carfuel cf ON cf.gid = c.carfuelid AND cf.status = 'A'
                WHERE c2c.status = 'A' AND c2c.clntid = :clientid
                    ORDER BY c2c.gid DESC
    """, nativeQuery = true)
    List<Object[]> findCarsClient(@Param("clientid") Integer clientid);
}
