package com.example.newdemo.controller;

import com.example.newdemo.Service.ImagesService;
import com.example.newdemo.bean.Images;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/images")
public class SendController {


    private static final Logger logger = LoggerFactory.getLogger(SendController.class);
    //获取上传的文件夹，具体路径参考application.properties中的配置
    @Value("${web.upload-path}")
    private String uploadPath;


    private static ResourceLoader resourceLoader;

    @Autowired
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    @Autowired
    private ImagesService imagesService;

    /**
     * POST请求
     * @param files
     * @return
     */
    @PostMapping(value = "/saveimage")
    public String index(@RequestParam("headimg")MultipartFile[] files,@RequestParam("uid")String  uid) {
//        //可以从页面传参数过来
//        logger.info(files.length+"");
//        logger.info(uid+"");
        List<Images> imagesList=imagesService.select(uid);
        if (imagesList.size()>0){
//            for (Images images : imagesList){
//                deletefile(uploadPath+images.getPath());
//            }

            imagesService.delete(uid);

        }
        String  nowTime= String.valueOf(System.currentTimeMillis());
        //这里可以支持多文件上传
        if(files!=null && files.length>=1) {
            BufferedOutputStream bw = null;
            try {
//                for (int i = 0;i<files.length;i++) {
                    String fileName = files[0].getOriginalFilename();
                    //判断是否有文件且是否为图片文件
                    if (fileName != null && !"".equalsIgnoreCase(fileName.trim()) && isImageFile(fileName)) {
                        String  hou = UUID.randomUUID().toString() + getFileType(fileName);
                        String  name=uploadPath + "/" + hou;
                        //创建输出文件对象
                        File outFile = new File(name);
                        //拷贝文件到输出文件对象
                        FileUtils.copyInputStreamToFile(files[0].getInputStream(), outFile);
                        imagesService.insert(Integer.parseInt(uid),hou,nowTime);
                    }
//                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                try {
                    if(bw!=null) {bw.close();}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }

    /**
     * 判断文件是否为图片文件
     * @param fileName
     * @return
     */
    private Boolean isImageFile(String fileName) {
        String [] img_type = new String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        if(fileName==null) {return false;}
        fileName = fileName.toLowerCase();
        for(String type : img_type) {
            if(fileName.endsWith(type)) {return true;}
        }
        return false;
    }

    /**
     * 获取文件后缀名
     * @param fileName
     * @return
     */
    private String getFileType(String fileName) {
        if(fileName!=null && fileName.indexOf(".")>=0) {
            return fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }
        return "";
    }


    //显示图片的方法关键 匹配路径像 localhost:8080/b7c76eb3-5a67-4d41-ae5c-1642af3f8746.png
    @RequestMapping(method = RequestMethod.GET, value = "/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {
//        String  name=uploadPath + "/" + filename;
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(uploadPath, filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    public boolean deletefile(String path){
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return false;
        }
        String[] str = file.list();
        for (int i = 0; i < str.length; i++) {
            System.out.println("333:"+str[i]);
            File fi = new File(path + "/" + str[i]);
            if (path.endsWith(file.separator)) {
                fi = new File(path + str[i]);
            } else {
                fi = new File(path + fi.separator + str[i]);
            }

            if(fi.exists()||fi.list().length==0){
                File myFilePath = new File(path+"/"+str[i]);
                myFilePath.delete();
            }
            if(fi.isDirectory())//如果文件假内还有 就继续调用本方法
            {
                deletefile(path+"/"+str[i]);
            }else{
                fi.delete();
            }

        }
        return true;
    }
}
