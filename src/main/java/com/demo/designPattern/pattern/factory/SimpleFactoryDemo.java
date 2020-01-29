package com.demo.designPattern.pattern.factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 需求: 便于披萨种类的扩展，便于维护
 * 1. 披萨的种类很多 比如 GreekPizza, CheesePizza
 * 2. 披萨的制作有 prepare, bake, cut, box
 * 3. 完成披萨订购功能
 */
public class SimpleFactoryDemo {
    static abstract class Pizza{
        String name;

        public abstract void prepare();

        public void bake(){
            System.out.println(name + " is baking");
        }

        public void cut(){
            System.out.println(name + " is cutting");
        }

        public void box(){
            System.out.println(name + " is boxing");
        }

        public void setName(String name){
            this.name = name;
        }
    }

    static class GreekPizza extends Pizza {

        public GreekPizza(){
            super.setName("希腊披萨");
        }

        @Override
        public void prepare() {
            System.out.println("给希腊披萨准备原材料");
        }
    }

    static class CheesePizza extends Pizza {

        public CheesePizza(){
            super.setName("奶酪披萨");
        }

        @Override
        public void prepare() {
            System.out.println("给奶酪披萨准备原材料");
        }
    }

    static class SimpleFactory{
        public Pizza createPizza(Integer type){
            Pizza pizza = null;
            if (type == 1){
                pizza = new GreekPizza();
            } else if(type == 2) {
                pizza = new CheesePizza();
            }
            return pizza;
        }
    }

    static class OrderPizza{
        private Pizza pizza;
        private Integer type;
        private SimpleFactory simpleFactory;

        public OrderPizza(SimpleFactory simpleFactory){
            setFactory(simpleFactory);
        }
        public void setFactory(SimpleFactory simpleFactory){
            type = getType();
            this.simpleFactory = simpleFactory;
            pizza = this.simpleFactory.createPizza(type);
            if (pizza == null){
                System.out.println("没有相应的Pizza");
            }
            System.out.println("成功订购到了" + pizza.name);
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();
        }

        public Integer getType(){
            String type;
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("input pizza which you want: ");
                type = bufferedReader.readLine();
                return Integer.parseInt(type);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static void main(String[] args) {
        new OrderPizza(new SimpleFactory());
    }
}
