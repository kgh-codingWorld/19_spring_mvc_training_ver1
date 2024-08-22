package com.application.trainingVer1.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.trainingVer1.board.dao.BoardDAO;
import com.application.trainingVer1.board.dto.BoardDTO;

/*

	# 패스워드 암호화
	
	1) build.gradle 파일에 dependency를 추가한다.
	
		// 패스워드 인코더
		implementation 'org.springframework.boot:spring-boot-starter-security' 
	
	2) SecurityConfig 클래스를 생성한다.
	
		@Configuration
		@EnableWebSecurity
		public class SecurityConfig{
		
		    @Bean
		    public PasswordEncoder passwordEncoder(){
		        return new BCryptPasswordEncoder();
		    }
		
		    @Bean
			public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
				http.cors().disable()			//cors 방지
					.csrf().disable()			//csrf 방지
					.formLogin().disable()		//기본 로그인페이지 없애기
					.headers().frameOptions().disable();
		 
				return http.build();
			}
		    
		}
	
	
	3) 서비스 로직에서 BCryptPasswordEncoder 객체를 생성한다.
	
		@Autowired
		private PasswordEncoder passwordEncoder;
	
	4) 사용법
	
		- passwordEncoder.encode(암호화하고 싶은 패스워드)   				// encode(평문)메서드를 통하여 패스워드를 암호화한다.
		- passwordEncoder.matches(입력받은 패스워드, 암호화된 패스워드) 	// matches(평문,암호문) 메서드를 통하여 입력받은 패스워드와 암호화된 패스워드를 비교한다.
		- 암호화된 패스워드를 복호화하는 메서드는 존재하지 않는다.		

*/


@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDAO; // BoardDAO객체를 주입한다.

	@Autowired
	private PasswordEncoder passwordEncoder; // SecurityConfig에서 생성된 BCryptPasswordEncoder 객체를 주입한다.
	
	@Override
	public void createBoard(BoardDTO boardDTO) {
		
		// 전달받은 객체의 비밀번호 데이터를 꺼내고 암호화하여 다시 저장한다.
		boardDTO.setPasswd(passwordEncoder.encode(boardDTO.getPasswd()));
		// 암호화된 비밀번호가 저장된 DTO객체를 DAO객체로 전달한다.
		boardDAO.createBoard(boardDTO);
		
	}

	@Override
	public List<BoardDTO> getBoardList() {
		// DAO 객체에서 게시글전체리스트를 반환받아 Controller 객체로 반환한다.
		return boardDAO.getBoardList();
	}

	@Override
	public BoardDTO getBoardDetail(long boardId , boolean isUpdateReadCnt) {
		
		// Controller 객체에서 boardId를 전달받아 DAO로 전달하여 조회수를 1증가 시킨다.
		if (isUpdateReadCnt) {
			boardDAO.updateReadCnt(boardId);
		}
		
		// Controller 객체에서 boardId를 전달받아 DAO로 전달한 후 반환된 1개의 게시글 정보를 Controller 객체로 반환한다.
		return boardDAO.getBoardDetail(boardId);
		
	}

	@Override
	public boolean checkAuthenticationUser(BoardDTO boardDTO) {
		
		// DAO에서 반환된 암호화된 비밀번호를 임시변수에 저장한다.
		String encodedPassword = boardDAO.getPasswd(boardDTO.getBoardId());
		
		// matches()메서드를 사용하여 Controller에서 전달된 패스워드(평문)와 db에서 조회한 패스워드(암호화)를 비교한다.
		boolean isAuthenticationUser = passwordEncoder.matches(boardDTO.getPasswd(), encodedPassword);
		
		return isAuthenticationUser;
		
		// 한방
		//return passwordEncoder.matches(boardDTO.getPasswd(), boardDAO.getPasswd(boardDTO.getBoardId()));
		
	}

	@Override
	public void updateBoard(BoardDTO boardDTO) {
		// Controller 객체에서 1개의 게시글을 전달받아 DAO로 전달한다.
		boardDAO.updateBoard(boardDTO);
	}

	@Override
	public void deleteBoard(long boardId) {
		// Controller 객체에서 boardId를 전달받아 DAO로 전달한다.
		boardDAO.deleteBoard(boardId);
	}
	
	
	
	
}
