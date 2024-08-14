package com.application.trainingVer1.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.application.trainingVer1.board.dto.BoardDTO;
import com.application.trainingVer1.board.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService; // BoardServiceImpl 객체를 주입한다.
	
	@GetMapping
	public String main() {
		return "board/boardMain";
	}
	
	@GetMapping("/createBoard")     // localhost/board/createBoard로 요청시 아래의 메서드로 매핑된다.
	public String createBoard() {
		return "board/createBoard"; // templates/board/createBoard.html로 이동
	}
	
	@PostMapping("/createBoard") // createBoard.html에서 submit진행시 아래의 메서드로 매핑된다.
	public String createBoard(@ModelAttribute BoardDTO boardDTO) {
		
		// 단위테스트 , CCTV
		//System.out.println(boardDTO);
		
		boardService.createBoard(boardDTO);
		
		// 메세지 출력후 게시글전체조회 화면으로 이동한다.
		String jsScript = """
				<script>
			 	   alert('작성 되었습니다.');
			 	   location.href = '/board/boardList';
		   	    </script>""";
		
		return jsScript;
		
	}
	
	@GetMapping("/boardList") // localhost/board/boardList로 요청시 아래의 메서드로 매핑된다.
	public String boardList(Model model) {
		
		model.addAttribute("boardList", boardService.getBoardList()); // Service객체에서 게시글전체리스트를 반환받아 Model 객체에 저장한다.
		
		return "board/boardList"; // templates/board/boardList.html(게시글 전체 리스트 포함)로 이동
	}
	
	@GetMapping("/boardDetail") // localhost/board/boardDetail로 요청시 아래의 메서드로 매핑된다.
	public String boardDetail(Model model, @RequestParam("boardId") long boardId) {
		
		// Service객체에서 boardId를 전달하고 1개의 게시글정보를 반환받아 Model 객체에 저장한다.
		model.addAttribute("boardDTO", boardService.getBoardDetail(boardId));
		
		return "board/boardDetail"; // templates/board/boardDetail.html(1개의 게시글 데이터포함)로 이동
	}
	

}


