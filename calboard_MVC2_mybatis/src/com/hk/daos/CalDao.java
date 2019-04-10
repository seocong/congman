package com.hk.daos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.hk.config.SqlMapConfig;
import com.hk.dtos.CalDto;

public class CalDao extends SqlMapConfig{
	private String namespace="com.hk.calboard.";
	
	//일정목록조희: 파라미터(id, 년월일 8자리)
	public List<CalDto> getCalList(String id, String yyyyMMdd){
		List<CalDto> list=null;
		SqlSession sqlSession=null;
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("yyyyMMdd", yyyyMMdd);
		try {
			sqlSession = getsqlSessionFactory().openSession(true);
			list=sqlSession.selectList(namespace+"callist", map);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		
		return list;
	}
	
	//일정 추가
	public boolean insertCalboard(CalDto dto) {
		int count=0;
		SqlSession sqlSession=null;
		try {
			sqlSession = getsqlSessionFactory().openSession(true);
			count=sqlSession.insert(namespace+"calinsert",dto);
		} catch (Exception e) {
			// TODO: handle exception'
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return count>0?true:false;
	}
	
	//달력에 일정일부를 보여주는 기능(달력에 일정 표시)
	public List<CalDto> getCalViewList(String id, String yyyyMM) {
		List<CalDto> list=null;
		SqlSession sqlSession = null;
		Map<String, String>map=new HashMap<>();
		map.put("id", id);
		map.put("yyyyMM", yyyyMM);
		try {
			sqlSession = getsqlSessionFactory().openSession(true);
			list=sqlSession.selectList(namespace+"calviewlist",map);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return list;
	}
	
	//달력에서 일정개수를 보여주는 기능
	public int getCalViewCount(String id, String yyyyMMdd) {
		int count =0;
		SqlSession sqlSession = null;
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("yyyyMMdd", yyyyMMdd);
		try {
			sqlSession = getsqlSessionFactory().openSession(true);
			count = sqlSession.selectOne(namespace+"calcount", map);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return count;
	}
	
	//일정상세보기
	public CalDto getCalBoard(int seq) {
		CalDto dto=null;
		SqlSession sqlSession=null;
		try {
			sqlSession = getsqlSessionFactory().openSession(true);
			dto=sqlSession.selectOne(namespace+"caldetail",seq);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return dto;
	}
	
	public boolean updateCalBoard(CalDto dto) {
		int count=0;
		SqlSession sqlSession=null;
		try {
			sqlSession = getsqlSessionFactory().openSession(true);
			count=sqlSession.update(namespace+"calupdate",dto);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			sqlSession.close();
		}
		return count>0?true:false;
	}
	//일정삭제하기
	public boolean calMulDel(String[] seqs) {
		int count = 0;
		Map<String,String[]> map = new HashMap<>();
		map.put("seqs", seqs);
		SqlSession sqlSession=null;
		try {
			sqlSession=getsqlSessionFactory().openSession(true);
			count=sqlSession.delete(namespace+"calmuldel",map);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return count>0?true:false;
	}
}
