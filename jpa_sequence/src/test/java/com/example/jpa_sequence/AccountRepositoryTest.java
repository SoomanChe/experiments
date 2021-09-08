package com.example.jpa_sequence;

import com.example.jpa_sequence.domain.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AccountRepositoryTest {

    @Autowired
    AccountRepository repository;

    @Test
    public void test() throws Exception {
        System.out.println("=================");
        final Account account = new Account();
        account.setName("이름");
        System.out.println("======생성후======");

        repository.save(account);
        System.out.println("======저장후======");

        final Optional<Account> byId = repository.findById(1L);
        assertEquals(byId.get().getId(), 1L);
    }
}