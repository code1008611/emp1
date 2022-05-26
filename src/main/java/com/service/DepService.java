package com.service;


import com.po.Dep;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DepService {
	/**查询所有**/
	public List<Dep> findAll();
}
