package com.cmbc.controller;

import com.cmbc.common.ApplicationContextHelper;
import com.cmbc.common.JsonData;
import com.cmbc.dao.SysAclModuleMapper;
import com.cmbc.exception.ParamException;
import com.cmbc.exception.PermissionException;
import com.cmbc.model.SysAclModule;
import com.cmbc.param.TestVo;
import com.cmbc.util.BeanValidator;
import com.cmbc.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData hello() {
        log.info("hello");
        throw new PermissionException("test exception");
        // return JsonData.success("hello, permission");
    }

    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVo vo) throws ParamException {
        log.info("validate");
        SysAclModuleMapper moduleMapper = ApplicationContextHelper.popBean(SysAclModuleMapper.class);
        SysAclModule module = moduleMapper.selectByPrimaryKey(1);
        log.info(JsonMapper.obj2String(module));
        BeanValidator.check(vo);
        return JsonData.success("test validate");
    }

    @RequestMapping("/TestVo.json")
    @ResponseBody
    public JsonData validateVo(TestVo vo)  {
        log.info("validateVo");
        Map<String,Object> map=null;
        try {
            /*Map<String,String> map=BeanValidator.validateObject(vo);
            if (map !=null &map.entrySet().size()>0){
                for (Map.Entry e:map.entrySet()){
                    log.info("{} --> {}",e.getKey(),e.getValue());
                }
            }*/
            //1.校验单个参数，ok
            /*String a=null;
            BeanValidator.check(a);*/

            //2.校验一个bean，保证其中每个参数都不能为空,并打印某字段空信息
           /* BeanValidator.check(vo);*/

            //3.校验自定义参数
            String a="1";
            String b="2";
            String c=null;
            map=BeanValidator.validateObject(a,b,c);
            log.info("xxxxxxxxx");

        } catch (Exception ex) {
            log.info("--------");
            if (map !=null &map.entrySet().size()>0){
                for (Map.Entry e:map.entrySet()){
                    log.info("{} --> {}",e.getKey(),e.getValue());
                }
            }
           log.error(ex.getMessage());
           throw new ParamException();
        }
        return JsonData.success("test validateVo");
    }

    @RequestMapping("/TestStr.json")
    @ResponseBody
    public JsonData validateStr(TestVo vo) throws ParamException {
        log.info("validateStr");
        String a="a";
        String b="b";
        String c=null;


        BeanValidator.check(BeanValidator.validateObject(a,b,c));
        System.out.println("-------");

        return JsonData.success("test validateStr");
    }
}
