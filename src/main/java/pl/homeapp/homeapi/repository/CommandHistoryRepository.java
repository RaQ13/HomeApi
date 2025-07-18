package pl.homeapp.homeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.homeapp.homeapi.domain.CoomandHistory;

@Repository
public interface CommandHistoryRepository extends JpaRepository<CoomandHistory, Long> {
}
