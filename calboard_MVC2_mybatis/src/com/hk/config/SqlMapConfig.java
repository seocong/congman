/*
 * 매우 중요한 파일이다. 
 */
package com.hk.config;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlMapConfig {
	
	private SqlSessionFactory sqlSessionFactory;
	
	public SqlSessionFactory getsqlSessionFactory() {
		String resource="com/hk/sql/Configuration.xml";//환경설정파일 경로
		
		try {
			//Reader: 메모지, Resources: 메모지를 만드는 객체
			//getResourceAsReader: 메모지에 메모를 한다.
			Reader reader = Resources.getResourceAsReader(resource);// resource에 선언된것을 읽어옴. configuration에 있는 코드를 자바로 가지고 온다.
			//SqlSessionFactoryBuilder(): sqlSessionFactory 객체를 구하는 객체
			//build(): 메모지를 전달
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);// SqlSessionFactory는 인터페이스로 아무것도 없기때문에 bulider로 객체 생성을 해주어야함
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sqlSessionFactory;
	}
}
