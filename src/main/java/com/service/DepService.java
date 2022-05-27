package com.service;


import com.po.Dep;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DepService {
	int a = 0;

	/**查询所有**/
	List<Dep> findAll();
}
