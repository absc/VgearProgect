package com.example.newdemo;

import com.example.newdemo.util.SensitiveWordUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashSet;
import java.util.Set;

//@ComponentScan(basePackages = {"com.example.newdemo.dao"})
@SpringBootApplication
public class NewdemoApplication  {// extends SpringBootServletInitializer

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(NewdemoApplication.class);
//	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(NewdemoApplication.class, args);



//		System.out.println("敏感词的数量：" + SensitiveWordUtil.sensitiveWordMap.size());
//        String string = "太多的伤感情怀也许只局限于饲养基地 荧幕中的情节。"
//                + "然后我们的扮演的角色就是跟随着主人公的喜红客联盟 怒哀乐而过于牵强的把自己的情感也附加于银幕情节中，然后感动就流泪，"
//                + "难过就躺在某一个人的怀里尽情的阐述心扉或者手机卡复制器一个贱人一杯红酒一部电影在夜 深人静的晚上，关上电话静静的发呆着。";
//        System.out.println("待检测语句字数：" + string.length());
//
////		是否含有关键字
//        boolean result = SensitiveWordUtil.contains(string);
//        System.out.println(result);
//        result = SensitiveWordUtil.contains(string, SensitiveWordUtil.MinMatchTYpe);
//        System.out.println(result);
//
//        //获取语句中的敏感词
//        Set<String> set = SensitiveWordUtil.getSensitiveWord(string);
//        System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
//        set = SensitiveWordUtil.getSensitiveWord(string, SensitiveWordUtil.MinMatchTYpe);
//        System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
//
//        //替换语句中的敏感词
//        String filterStr = SensitiveWordUtil.replaceSensitiveWord(string, '*');
//        System.out.println(filterStr);
//        filterStr = SensitiveWordUtil.replaceSensitiveWord(string, '*', SensitiveWordUtil.MinMatchTYpe);
//        System.out.println(filterStr);
//
//        String filterStr2 = SensitiveWordUtil.replaceSensitiveWord(string, "[*敏感词*]");
//        System.out.println(filterStr2);
//        filterStr2 = SensitiveWordUtil.replaceSensitiveWord(string, "[*敏感词*]", SensitiveWordUtil.MinMatchTYpe);
//        System.out.println(filterStr2);
	}


}
