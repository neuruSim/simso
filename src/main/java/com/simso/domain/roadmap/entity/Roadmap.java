package com.simso.domain.roadmap.entity;

import com.simso.domain.user.entity.User;
import com.simso.domain.roadmap.dto.RoadmapSaveRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Roadmap {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_id")
    private Long id;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "content")
    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Roadmap(Long id, String title, String content, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    private void setUser(User user) {
        this.user = user;
        user.getRoadmapList().add(this);
    }

    /*
     *  생성 로드맵
     */
    public static Roadmap createRoadmap(User user, RoadmapSaveRequestDto requestDto) {
        Roadmap roadmap = requestDto.toEntity();
        roadmap.setUser(user);
        return  roadmap;
    }

    /*
    *  업데이트 로드맵
    */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
