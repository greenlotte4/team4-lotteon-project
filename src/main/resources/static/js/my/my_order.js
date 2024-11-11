'use strict'

// 기간별 조회 선택한 기간 하이라이트 처리
const myInfo_Search_Times = document.querySelectorAll('.myInfo_Search_Time');

// 숨겨뒀던 선택된 기간의 값 불러오기
const selected_period = document.querySelector('#selected_period').value;
const selected_month = document.querySelector('#selected_month').value;

// 숨겨놓은 값과 일치하면 해당 태그에 하이라이트용 클래스 명 추가
if (selected_period !== null) {
    switch (selected_period) {
        case "week":
            document.querySelector('#period_week').classList.add('Time_current');
            break;
        case "15days":
            document.querySelector('#period_15days').classList.add('Time_current');
            break;
        case "month":
            document.querySelector('#period_month').classList.add('Time_current');
            break;
    }
}

console.log("selected_month = " + selected_month);

// 숨겨놓은 개월 이름을 포함하는 태그에 하이라이트용 클래스 명 추가 (변환 과정에서 09월과 9월로 나뉘어서 일치하는 조건문은 불가)
if (selected_month != null) {
    const months = document.querySelectorAll('.months');
    for (const month of months) {
        if (month.textContent.trim().includes(selected_month)) {
            console.log("month.textContent = " + month.textContent)
            month.querySelector('a').classList.add('Time_current');
        }
    }
}

// 사용자 지정 기간 조회 버튼에 이벤트 처리(location href)
const customDate = document.querySelector('#customDate');
customDate.addEventListener('click', function (e) {
    e.preventDefault();
    const inputDates = e.target.previousElementSibling.querySelectorAll('input');
    const date1 = inputDates[0].value;
    const date2 = inputDates[1].value;
    if (date1 && date2) {
        location.href = `/lotteon/my/order?startDate=${encodeURIComponent(date1)}&endDate=${encodeURIComponent(date2)}`;
    } else {
        alert('조회할 기간을 선택해주세요.')
    }
});