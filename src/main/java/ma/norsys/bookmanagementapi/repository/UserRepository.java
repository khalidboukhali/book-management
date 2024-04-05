package ma.norsys.bookmanagementapi.repository;

import ma.norsys.bookmanagementapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
