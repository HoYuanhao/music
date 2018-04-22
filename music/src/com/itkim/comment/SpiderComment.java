package com.itkim.comment;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itkim.entity.CommentBean;
import com.itkim.entity.Comments;
import com.itkim.utils.EncryptUtils;

/**
 * Response里面的链接没改 R_SO_4_386538
 * 
 * @author KimJun
 *
 */
public class SpiderComment {
	private static String driverClass="com.mysql.jdbc.Driver";
	private static String jdbcUrl="jdbc:mysql://123.207.155.230:3306/root?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";
	private static String username="root";
	private static String password="HeYuanHao1!";
	private static String validationQuery="SELECT 1";


	public static void  main(String[] args) throws InterruptedException, SQLException {
		// 数据库连接池没改好
	
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setValidationQuery(validationQuery);
		Connection con=null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e2) {
			System.out.println("数据库断开连接");
			try {
				Thread.sleep(60000l);
			} catch (InterruptedException e) {
				
			}
			try {
				con=dataSource.getConnection();
				System.out.println("数据库重新连接");
			} catch (SQLException e) {
				System.out.println("数据库断开连接");
				Thread.sleep(5*60000l);
				con = dataSource.getConnection();
				System.out.println("数据库重新连接");
			}
		}
		PreparedStatement pstmt = null;
		long id=150000;
		
		for(long j = 1; j <= Long.MAX_VALUE; j++){
			Thread.sleep((long) (5*1000+Math.random()*2000));
		for (long i = 1; i <= Long.MAX_VALUE; i++) {
			try{
			Thread.sleep(3000 + (i % 2) * 1000);
			String secKey = new BigInteger(100, new SecureRandom()).toString(32).substring(0, 16);
			String encText = EncryptUtils.aesEncrypt(
					EncryptUtils.aesEncrypt("{\"offset\":" + i*10 + ",\"limit\":" + 10 + "};", "0CoJUm6Qyw8W8jud"),
					secKey);
			String encSecKey = EncryptUtils.rsaEncrypt(secKey);
			
			Response execute = Jsoup
					.connect("http://music.163.com/weapi/v1/resource/comments/R_SO_4_"+id+"?csrf_token=")
					.header("Referer", "http://music.163.com/song?id="+id)
					.header("Cookie",
							"usertrack=ZUcIhlnSS1qHgwIWFHDLAg==; _ntes_nnid=049405498bcee7a5fa1501f140d72401,1506954083542; _ntes_nuid=049405498bcee7a5fa1501f140d72401; JSESSIONID-WYYY=euocg8ZvRO0I%2F4bE5%2BaS4dmjvTbVRYFApZS7FuHgy%2B5Q%2BIVIyD7G6D%5CwtF0DyqHMvXFqF7EzoHhWAvz80o1h7tp%2BYbQHh738CVVjSGfeyJ9F6mEUHqiG8A%2BRHsnAI6itr6NDZdrrY%2B7Z%5CfYz%2BmsP1WaTlYj4AbZr7T75JBS%2BmHno1Kz9%3A1523114767870; _iuqxldmzr_=32; __utma=94650624.705728554.1522945659.1523017480.1523112968.4; __utmb=94650624.5.10.1523112968; __utmc=94650624; __utmz=94650624.1523112968.4.4.utmcsr=baidu|utmccn=(organic)|")
					.data("params", encText).data("encSecKey", encSecKey)
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
					.header("Cache-Control", "no-cache").header("Accept", "*/*")
					.header("Accept-Ecoding", "gzip, deflate").header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
					.header("Connection", "keep-alive").header("Host", "music.163.com")
					.header("Content-Type", "application/x-www-form-urlencoded").method(Method.POST)
					.ignoreContentType(true).timeout(2000000000).execute();
			
			
			String string = execute.body().toString();
			CommentBean readValue=null;
			try{
			 readValue = JSON.parseObject(string, CommentBean.class);
			}catch(Exception e){
			
			}
	
		if(readValue==null|readValue.getComments()==null||readValue.getComments().size()==0){
				JSONObject json=JSONObject.parseObject(string);
			boolean more=json.getBoolean("more");
				if(!more){
					id++;
					break;
				}
				
		}
			if(readValue!=null){
			List<Comments> comments = readValue.getComments();
			if(comments==null){
				Thread.sleep(20*60*1000);
			}
		if(comments!=null){
			for (Comments comments2 : comments) {
				String selectSql = "select count(*) as count from NetEasyMusicComments_Mayday1 where id=?";
				String sql = "insert into NetEasyMusicComments_Mayday1 value(?,?,?,?,?,?)";
				pstmt = con.prepareStatement(selectSql);
				pstmt.setLong(1, comments2.getCommentId());
				ResultSet resultSet = pstmt.executeQuery();

				int count = 0;
				if (resultSet.next()) {
					count = resultSet.getInt("count");
				}
		
				if (count == 0) {
					pstmt = con.prepareStatement(sql);
					pstmt.setLong(1, comments2.getCommentId());
					pstmt.setString(2, comments2.getUser().getNickname()
							.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*"));
					// System.out.println(comments2.getUser().getNickname());
					pstmt.setString(3,
							comments2.getContent().replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*"));
					// System.out.println(comments2.getContent());
					pstmt.setLong(4, comments2.getLikedCount());
					// System.out.println(comments2.getLikedCount());
					pstmt.setLong(5, comments2.getTime());
					// System.out.println(comments2.getTime());
					pstmt.setString(6, comments2.getUser().getAvatarUrl());
					// System.out.println(comments2.getUser().getAvatarUrl());
					System.out.println("注入数据库-------------"+comments2+"---------"+id);
					pstmt.execute();
					}
				}
			}
		}
			
		}
		catch(Exception e){
			id++;
			
		}
	}
}}
	}
