package meat.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import meat.store.entity.MeatStore;
public interface MeatStoreDao extends JpaRepository<MeatStore, Long>{

}
