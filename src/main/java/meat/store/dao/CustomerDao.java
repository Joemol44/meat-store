package meat.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import meat.store.entity.Customer;


public interface CustomerDao extends JpaRepository<Customer, Long>{

}


