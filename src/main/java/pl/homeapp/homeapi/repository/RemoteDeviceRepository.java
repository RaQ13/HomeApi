package pl.homeapp.homeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.homeapp.homeapi.domain.RemoteDevice;

@Repository
public interface RemoteDeviceRepository extends JpaRepository<RemoteDevice, Long> {
}
