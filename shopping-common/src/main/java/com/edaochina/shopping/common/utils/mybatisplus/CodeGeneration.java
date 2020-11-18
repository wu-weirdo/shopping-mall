package com.edaochina.shopping.common.utils.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @BelongsProject: customize
 * @BelongsPackage: com.edaochina.shopping.common.utils.mybatisPlusUtil
 * @Author: Jason
 * @CreateTime: 2018-10
 * @Description: 代码生成器
 */
public class CodeGeneration {
    /**
     * @param args
     * @Title: main
     * @Description: 生成
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("D://file");
        gc.setFileOverride(true);
        /**
         * 不需要ActiveRecord特性的请改为false
         */
        gc.setActiveRecord(true);
        /**
         * XML 二级缓存
         */
        gc.setEnableCache(false);
        /**
         * XML ResultMap
         */
        gc.setBaseResultMap(true);
        /**
         * XML columList
         */
        gc.setBaseColumnList(true);
        /**
         * 作者
         */
//        gc.setAuthor("xww");

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("Hzydkj@75342");
        dsc.setUrl("jdbc:mysql://116.62.207.162:3306/cdshopping?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        /**
         * 此处可以修改为您的表前缀
         */
//        strategy.setTablePrefix(new String[]{"sys_"});
        /**
         * 表名生成策略
         */
        strategy.setNaming(NamingStrategy.underline_to_camel);
        /**
         * 需要生成的表
         */
        //TODO 此处添加你需要生成的表 数组
        strategy.setInclude(new String[]{"withdrawal_record"});
        /**
         * 是否为lombok模型
         */
        strategy.setEntityLombokModel(true);
        /**
         * Boolean类型字段是否移除is前缀处理
         */
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        strategy.setRestControllerStyle(true);
        strategy.setEntityBuilderModel(true);
        /**
         * 设置乐观锁字段名
         */
        strategy.setVersionFieldName("version");
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.edaochina.shopping");
        //TODO 此处请修改成你需要生成的模块对应的目录
//        pc.setController("web.user");
//        pc.setService("service.user");
//        pc.setServiceImpl("service.user.impl");
//        pc.setMapper("dao.user");
//        pc.setEntity("domain.entity.user");

        pc.setController("web");
        pc.setService("api.service");
        pc.setServiceImpl("api.service.impl");
        pc.setMapper("api.dao");
        pc.setEntity("domain.entity");
        pc.setXml("xml");
        mpg.setPackageInfo(pc);

        // 执行生成
        mpg.execute();
    }
}

