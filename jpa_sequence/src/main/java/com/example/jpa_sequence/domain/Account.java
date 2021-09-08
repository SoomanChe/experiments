package com.example.jpa_sequence.domain;

import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
public class Account {

    @Id
    @SequenceGenerator(
            name="user_seq_gen", //시퀀스 제너레이터 이름
            sequenceName="user_seq", //시퀀스 이름
            initialValue=1, //시작값
            allocationSize=1 //메모리를 통해 할당할 범위 사이즈
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_seq")
    private Long id;

    private String name;
}
