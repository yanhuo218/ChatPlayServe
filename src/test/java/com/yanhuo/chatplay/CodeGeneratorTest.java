package com.yanhuo.chatplay;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * <p>
 * mybatis-plus代码生成器模板 适用版本: mybatis-plus-generator 3.5.1 及其以上版本
 * </p>
 *
 * @author yanhuo
 * @since 2023-03-11
 */
public class CodeGeneratorTest {
    @Test
    public void main1() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/chatplay", "root", "root").globalConfig(builder -> {
            builder.author("yanhuo")                // 设置作者
                    .disableOpenDir()               //设置不打开资源管理器
                    .dateType(DateType.ONLY_DATE)   //时间策略 默认:yyyy-MM-dd 可用 .commentDate("yyyy-MM-dd") 修改
//                    .enableSwagger()                // 开启 swagger 模式
                    .outputDir("D:\\Projects\\IDEA\\ChatPlayReset\\src\\main\\java"); // 指定输出目录
        }).packageConfig(builder -> {
            builder.parent("com.yanhuo")            //设置父包名
                    .moduleName("chatplay")         //设置父包模块名
                    .entity("entity")               //设置实体类包名
                    .service("service")             //设置service包名
                    .serviceImpl("service.impl")    //设置serviceImpl包名
                    .mapper("mapper")               //设置mapper包名
                    .xml("mapper.xml")              //设置mapperImpl包名
                    .controller("controller");      //设置controller包名
        }).strategyConfig(builder -> {
            builder.addInclude("chathistory", "user")   // 设置需要生成的表名 可多表
                    //---------------实体策略-----------------
                    .entityBuilder()                    //开启实体策略
                    .enableLombok()                     //开启lombok
                    .idType(IdType.ASSIGN_ID)           //id生成策略
                    .enableFileOverride()               //开启文件覆盖
//                    .addIgnoreColumns("_")            //表字段过滤
                    .enableTableFieldAnnotation()       //生成字段注解
                    .logicDeleteColumnName("is_deleted")        //逻辑删除字段名(数据库)
                    .logicDeletePropertyName("isDeleted")       //逻辑删除属性名(实体)
                    .columnNaming(NamingStrategy.underline_to_camel)    //字段的命名策略
                    //---------------service策略--------------
                    .serviceBuilder()       //开启service策略
                    .enableFileOverride()   //开启文件覆盖
                    .formatServiceFileName("%sService")         //格式化service接口文件名称 设置取消service前加I
                    .formatServiceImplFileName("%sServiceImpl") //设置取消serviceImpl前加I
                    //---------------controller策略------------
                    .controllerBuilder()    //开启controller策略
                    .enableRestStyle()
                    .enableFileOverride()   // 开启文件覆盖
                    //---------------mapper策略---------------
                    .mapperBuilder()        // 开启mapper策略
                    .enableFileOverride();  //开启文件覆盖
        }).execute();
    }
}
