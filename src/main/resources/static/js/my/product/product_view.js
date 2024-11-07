/*
     날짜 : 2024/11/04
     이름 : 전규찬
     내용 : product_view.js 생성

     수정이력
     - 2024/11/04 전규찬 - 옵션 선택 시 해당 제품 수량 선택 영역 생성 / 제품 가격 명시 / 가격 총합 계산
     - 2024/11/06 전규찬 - 옵션 선택 시 추가되는 제품 가격에 할인율 적용 / 숫자 입력란에 음수, 0, 최대수량 초과, 미입력에 대한 예외 처리
                        - 옵션 선택 시 하위 옵션 초기화 기능 추가
*/

'use strict'

function roundToTens(num) {
    return Math.round(num / 10) * 10;
}

const discountRateNode = document.querySelector('.discount-rate');

// 할인가격 계산 후 주입
const originalPriceNode = document.querySelector('.original-price');
const discountedPriceNode = document.querySelector('.discount-price');

const originalPrice = parseInt(originalPriceNode.getAttribute('data-originalPrice'));
const discountRate = parseInt(discountRateNode.getAttribute('data-discountRate'));
const discountedPrice = originalPrice * (1 - discountRate / 100);
discountedPriceNode.textContent = roundToTens(discountedPrice).toLocaleString() + "원";

function discounted_price(price) {
    return roundToTens(price * (1 - discountRate / 100));
}

// 재고 0 인 제품 선택 비활성화
const optionSelectsNodes = document.querySelectorAll('.option-select');


document.addEventListener('DOMContentLoaded', function () {

    // 서버에서 전달된 variantDTO JSON 데이터
    const variantDTO = JSON.parse(document.getElementById('product-dto').textContent);

    const selects = document.querySelectorAll('#variant-options select');

    function findMatchingVariant(e) {

        // 옵션 선택 시 하위 옵션 초기화("선택하세요" 라는 첫 번째 옵션값으로 재설정)
        if (e.target === selects[0]) {
            for (let i = 1; i < selects.length; i++) {
                selects[i].selectedIndex = 0;
            }
        } else if (e.target === selects[1]) {
            for (let i = 2; i < selects.length; i++)
                selects[i].selectedIndex = 0;
        }

        // 선택된 옵션을 배열에 담음
        const selectedOptions = [];
        selects.forEach(select => {
            selectedOptions.push(select.value);
        });

        // 선택된 옵션이 모두 선택된 경우에만 처리
        if (selectedOptions.every(value => value)) {
            const selectedOptionsKey = `[${Array.from(selects).map(select => select.previousElementSibling.innerText.replace(':', '').trim()).join(', ')}]`;
            let matchingVariant = null;

            // variantDTO에서 옵션 키와 매칭되는 variant 찾기
            matchingVariant = variantDTO.productVariants.find(variant => {
                const variantOptions = variant.options[selectedOptionsKey];
                return JSON.stringify(variantOptions) === JSON.stringify(selectedOptions);
            });

            // 매칭된 variant가 있을 때 링크 설정
            if (matchingVariant) {
                console.log("Matched Variant Price:", matchingVariant.price);
                const total_price = document.querySelector('.total-price');

                // 옵션 선택 시 해당 제품 수량 선택 영역 생성
                const innerHtml = `
                                <div id="${matchingVariant.variant_id}" class="selected_variant">
                                    <input class="variantsIds" type="hidden" value="${matchingVariant.variant_id}">
                                    <div class="delete_variant_div">
                                      <button class="delete_variant" type="button">X</button>                                  
                                    </div>
                                    <div class="variant_info_div">
                                       <span>${matchingVariant.sku}</span>            
                                       <div class="btnPlusMinusDiv">
                                            <button class="btn_minus" type="button">-</button>
                                            <input class="inputNum" type="number" min="1" max="${matchingVariant.stock}" value="1">
                                            <button class="btn_plus" type="button">+</button>
                                        </div>
                                        <span class="variant_price">${discounted_price(matchingVariant.price).toLocaleString() + "원"}</span>
                                    </div>
                                </div>
                              `;
                try {
                    // 동일한 제품에 대한 영역이 없다면 추가
                    if (document.getElementById(`${matchingVariant.variant_id}`) === null) {

                        total_price.insertAdjacentHTML('beforebegin', innerHtml);

                        // 생성된 제품 1개 가격을 총 상품금액에 더하기
                        let totalPrice = total_price.querySelector('strong');

                        totalPrice.textContent = (parseInt(totalPrice.textContent.replace(",", "").replace("원", "")) + parseInt(discounted_price(matchingVariant.price))).toLocaleString() + "원";

                        // 영역 제거 버튼 기능 추가
                        const btn_delete_variant = document.getElementById(`${matchingVariant.variant_id}`).querySelector('.delete_variant');
                        btn_delete_variant.addEventListener('click', function (e) {
                            e.preventDefault();
                            e.target.parentElement.parentElement.remove();
                            const currentCount = e.target.parentElement.nextElementSibling.querySelector('.btnPlusMinusDiv').querySelector('input').value;
                            const VariantPrice = e.target.parentElement.nextElementSibling.querySelector('.variant_price').textContent.replace(",", "").replace("원", "");
                            const priceToMinus = parseInt(currentCount) * parseInt(VariantPrice);
                            totalPrice.textContent = (parseInt(totalPrice.textContent.replace(",", "").replace("원", "")) - priceToMinus).toLocaleString() + "원";
                        });

                        // 수량 증가 / 감소 버튼 기능 추가 + 입력란 직접 숫자 입력 기능 추가
                        const btn_minus = document.getElementById(`${matchingVariant.variant_id}`).querySelector('.btn_minus');
                        const btn_plus = document.getElementById(`${matchingVariant.variant_id}`).querySelector('.btn_plus');
                        const inputNum = document.getElementById(`${matchingVariant.variant_id}`).querySelector('.inputNum');

                        console.log("inputNum = " + inputNum);

                        let previousNum = parseInt(inputNum.value);
                        console.log("previousNum = " + previousNum);

                        btn_minus.addEventListener('click', function (e) {
                            e.preventDefault();

                            // 숫자 입력란 호출
                            let currentNum = parseInt(e.target.nextElementSibling.value);
                            console.log("currentNum = " + currentNum);

                            // 최소값보다 작거나 같으면 비활성화
                            if (currentNum <= parseInt(e.target.nextElementSibling.min)) {
                                e.target.disabled;
                            } else {
                                // 클릭마다 1씩 감소, 제품 가격만큼 총 상품금액에서 차감
                                e.target.nextElementSibling.value = currentNum - 1;
                                let currentVariantPrice = e.target.parentElement.nextElementSibling.textContent.replace(",", "").replace("원", "");
                                totalPrice.textContent = (parseInt(totalPrice.textContent.replace(",", "").replace("원", "")) - parseInt(currentVariantPrice)).toLocaleString() + "원";
                                previousNum = currentNum - 1;
                                console.log("previousNum 마이너스" + previousNum)
                            }
                        });

                        btn_plus.addEventListener('click', function (e) {
                            e.preventDefault();

                            let currentNum = parseInt(e.target.previousElementSibling.value);
                            console.log("currentNum = " + currentNum);
                            if (currentNum >= parseInt(e.target.previousElementSibling.max)) {
                                e.target.disabled;
                            } else {
                                e.target.previousElementSibling.value = currentNum + 1;
                                let currentVariantPrice = e.target.parentElement.nextElementSibling.textContent.replace(",", "").replace("원", "");
                                totalPrice.textContent = (parseInt(totalPrice.textContent.replace(",", "").replace("원", "")) + parseInt(currentVariantPrice)).toLocaleString() + "원";
                                previousNum = currentNum + 1;
                                console.log("previousNum 플러스" + previousNum)
                            }

                        });

                        // 숫자 입력으로 수량 조정 기능
                        inputNum.addEventListener('change', function (e) {

                            let currentNum = parseInt(e.target.value);
                            if (currentNum > parseInt(e.target.max)) {
                                alert(`해당 상품의 최대 수량은 ${e.target.max}개 입니다.`)
                                e.target.value = previousNum;
                                return;
                            } else if (currentNum <= 0) {
                                alert('0이나 음수 값은 입력하실 수 없습니다.')
                                e.target.value = previousNum;
                                return;
                            } else if (isNaN(currentNum)) {
                                alert('숫자를 입력해주세요')
                                e.target.value = previousNum;
                                return;
                            }
                            console.log("currentNum = " + currentNum);
                            if (currentNum > previousNum) {
                                const numDiff = currentNum - previousNum;
                                let currentVariantPrice = e.target.parentElement.nextElementSibling.textContent.replace(",", "").replace("원", "");
                                totalPrice.textContent = (parseInt(totalPrice.textContent.replace(",", "").replace("원", "")) + parseInt(numDiff * currentVariantPrice)).toLocaleString() + "원";
                                previousNum = currentNum;
                                console.log("previousNum =" + previousNum)
                            } else if (currentNum < previousNum) {
                                const numDiff = previousNum - currentNum;
                                let currentVariantPrice = e.target.parentElement.nextElementSibling.textContent.replace(",", "").replace("원", "");
                                totalPrice.textContent = (parseInt(totalPrice.textContent.replace(",", "").replace("원", "")) - parseInt(numDiff * currentVariantPrice)).toLocaleString() + "원";
                                previousNum = currentNum;
                                console.log("previousNum =" + previousNum)
                            }
                        });

                    }
                } catch (error) {
                    console.error('고유 제품 HTML 삽입 중 오류 발생:', error);
                }
            } else {
                console.log("No matching variant found");
                document.querySelector('.selected-variant-price').textContent = "제품 가격 : 0원";
            }
        }
    }

    // 옵션이 변경될 때마다 variant를 찾음
    selects.forEach(select => {
        select.addEventListener('change', findMatchingVariant);
    });

    const btnCart = document.querySelector('.cart-button');
    const btnBuy = document.querySelector('.buy-button');

    // 장바구니에 담기 위한 fetch post
    btnCart.addEventListener('click', function (event) {
        event.preventDefault(); // 기본 폼 제출 방지

        const variantsIdNodes = document.querySelectorAll('.variantsIds');
        const variantsIds = Array.from(variantsIdNodes).map(node => parseInt(node.value.trim()));

        const countsNodes = document.querySelectorAll('.inputNum');
        const counts = Array.from(countsNodes).map(node => parseInt(node.value.trim()));

        const json = {
            productVariants: variantsIds,
            counts: counts
        }

        // AJAX 요청
        fetch('/lotteon/product/cart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(json)
        })
            .then(response => response.text())
            .then(data => {
                if (data === "success") {
                    // 성공 시 알림창 띄우기
                    if (confirm("장바구니에 상품이 담겼습니다. 장바구니로 이동하시겠습니까?")) {
                        window.location.href = "/lotteon/product/cart"; // 장바구니 페이지로 리다이렉트
                    }
                } else if (data === "failed") {
                    alert("장바구니 등록에 실패했습니다.");
                } else if (data === "noUser") {
                    alert("로그인이 필요합니다."); // 로그인 필요 알림
                    window.location.href = "/lotteon/member/login"; // 로그인 페이지로 리다이렉트
                }
            })
            .catch(error => console.error('Error:', error));
    });

    // 바로구매를 위한 fetch post
    btnBuy.addEventListener('click', function (event) {
        event.preventDefault(); // 기본 폼 제출 방지

        const variantsIdNodes = document.querySelectorAll('.variantsIds');
        const variantsIds = Array.from(variantsIdNodes).map(node => parseInt(node.value.trim()));

        const countsNodes = document.querySelectorAll('.inputNum');
        const counts = Array.from(countsNodes).map(node => parseInt(node.value.trim()));

        const json = {
            productVariants: variantsIds,
            counts: counts
        }

        // AJAX 요청
        fetch('/lotteon/product/order', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(json)
        })
            .then(response => response.text())
            .then(data => {
                if (data === "success") {
                    // 성공 시 알림창 띄우기
                        alert("주문 페이지로 이동합니다.");
                        window.location.href = "/lotteon/product/order"; // 구매페이지로 바로 가기
                } else if (data === "failed") {
                    alert("상품 구매 중 문제가 발생하였습니다.");
                } else if (data === "noUser") {
                    alert("로그인이 필요합니다."); // 로그인 필요 알림
                    window.location.href = "/lotteon/member/login"; // 로그인 페이지로 리다이렉트
                }
            })
            .catch(error => console.error('Error:', error));
    });


});