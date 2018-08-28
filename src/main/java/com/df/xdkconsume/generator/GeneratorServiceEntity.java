//package com.df.xdkconsume.generator;
//import org.junit.Test;
//
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.rules.DbType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//
///**
// * @author df
// * @version 创建时间：2018年7月5日 下午6:57:29
// * @Description 生成代码
// */
//public class GeneratorServiceEntity {
//	String dbUrl = "jdbc:sqlserver://121.46.26.158:1980;DatabaseName=czn_xycykt";//数据库地址
//	String userName = "czn_wxykt";//用户名
//	String password = "WeiXin*1300/1040";//密码
//	String author = "df";//作者
//	String place = "d:\\mybats-plus-generate";//生成路径
//	String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";//驱动
//	DbType type = DbType.SQL_SERVER;//数据库类型
//	String packageName = "com.df.xdkconsume";//包名
//	String[] tables = {"Ct_Canteen","Food_Menu"};//修改替换成你需要的表名，多个表名传数组
//	boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
//
//	@Test
//	public void generateCode() {
//		generateByTables(serviceNameStartWithI, packageName,tables);
//	}
//
//	private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
//		GlobalConfig config = new GlobalConfig();
//		DataSourceConfig dataSourceConfig = new DataSourceConfig();
//
//		dataSourceConfig.setDbType(type)
//		.setUrl(dbUrl)
//		.setUsername(userName)
//		.setPassword(password)
//		.setDriverName(driver);
//		StrategyConfig strategyConfig = new StrategyConfig();
//		strategyConfig
//		.setCapitalMode(true)
//		.setEntityLombokModel(false)
//		.setDbColumnUnderline(true)
//		.setNaming(NamingStrategy.underline_to_camel)
//		.setInclude(tableNames);
//		config.setActiveRecord(false)
//		.setAuthor(author)
//		.setOutputDir(place)
//		.setFileOverride(true)
//		.setEnableCache(false);
//		if (!serviceNameStartWithI) {
//			config.setServiceName("%sService");
//		}
//		new AutoGenerator().setGlobalConfig(config)
//		.setDataSource(dataSourceConfig)
//		.setStrategy(strategyConfig)
//		.setPackageInfo(
//				new PackageConfig()
//				.setParent(packageName)
//				.setController("controller")
//				.setEntity("entity")
//				).execute();
//	}
//}
