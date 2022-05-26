package com.test;

import com.mapper.IEmpMapper;
import com.po.Emp;
import com.service.EmpService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class EmpServiceTest {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        EmpService bean = ac.getBean(EmpService.class);
//        List<Emp> all = bean.findPageAll(1, 5);
//        System.out.println(all);
        Emp emp = bean.findById(14);
        System.out.println(emp);
    }
}
