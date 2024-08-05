package com.jyhun.CommunityConnect.domain.board.service.view;

import com.jyhun.CommunityConnect.domain.board.entity.Board;
import com.jyhun.CommunityConnect.domain.board.repository.BoardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(properties = "classpath:application.test.yml")
@SpringBootTest
@Transactional
class SynchronizedBoardViewServiceTest {

    @Autowired
    private SynchronizedBoardViewService synchronizedBoardViewService;

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
    void view() {
        synchronizedBoardViewService.view(1L);
        Board board = boardRepository.findById(1L).orElseThrow();
        assertThat(board.getViewCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("동시에100개요청")
    void requests_100_AtTheSameTime() throws InterruptedException {
        // 멀티스레드 이용 ExecutorService : 비동기를 단순하게 처리할 수 있도록 해주는 java api
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        // 다른 스레드에서 수행이 완료될때 까지 대기할 수 있도록 도와주는 API - 요청이 끝날때 까지 기다림
        CountDownLatch latch = new CountDownLatch(100);

        for(int i=0;i<100;i++) {
            executorService.submit(() -> {
                try {
                    synchronizedBoardViewService.view(1L);
                }finally {
                    latch.countDown();
                }
            });
        }
        latch.await(); // 다른 쓰레드에서 수행중인 작업이 완료될때까지 기다려줌

        Board board = boardRepository.findById(1L).orElseThrow();
        assertThat(board.getViewCount()).isEqualTo(100);
    }

}