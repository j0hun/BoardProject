package com.jyhun.CommunityConnect.domain.board.repository;

import com.jyhun.CommunityConnect.domain.board.dto.BoardSearchDTO;
import com.jyhun.CommunityConnect.domain.board.entity.Board;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.jyhun.CommunityConnect.domain.board.entity.QBoard.board;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all",searchDateType) || searchDateType == null) {
            return null;
        }else if (StringUtils.equals("1d",searchDateType)) {
            dateTime = dateTime.minusDays(1);
        }else if (StringUtils.equals("1w",searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        }else if (StringUtils.equals("1m",searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        }else if(StringUtils.equals("6m",searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }
        return board.createdAt.after(dateTime);
    }

    private BooleanExpression viewCountGreaterThan(Long viewCount){
        return viewCount != null ? board.viewCount.gt(viewCount) : null;
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("boardTitle",searchBy)) {
            return board.title.like("%" + searchQuery + "%");
        }else if (StringUtils.equals("createdBy", searchBy)) {
            return board.member.name.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public Page<Board> findBoardPage(BoardSearchDTO boardSearchDTO, Pageable pageable) {
        List<Board> results = queryFactory
                .selectFrom(board)
                .where(regDtsAfter(boardSearchDTO.getSearchDateType()),
                        searchByLike(boardSearchDTO.getSearchBy(), boardSearchDTO.getSearchQuery()))
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(board.count())
                .from(board)
                .where(regDtsAfter(boardSearchDTO.getSearchDateType()),
                        searchByLike(boardSearchDTO.getSearchBy(), boardSearchDTO.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(results,pageable,total);
    }

    @Override
    public Page<Board> findBoardPopular(Pageable pageable) {
        List<Board> results = queryFactory
                .selectFrom(board)
                .where(viewCountGreaterThan(100L))
                .orderBy(board.createdAt.desc())
                .fetch();

        Long total = queryFactory
                .select(board.count())
                .from(board)
                .where(viewCountGreaterThan(100L))
                .orderBy(board.createdAt.desc())
                .fetchOne();

        return new PageImpl<>(results, pageable,total);
    }
}
