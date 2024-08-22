package com.application.trainingVer1.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.application.trainingVer1.board.dto.BoardDTO;
import com.application.trainingVer1.board.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService; // BoardServiceImpl 객체를 주입한다.

	@GetMapping // localhost/board로 요청시 아래의 메서드로 매핑된다.
	public String main() {
		return "board/boardMain"; // templates/board/boardMain.html로 이동
	}
	
	@GetMapping("/createBoard")     // localhost/board/createBoard로 요청시 아래의 메서드로 매핑된다.
	public String createBoard() {
		return "board/createBoard"; // templates/board/createBoard.html로 이동
	}
	
	@PostMapping("/createBoard") // createBoard.html에서 submit진행시 아래의 메서드로 매핑된다.
	@ResponseBody
	public String createBoard(@ModelAttribute BoardDTO boardDTO) {  // <form>태그의 데이터를 BoardDTO타입으로 전달받는다.
		
		// 단위테스트 , CCTV
		//System.out.println(boardDTO);
		
		boardService.createBoard(boardDTO); // createBoard.html에서 전달받은 데이터를 Service객체로 전달한다.
		
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
		
		model.addAttribute("boardList" , boardService.getBoardList());  // Service객체에서 게시글전체리스트를 반환받아 Model 객체에 저장한다.
		
		return "board/boardList"; // templates/board/boardList.html(게시글 전체 리스트 포함)로 이동
		
	}
	
	
	@GetMapping("/boardDetail") // localhost/board/boardDetail로 요청시 아래의 메서드로 매핑된다.
	public String boardDetail(Model model , @RequestParam("boardId") long boardId) {  // 요청시 함께 전송된 데이터 boardId를 받는다.
		
		// Service객체에서 boardId를 전달하고 1개의 게시글정보를 반환받아 Model 객체에 저장한다.
		model.addAttribute("boardDTO" , boardService.getBoardDetail(boardId , true));
		
		return "board/boardDetail"; // templates/board/boardDetail.html(1개의 게시글 데이터포함)로 이동
		
	}
	
	
	@GetMapping("/authentication") // localhost/board/authentication로 요청시 아래의 메서드로 매핑된다.
	public String authentication(Model model,
								 @RequestParam("boardId") long boardId,
								 @RequestParam("menu") String menu) { // 요청시 함께 전송된 데이터 boardId , menu를 받는다.
		
		// Service객체에서 boardId를 전달하고 1개의 게시글정보를 반환받아 Model 객체에 저장한다.
		model.addAttribute("boardDTO" , boardService.getBoardDetail(boardId , false));
		
		//요청시 함께 전송된 데이터 menu(update or delete)를 Model 객체에 저장한다.
		model.addAttribute("menu" , menu);
		
		return "board/authentication"; // templates/board/authentication.html(1개의 게시글 데이터 , menu 포함)로 이동
		
	}
	
	
	@PostMapping("/authentication") // authentication.html에서 submit진행시 아래의 메서드로 매핑된다.
	@ResponseBody
	public String authentication(@ModelAttribute BoardDTO boardDTO ,
								 @RequestParam("menu") String menu) { // <form>태그의 (boardId , passwd)를 @ModelAttribute으로 전달받고 함께 전송된 menu를 @RequestParam으로 전달받는다.
		
		String jsScript = "";
		if (boardService.checkAuthenticationUser(boardDTO)) { // 인증이 성공할 경우 true가 반환된다.
			
			if (menu.equals("update")) {
				jsScript = "<script>";
				jsScript +=	"location.href = '/board/updateBoard?boardId=" + boardDTO.getBoardId() + "';";
				jsScript += "</script>";
			}
			else if (menu.equals("delete")) {
				jsScript = "<script>";
				jsScript +=	"location.href = '/board/deleteBoard?boardId=" + boardDTO.getBoardId() + "';";
				jsScript += "</script>";
			}
			
		}
		else {
			jsScript = """
					<script>
				 	   alert('패스워드를 확인하세요.');
				 	   history.go(-1);
			   	    </script>""";
		}
		
		return jsScript;
		
	}
	
	
	@GetMapping("/updateBoard")  // localhost/board/updateBoard로 요청시 아래의 메서드로 매핑된다.
	public String updateBoard(Model model , @RequestParam("boardId") long boardId) {  // authentication에서 전송된 데이터 boardId를 받는다.
		
		// Service객체에서 boardId를 전달하고 1개의 게시글정보를 반환받아 Model 객체에 저장한다.
		model.addAttribute("boardDTO", boardService.getBoardDetail(boardId  , false));
		
		return "board/updateBoard"; // templates/board/updateBoard.html(1개의 게시글 데이터포함)로 이동
		
	}
	
	
	@PostMapping("/updateBoard") // updateBoard.html에서 submit진행시 아래의 메서드로 매핑된다.
	@ResponseBody
	public String updateBoard(@ModelAttribute BoardDTO boardDTO) { // <form>태그의 데이터를 BoardDTO타입으로 전달받는다.
		
		// updateBoard.html에서 전달받은 데이터를 Service객체로 전달한다.
		boardService.updateBoard(boardDTO);
		
		// 메세지 출력후 게시글전체조회 화면으로 이동한다.
		String jsScript = """
				<script>
			 	   alert('수정 되었습니다.');
			 	   location.href = '/board/boardList';
		   	    </script>""";
		
		return jsScript;	  
		
	}
	
	
	@GetMapping("/deleteBoard") // localhost/board/deleteBoard로 요청시 아래의 메서드로 매핑된다.
	public String deleteBoard(Model model , 
							  @RequestParam("boardId") long boardId) { // authentication에서 전송된 데이터 boardId를 받는다.
		
		 // authentication에서 전송된 데이터를 Model 객체에 저장한다.
		model.addAttribute("boardId", boardId);
		
		return "board/deleteBoard"; // templates/board/deleteBoard.html(boardId 데이터포함)로 이동
		
	}
	
	
	@PostMapping("/deleteBoard") // deleteBoard.html에서 submit진행시 아래의 메서드로 매핑된다.
	@ResponseBody
	public String deleteBoard(@RequestParam("boardId") long boardId) {  // 요청시 함께 전송된 데이터 boardId를 받는다.
		
		// deleteBoard.html에서 전달받은 boardId를 Service객체로 전달한다.
		boardService.deleteBoard(boardId);
		
		// 메세지 출력후 게시글 전체조회 화면으로 이동한다.
		String jsScript = """
				<script>
			 	   alert('삭제 되었습니다.');
			 	   location.href = '/board/boardList';
		   	    </script>""";
	
		return jsScript;	
		
	}
	
	
	
	
	
	
}


