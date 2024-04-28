package meat.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import meat.store.controller.model.MeatStoreData;
import meat.store.controller.model.MeatStoreData.CustomerData;
import meat.store.controller.model.MeatStoreData.MeatsData;
import meat.store.dao.CustomerDao;
import meat.store.dao.MeatStoreDao;
import meat.store.dao.MeatsDao;
import meat.store.entity.Customer;
import meat.store.entity.MeatStore;
import meat.store.entity.Meats;


@Service
public class MeatStoreService {

	@Autowired
	private MeatStoreDao meatStoreDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private MeatsDao meatsDao;
	
	@Transactional (readOnly = false)
	public MeatStoreData saveMeatStore(MeatStoreData meatStoreData) {
		Long meatStoreId = meatStoreData.getMeatStoreId();
		MeatStore meatStore = findOrCreateMeatStore(meatStoreId);
		
		copyMeatStoreFields(meatStore, meatStoreData);
		return new MeatStoreData(meatStoreDao.save(meatStore));
	}
	
	private void copyMeatStoreFields (MeatStore meatStore, 
			MeatStoreData meatStoreData) {
	meatStore.setMeatStoreLocation(meatStoreData.getMeatStoreLocation());
	meatStore.setMeatStoreAddress(meatStoreData.getMeatStoreAddress());
	meatStore.setMeatStoreCity(meatStoreData.getMeatStoreCity());
	meatStore.setMeatStoreState(meatStoreData.getMeatStoreState());
	meatStore.setMeatStoreZip(meatStoreData.getMeatStoreZip());
	}
	private MeatStore findOrCreateMeatStore (Long meatStoreId) {
		if (Objects.isNull(meatStoreId)) {
		return new MeatStore();
	}	
		return findMeatStoreById(meatStoreId);
	}
	


private MeatStore findMeatStoreById(Long meatStoreId) {
	return meatStoreDao.findById(meatStoreId)
			.orElseThrow(() -> new NoSuchElementException(
					"Meat Store with ID=" + meatStoreId + " was not found."));
}

public CustomerData saveCustomer(Long meatStoreId, CustomerData meatStoreCustomer) {
	MeatStore meatStore = findMeatStoreById(meatStoreId);
	Long customerId = meatStoreCustomer.getCustomerId();
	Customer customer = findOrCreateCustomer(meatStoreId, customerId);
	setFieldsInMeatStoreCustomer(customer, meatStoreCustomer);
	customer.getMeatStores().add(meatStore);
	meatStore.getCustomers().add(customer);
	Customer dbCustomer = customerDao.save(customer);
	return new CustomerData(dbCustomer);
}
private void setFieldsInMeatStoreCustomer(Customer customer, CustomerData meatStoreCustomer) {
	customer.setCustomerFirstName(meatStoreCustomer.getCustomerFirstName());
	customer.setCustomerLastName(meatStoreCustomer.getCustomerLastName());
	customer.setCustomerAddress(meatStoreCustomer.getCustomerAddress());;
	
}

private Customer findOrCreateCustomer(Long meatStoreId, Long customerId) {
	Customer customer;
	if(Objects.isNull(customerId)) {
		customer = new Customer();
	} else {
		customer = findCustomerById(meatStoreId, customerId);
	}
	return customer;
}

private Customer findCustomerById(Long meatStoreId, Long customerId) {
	Customer customer = customerDao.findById(customerId)
			.orElseThrow(() -> new NoSuchElementException(
					"Customer with ID =" + customerId + " was not found"));
	
	boolean found = false;
	
	for (MeatStore petStore : customer.getMeatStores()) {
		
	if (petStore.getMeatStoreId() == meatStoreId) {
		found = true;
		break;
	} else {
		if(!found) {
			throw new IllegalArgumentException(
					"Customer with ID =" + customerId
				+ " is not a member of the meat store with ID =" + meatStoreId);
			
		
		}
	}
	}
	return customer;
}

@Transactional(readOnly = true)
public List<MeatStoreData> retrieveAllMeatStores() {
	List<MeatStore> meatStores = meatStoreDao.findAll();
	
	List<MeatStoreData> result = new LinkedList<>();
	
	for(MeatStore meatStore : meatStores) {
		MeatStoreData psd = new MeatStoreData(meatStore);
		
		psd.getCustomers().clear();
		psd.getMeats().clear();
		
		result.add(psd);
	}
	return result;
		
	}
	@Transactional(readOnly = true)
	public MeatStoreData retrieveMeatStoreById(Long meatStoreId) {
		return new MeatStoreData(findMeatStoreById(meatStoreId));
	}
	@Transactional(readOnly = false)
	public void deleteMeatStoreById(Long meatStoreId)  {
		MeatStore meatStore = findMeatStoreById(meatStoreId);
		meatStoreDao.delete(meatStore);;
	}
	


public MeatsData addMeatsToMeatStore(MeatsData meatsData, Long meatStoreId) {
	MeatStore meatStore = findMeatStoreById(meatStoreId);
	Long meatId = meatsData.getMeatId();
	Meats meats = findOrCreateMeats(meatStoreId, meatId);
	
	copyMeatsFields(meats, meatsData);
	
	meats.setMeatStore(meatStore);
	meatStore.getMeats().add(meats);
	
	Meats dbMeats = meatsDao.save(meats);
	return new MeatsData(dbMeats);  
}


	private void copyMeatsFields(Meats meats, 
			MeatsData meatsData) {
	meats.setMeatId(meatsData.getMeatId());
	meats.setMeatType(meatsData.getMeatType());
	meats.setMeatGrade(meatsData.getMeatGrade());
	}
	

private Meats findOrCreateMeats(Long meatStoreId, Long meatId) {
	Meats meat;
	
	if(Objects.isNull(meatId)) {
		meat = new Meats();
	} else {
		meat = findMeatById(meatId, meatStoreId);
		
	}
	return meat;
}


private Meats findMeatById(Long meatStoreId, Long meatId) {
	Meats meats = meatsDao.findById(meatId)
		.orElseThrow(() -> new NoSuchElementException(
				"Meat with Id=" + meatId + " was not found."));
	
	if(meats.getMeatStore().getMeatStoreId() != meatStoreId) {
		throw new IllegalArgumentException("The meat with Id=" + meatId
				+ " is not a meat available in meat store with ID=" + meatStoreId + ".");
		
	}
			return meats;
}
}