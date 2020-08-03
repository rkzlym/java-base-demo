package com.demo.design.pattern.prototype;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Object类提供clone()方法，可以将对象复制一份，需要实现Cloneable接口
 * 深克隆 在重写clone()方法的时候对类内部的对象进行克隆
 */
public class DeepClone {

    @Data
    @AllArgsConstructor
    static class Location implements Cloneable {
        private Integer x;
        private Integer y;

        @Override
        protected Location clone() throws CloneNotSupportedException {
            return (Location) super.clone();
        }
    }

    @Data
    @AllArgsConstructor
    static class Sheep implements Cloneable {
        private String name;
        private Integer age;
        private String color;
        private Location loc;

        @Override
        protected Sheep clone() throws CloneNotSupportedException {
            Sheep sheep = (Sheep) super.clone();
            this.setLoc(loc.clone());
            return sheep;
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Sheep sheep1 = new Sheep("Tom", 1, "白色", new Location(100, 100));
        Sheep sheep2 = sheep1.clone();
        System.out.println(sheep1.getLoc() == sheep2.getLoc());
        sheep1.getLoc().setX(0);
        System.out.println(sheep1);
        System.out.println(sheep2);
    }
}
