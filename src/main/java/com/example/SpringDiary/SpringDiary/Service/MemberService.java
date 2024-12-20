package com.example.SpringDiary.SpringDiary.Service;

import com.example.SpringDiary.SpringDiary.Domain.Board;
import com.example.SpringDiary.SpringDiary.Domain.Member;
import com.example.SpringDiary.SpringDiary.Repository.BoardRepository;
import com.example.SpringDiary.SpringDiary.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public boolean joinProc(Member member) {
        int suc = memberRepository.isUser(member);
        if(suc!=0) return false;

        member.setRole("USER");
        member.setUserPass(bCryptPasswordEncoder.encode(member.getUserPass())); //비밀번호 해싱
        memberRepository.joinProc(member);
        return true;
    }

    public Page<Member> getAllUser(Pageable pageable) {
        int page = pageable.getPageNumber() * pageable.getPageSize();
        int size = pageable.getPageSize();

        Map<String, Object> input = new HashMap<>();
        input.put("page", page);
        input.put("size", size);
        List<Member> members = memberRepository.getAllUser(input);
        size = memberRepository.getUserCnt();
        return new PageImpl<>(members, pageable, size);
    }

    public void deleteUser(String userId) {
        boardRepository.removeLikeByUserId(userId);
        boardRepository.removeCommentByUserId(userId);

        List<Board> boardList = boardRepository.boardGetByUserId(userId);
        for (Board board : boardList) {
            boardRepository.removeLike(board.getBoardId());
            boardRepository.removeComment(board.getBoardId());
            boardRepository.removeBoard(board.getBoardId());
        }
        memberRepository.removeUser(userId);
    }

    public boolean adminJoinProc(Member member) {
        int suc = memberRepository.isUser(member);
        if(suc!=0) return false;

        member.setRole("ADMIN");
        member.setUserPass(bCryptPasswordEncoder.encode(member.getUserPass()));
        memberRepository.joinProc(member);
        return true;
    }
}
