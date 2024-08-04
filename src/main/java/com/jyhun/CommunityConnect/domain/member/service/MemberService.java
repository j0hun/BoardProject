package com.jyhun.CommunityConnect.domain.member.service;

import com.jyhun.CommunityConnect.domain.member.dto.MemberResponseDTO;
import com.jyhun.CommunityConnect.domain.member.entity.Member;
import com.jyhun.CommunityConnect.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberResponseDTO findMemberByEmail(String email){
        Member member = memberRepository.findByEmail(email).orElse(null);
        return MemberResponseDTO.toDTO(member);
    }

}
