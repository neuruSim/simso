package com.simso.service;

import com.simso.domain.roadmap.dto.RoadmapsResponseDto;
import com.simso.domain.user.entity.Role;
import com.simso.domain.user.entity.User;
import com.simso.domain.roadmap.entity.Roadmap;
import com.simso.domain.roadmap.exception.RoadmapNotFoundException;
import com.simso.domain.roadmap.service.RoadmapService;
import com.simso.domain.user.dto.UserResponseDto;
import com.simso.domain.roadmap.dto.RoadmapResponseDto;
import com.simso.domain.roadmap.dto.RoadmapSaveRequestDto;
import com.simso.domain.roadmap.dto.RoadmapUpdateRequestDto;
import com.simso.domain.roadmap.repository.RoadmapRepository;
import com.simso.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class RoadmapServiceTest {

    @Autowired
    RoadmapService roadmapService;
    @Autowired
    RoadmapRepository roadmapRepository;
    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void cleanup() {
        roadmapRepository.deleteAll();
    }

    @DisplayName("등록한다 로드맵 기본필드를")
    @Test
    public void register() {

        //given
        User user = createUser();
        Optional<User> findUser = userRepository.findById(user.getId());
        UserResponseDto responseDto = new UserResponseDto(findUser.orElse(null));
        RoadmapSaveRequestDto requestDto = createRoadmapRequestDto(responseDto);

        //when
        Long register = roadmapService.register(requestDto);

        //then
        Optional<Roadmap> findByRoadmap = roadmapRepository.findById(register);
        assertThat(register).isEqualTo(findByRoadmap.orElse(null).getId());

    }

    @DisplayName("조회한다 등록된 로드맵을 ")
    @Test
    public void findRoadmapList() {
        //given
        User user = createUser();
        Optional<User> findUser = userRepository.findById(user.getId());
        UserResponseDto responseDto = new UserResponseDto(findUser.orElse(null));

        RoadmapSaveRequestDto requestDto = createRoadmapRequestDto(responseDto);
        RoadmapSaveRequestDto requestDto2 = createRoadmapRequestDto2(responseDto);
        roadmapService.register(requestDto);
        roadmapService.register(requestDto2);

        //when
        List<String> collect =  roadmapService.findAllDesc()
                .getRoadmapResponseList()
                .stream()
                .map(RoadmapResponseDto::getTitle)
                .collect(Collectors.toList());

        //then
        assertThat(collect).containsExactly(requestDto2.getTitle(), requestDto.getTitle());
        assertThat(collect).contains(
                requestDto.getTitle(),
                requestDto2.getTitle()
        );


    }


    @DisplayName("수정한다 로드맵을")
    @Test
    public void update() {
        //given
        Long roadmapEntity = createRoadmapEntity();
        RoadmapUpdateRequestDto requestDto = new RoadmapUpdateRequestDto("업데이타이틀", "업데이트 컨텐트");

        //when
        Long updateId = roadmapService.update(roadmapEntity, requestDto);

        //then
        Optional<Roadmap> findId = roadmapRepository.findById(roadmapEntity);
        Optional<Roadmap> findUpdateId = roadmapRepository.findById(updateId);

        assertThat(findId.orElse(null).getContent()).
                isEqualTo(findUpdateId.orElse(null).getContent());

        assertThat(findId.orElse(null).getTitle()).
                isEqualTo(findUpdateId.orElse(null).getTitle());

    }

    @DisplayName("수정상황에서 RoadmapNotFoundException 예외 발생")
    @Test
    public void updateIllegalArgumentException() {
        //given
        Long roadmapEntity = createRoadmapEntity();
        RoadmapUpdateRequestDto requestDto = new RoadmapUpdateRequestDto("업데이타이틀", "업데이트 컨텐트");

        //when
        //then
        assertThatThrownBy(() -> roadmapService.update(1000L, requestDto))
                .isInstanceOf(RoadmapNotFoundException.class);
    }

    @DisplayName("삭제한다 로드맵을")
    @Test
    public void remove() {
        //given
        Long roadmapEntity = createRoadmapEntity();

        //when
        roadmapService.remove(roadmapEntity);

        //then
        Optional<Roadmap> findId = roadmapRepository.findById(roadmapEntity);

        assertThat(findId.orElse(null)).isNull();

    }

    @DisplayName("조회한다 단건 로드맵을")
    @Test
    public void findBySingleRoadMap() {
        //given
        Long roadmapEntity = createRoadmapEntity();

        //when
        RoadmapResponseDto singleRoadmap = roadmapService.findById(roadmapEntity);

        //then
        assertThat(singleRoadmap).isNotNull();
        assertThat(singleRoadmap.getId()).isEqualTo(roadmapEntity);

    }

    @DisplayName("조회에서 예외 RoadmapNotFoundException 발생")
    @Test
    public void findSingle_RoadmapNotFoundException() {
        //given
        Long roadmapEntity = createRoadmapEntity();
        roadmapService.remove(roadmapEntity);

        //when
        //then
        assertThatThrownBy(() -> roadmapService.findById(roadmapEntity))
                .isInstanceOf(RoadmapNotFoundException.class);
    }

    @DisplayName("조회에서 예외 RoadmapNotFoundException 발생하지 않는다")
    @Test
    public void findSingleNot_RoadmapNotFoundException() {
        //given
        Long roadmapEntity = createRoadmapEntity();

        //when
        //then
        assertThatNoException().isThrownBy(
                () -> roadmapService.findById(roadmapEntity)
        );

    }


    // 로드맵 등록하고 저장하는 부분
    private Long createRoadmapEntity() {
        User user = createUser();
        Optional<User> findUser = userRepository.findById(user.getId());
        UserResponseDto responseDto = new UserResponseDto(findUser.orElse(null));

        RoadmapSaveRequestDto requestDto = createRoadmapRequestDto(responseDto);
        return roadmapService.register(requestDto);
    }

    // 로드맵 create save Request dto
    private RoadmapSaveRequestDto createRoadmapRequestDto(UserResponseDto responseDto) {
        return RoadmapSaveRequestDto.builder()
                .title("테스트 타이틀")
                .content("테스트 본문")
                .userId(responseDto.getId())
                .build();
    }

    // 로드맵 create save Request dto
    private RoadmapSaveRequestDto createRoadmapRequestDto2(UserResponseDto responseDto) {
        return RoadmapSaveRequestDto.builder()
                .title("테스트 타이틀2")
                .content("테스트 본문2")
                .userId(responseDto.getId())
                .build();
    }

    // user create save Request dto
    private User createUser() {
        return userRepository.save(User.builder()
                .username("빌더테스트")
                .password("1234")
                .role(Role.USER)
                .build());
    }

}