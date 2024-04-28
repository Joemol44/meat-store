package meat.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import meat.store.entity.MeatStore;
import meat.store.entity.Customer;
import meat.store.entity.Meats;

@Data
@NoArgsConstructor
public class MeatStoreData {

	private Long meatStoreId;
	private String meatStoreLocation;
	private String meatStoreAddress;
	private String meatStoreCity;
	private String meatStoreState;
	private String meatStoreZip;

	
	private Set<CustomerData> customers = new HashSet<>();
	private Set<MeatsData> meats = new HashSet<>();
	
	
	public MeatStoreData (MeatStore meatStore) {
		meatStoreId = meatStore.getMeatStoreId();
		meatStoreLocation = meatStore.getMeatStoreLocation();
		meatStoreAddress = meatStore.getMeatStoreAddress();
		meatStoreCity = meatStore.getMeatStoreCity();
		meatStoreState = meatStore.getMeatStoreState();
		meatStoreZip = meatStore.getMeatStoreZip();
		
		
		for (Customer customer :meatStore.getCustomers()) {
			customers.add(new CustomerData(customer));
		}
		for (Meats meat : meatStore.getMeats()) {
			meats.add(new MeatsData(meat));
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class CustomerData {
		private Long customerId;
		private String customerFirstName;
		private String customerLastName;
		private String customerAddress;
		
		public CustomerData (Customer customer) {
			customerId = customer.getCustomerId();
			customerFirstName = customer.getCustomerFirstName();
			customerLastName = customer.getCustomerLastName();
			customerAddress = customer.getCustomerAddress();
			
		}
	}
	
	@Data
	@NoArgsConstructor
	public static class MeatsData {
		private Long meatId;
		private String meatType;
		private String meatGrade;
		
		public MeatsData (Meats meat) {
			meatId = meat.getMeatId();
			meatType = meat.getMeatType();
			meatGrade = meat.getMeatGrade();
			
		}	
		}
			
	}

	
 