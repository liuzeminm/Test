package cn.controller;

import cn.pojo.Commodity;
import cn.service.ImgService;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.models.Model;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Api(tags = "商家上架多个商品")
@Configuration
@RequestMapping("/photoand")
public class FileUploadController {
    private static Logger logger = Logger.getLogger(FileUploadController.class);
    @Resource(name = "ssss")
    private ImgService is;
    public ImgService getIs() {
        return is;
    }

    public void setIs(ImgService is) {
        this.is = is;
    }


    public String toFileUpLoad(HttpServletRequest request, Model model){
        return "fileUpLoad";
    }
    //@Value("#{settings['picPath.picUrl']}")

    @RequestMapping("/addAction")
    @ResponseBody//将返回结果写到response中
    public String save(HttpServletRequest request, HttpServletResponse response,MultipartFile[] file,
                       @RequestParam(value = "comname",required = false)String comname ,
                       @RequestParam(value = "comspecificationofgoods",required = false)String comspecificationofgoods ,
                       @RequestParam(value = "comcommodityprices",required = false)Integer comcommodityprices ,
                       @RequestParam(value = "combrand",required = false)Integer combrand ,
                       @RequestParam(value = "comclassificationgoods",required = false)Integer comclassificationgoods,
                       @RequestParam(value = "comgetacoupon",required = false)String comgetacoupon,
                       @RequestParam(value = "comtradedescription",required = false)String comtradedescription,
                       @RequestParam(value = "comsourceofcommoditysales",required = false)Integer comsourceofcommoditysales) throws IOException {
        response.setHeader("Access-Control-Allow-Origin","*");
        //文件名
        String newFileName = "";
        // 客户商品对象
        Commodity con = new Commodity();
        //图片集合
        List<String> listImagePath=new ArrayList<String>();
        for (MultipartFile mf : file) {
            if (file != null && !mf.isEmpty()) {
                //生成uuid作为文件名称
                String uuid = UUID.randomUUID().toString().replaceAll("-","");
                //获得文件类型（可以判断如果不是图片，禁止上传）
                String contentType=mf.getContentType();
                //获得文件后缀名称
                String imageName=contentType.substring(contentType.indexOf("/")+1);
                System.out.println(imageName+"获得文件后缀名称");
                newFileName =uuid+"."+imageName;
                System.out.println(newFileName+"新的文件名字");
                String path="\\upload\\"+uuid+"."+imageName;
                String realPath = request.getSession().getServletContext().getRealPath("");
                mf.transferTo(new File(realPath+path));
                listImagePath.add(path+",");
                //将图片上传到服务器
                //saveFile(newFileName,filedata);
                saveFile(request, newFileName, mf);
                //图片路径

                //将图片名称路径集合保存至数据库
                con.setCompicture(listImagePath.toString());
                System.out.println(listImagePath.toString());
                con.setComname(comname);
                con.setComspecificationofgoods(comspecificationofgoods);
                con.setComtradedescription(comtradedescription);
                con.setComcommodityprices(comcommodityprices);
                con.setComsourceofcommoditysales(comsourceofcommoditysales);
                con.setCombrand(combrand);
                con.setComclassificationgoods(comclassificationgoods);
                con.setComgetacoupon(comgetacoupon);

            }
        }
        return is.selgds(con);

    }
    private void saveFile(HttpServletRequest request,String newFileName, MultipartFile filedata) {
        //根据配置文件获取服务器图片存放路径
        String realPath1 = request.getSession().getServletContext().getRealPath("\\upload");

//        String realPath = ("http://localhost:8088//upload");
//        String saveFilePath = realPath;
//        String saveFilePath="C:\\Users\\Administrator\\Desktop\\SMBMS_C11_06\\smallybook\\Configuration\\src\\main\\webapp\\upload";
        //构建文件目录

        File tempFile=new File(realPath1);
        if(!tempFile.exists()){
            tempFile.mkdirs();
        }

        //保存文件到服务器
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(realPath1+"\\"+newFileName);
            fos.write(filedata.getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
