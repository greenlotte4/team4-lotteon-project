/*
     날짜 : 2024/11/04
     이름 : 전규찬
     내용 : product_view.js 생성

     수정이력
     - 2024/11/04 전규찬 - 옵션 선택 시 해당 제품 수량 선택 영역 생성 / 제품 가격 명시 / 가격 총합 계산
*/

'use strict'

window.onload = function () {
    // 할인가격 계산 후 주입
    const originalPriceNode = document.querySelector('.original-price');
    const discountRateNode = document.querySelector('.discount-rate');
    const discountedPriceNode = document.querySelector('.discount-price');

    const originalPrice = parseFloat(originalPriceNode.getAttribute('data-originalPrice'));
    const discountRate = parseFloat(discountRateNode.getAttribute('data-discountRate'));
    const discountedPrice = originalPrice * (1 - discountRate / 100);
    discountedPriceNode.textContent = discountedPrice.toLocaleString() + "원";

    // 재고 0 인 제품 선택 비활성화
    const optionSelectsNodes = document.querySelectorAll('.option-select');


};


// document.addEventListener('DOMContentLoaded', function() {
//
//     // 수량이 변경될 때마다 count 필드에 값 업데이트
//     document.getElementById('quantity').addEventListener('input', function () {
//         document.getElementById('count').value = this.value;
//     });
//
//     // 페이지 로드 시 초기 수량 값 설정
//     document.getElementById('count').value = document.getElementById('quantity').value;
//
//     const productDataElement = document.getElementById('product-dto');
//     const productDTO = JSON.parse(productDataElement.textContent);
//
//     console.log("Loaded productDTO:", productDTO); // 전체 productDTO 객체 확인
//
//     const selects = document.querySelectorAll('#variant-options select');
//
//     function findMatchingVariant() {
//         // 선택된 옵션을 배열에 담음
//         const selectedOptions = [];
//         selects.forEach(select => {
//             selectedOptions.push(select.value);
//         });
//         console.log("Selected Options Array:", selectedOptions);
//
//         // 옵션의 키를 생성
//         let selectedOptionsKey = `[${Array.from(selects).map(select => select.previousElementSibling.innerText.replace(':', '').trim()).join(', ')}]`;
//         console.log("Generated Key for Selected Options (original):", selectedOptionsKey);
//
//         // 순열 생성 함수
//         function generatePermutations(array) {
//             if (array.length <= 1) return [array];
//             const permutations = [];
//             for (let i = 0; i < array.length; i++) {
//                 const currentElement = array[i];
//                 const remainingElements = array.slice(0, i).concat(array.slice(i + 1));
//                 const remainingPermutations = generatePermutations(remainingElements);
//                 remainingPermutations.forEach(permutation => {
//                     permutations.push([currentElement, ...permutation]);
//                 });
//             }
//             return permutations;
//         }
//
//         // 모든 순열 생성
//         const optionsPermutations = generatePermutations(selectedOptions);
//         const keysPermutations = generatePermutations(Array.from(selects).map(select => select.previousElementSibling.innerText.replace(':', '').trim()));
//
//         console.log("All Options Permutations:", optionsPermutations);
//         console.log("All Keys Permutations:", keysPermutations);
//
//         let matchingVariant = null;
//
//         if (selectedOptions.every(value => value)) {
//             // 각 키와 옵션 배열의 모든 순열로 variant 찾기
//             for (let keyPerm of keysPermutations) {
//                 selectedOptionsKey = `[${keyPerm.join(', ')}]`;
//                 console.log("Trying Key Permutation:", selectedOptionsKey);
//
//                 for (let optionsPerm of optionsPermutations) {
//                     console.log("Trying Options Permutation:", optionsPerm);
//
//                     matchingVariant = productDTO.productVariants.find(variant => {
//                         const variantOptions = variant.options[selectedOptionsKey];
//                         console.log("Checking variant:", variant.variant_id, "with options:", variantOptions, "against selected options:", optionsPerm);
//                         return JSON.stringify(variantOptions) === JSON.stringify(optionsPerm);
//                     });
//
//                     if (matchingVariant) {
//                         console.log("Match found with Key:", selectedOptionsKey, "and Options:", optionsPerm);
//                         break; // 매칭이 되면 더 이상 반복하지 않음
//                     }
//                 }
//                 if (matchingVariant) break;
//             }
//         }
//
//         if (matchingVariant) {
//             console.log("Matched Variant ID:", matchingVariant.variant_id);
//             document.querySelector('.selected-variant-price').value = matchingVariant.variant_id;
//         } else {
//             console.log("No matching variant found");
//             document.querySelector('.selected-variant-price').value = "선택된 제품 없음";
//         }
//     }
//
//     selects.forEach(select => {
//         select.addEventListener('change', findMatchingVariant);
//     });
//
// });

document.addEventListener('DOMContentLoaded', function () {

    // 서버에서 전달된 variantDTO JSON 데이터
    const variantDTO = JSON.parse(document.getElementById('product-dto').textContent);

    const selects = document.querySelectorAll('#variant-options select');

    function findMatchingVariant() {
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
                                        <span class="variant_price">${matchingVariant.price.toLocaleString() + "원"}</span>
                                    </div>
                                </div>
                              `;
                try {
                    // 동일한 제품에 대한 영역이 없다면 추가
                    if (document.getElementById(`${matchingVariant.variant_id}`) === null) {

                        total_price.insertAdjacentHTML('beforebegin', innerHtml);

                        // 생성된 제품 1개 가격을 총 상품금액에 더하기
                        let totalPrice = total_price.querySelector('strong');

                        totalPrice.textContent = (parseInt(totalPrice.textContent.replace(",", "").replace("원", "")) + parseInt(matchingVariant.price)).toLocaleString() + "원";

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

                        btn_minus.addEventListener('click', function (e) {
                            e.preventDefault();

                            // 숫자 입력란 호출
                            const currentNum = e.target.nextElementSibling;
                            console.log("currentNum = " + currentNum);

                            // 최소값보다 작거나 같으면 비활성화
                            if (parseInt(currentNum.value) <= parseInt(currentNum.min)) {
                                e.target.disabled;
                            } else {
                                // 클릭마다 1씩 감소, 제품 가격만큼 총 상품금액에서 차감
                                currentNum.value = parseInt(currentNum.value) - 1;
                                let currentVariantPrice = e.target.parentElement.nextElementSibling.textContent.replace(",", "").replace("원", "");
                                totalPrice.textContent = (parseInt(totalPrice.textContent.replace(",", "").replace("원", "")) - parseInt(currentVariantPrice)).toLocaleString() + "원";
                            }
                        });

                        btn_plus.addEventListener('click', function (e) {
                            e.preventDefault();

                            const currentNum = e.target.previousElementSibling;
                            console.log("currentNum = " + currentNum);
                            if (parseInt(currentNum.value) >= parseInt(currentNum.max)) {
                                e.target.disabled;
                            } else {
                                currentNum.value = parseInt(currentNum.value) + 1;
                                let currentVariantPrice = e.target.parentElement.nextElementSibling.textContent.replace(",", "").replace("원", "");
                                totalPrice.textContent = (parseInt(totalPrice.textContent.replace(",", "").replace("원", "")) + parseInt(currentVariantPrice)).toLocaleString() + "원";
                            }

                        });

                        let previousNum = parseInt(inputNum.value);
                        console.log("previousNum = " + previousNum);

                        inputNum.addEventListener('change', function (e) {
                            const currentNum = parseInt(e.target.value);
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


});