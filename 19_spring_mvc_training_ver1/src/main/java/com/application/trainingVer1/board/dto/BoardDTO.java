package com.application.trainingVer1.board.dto;

import java.util.Date;

import lombok.Data;

@Data
public class BoardDTO {
	
	private long boardId;
	private String passwd;
	private String writer;
	private String subject;
	private String content;
	private int readCnt;
	private Date enrollAt;
}
