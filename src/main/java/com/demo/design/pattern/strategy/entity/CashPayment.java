package com.demo.design.pattern.strategy.entity;

/**
 * @author keith
 */
public class CashPayment implements Payment {

    @Override
    public void pay() {
        System.out.println("The pay method is cash");
    }
}
