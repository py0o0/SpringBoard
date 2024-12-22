package com.example.SpringDiary.SpringDiary.Domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardFile {
    private Long id;
    private Long boardId;
    private String originalFileName;
    private String storedFileName;
}
