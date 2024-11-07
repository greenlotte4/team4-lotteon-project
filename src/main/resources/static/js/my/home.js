'use strict'

const orderLink = document.querySelector('.orderDetailBtn');
const orderReviewBtns = document.getElementsByClassName('orderReviewBtn');
const orderInquireBtns = document.getElementsByClassName('orderInquireBtn');
const orderSellerBtns = document.getElementsByClassName('orderSellerBtn');
const orderAcceptBtns = document.getElementsByClassName('orderAcceptBtn');
const orderReturnBtns = document.getElementsByClassName('orderReturnBtn');
const orderChangeBtns = document.getElementsByClassName('orderChangeBtn');
const closeBtns = document.querySelectorAll('.closeBtn');
const cancelBtns = document.querySelectorAll('.cancelBtn');

orderLink.addEventListener('click', function (event) {
    event.preventDefault(); // a 태그의 기본 동작(링크 이동)을
    const modal = document.getElementById('productModal');
    if (modal) {
        modal.classList.remove('Modalhidden'); // Modalhidden 클래스 제거
    }
})


for (let i = 0; i < orderReviewBtns.length; i++) {
    orderReviewBtns[i].addEventListener('click', function (event) {
        event.preventDefault(); // a 태그의 기본 동작(링크 이동)을 막음
        const modal = document.getElementById('ReviewModal');
        if (modal) {
            modal.classList.remove('Modalhidden'); // Modalhidden 클래스 제거
        }
    });
}


for (let i = 0; i < orderInquireBtns.length; i++) {
    orderInquireBtns[i].addEventListener('click', function (event) {
        event.preventDefault(); // a 태그의 기본 동작(링크 이동)을 막음
        const modal = document.getElementById('inquireModal');
        if (modal) {
            modal.classList.remove('Modalhidden'); // Modalhidden 클래스 제거
        }
    });
}

// 판매자 상세 정보에 데이터 주입을 위해 해당 태그 호출
const sellerGrade = document.querySelector('#sellerGrade');
const sellerComName = document.querySelector('#sellerComName');
const sellerCeo = document.querySelector('#sellerCeo');
const sellerHp = document.querySelector('#sellerHp');
const sellerFax = document.querySelector('#sellerFax');
const sellerEmail = document.querySelector('#sellerEmail');
const sellerComNumber = document.querySelector('#sellerComNumber');
const sellerAddress = document.querySelector('#sellerAddress');

for (let i = 0; i < orderSellerBtns.length; i++) {
    orderSellerBtns[i].addEventListener('click', function (event) {
        event.preventDefault(); // a 태그의 기본 동작(링크 이동)을 막음
        const modal = document.getElementById('sellerModal');
        sellerGrade.textContent = event.target.parentElement.querySelector('.seller_grade').value;
        sellerComName.textContent = event.target.parentElement.querySelector('.seller_comName').value;
        sellerCeo.textContent = event.target.parentElement.querySelector('.seller_ceo').value;
        sellerHp.textContent = event.target.parentElement.querySelector('.seller_hp').value;
        sellerFax.textContent = event.target.parentElement.querySelector('.seller_fax').value;
        sellerEmail.textContent = event.target.parentElement.querySelector('.seller_email').value;
        sellerComNumber.textContent = event.target.parentElement.querySelector('.seller_comNumber').value;
        sellerAddress.textContent = "[" + event.target.parentElement.querySelector('.seller_zipCode').value.substring(0, 3) + "**] " + event.target.parentElement.querySelector('.seller_addr1').value + event.target.parentElement.querySelector('.seller_addr2').value;
        if (modal) {
            modal.classList.remove('Modalhidden'); // Modalhidden 클래스 제거
        }
    });
}

for (let i = 0; i < orderAcceptBtns.length; i++) {
    orderAcceptBtns[i].addEventListener('click', function (event) {
        event.preventDefault(); // a 태그의 기본 동작(링크 이동)을 막음
        const modal = document.getElementById('acceptModal');
        if (modal) {
            modal.classList.remove('Modalhidden'); // Modalhidden 클래스 제거
        }
    });
}

for (let i = 0; i < orderReturnBtns.length; i++) {
    orderReturnBtns[i].addEventListener('click', function (event) {
        event.preventDefault(); // a 태그의 기본 동작(링크 이동)을 막음
        const modal = document.getElementById('ReturnModal');
        if (modal) {
            modal.classList.remove('Modalhidden'); // Modalhidden 클래스 제거
        }
    });
}

for (let i = 0; i < orderChangeBtns.length; i++) {
    orderChangeBtns[i].addEventListener('click', function (event) {
        event.preventDefault(); // a 태그의 기본 동작(링크 이동)을 막음
        const modal = document.getElementById('ChangeModal');
        if (modal) {
            modal.classList.remove('Modalhidden'); // Modalhidden 클래스 제거
        }
    });
}

for (let i = 0; i < closeBtns.length; i++) {
    closeBtns[i].addEventListener('click', function (event) {
        event.preventDefault(); // a 태그의 기본 동작(링크 이동)을 막음
        const displayStatus = event.target.closest('.myInfo_modal');
        if (displayStatus) {
            displayStatus.classList.add('Modalhidden'); // Modalhidden 클래스 추가
        }
    });
}

for (let i = 0; i < cancelBtns.length; i++) {
    cancelBtns[i].addEventListener('click', function (event) {
        event.preventDefault(); // a 태그의 기본 동작(링크 이동)을 막음
        const displayStatus = event.target.closest('.myInfo_modal');
        if (displayStatus) {
            displayStatus.classList.add('Modalhidden'); // Modalhidden 클래스 추가
        }
    });
}