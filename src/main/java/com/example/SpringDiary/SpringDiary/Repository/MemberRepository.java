package com.example.SpringDiary.SpringDiary.Repository;
import com.example.SpringDiary.SpringDiary.Domain.Member;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MemberRepository {

    void joinProc(Member member);

    int isUser(Member member);

    Member findById(String username);
}
