package com.example.SpringDiary.SpringDiary.Repository;

import com.example.SpringDiary.SpringDiary.Domain.Board;
import com.example.SpringDiary.SpringDiary.Domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardRepository {

    void write(Board board);

    List<Board> getMainBoard();

    Board findById(int boardId);

    void updatePass(int boardId);

    int like(Map<String, Object> input);

    void updateLike(int boardId);

    void insertLike(Map<String, Object> input);

    void insertComment(Comment comment);

    List<Comment> getComment(int boardId);

    void updateBoard(Board board);

    void removeBoard(Long boardId);

    void removeLike(Long boardId);

    void removeComment(Long boardId);

    List<Board> likeByGetBoard(String id);

    List<Board> commentByGetBoard(String id);

    List<Board> boardIdByGetBoard(String id);

    List<Board> searchBoard(String input);
}
