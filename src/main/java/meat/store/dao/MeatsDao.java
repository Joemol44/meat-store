package meat.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import meat.store.entity.Meats;



public interface MeatsDao extends JpaRepository<Meats, Long> {

}
