package com.example.SpringDiary.SpringDiary.Service;

import com.example.SpringDiary.SpringDiary.Domain.Board;
import com.example.SpringDiary.SpringDiary.Domain.Member;
import com.example.SpringDiary.SpringDiary.Repository.BoardRepository;
import com.example.SpringDiary.SpringDiary.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Member> getAllUser() {
        return memberRepository.getAllUser();
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
