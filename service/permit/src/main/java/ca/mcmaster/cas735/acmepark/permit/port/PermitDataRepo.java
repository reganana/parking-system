package ca.mcmaster.cas735.acmepark.permit.port;

import ca.mcmaster.cas735.acmepark.permit.business.entity.Permit;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermitDataRepo extends JpaRepository<Permit, Integer> {
    Optional<Permit> findByTransponderNumberAndLotId(UUID transponderId, Long lotId);
    Optional<Permit> findByPermitId(int permitId);
}
