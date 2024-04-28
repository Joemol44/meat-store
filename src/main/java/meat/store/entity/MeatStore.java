package meat.store.entity;

import java.util.HashSet;
import java.util.Set;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data


public class MeatStore {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long meatStoreId;
	private String meatStoreLocation;
	private String meatStoreAddress;
	private String meatStoreCity;
	private String meatStoreState;
	private String meatStoreZip;
	
		@ManyToMany(cascade = CascadeType.PERSIST)
		@JoinTable(name= "meat_store_customer",
		joinColumns = @JoinColumn(name = "meat_store_id"),
		inverseJoinColumns = @JoinColumn(name = "customer_id"))
	@EqualsAndHashCode.Exclude
	@ToString.Exclude

	
	
private Set<Customer> customers = new HashSet<>();
	
	@OneToMany(mappedBy = "meatStore",
	cascade = CascadeType.ALL,
	orphanRemoval = true)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	
	private Set<Meats> meats = new HashSet<>();
	
	
	
	
}
