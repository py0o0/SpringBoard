package com.example.SpringDiary.SpringDiary.Domain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Board {
    private Long boardId;
    private String boardTitle;
    private String userId;
    private String boardContents;
    private Long boardPass;
    private Long boardLike;
    private String boardCreated;
}