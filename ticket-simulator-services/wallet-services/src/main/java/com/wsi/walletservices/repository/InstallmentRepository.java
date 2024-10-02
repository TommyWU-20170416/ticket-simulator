package com.wsi.walletservices.repository;

import com.wsi.walletservices.model.Installment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstallmentRepository extends JpaRepository<Installment, Integer> {

    @Query("SELECT i FROM Installment i WHERE i.dueDate <= CURRENT_TIMESTAMP")
    List<Installment> findInstallmentByDueDate();
}
