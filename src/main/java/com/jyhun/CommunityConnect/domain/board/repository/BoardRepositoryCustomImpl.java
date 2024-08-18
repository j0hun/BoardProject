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

import java.util.List;

import static com.jyhun.CommunityConnect.domain.board.entity.QBoard.board;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression viewCountGreaterThan(Long viewCount){
        return viewCount != null ? board.viewCount.gt(viewCount) : null;
    }

    private BooleanExpression searchKeywords(String searchKey, String searchValue) {
        if(StringUtils.equals("title",searchKey)) {
            return board.title.like("%" + searchValue + "%");
        }else if (StringUtils.equals("author", searchKey)) {
            return board.member.name.like("%" + searchValue + "%");
        }
        return null;
    }

    @Override
    public Page<Board> findBoardPage(BoardSearchDTO boardSearchDTO, Pageable pageable) {
        List<Board> results = queryFactory
                .selectFrom(board)
                .where(searchKeywords(boardSearchDTO.getSearchKey(), boardSearchDTO.getSearchValue()))
                .orderBy(board.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(board.count())
                .from(board)
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
