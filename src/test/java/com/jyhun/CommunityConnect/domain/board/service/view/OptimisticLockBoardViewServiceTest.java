package com.jyhun.CommunityConnect.domain.board.service.view;

import com.jyhun.CommunityConnect.domain.board.entity.Board;
import com.jyhun.CommunityConnect.domain.board.repository.BoardRepository;
import com.jyhun.CommunityConnect.domain.board.service.view.archive.OptimisticLockBoardViewService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class OptimisticLockBoardViewServiceTest {

    @Autowired
    private OptimisticLockBoardViewService viewService;

    @Autowired
    private BoardRepository boardRepository;

    @BeforeEach
    public void before() {
        Board board = new Board("title", "content");
        boardRepository.saveAndFlush(board);
    }

    @AfterEach
    public void after() {
        boardRepository.deleteAll();
    }

    @Test
    void view() throws InterruptedException {
        viewService.updateViewWithRetry(1L);
        Board board = boardRepository.findById(1L).orElseThrow();
        assertThat(board.getViewCount()).isEqualTo(1);
    }

    @Test
    void OptimisticLock_동시에_100개_요청() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    viewService.updateViewWithRetry(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        Board board = boardRepository.findById(1L).orElseThrow();
        assertThat(board.getViewCount()).isNotEqualTo(100);
    }
}
