package com.simso.domain.roadmap.dto;

import com.simso.domain.roadmap.entity.Roadmap;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoadmapSaveRequestDto {
    private String title;
    private String content;
    private Long userId;

    @Builder
    public RoadmapSaveRequestDto(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public Roadmap toEntity() {
        return Roadmap.builder()
                .title(title)
                .content(content)
                .build();
    }
}
