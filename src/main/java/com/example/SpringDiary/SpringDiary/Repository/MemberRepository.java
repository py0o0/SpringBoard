package com.example.SpringDiary.SpringDiary.Repository;
import com.example.SpringDiary.SpringDiary.Domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MemberRepository {

    List<Member> getAllUser();

    void joinProc(Member member);

    int isUser(Member member);

    Member findById(String username);

    void removeUser(String userId);
}
