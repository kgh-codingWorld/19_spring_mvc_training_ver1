package com.application.trainingVer1.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.trainingVer1.board.dao.BoardDAO;
import com.application.trainingVer1.board.dto.BoardDTO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDAO; // BoardDAO객체를 주입한다.

	@Override
	public void createBoard(BoardDTO boardDTO) {
		
		// 비밀번호 암호화할 예정
		
		boardDAO.createBoard(boardDTO);
		
	}

	@Override
	public List<BoardDTO> getBoardList() {
		// DAO 객체에서 게시글전체리스트를 반환받아 Controller 객체로 반환한다.
		return boardDAO.getBoardList();
	}

	@Override
	public BoardDTO getBoardDetail(long boardId) {
		
		// Controller 객체에서 boardId를 전달받아 DAO로 전달하여 조회수를 1증가 시킨다.
		boardDAO.updateReadCnt(boardId);
		
		// Controller 객체에서 boardId를 전달받아 DAO로 전달한 후 반환된 1개의 게시글 정보를 Controller 객체로 반환한다.
		return boardDAO.getBoardDetail(boardId);
	}

	
	
	
	
}
