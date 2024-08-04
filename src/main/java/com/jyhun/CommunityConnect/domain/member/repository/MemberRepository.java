package com.jyhun.CommunityConnect.domain.member.repository;

import com.jyhun.CommunityConnect.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);

    @Query(
            "select m from Member m " +
                    "join fetch m.boardList b "

    )
    List<Member> findFetchJoinByAll();

    @Query(
            "select m from Member m "
    )
    Page<Member> findFetchByToOne(Pageable pageable);

}