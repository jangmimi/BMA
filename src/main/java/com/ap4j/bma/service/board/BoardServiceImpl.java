package com.ap4j.bma.service.board;


import com.ap4j.bma.model.entity.board.BoardDTO;
import com.ap4j.bma.model.entity.board.BoardEntity;
import com.ap4j.bma.model.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {


	// BoardRepository 인터페이스 객체의 Bean 을 자동으로 주입
	@Autowired
	private BoardRepository boardRepository ;

	@Override
	// Board 전체 목록 실행
	public List<BoardEntity> findAll() {
		log.info("BoardService 의 index() 실행");

		return boardRepository.findAll();
	}

	@Override
	// 1건 조회
	public BoardEntity findOne(Long id) {
		log.info("BoardService 의 show() 실행");

		return boardRepository.findById(id).orElse(null);
	}





	@Override
	// 생성
	public BoardEntity save(BoardDTO dto) {
		log.info("BoardService 의 save() 실행");
		BoardEntity board = dto.toEntity();

		// id는 DB가 자동으로 생성하므로 id가 넘어오는 데이터를 저장 하지 않는다.
		if (board.getId() != null) {
			return null;
		}
		return boardRepository.save(board);
	}

	@Override
	@Transactional
	public String saveBoard(BoardDTO dto) {
		if (dto.getTitle() != null && dto.getContent() != null) {
			save(dto);
			log.info("BoardController.boardSave() : data add complete");
			return "redirect:/board";
		} else {
			log.info("BoardController.boardSave() : data add failed");
			return "redirect:board/write/failed";
		}
	}

	@Override
	// 수정
	public BoardEntity update(Long id, BoardDTO dto) {
		log.info("BoardService 의 update() 실행");
		BoardEntity board = dto.toEntity();
		BoardEntity target = boardRepository.findById(id).orElse(null);

		// 수정할 대상이 없거나 id 가 다른 경우 잘못된 요청이다 (400 에러 띄움)
		if (target != null || id != board.getId()) {
			log.info("잘못된 요청! id: {}, Board: {}", id, board.toString());
			return null;
		}
		// 수정할 title 이나 수정할 age 가 입력 되었는지 확인
		// 수정할 대상이 있는 필드들을 새로 저장해주기
		// patch() 메서드가 한다.
		target.patch(dto.toEntity());
		return boardRepository.save(target);
	}

	@Override
	// 삭제
	public BoardEntity delete(Long id) {
		log.info("BoardService 의 delete() 실행");
		BoardEntity target = boardRepository.findById(id).orElse(null);

		// 수정할 대상이 없거나 id 가 다른 경우 잘못된 요청이다 (400 에러 띄움)
		if (target != null) {
			log.info("잘못된 요청! {} 번 글은 테이블에 존재하지 않습니다. ", id);
			return null;
		}
		boardRepository.delete(target);
		return target;
	}


}
