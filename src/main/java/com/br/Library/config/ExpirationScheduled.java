package com.br.Library.config;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.br.Library.enums.LoanStatus;
import com.br.Library.enums.ReserveStatus;
import com.br.Library.model.LoanModel;
import com.br.Library.model.ReserveModel;
import com.br.Library.repository.LoanRepository;
import com.br.Library.repository.ReserveRepository;

@EnableScheduling
@Component
public class ExpirationScheduled {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ReserveRepository reserveRepository;

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void checkExpiration() {
        LocalDate currentDate = LocalDate.now();

        Collection<ReserveModel> reserves = reserveRepository.findAll();

        for(ReserveModel reserve : reserves) {
            if(reserve.getExpirationDate().isBefore(currentDate)) {
                reserve.setStatus(
                    ReserveStatus.EXPIRED
                );
                reserveRepository.save(reserve);
            }
        }

        Collection<LoanModel> loans = loanRepository.findAll();

        for(LoanModel loan : loans) {
            if(loan.getDeliveryDate().isBefore(currentDate)) {
                loan.setStatus(
                    LoanStatus.LATE
                );
                loanRepository.save(loan);
            }
        }
    }
}