package pl.homeapp.homeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.homeapp.homeapi.domain.Home;

@Repository
public interface HomeRepository extends JpaRepository<Home,Long> {
}
