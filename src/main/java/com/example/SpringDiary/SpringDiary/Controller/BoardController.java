package com.example.SpringDiary.SpringDiary.Controller;

import com.example.SpringDiary.SpringDiary.Domain.Board;
import com.example.SpringDiary.SpringDiary.Domain.Comment;
import com.example.SpringDiary.SpringDiary.Service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public String home(Model model, @AuthenticationPrincipal UserDetails userDetails,
                       @PageableDefault(size = 5) Pageable pageable){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("id", id);
        model.addAttribute("principal", userDetails);


        Page<Board> boardPage = boardService.getMainBoard(pageable);
        model.addAttribute("boardPage", boardPage);

        boolean isAdmin = userDetails != null && userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);

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
    public String board(@PathVariable int boardId, Model model,
    @AuthenticationPrincipal UserDetails userDetails){
        Board board = boardService.findById(boardId);
        model.addAttribute("board", board);

        List<Comment> commentList = boardService.getComment(boardId);
        model.addAttribute("commentList", commentList);

        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("id",id);

        boolean isAdmin = userDetails != null && userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);
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
    public String myBoard(Model model,@PageableDefault(size = 5) Pageable pageable){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<Board>boardPage = boardService.boardIdByGetBoard(pageable,id);
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("board",true);
        return "myBoard";
    }

    @PostMapping("/search")
    public String searchBoard(String input,Model model){
        List<Board>boardList = boardService.searchBoard(input);
        model.addAttribute("boardList", boardList);
        return "myBoard";
    }
    @GetMapping("/userBoard/{userId}")
    public String myBoard(Model model, @PathVariable String userId, @PageableDefault(size = 5) Pageable pageable){
        Page<Board>boardPage = boardService.boardIdByGetBoard(pageable,userId);
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("admin",true);
        model.addAttribute("userId", userId);
        return "myBoard";
    }
}
