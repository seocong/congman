package com.hk.dtos;

import java.io.Serializable;
import java.util.Date;

public class CalDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int seq;
	private String id;
	private String title;
	private String content;
	private String mdate;
	private Date regdate;
	
	public CalDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CalDto(int seq, String id, String title, String content, String mdate, Date regdate) {
		super();
		this.seq = seq;
		this.id = id;
		this.title = title;
		this.content = content;
		this.mdate = mdate;
		this.regdate = regdate;
	}

	public int getSeq() {
		return seq;
	}

	public CalDto setSeq(int seq) {
		this.seq = seq;
		return this;
	}

	public String getId() {
		return id;
	}

	public CalDto setId(String id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public CalDto setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getContent() {
		return content;
	}

	public CalDto setContent(String content) {
		this.content = content;
		return this;
	}

	public String getMdate() {
		return mdate;
	}

	public CalDto setMdate(String mdate) {
		this.mdate = mdate;
		return this;
	}

	public Date getRegdate() {
		return regdate;
	}

	public CalDto setRegdate(Date regdate) {
		this.regdate = regdate;
		return this;
	}

	@Override
	public String toString() {
		return "CalDto [seq=" + seq + ", id=" + id + ", title=" + title + ", content=" + content + ", mdate=" + mdate
				+ ", regdate=" + regdate + "]";
	}
	
	
}
