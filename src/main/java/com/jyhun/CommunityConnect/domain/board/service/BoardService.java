package com.jyhun.CommunityConnect.domain.board.service;

import com.jyhun.CommunityConnect.domain.board.dto.BoardResponseDTO;
import com.jyhun.CommunityConnect.domain.board.dto.BoardSearchDTO;
import com.jyhun.CommunityConnect.domain.member.entity.Member;
import com.jyhun.CommunityConnect.domain.board.repository.BoardRepository;
import com.jyhun.CommunityConnect.domain.member.repository.MemberRepository;
import com.jyhun.CommunityConnect.domain.board.dto.BoardRequestDTO;
import com.jyhun.CommunityConnect.domain.board.entity.Board;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<BoardResponseDTO> findBoards(){
        List<Board> boardList = boardRepository.findAll();
        List<BoardResponseDTO> boardResponseDTOList = new ArrayList<>();
        for (Board board : boardList) {
            BoardResponseDTO boardResponseDTO = BoardResponseDTO.toDTO(board);
            boardResponseDTOList.add(boardResponseDTO);
        }
        return boardResponseDTOList;
    }

    @Transactional(readOnly = true)
    public BoardResponseDTO findBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        BoardResponseDTO boardResponseDTO = BoardResponseDTO.toDTO(board);
        return boardResponseDTO;
    }

    @Transactional(readOnly = true)
    public Page<BoardResponseDTO> findBoardPage(BoardSearchDTO boardSearchDTO, Pageable pageable) {
        Page<Board> boardPage = boardRepository.findBoardPage(boardSearchDTO, pageable);
        return boardPage.map(board -> BoardResponseDTO.toDTO(board));
    }

    public BoardResponseDTO addBoard(BoardRequestDTO boardRequestDTO,String email) {
        Board board = boardRequestDTO.toEntity();
        Member member = memberRepository.findByEmail(email).orElse(null);
        board.changeMember(member);
        Board savedBoard = boardRepository.save(board);
        BoardResponseDTO boardResponseDTO = BoardResponseDTO.toDTO(savedBoard);
        return boardResponseDTO;
    }

    public BoardResponseDTO modifyBoard(BoardRequestDTO boardRequestDTO,Long boardId) {
        Board board = boardRequestDTO.toEntity();
        Board foundBoard = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        foundBoard.updateBoard(board);
        BoardResponseDTO boardResponseDTO = BoardResponseDTO.toDTO(foundBoard);
        return boardResponseDTO;
    }

    public void removeBoard(Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        boardRepository.delete(board);
    }

}
