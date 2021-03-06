package com.demo.design.pattern.responsibilityChain;

/**
 * x < 5000 主任审批
 * 5000 <= x < 10000 院长审批
 * 10000 <= x 校长审批
 */
abstract class Audit {
    protected Audit audit;

    protected String name;

    public Audit(String name){
        this.name = name;
    }

    // 设置下一个处理者
    public void setAudit(Audit audit){
        this.audit = audit;
    }

    // 处理审批请求的方法，子类完成，因此将此方法作为抽象
    public abstract void handleRequest(Request request);
}

class CollegeAudit extends Audit {

    public CollegeAudit(String name) {
        super(name);
    }

    @Override
    public void handleRequest(Request request) {
        if (request.getPrice() >= 5000 && request.getPrice() < 10000){
            System.out.println("请求编号:" + request.getId() + "\t被" + this.name + "处理");
        } else{
            audit.handleRequest(request);
        }
    }
}

class DeptAudit extends Audit {

    public DeptAudit(String name) {
        super(name);
    }

    @Override
    public void handleRequest(Request request) {
        if (request.getPrice() < 5000){
            System.out.println("请求编号:" + request.getId() + "\t被" + this.name + "处理");
        } else{
            audit.handleRequest(request);
        }
    }
}

class UniversityAudit extends Audit {

    public UniversityAudit(String name) {
        super(name);
    }

    @Override
    public void handleRequest(Request request) {
        if (request.getPrice() >= 10000){
            System.out.println("请求编号:" + request.getId() + "\t被" + this.name + "处理");
        } else{
            audit.handleRequest(request);
        }
    }
}

class Request {
    private int id = 0;
    private float price = 0.0f;

    public Request(int id, float price){
        this.id = id;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }
}

public class Client {
    public static void main(String[] args) {
        // 创建一个请求
        Request request = new Request(1, 1000.00f);

        // 创建相关的审批人
        DeptAudit deptAudit = new DeptAudit("张主任");
        CollegeAudit collegeAudit = new CollegeAudit("李院长");
        UniversityAudit universityAudit = new UniversityAudit("王校长");

        // 需要将各个审批级别的下一级设置好(处理人构成环形)
        deptAudit.setAudit(collegeAudit);
        collegeAudit.setAudit(universityAudit);
        universityAudit.setAudit(deptAudit);

        // 处理
        deptAudit.handleRequest(request);
    }
}
