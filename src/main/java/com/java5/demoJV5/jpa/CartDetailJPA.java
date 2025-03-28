package com.java5.demoJV5.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.java5.demoJV5.entity.CartDetailEntity;
import com.java5.demoJV5.entity.ProductEntity;

import jakarta.transaction.Transactional;

public interface CartDetailJPA extends JpaRepository<CartDetailEntity, Integer> {
	
	@Query(value = "SELECT * FROM cart_details WHERE cart_id=?1 AND product_id=?2", nativeQuery = true)
	  public Optional<CartDetailEntity> findByCartIdAndProdId(int cartId, int prodId);

	  @Query(value = "SELECT * FROM cart_details WHERE cart_id=?1 AND cart_detail_id=?2", nativeQuery = true)
	  public Optional<CartDetailEntity> findByCartIdAndId(int cartId, int id);
	  
	  @Query(value = "SELECT * FROM cart_details WHERE cart_id=?1 AND product_id=?2 AND size = ?3", nativeQuery = true)
	  Optional<CartDetailEntity> findByCartIdAndProdIdAndSize(int cartId, int  productId, String size);
	  
		/* List<CartDetail> findAllById(List<Integer> ids); */
	  List<CartDetailEntity> findByIdIn(List<Long> ids);
	  
	  @Query(value = "SELECT cd.*, c.cart_id AS id FROM cart_details cd JOIN cart c ON cd.cart_id = c.cart_id WHERE c.user_id = ?1", nativeQuery = true)
	  List<CartDetailEntity> findByUserId(int id);
	  
		@Transactional
		@Modifying
		@Query(value = "DELETE FROM cart_details WHERE cart_id = ?1", nativeQuery = true)
		void clearByCartId(int cartId);

}
