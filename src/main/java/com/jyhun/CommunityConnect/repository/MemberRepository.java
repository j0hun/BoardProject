package com.jyhun.CommunityConnect.repository;


import com.jyhun.CommunityConnect.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByEmail(String email);

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
