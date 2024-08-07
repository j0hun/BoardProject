package com.jyhun.CommunityConnect.domain.board.service.view;

import com.jyhun.CommunityConnect.domain.board.entity.Board;
import com.jyhun.CommunityConnect.domain.board.repository.BoardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class RedisBoardViewServiceTest {

    @Autowired
    private RedisBoardViewService viewService;

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
        viewService.view(1L);
        Thread.sleep(6000); // DB에 60초후 저장되기때문에 60초 멈춤
        Board board = boardRepository.findById(1L).orElseThrow();
        assertThat(board.getViewCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Redis 동시에 100개 요청")
    void Redis_request_100_AtTheSameTime() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(100);
        for(int i=0;i<100;i++) {
            executorService.submit(() -> {
                try{
                    viewService.view(1L);
                }finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        Thread.sleep(6000);
        Board board = boardRepository.findById(1L).orElseThrow();
        assertThat(board.getViewCount()).isEqualTo(100);

    }

}