package com.example.jpa_sequence;

import com.example.jpa_sequence.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
