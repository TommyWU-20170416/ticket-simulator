package com.wsi.walletservices.repository;

import com.wsi.walletservices.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

public interface WalletRepository extends JpaRepository<Wallet, Integer>{

    /**
     * 這種鎖像是 select ... for update 會阻擋其他人寫入，不阻擋一般查詢，但會擋下相同的 select ... for update
     *
     * 這方法解決了高併發下的問題，接著是效能問題，這樣的查詢會阻塞
     *
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT w FROM Wallet w WHERE w.walletId = :id")
    Wallet findByIdForUpdate(@Param("id") Integer id);
}
