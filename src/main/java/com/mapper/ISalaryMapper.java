package com.mapper;

import com.po.Salary;
import org.springframework.stereotype.Service;

@Service("ISalaryMapper")
public interface ISalaryMapper {
    /**增加**/
	public int save(Salary sa);
	/**根据员工编号修改薪资**/
	public int update(Salary sa);
	/**根据员工编号删除薪资**/
	public int delByEid(Integer eid);
	/**根据员工编号查询单个**/
	public Salary findByEid(Integer eid);
	
	
}
