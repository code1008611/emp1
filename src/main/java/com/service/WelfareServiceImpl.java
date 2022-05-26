package com.service;

import com.mapper.IWelfareMapper;
import com.po.Welfare;
import org.springframework.stereotype.Service;
import sun.dc.pr.PRError;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WelfareServiceImpl implements WelfareService {

    @Resource
    private IWelfareMapper welfareMapper;

    @Override
    public List<Welfare> getAll() {
        return welfareMapper.findAll();
    }


}
