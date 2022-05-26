package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.po.Dep;
import com.po.Emp;
import com.po.PageBean;
import com.po.Welfare;
import com.service.DepService;
import com.service.EmpService;
import com.service.WelfareService;
import com.util.AJAxUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class EmpController {
    @Resource
    private EmpService empService;
    @Resource
    private DepService depService;
    @Resource
    private WelfareService welfareService;

    @RequestMapping("/ready")
    public String ready(HttpServletResponse response, HttpServletRequest request) throws IOException {
        List<Dep> depList = depService.findAll();
        List<Welfare> welfareList = welfareService.getAll();
        Map<String,Object> map = new HashMap<>();
        map.put("dep",depList);
        map.put("wel",welfareList);
        PropertyFilter propertyFilter=AJAxUtil.filterProperts("birthday","pic");
        String jsonstr=JSONObject.toJSONString(map,propertyFilter, SerializerFeature.DisableCircularReferenceDetect);
        AJAxUtil.printString(response, jsonstr);
        return null;
    }
    @RequestMapping("/add")
    public String add(Emp emp, HttpServletResponse response, HttpServletRequest request){
        System.out.println(emp);
        String realpath=request.getRealPath("/");
        //文件上传
        //获取上传照片对象
        MultipartFile multipartFile=emp.getPic();
        if(multipartFile!=null && !multipartFile.isEmpty()){
            //获取上传文件名称
            String fname=multipartFile.getOriginalFilename();
            //更名
            if(fname.lastIndexOf(".")!=-1){
                //获取后缀
                String ext=fname.substring(fname.lastIndexOf("."));
                //限制上传格式
                if(ext.equalsIgnoreCase(".jpg")){
                    String newfname=System.currentTimeMillis()+ext;
                    //创建文件对象，将上传的字节文件写入到指定文件
                    File dostFile=new File(realpath+"/uppic/"+newfname);
                    //上传
                    try {
                        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), dostFile);
                        emp.setPhoto(newfname);
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }
        }
        boolean flag = empService.save(emp);
        if(flag){
            AJAxUtil.printString(response, 1+"");
        }else{
            AJAxUtil.printString(response, 0+"");
        }
        return null;
    }
    @RequestMapping("/getAll")
    public String getAll(HttpServletResponse response, HttpServletRequest request,Integer rows,Integer page){
        rows = rows>0||rows==null?rows:5;
        page = page>0||page==null?page:1;
        rows = rows>10?10:rows;
        PageBean pageBean = new PageBean(page, rows);
        List<Emp> all = empService.getAll(pageBean);
        Map<String,Object> map = new HashMap<>();
        Integer maxRows = empService.findMaxRows();
        map.put("page", page);
        map.put("rows", all);
        map.put("total", maxRows);
        PropertyFilter propertyFilter=AJAxUtil.filterProperts("birthday","pic");
        String jsonstr=JSONObject.toJSONString(map,propertyFilter, SerializerFeature.DisableCircularReferenceDetect);
        AJAxUtil.printString(response, jsonstr);
        return null;
    }
    @RequestMapping("/del")
    public String del(HttpServletResponse response, HttpServletRequest request,Integer eid){
        String realpath=request.getRealPath("/");
        Emp emp = empService.findById(eid);
        String photo = emp.getPhoto();
        File file = new File(realpath+"/uppic/"+photo);
        if (file.exists()) {
            file.delete();
        }
        boolean flag = empService.del(eid);
        if(flag){
            AJAxUtil.printString(response, 1+"");
            System.out.println(1);
        }else{
            AJAxUtil.printString(response, 0+"");
        }
        return null;
    }
    @RequestMapping("/findOne")
    public String findOne(HttpServletResponse response, HttpServletRequest request,Integer eid){
        Emp emp = empService.findById(eid);
        List<Welfare> lswf = emp.getLswf();
        System.out.println(lswf);
        PropertyFilter propertyFilter=AJAxUtil.filterProperts("birthday","pic");
        String jsonstr=JSONObject.toJSONString(emp,propertyFilter, SerializerFeature.DisableCircularReferenceDetect);
        AJAxUtil.printString(response, jsonstr);
        return null;
    }
    @RequestMapping("/update")
    public String update(HttpServletRequest request,HttpServletResponse response,Emp emp){
        String realpath=request.getRealPath("/");
        String oldphoto=empService.findById(emp.getEid()).getPhoto();
        if(emp.getPic()!=null){
            MultipartFile multipartFile=emp.getPic();
            if(multipartFile!=null && !multipartFile.isEmpty()){
                String fname=multipartFile.getOriginalFilename();
                if(fname.lastIndexOf(".")!=-1){
                    String ext=fname.substring(fname.lastIndexOf("."));
                    if(ext.equalsIgnoreCase(".jpg")){
                        String newfname=System.currentTimeMillis()+ext;
                        File dostFile=new File(realpath+"/uppic/"+newfname);
                        try {
                            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), dostFile);
                            emp.setPhoto(newfname);
                            File oldfile=new File(realpath+"/uppic/"+oldphoto);
                            if(oldfile.exists()&& !oldphoto.equalsIgnoreCase("default.jpg")){
                                oldfile.delete();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }else{
            emp.setPhoto(oldphoto);
        }
        boolean flag=empService.update(emp);
        if(flag){
            AJAxUtil.printString(response, 1+"");
        }else{
            AJAxUtil.printString(response, 0+"");
        }
        return null;
    }
}
