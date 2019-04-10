package com.hk.cal.daos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hk.dtos.CalDto;
@Repository
public class CalDao implements ICalDao{
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	private String namespace="com.hk.cal.";
	
	@Override
	public List<CalDto> getCalList(String id, String yyyyMMdd) {
		Map<String, String> map	= new HashMap<String,String>();
		map.put("id", id);
		map.put("yyyyMMdd", yyyyMMdd);
		return sqlSession.selectList(namespace+"callist",map);
	}

	@Override
	public boolean insertCalboard(CalDto dto) {
		int count = sqlSession.insert(namespace+"calinsert",dto);
		return count>0?true:false;
	}

	@Override
	public List<CalDto> getCalViewList(String id, String yyyyMM) {
		Map<String, String> map	= new HashMap<String,String>();
		map.put("id", id);
		map.put("yyyyMM", yyyyMM);
		return sqlSession.selectList(namespace+"calviewlist",map);
	}

	@Override
	public int getCalViewCount(String id, String yyyyMMdd) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("yyyyMMdd", yyyyMMdd);
		return sqlSession.selectOne(namespace+"calcount",map); 
	}
	@Override
	public CalDto getCalBoard(int seq) {
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("seq", seq);
		return sqlSession.selectOne(namespace+"caldetail",map);
	}

	@Override
	public boolean updateCalBoard(CalDto dto) {
		int count = sqlSession.update(namespace+"calupdate",dto);
		return count>0?true:false;
	}

	@Override
	public boolean calMulDel(String[] seqs) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("seqs", seqs);
		int count = sqlSession.delete(namespace+"calmuldel",map);
		return count>0?true:false;
	}

}
