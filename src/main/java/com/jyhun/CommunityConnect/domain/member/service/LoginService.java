package com.jyhun.CommunityConnect.domain.member.service;

import com.jyhun.CommunityConnect.domain.member.dto.RegisterDTO;
import com.jyhun.CommunityConnect.domain.member.entity.Member;
import com.jyhun.CommunityConnect.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterDTO registerDTO) {
        // 이메일로 이미 등록된 회원이 있는지 확인
        if (memberRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        registerDTO.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Member member = registerDTO.toEntity();

        // 회원 정보 저장
        memberRepository.save(member);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username).orElse(null);

        if (member == null) {
            throw new UsernameNotFoundException("회원을 찾을 수 없습니다.");
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();

    }
}
