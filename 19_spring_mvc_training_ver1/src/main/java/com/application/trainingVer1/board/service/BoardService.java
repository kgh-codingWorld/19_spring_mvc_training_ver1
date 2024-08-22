package com.application.trainingVer1.board.service;

import java.util.List;

import com.application.trainingVer1.board.dto.BoardDTO;

public interface BoardService {

	public void createBoard(BoardDTO boardDTO);
	public List<BoardDTO> getBoardList();
	public BoardDTO getBoardDetail(long boardId , boolean isUpdateReadCnt);
	public boolean checkAuthenticationUser(BoardDTO boardDTO);
	public void updateBoard(BoardDTO boardDTO);
	public void deleteBoard(long boardId);
	
}




