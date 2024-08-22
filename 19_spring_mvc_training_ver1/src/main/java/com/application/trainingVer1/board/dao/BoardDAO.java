package com.application.trainingVer1.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.application.trainingVer1.board.dto.BoardDTO;

@Mapper
public interface BoardDAO {

	public void createBoard(BoardDTO boardDTO);		// Service에서 전달받은 객체를 <id="createBoard"> 매퍼로 전달한다.
	public List<BoardDTO> getBoardList();			// <id="getBoardList"> 매퍼에서 게시글전체리스트를 검색하여 DAO객체로 전달한다.
	public BoardDTO getBoardDetail(long boardId);	// <id="getBoardDetail"> 매퍼에 boardId를 전달하고 1개의 게시글정보를 검색하여 DAO객체로 전달한다.
	public void updateReadCnt(long boardId);		// <id="updateReadCnt"> 매퍼에 boardId를 전달한다.
	public String getPasswd(long boardId);			// <id="getPasswd"> 매퍼에 boardId를 전달하고 비밀번호를 검색하여 DAO객체로 전달한다.
	public void updateBoard(BoardDTO boardDTO);		// <id="updateBoard"> 매퍼에 1개의 게시글정보를 전달한다.
	public void deleteBoard(long boardId);			// <id="deleteBoard"> 매퍼에 boardId를 전달한다.
	
	
	
}
