package com.mapper;

import com.po.Welfare;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("IWelfareMapper")
public interface IWelfareMapper {
	/**查询所有**/
	public List<Welfare> findAll();

}
