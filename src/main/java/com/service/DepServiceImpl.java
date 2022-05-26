package com.service;


import com.mapper.IDepMapper;
import com.po.Dep;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepServiceImpl implements DepService {

	@Resource
	private IDepMapper iDepMapper;


	@Override
	public List<Dep> findAll() {
		System.out.println(iDepMapper.findAll());
		return iDepMapper.findAll();
	}

}
