package com.mapper;

import com.po.Emp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("IEmpMapper")
public interface IEmpMapper {
    /**增加**/
	public int save(Emp emp);
    /**修改**/
	public int update(Emp emp);
	/**删除**/
	public int delById(Integer eid);
	/**查询单个**/
	public Emp findById(Integer eid);
	/**分页查询**/
	public List<Emp> findPageAll(@Param("page") int page,@Param("rows") int rows);
	/**查询总记录数**/
	public int findMaxRows();

	public int findMaxEid();

}
