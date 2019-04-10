package com.hk.cal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hk.cal.daos.ICalDao;
import com.hk.dtos.CalDto;

@Service
public class CalService implements ICalService{
	@Autowired
	private ICalDao calDao;
	
	@Override
	public List<CalDto> getCalList(String id, String yyyyMMdd) {
		return calDao.getCalList(id, yyyyMMdd);
	}

	@Override
	public boolean insertCalboard(CalDto dto) {
		return calDao.insertCalboard(dto);
	}

	@Override
	public List<CalDto> getCalViewList(String id, String yyyyMM) {
		return calDao.getCalViewList(id, yyyyMM);
	}

	@Override
	public int getCalViewCount(String id, String yyyyMMdd) {
		return calDao.getCalViewCount(id, yyyyMMdd);
	}

	@Override
	public CalDto getCalBoard(int seq) {
		return calDao.getCalBoard(seq);
	}

	@Override
	public boolean updateCalBoard(CalDto dto) {
		return calDao.updateCalBoard(dto);
	}

	@Override
	public boolean calMulDel(String[] seqs) {
		return calDao.calMulDel(seqs);
	}

}
