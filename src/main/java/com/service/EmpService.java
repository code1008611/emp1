package com.service;

import com.po.Emp;
import com.po.PageBean;

import java.util.List;

public interface EmpService {

    public boolean save(Emp emp);

    public boolean update(Emp emp);

    public List<Emp> getAll(PageBean pageBean);

    public Integer findMaxRows();

    public boolean del(Integer eid);

    public Emp findById(Integer eid);
}
