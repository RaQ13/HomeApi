package pl.homeapp.homeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.homeapp.homeapi.domain.Command;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {
}
