package com.test;

import com.mapper.IEmpMapper;
import com.po.Emp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class EmpMapperTest {
    public static void main(String[] args) {

        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        IEmpMapper iEmpMapper = ac.getBean(IEmpMapper.class);
//        Integer eid=iEmpMapper.findMaxEid();
//        System.out.println(eid);
        Emp emp = iEmpMapper.findById(1);
        System.out.println(emp.getPhoto());
    }
}
