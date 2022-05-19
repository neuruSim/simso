package com.simso.domain.ability.service;

import com.simso.domain.ability.repository.AbilityRepository;
import com.simso.domain.user.entity.Role;
import com.simso.domain.user.entity.User;
import com.simso.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //테스트 클래스가 사용
@Nested
class AbilityServiceTest {

    @Mock // mock 객체 , test runtime 때 주입
    private AbilityRepository abilityRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AbilityService abilityService;


    @DisplayName("생성한다 능력치를")
    @Test
    void createAbilities() {
        //given
        User user = User.builder()
                .username("testUser")
                .password("1234")
                .role(Role.USER)
                .build();
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        when(abilityRepository.save(any())).thenReturn(any());

        //when
        abilityService.register(user.getId());

        //then
        verify(abilityRepository, times(1)).save(any());
    }

}