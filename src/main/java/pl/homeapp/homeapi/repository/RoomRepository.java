package pl.homeapp.homeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.homeapp.homeapi.domain.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{
}
