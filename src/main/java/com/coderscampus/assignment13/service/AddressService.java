package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AddressService {

    @Autowired
    private AddressRepository addressRepo;

    public Optional<Address> findById(Long userId) {
        return addressRepo.findById(userId);
    }

    public Address saveAddress(Address address) {
        return addressRepo.save(address);
    }

    public Address createAddressForUser(User user) {
        Address address = new Address();
        address.setUser(user);
        address.setUserId(user.getUserId());
        return addressRepo.save(address);
    }

    public void deleteAddress(Long userId) {
        addressRepo.deleteById(userId);
    }
}
