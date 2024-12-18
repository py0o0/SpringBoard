package com.example.SpringDiary.SpringDiary.Controller;

import com.example.SpringDiary.SpringDiary.Domain.Board;
import com.example.SpringDiary.SpringDiary.Domain.Comment;
import com.example.SpringDiary.SpringDiary.Service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("id", id);
        model.addAttribute("principal", userDetails);

        List<Board>boardList = boardService.getMainBoard();
        model.addAttribute("boardList", boardList);

        return "home";
    }

    @GetMapping("/writePage")
    public String writePage(){
        return "writePage";
    }

    @PostMapping("/write")
    public String write(Board board){
        boardService.write(board);
        return "redirect:/";
    }

    @GetMapping("/board/{boardId}")
    public String board(@PathVariable int boardId, Model model){
        Board board = boardService.findById(boardId);
        model.addAttribute("board", board);

        List<Comment> commentList = boardService.getComment(boardId);
        model.addAttribute("commentList", commentList);

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("id",id);
        return "board";
    }

    @GetMapping("like")
    public ResponseEntity<Boolean> like(int boardId, String userId){
        boolean suc = boardService.like(boardId,userId);
        return ResponseEntity.ok(suc);
    }

    @GetMapping("commentWrite/{boardId}")
    public String commentWrite(){
        return "commentWrite";
    }

    @PostMapping("commentWrite/{boardId}")
    public String commentWrite(@PathVariable int boardId, Comment comment){
        comment.setBoardId(boardId);
        boardService.insertComment(comment);
        return "redirect:/board/" + boardId;
    }

    @GetMapping("/board/modify/{boardId}")
    public String boardModify(@PathVariable Long boardId, Model model){
        model.addAttribute("boardId", boardId);
        return "boardModify";
    }
    @PostMapping("/modify/{boardId}")
    public String boardModify(@PathVariable Long boardId, Board board){
        board.setBoardId(boardId);
        boardService.updateBoard(board);
        return "redirect:/board/" + boardId;
    }

    @GetMapping("/board/remove/{boardId}")
    public String boardRemove(@PathVariable Long boardId){
        boardService.removeBoard(boardId);
        return "redirect:/";
    }

    @GetMapping("/my/like")
    public String myLike(Model model){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Board>boardList = boardService.likeByGetBoard(id);
        model.addAttribute("boardList", boardList);
        return "myBoard";
    }

    @GetMapping("/my/comment")
    public String myComment(Model model){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Board>boardList = boardService.commentByGetBoard(id);
        model.addAttribute("boardList", boardList);
        return "myBoard";
    }

    @GetMapping("/my/board")
    public String myBoard(Model model){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Board>boardList = boardService.boardIdByGetBoard(id);
        model.addAttribute("boardList", boardList);
        return "myBoard";
    }

    @PostMapping("/search")
    public String searchBoard(String input,Model model){
        List<Board>boardList = boardService.searchBoard(input);
        model.addAttribute("boardList", boardList);
        return "myBoard";
    }
}
