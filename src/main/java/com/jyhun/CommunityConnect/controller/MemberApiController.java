package com.jyhun.CommunityConnect.controller;

import com.jyhun.CommunityConnect.dto.MemberResponseDTO;
import com.jyhun.CommunityConnect.dto.ResultDTO;
import com.jyhun.CommunityConnect.entity.Member;
import com.jyhun.CommunityConnect.service.MemberSearchService;
import com.jyhun.CommunityConnect.utility.ExecutionTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberApiController {

    private final MemberSearchService memberSearchService;

    /**
     * V1. 엔티티 직접 조회
     * - 엔티티가 변하면 API 스펙이 변한다.
     * - 양방향 무한루프 참조로 인해 StackOverflowError
     */
    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return ExecutionTimeUtils.measureExecutionTime(()->memberSearchService.findMembersV1(),"memberV1");
    }

    /**
     * V2. 엔티티 대신 DTO 조회
     * fetch join 사용 X
     */
    @GetMapping("/api/v2/members")
    public List<MemberResponseDTO> membersV2() {
        return ExecutionTimeUtils.measureExecutionTime(() -> memberSearchService.findMembersV2(),"memberV2");
    }

    /**
     * V3. 엔티티 대신 DTO 조회
     * fetch join 사용 O
     * 페이징 X
     */
    @GetMapping("/api/v3/members")
    public List<MemberResponseDTO> membersV3() {
        return ExecutionTimeUtils.measureExecutionTime(() -> memberSearchService.findMembersV3(),"memberV3");
    }

    /**
     * V4. fetch join 사용하면서 페이징
     * ToOne 관계만 우선 모두 페치 조인으로 최적화
     * 컬렉션 관계는 hibernate.default_batch_fetch_size로 최적화
     */
    @GetMapping("/api/v4/members")
    public Page<MemberResponseDTO> memberV4(Pageable pageable
    ) {
        return ExecutionTimeUtils.measureExecutionTime(() -> memberSearchService.findMembersV4(pageable),"memberV4");
    }

    /**
     * V5. ResultDTO 클래스로 컬렉션을 감싸서 향후 필요한 필드를 추가할 수 있게 함.
     * 여기서는 count를 넣음.
     */
    @GetMapping("/api/v5/members")
    public ResultDTO memberV5(){
        return ExecutionTimeUtils.measureExecutionTime(() -> memberSearchService.findMembersV5(),"memberV5");
    }

}
