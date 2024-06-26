$(document).ready(function () {
    // 사용자 정보를 가져오는 API 호출
    $.ajax({
        url: '/api/user',
        type: 'GET',
        headers: {
            "Authorization": "Bearer " + sessionStorage.getItem('token') // 로컬 스토리지에서 토큰을 가져와 헤더에 추가
        },
        success: function (response) {
            console.log(response);
            if (response) {
                // 사용자가 인증된 경우
                $('#logoutLink').show();
                $('#loginLink').hide();
            }
        },
        error: function (xhr, status, error) {
            if (xhr.status === 401) {
                console.log('User is not authenticated');
                // 401 에러 발생 시 로그인 링크 표시
                $('#loginLink').show();
                $('#logoutLink').hide();
            } else {
                console.log('Error fetching user details:', error);
                // 다른 에러 발생 시 로그인 링크 표시
                $('#loginLink').show();
                $('#logoutLink').hide();
            }
        }
    });

    // 로그아웃 버튼 클릭 이벤트 처리
    $('#logoutLink').click(function (e) {
        e.preventDefault();
        $.ajax({
            url: '/logout',
            type: 'GET',
            success: function () {
                sessionStorage.removeItem('token'); // 로컬 스토리지에서 토큰 제거
                window.location.href = '/login'; // 로그인 페이지로 리디렉션
            },
            error: function (xhr, status, error) {
                console.log('Error logging out:', error);
            }
        });
    });

});