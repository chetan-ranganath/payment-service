package com.scalable.paymentservice.repository;

import com.scalable.paymentservice.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<PaymentRecord,Long> {

    Optional<PaymentRecord> findByReference(String reference);
    Optional<PaymentRecord> findByOrderId(Long orderId);
    boolean existsByReference(String reference);

}
