window.onload = function () {
    const addPoint = document.getElementById("addPoint");
    const allPoint = document.getElementById("allPoint");
    const nowPoint = document.getElementById("nowPoint");
    const all__sold = document.getElementById("all__sold");
    const all__discount = document.getElementById("all__discount");
    const all__deliveryfee = document.getElementById("all__deliveryfee");
    let all__totalPrice = document.getElementById("all__totalPrice");
    const selectElement = document.getElementById('condition');
    const pay__deliveryfee = document.getElementById('pay__deliveryfee');
    const recip__name = document.getElementById('recip__name');
    const phone__number = document.getElementById('phone__number');



    // option1 (색상) 선택값
    const option1Element = document.querySelector('.option1');
    const option1Value = () => option1Element.options[option1Element.selectedIndex].value;

    // option2 (사이즈) 선택값
    const option2Element = document.querySelector('.option2');
    const option2Value = () => option2Element.options[option2Element.selectedIndex].value;

    const postcodeInput = document.getElementById("postcode");
    const addressInput = document.getElementById("address");
    const detailAddressInput = document.getElementById("detailAddress");

    // 우편번호 검색 완료 시
    var findPostcodeBtn = document.getElementById("findPostcodeBtn");
    findPostcodeBtn.onclick = function() {
        new daum.Postcode({
            oncomplete: function(data) {
                postcodeInput.value = data.zonecode;
                addressInput.value = data.roadAddress || data.jibunAddress;
                detailAddressInput.focus();
            }
        }).open();
    };

    // 숫자 변환 함수 (콤마와 원 단위 제거)
    function parsePrice(priceText) {
        return parseInt(priceText.replace(/[^0-9]/g, ''), 10) || 0;
    }

    // 최종 계산 수행 함수
    function calculateFinalPrice() {
        let soldPrice = parsePrice(all__sold.innerText);
        let deliveryFee = parsePrice(all__deliveryfee.innerText);

        // 쿠폰 금액 추출
        var selectedOptionText = selectElement.options[selectElement.selectedIndex].text;
        var couponValue = selectedOptionText.match(/\d+/);
        let couponDiscount = couponValue ? parseInt(couponValue[0]) : 0;

        // 사용된 포인트 계산
        let usedPoint = parseInt(addPoint.value) || 0;

        // 할인 계산
        let totalDiscount = couponDiscount + (soldPrice * 0.28) + usedPoint;
        all__discount.innerText = totalDiscount.toLocaleString() + '원';

        // 최종 결제 금액 계산
        let finalPrice = soldPrice - totalDiscount + deliveryFee;
        if (finalPrice < 0) {
            finalPrice = 0;
        }
        all__totalPrice.innerText = finalPrice.toLocaleString() + '원';
    }

    addPoint.addEventListener('input', function () {
        let usedPoint = parseInt(addPoint.value) || 0;

        if (usedPoint < 0) {
            usedPoint = 0;
            addPoint.value = 0;
        }

        if (usedPoint === 5000) {
            addPoint.setAttribute('step', '1000');
        }

        if (usedPoint >= 5000 && usedPoint % 1000 !== 0) {
            alert("5000 이후에는 1000 포인트 단위로 사용 가능합니다.");
            usedPoint = Math.floor(usedPoint / 1000) * 1000;
            addPoint.value = usedPoint;
        }

        let maxPoint = parseInt(nowPoint.innerText) || 0;
        if (usedPoint > maxPoint) {
            usedPoint = maxPoint;
            addPoint.value = maxPoint;
        }

        nowPoint.innerText = (maxPoint - usedPoint) + 'p';
        calculateFinalPrice();
    });

    selectElement.addEventListener('change', function () {
        calculateFinalPrice();
    });

    allPoint.addEventListener('click', function () {
        if (confirm("전체 사용하시겠습니까?")) {
            addPoint.value = parseInt(nowPoint.innerText) || 0;
            nowPoint.innerText = '0p';
            calculateFinalPrice();
        }
    });

    calculateFinalPrice();

    function YYYYMMDDHHMMSS(date) {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');

        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        const seconds = String(date.getSeconds()).padStart(2, '0');

        return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }

    const currentDate = new Date();
    const formattedDate = YYYYMMDDHHMMSS(currentDate);


    const checkoutBtn = document.querySelector('.checkout-btn');
    checkoutBtn.addEventListener('click', function (event) {
        let usedPoint = parseInt(addPoint.value) || 0;

        if (usedPoint > 0 && usedPoint < 5000) {
            alert('사용 포인트가 5000p 미만입니다. 포인트를 5000p 이상으로 사용하여 주십시오.');
            event.preventDefault();
            return;
        }

        const date = new Date();
        console.log(date);


        if (confirm('결제를 진행하시겠습니까?')) {
            const data = {
                //결재방법, 상태값(결재완료 시 0 = 결재완료, count = 수량 받아와야함),
                Date: formattedDate,
                pay: 1,
                status: 0,
                count: 1,
                couponUse: 0,
                deliveryFee: parsePrice(all__deliveryfee.innerText),
                discount: 10,
                memberInfoId: 1,
                option1: option1Value(),
                option2: option2Value(),
                price: parsePrice(all__sold.innerText),
                productId: 3,
                recipZip: postcodeInput.value,
                recipAddr1: addressInput.value,
                recipAddr2: detailAddressInput.value,
                recipHp: phone__number.value,
                recipName: recip__name.value,
                savePoint: parseInt(nowPoint.innerText),
                totalPrice: parsePrice(all__totalPrice.innerText),
                usedPoint: usedPoint
            };
            console.log(data);

            fetch('/lotteon/product/order/save', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
                .then(response => response.json())
                .then(result => {
                    if (result.success) {
                        alert('결제가 완료되었습니다.');
                        window.location.href = '/lotteon/product/complete';
                    } else {
                        alert('결제가 실패하였습니다.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('결제 중 오류가 발생하였습니다.');
                });
        } else {
            event.preventDefault();
        }
    });
};