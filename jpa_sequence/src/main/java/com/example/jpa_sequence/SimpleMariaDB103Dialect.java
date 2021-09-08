package com.example.jpa_sequence;

import org.hibernate.dialect.MariaDB103Dialect;

public class SimpleMariaDB103Dialect extends MariaDB103Dialect {

    @Override
    public String getSelectSequenceNextValString(String sequenceName) {
        return "nextval('" + sequenceName + "')";

    }
}
