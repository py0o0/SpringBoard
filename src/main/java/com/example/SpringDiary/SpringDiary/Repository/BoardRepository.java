package com.example.SpringDiary.SpringDiary.Repository;

import com.example.SpringDiary.SpringDiary.Domain.Board;
import com.example.SpringDiary.SpringDiary.Domain.BoardFile;
import com.example.SpringDiary.SpringDiary.Domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardRepository {

    void write(Board board);

    Board findById(long boardId);

    void updatePass(long boardId);

    int like(Map<String, Object> input);

    void updateLike(long boardId);

    void insertLike(Map<String, Object> input);

    void insertComment(Comment comment);

    List<Comment> getComment(long boardId);

    void updateBoard(Board board);

    void removeBoard(Long boardId);

    void removeLike(Long boardId);

    void removeComment(Long boardId);

    List<Board> likeByGetBoard(Map<String, Object> input);

    List<Board> commentByGetBoard(Map<String, Object> input);

    List<Board> searchBoard(String input);

    void removeLikeByUserId(String userId);

    void removeCommentByUserId(String userId);

    List<Board> getMainBoard(Map<String, Object> input);

    int getBoardSize();

    List<Board> boardIdByGetBoard(Map<String, Object> input);

    int userWriteBoardCnt(String id);

    List<Board> boardGetByUserId(String userId);

    int boardGetByCommentCnt(String userId);

    int boardGetByLikeCnt(String userId);

    void saveFile(BoardFile boardFile);

    BoardFile findFile(long boardId);
}
