package com.jyhun.CommunityConnect.service;

import com.jyhun.CommunityConnect.dto.MemberResponseDTO;
import com.jyhun.CommunityConnect.dto.ResultDTO;
import com.jyhun.CommunityConnect.entity.Member;
import com.jyhun.CommunityConnect.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberSearchService {

    private final MemberRepository memberRepository;

    public List<Member> findMembersV1() {
        return memberRepository.findAll();
    }

    public List<MemberResponseDTO> findMembersV2() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(member -> MemberResponseDTO.toDTO(member)).collect(Collectors.toList());
    }

    public List<MemberResponseDTO> findMembersV3() {
        List<Member> members = memberRepository.findFetchJoinByAll();
        return members.stream().map(member -> MemberResponseDTO.toDTO(member)).collect(Collectors.toList());
    }

    public Page<MemberResponseDTO> findMembersV4(Pageable pageable) {
        Page<Member> members = memberRepository.findFetchByToOne(pageable);
        return members.map(member -> MemberResponseDTO.toDTO(member));
    }

    public ResultDTO findMembersV5() {
        List<Member> members = memberRepository.findFetchJoinByAll();
        List<MemberResponseDTO> collect = members.stream().map(member -> MemberResponseDTO.toDTO(member)).collect(Collectors.toList());
        long count = memberRepository.count();
        return new ResultDTO<>(count,collect);
    }
}
