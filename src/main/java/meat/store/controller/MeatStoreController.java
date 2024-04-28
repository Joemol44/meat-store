package meat.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import meat.store.controller.model.MeatStoreData;
import meat.store.controller.model.MeatStoreData.CustomerData;
import meat.store.controller.model.MeatStoreData.MeatsData;
import meat.store.entity.Customer;
import meat.store.service.MeatStoreService;


@RestController
@RequestMapping("/meat_store")
@Slf4j
public class MeatStoreController {
	@Autowired
	private MeatStoreService meatStoreService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public MeatStoreData insertMeatStore(@RequestBody MeatStoreData meatStoreData) {
		log.info("Creating meat store {}", meatStoreData);
		return meatStoreService.saveMeatStore(meatStoreData);
	}
	
	@PutMapping("/{meatStoreId}")
	public MeatStoreData updateMeatStore(@PathVariable Long meatStoreId,
			@RequestBody MeatStoreData meatStoreData) {
		meatStoreData.setMeatStoreId(meatStoreId);
		log.info("Updating meat store {}", meatStoreData);
		return meatStoreService.saveMeatStore(meatStoreData);
	}

@PostMapping("/{meatStoreId}/customer")
@ResponseStatus(code = HttpStatus.CREATED)
public CustomerData insertMeatStoreCustomer(@PathVariable Long meatStoreId,
		@RequestBody CustomerData meatStoreCustomer) {
	log.info("Creating a customer for meat store{}", meatStoreId);
	return meatStoreService.saveCustomer(meatStoreId, meatStoreCustomer);
}
@GetMapping("/meat_store")
public List<MeatStoreData> retrieveAllMeatStores() {
	log.info("Retreaving all meat Stores{}");
	return meatStoreService.retrieveAllMeatStores();
}
@DeleteMapping("/{meatStoreId}")
public Map<String, String> deleteMeatStoreById(@PathVariable Long meatStoreId) {
log.info("Deleting meat store with id ={}", meatStoreId);
meatStoreService.deleteMeatStoreById(meatStoreId);

return Map.of("Message", "Deletion of contributor with ID=" + meatStoreId + " was successful.");
}

@PostMapping("/{meatStoreId}/meats")
@ResponseStatus(code = HttpStatus.CREATED)
public MeatsData addMeatsToMeatStore(@PathVariable Long meatStoreId,
		@RequestBody MeatsData meatStoreMeats) {
	log.info("Adding meat {} to meat store with ID={}", meatStoreMeats, meatStoreId);
	return meatStoreService.addMeatsToMeatStore(meatStoreMeats, meatStoreId);
}
}
