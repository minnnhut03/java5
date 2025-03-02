package com.java5.demoJV5.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.java5.demoJV5.entity.AddressEntity;

@Repository
public interface AddressJPA extends JpaRepository<AddressEntity, Integer> {
    List<AddressEntity> findByUser_Id(Integer userId); 
}
