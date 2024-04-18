package com.jyhun.CommunityConnect.repository;


import com.jyhun.CommunityConnect.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByEmail(String email);

    Member findByName(String name);

}
