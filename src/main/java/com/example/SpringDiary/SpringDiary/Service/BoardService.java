package com.example.SpringDiary.SpringDiary.Service;

import com.example.SpringDiary.SpringDiary.Domain.Board;
import com.example.SpringDiary.SpringDiary.Domain.Comment;
import com.example.SpringDiary.SpringDiary.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
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

    public List<Board> getMainBoard() {
        return boardRepository.getMainBoard();
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

    public List<Board> likeByGetBoard(String id) {
        return boardRepository.likeByGetBoard(id);
    }

    public List<Board> commentByGetBoard(String id) {
        return boardRepository.commentByGetBoard(id);
    }

    public List<Board> boardIdByGetBoard(String id) {
        return boardRepository.boardIdByGetBoard(id);
    }

    public List<Board> searchBoard(String input) {
        return boardRepository.searchBoard(input);
    }
}
