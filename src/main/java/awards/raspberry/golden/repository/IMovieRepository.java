package awards.raspberry.golden.repository;

import awards.raspberry.golden.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRepository extends JpaRepository<MovieEntity, Long> {

}
