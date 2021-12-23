package com.artonhanger.manage.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtistTest {
    @Test
    public void 내부_builder_테스트() {
        assertDoesNotThrow(() -> Artist.BankAccount.builder().bankName("bankname").account("account").accountHolder("holder").build());
//        assertThrows(IllegalArgumentException.class,
//                () -> Artist.BankAccount.builder().account("account").build());
//        assertThrows(IllegalArgumentException.class,
//                () -> Artist.BankAccount.builder().bankName("bankname").build());
//        assertThrows(IllegalArgumentException.class,
//                () -> Artist.BankAccount.builder().account("").bankName("bankname").accountHolder("holder").build());
    }

    @Test
    public void lombok_NPE_테스트() {
        Artist artist = Artist.builder().build();
        assertThrows(NullPointerException.class,
                () -> artist.changeAccount(null));
    }
}