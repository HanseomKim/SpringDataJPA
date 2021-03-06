package com.spring.data.datajpa.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter @Setter
// 기본 생성자 막고 싶은데, JPA 스팩상 PROTECTED로 열어두어야 함
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// @ToString은 가급적 내부 필드만 (연관관계 없는 필드만)
@ToString(of = {"id", "username", "age"})
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    public Member(String username) {
        this(username, 0);
    }

    public Member(String username, int age) {
        this(username, age, null);
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    // 양방향 연관관계 처리(연관관계 편의 메소드)
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}