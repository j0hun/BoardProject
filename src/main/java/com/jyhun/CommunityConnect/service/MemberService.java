package com.jyhun.CommunityConnect.service;

import com.jyhun.CommunityConnect.dto.MemberRequestDTO;
import com.jyhun.CommunityConnect.entity.Member;
import com.jyhun.CommunityConnect.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void addMember(MemberRequestDTO memberRequestDTO) {
        Member findMember = memberRepository.findByEmail(memberRequestDTO.getEmail());
        if (findMember != null) {
            throw new IllegalArgumentException("이미 가입된 회원입니다.");
        }
        memberRequestDTO.setPassword(passwordEncoder.encode(memberRequestDTO.getPassword()));
        Member member = memberRequestDTO.toEntity();
        memberRepository.save(member);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByEmail(email);

        if (findMember == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(findMember.getEmail())
                .password(findMember.getPassword())
                .roles(findMember.getRole().toString())
                .build();
    }

}
