package com.example.SpringDiary.SpringDiary.Service;

import com.example.SpringDiary.SpringDiary.Domain.Board;
import com.example.SpringDiary.SpringDiary.Domain.Comment;
import com.example.SpringDiary.SpringDiary.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;


    public void write(Board board) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        board.setUserId(id);
        boardRepository.write(board);
    }



    public Board findById(int boardId) {
        boardRepository.updatePass(boardId);
        return boardRepository.findById(boardId);
    }

    public boolean like(int boardId, String userId) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        if(id.equals(userId)) return false;
        Map<String, Object> input = new HashMap<>();
        input.put("boardId",boardId);
        input.put("userId",id);
        int cnt = boardRepository.like(input);
        if(cnt == 0) {
            boardRepository.updateLike(boardId);
            boardRepository.insertLike(input);
        }
        return cnt == 0;
    }

    public void insertComment(Comment comment) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        comment.setUserId(id);
        boardRepository.insertComment(comment);
    }

    public List<Comment> getComment(int boardId) {
        return boardRepository.getComment(boardId);
    }

    public void updateBoard(Board board) {
        boardRepository.updateBoard(board);
    }

    public void removeBoard(Long boardId) {
        boardRepository.removeLike(boardId);
        boardRepository.removeComment(boardId);
        boardRepository.removeBoard(boardId);
    }

    public Page<Board> likeByGetBoard(String userId, Pageable pageable) {
        int page = pageable.getPageNumber() * pageable.getPageSize();
        int size = pageable.getPageSize();
        Map<String, Object> input = new HashMap<>();
        input.put("userId",userId);
        input.put("page",page);
        input.put("size",size);
        List<Board> boards = boardRepository.likeByGetBoard(input);
        size = boardRepository.boardGetByLikeCnt(userId);
        return new PageImpl<>(boards,pageable,size);
    }

    public  Page<Board> commentByGetBoard(Pageable pageable,String userId){
        int page = pageable.getPageNumber() * pageable.getPageSize();
        int size = pageable.getPageSize();
        Map<String, Object> input = new HashMap<>();
        input.put("userId",userId);
        input.put("page",page);
        input.put("size",size);
        List<Board> boards = boardRepository.commentByGetBoard(input);
        size = boardRepository.boardGetByCommentCnt(userId);
        return new PageImpl<>(boards,pageable,size);
    }

    public Page<Board> boardIdByGetBoard(Pageable pageable, String userId) {
        int page = pageable.getPageNumber() * pageable.getPageSize();
        int size = pageable.getPageSize();
        Map<String, Object> input = new HashMap<>();
        input.put("userId",userId);
        input.put("page",page);
        input.put("size",size);
        List<Board> boards = boardRepository.boardIdByGetBoard(input);
        size = boardRepository.userWriteBoardCnt(userId);
        return new PageImpl<>(boards, pageable, size);
    }

    public List<Board> searchBoard(String input) {
        return boardRepository.searchBoard(input);
    }

    public Page<Board> getMainBoard(Pageable pageable) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int pageSize = pageable.getPageSize();
        Map<String, Object> input = new HashMap<>();
        input.put("page",offset);
        input.put("size",pageSize);
        List<Board> boards = boardRepository.getMainBoard(input);
        int size = boardRepository.getBoardSize();

        // Page 객체로 변환하여 반환
        return new PageImpl<>(boards, pageable, size);
    }

}
