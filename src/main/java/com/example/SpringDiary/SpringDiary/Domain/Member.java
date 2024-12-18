package com.example.SpringDiary.SpringDiary.Domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Member {
    private String userId;
    private String userPass;
    private String role;
}
