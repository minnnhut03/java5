package com.java5.demoJV5.service;

import com.java5.demoJV5.entity.AddressEntity;
import com.java5.demoJV5.jpa.AddressJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressJPA addressJPA;

    // ✅ Lấy tất cả địa chỉ
    public List<AddressEntity> getAllAddresses() {
        return addressJPA.findAll();
    }

    // ✅ Lấy địa chỉ theo ID
    public Optional<AddressEntity> getAddressById(Integer id) {
        return addressJPA.findById(id);
    }

    // ✅ Lấy địa chỉ theo userId
    public List<AddressEntity> getAddressesByUserId(Integer userId) {
        return addressJPA.findByUser_Id(userId);
    }

    // ✅ Lưu hoặc cập nhật địa chỉ
    public AddressEntity saveOrUpdateAddress(AddressEntity address) {
        return addressJPA.save(address);
    }

    // ✅ Xóa địa chỉ theo ID
    public void deleteAddress(Integer id) {
        addressJPA.deleteById(id);
    }
}
