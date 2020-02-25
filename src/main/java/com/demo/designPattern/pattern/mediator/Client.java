package com.demo.designPattern.pattern.mediator;

import java.util.HashMap;
import java.util.Map;

class Mediator {
    private Map<String, Colleague> colleagueMap;
    private Map<String, String> interMap;

    public Mediator() {
        colleagueMap = new HashMap<>();
        interMap = new HashMap<>();
    }

    public void register(String name, Colleague colleague) {
        colleagueMap.put(name, colleague);
        if (colleague instanceof Alarm) {
            interMap.put("Alarm", name);
        } else if (colleague instanceof Tv) {
            interMap.put("Tv", name);
        }
    }

    public void getMessage(int stateChange, String name) {
        if (colleagueMap.get(name) instanceof Alarm) {
            action(stateChange);
        } else if (colleagueMap.get(name) instanceof Tv) {
            action(stateChange);
        }
    }

    private void action(int stateChange) {
        if (stateChange == 0) {
            Alarm alarm = (Alarm) colleagueMap.get(interMap.get("Alarm"));
            Tv tv = (Tv) colleagueMap.get(interMap.get("Tv"));
            alarm.start();
            tv.start();
        } else if (stateChange == 1) {
            Alarm alarm = (Alarm) colleagueMap.get(interMap.get("Alarm"));
            Tv tv = (Tv) colleagueMap.get(interMap.get("Tv"));
            alarm.stop();
            tv.stop();
        }
    }
}

abstract class Colleague {
    private Mediator mediator;
    public String name;

    public Colleague(Mediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public Mediator getMediator() {
        return this.mediator;
    }

    public abstract void sendMessage(int stateChange);
}

class Alarm extends Colleague {

    public Alarm(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name, this);
    }

    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, name);
    }

    public void start() {
        System.out.println("闹钟响了");
    }

    public void stop() {
        System.out.println("闹钟停了");
    }
}

class Tv extends Colleague {

    public Tv(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name, this);
    }

    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, name);
    }

    public void start() {
        System.out.println("电视开了");
    }

    public void stop() {
        System.out.println("电视关了");
    }
}

public class Client {
    public static void main(String[] args) {
        Mediator mediator = new Mediator();
        Alarm alarm = new Alarm(mediator, "Alarm");
        Tv tv = new Tv(mediator, "Tv");
        alarm.sendMessage(0);
        tv.sendMessage(1);
    }
}
