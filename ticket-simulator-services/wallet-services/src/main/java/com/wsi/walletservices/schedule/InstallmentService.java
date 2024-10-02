package com.wsi.walletservices.schedule;

import com.wsi.walletservices.exception.UserNotFoundException;
import com.wsi.walletservices.exception.WalletNotFoundException;
import com.wsi.walletservices.model.Installment;
import com.wsi.walletservices.model.TransactionType;
import com.wsi.walletservices.model.User;
import com.wsi.walletservices.model.Wallet;
import com.wsi.walletservices.repository.InstallmentRepository;
import com.wsi.walletservices.repository.UserRepository;
import com.wsi.walletservices.repository.WalletRepository;
import com.wsi.walletservices.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstallmentService {

    @Autowired
    private InstallmentRepository installmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletService walletService;

    //    @Scheduled(cron = "*/1 * * * * *") // 每秒查一次有沒有人要分期付款，可由 WalletController 的 checkInstallmentDueDate 來觸發
    public Integer checkInstallmentDueDate() {
        Integer finishCount = 0;

        List<Installment> installments = installmentRepository.findInstallmentByDueDate();
        for (Installment installment : installments) {
            // 查找 walletId
            User user = userRepository.findById(installment.getUserId()).orElseThrow(() -> new UserNotFoundException("錢包不存在"));
            // 這邊要做扣款
            Wallet savedWallet = walletService.payPessimisticWrite(user.getWalletId(), TransactionType.INSTALLMENT, installment.getNextAmount());
            finishCount++;
        }
        return finishCount;
    }
}
