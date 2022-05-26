package com.service;

import com.mapper.IDepMapper;
import com.mapper.IEmpMapper;
import com.mapper.IEmpWelfareMapper;
import com.mapper.ISalaryMapper;
import com.po.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class EmpServiceImpl implements EmpService {

    @Resource
    private IEmpMapper iEmpMapper;
    @Resource
    private ISalaryMapper iSalaryMapper;
    @Resource
    private IEmpWelfareMapper iEmpWelfareMapper;
    @Resource
    private IDepMapper iDepMapper;

    @Override
    public boolean save(Emp emp) {
//添加员工信息
        int code=iEmpMapper.save(emp);
        System.out.println(code>0);
        if(code>0){
            //因为eid是数据库自增的，我只能获取刚才添加的员工编号
            Integer eid=iEmpMapper.findMaxEid();
            System.out.println("2");
            //添加该员工的薪资
            Salary sa=new Salary(eid,emp.getEmoney());
            int code1 = iSalaryMapper.save(sa);
            //添加该员工的福利
            String[] wids=emp.getWids();
            if(wids!=null && wids.length>0){
                for(int i=0;i<wids.length;i++){
                    EmpWelfare ewf=new EmpWelfare(eid,Integer.parseInt(wids[i]));
                    iEmpWelfareMapper.save(ewf);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Emp emp) {
        boolean flag = false;
        flag = iEmpMapper.update(emp)>0?true:false;
        Float emoney = emp.getEmoney();
        Salary salary = new Salary(emp.getEid(), emoney);
        flag = iSalaryMapper.update(salary)>0?true:false;
        flag = iEmpWelfareMapper.delByEid(emp.getEid())>0?true:false;
        for (int i = 0; i < emp.getWids().length; i++) {
            iEmpWelfareMapper.save(new EmpWelfare(emp.getEid(),Integer.parseInt(emp.getWids()[i])));
        }
        return flag;
    }

    @Override
    public List<Emp> getAll(PageBean pageBean) {
        List<Emp> all = iEmpMapper.findPageAll(pageBean.getPage(), pageBean.getRows());
        return all;
    }

    @Override
    public Integer findMaxRows() {
        return iEmpMapper.findMaxRows();
    }

    @Override
    public boolean del(Integer eid) {
        boolean flag = true;
        //删除福利
        flag = iEmpWelfareMapper.delByEid(eid)>0?true:false;
        //删除薪资
        flag = iSalaryMapper.delByEid(eid)>0?true:false;
        //删除员工
        flag = iEmpMapper.delById(eid)>0?true:false;
        return flag;
    }

    @Override
    public Emp findById(Integer eid) {
        Emp emp = iEmpMapper.findById(eid);
        String depname = iDepMapper.findDepByDepId(emp.getDepid());
        emp.setDepname(depname);
        Salary salary = iSalaryMapper.findByEid(eid);
        emp.setEmoney(salary.getEmoney());
        List<Welfare> list = iEmpWelfareMapper.findById(eid);
        emp.setLswf(list);
        return emp;
    }
}
