package cn.service.impl;

import cn.dao.INotenoteDao;
import cn.service.Collectionservice;
import cn.pojo.Notenote;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service("csi")
public class Collectionserviceimpl implements Collectionservice {

    @Resource(name = "INotenoteDao")
    private INotenoteDao nn;

    public INotenoteDao getNn() {
        return nn;
    }

    public void setNn(INotenoteDao nn) {
        this.nn = nn;
    }

    @Override
    public String insertSelective(Notenote record) {
        String re = "";
        if (record.getNcid()==null&&
                record.getNccollectionid()==null&&
                    record.getNcusernameid()==null&&
                        record.getNcstate()==null){
            re = "您无法添加心愿单";
        }else{
            int i = nn.insert(record);
            if (i > 0){
                re = "添加成功";
            }else {
                re = "添加失败";
            }
        }
        return JSON.toJSONString(re);
    }
}
