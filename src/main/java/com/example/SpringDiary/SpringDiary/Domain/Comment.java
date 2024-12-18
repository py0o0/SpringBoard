package com.example.SpringDiary.SpringDiary.Domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Comment {
    private int commentId;
    private String userId;
    private int boardId;
    private String commentContents;
}