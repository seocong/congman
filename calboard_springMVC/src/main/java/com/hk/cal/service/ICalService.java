package com.hk.cal.service;

import java.util.List;

import com.hk.dtos.CalDto;

public interface ICalService {
public List<CalDto> getCalList(String id, String yyyyMMdd);
	
	//일정 추가
	public boolean insertCalboard(CalDto dto);
	
	//달력에 일정일부를 보여주는 기능(달력에 일정 표시)
	public List<CalDto> getCalViewList(String id, String yyyyMM) ;
	
	//달력에서 일정개수를 보여주는 기능
	public int getCalViewCount(String id, String yyyyMMdd);
	
	//일정상세보기
	public CalDto getCalBoard(int seq);
	
	public boolean updateCalBoard(CalDto dto) ;
	//일정삭제하기
	public boolean calMulDel(String[] seqs);
}
