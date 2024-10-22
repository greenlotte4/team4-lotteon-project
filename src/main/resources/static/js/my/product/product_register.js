'use strict';

// 카테고리 출력 파트 ------------------------------------------------------------------------------

const category1 = document.querySelector('#category1');
const category2 = document.querySelector('#category2');
const category3 = document.querySelector('#category3');

category1.addEventListener('change', function (e) {
    const parent = e.target.value;

    fetch(`/lotteon/admin/product/register/${parent}`)
        .then(resp => resp.json())
        .then(data => {
            console.log(data)
            if (category2.childNodes.length !== 1) {
                category2.innerHTML = "<option value=\"\" disabled selected hidden>2차분류 선택</option>";

                data.forEach(option2 => {
                    const option = document.createElement('option');
                    option.value = option2.productCateId;
                    option.text = option2.name;
                    category2.appendChild(option);
                });
            } else {
                data.forEach(option2 => {
                    const option = document.createElement('option');
                    option.value = option2.productCateId;
                    option.text = option2.name;
                    category2.appendChild(option);
                });
            }
        })
        .catch(err => console.log(err));


});

category2.addEventListener('change', function (e) {
    const parent = e.target.value;

    fetch(`/lotteon/admin/product/register/${parent}`)
        .then(resp => resp.json())
        .then(data => {
            console.log(data)

            if (category3.childNodes.length !== 1) {
                category3.innerHTML = "<option value=\"\" disabled selected hidden>3차분류 선택</option>";

                data.forEach(option3 => {
                    const option = document.createElement('option');
                    option.value = option3.productCateId;
                    option.text = option3.name;
                    category3.appendChild(option);
                });
            } else {
                data.forEach(option3 => {
                    const option = document.createElement('option');
                    option.value = option3.productCateId;
                    option.text = option3.name;
                    category3.appendChild(option);
                });
            }
        })
        .catch(err => console.log(err));

});


// 상품 옵션 입력 파트 ----------------------------------------------------------------------------

const fake_inputs = document.querySelectorAll('.fake_input');
const option = document.querySelector('#option');
const option_names = document.querySelectorAll('.option-name');
let oldFake = fake_inputs[fake_inputs.length - 1].classList.value;
let oldName = option_names[option_names.length - 1].classList.value;

// 옵션값 입력 후 엔터로 추가하는 이벤트
option.addEventListener('keydown', function (event) {

    if (event.target && event.target.classList.contains("option-value-input") && event.key === 'Enter') {
        event.preventDefault(); // 기본 제출 동작 막기

        // 입력 필드가 비어 있지 않은 경우에만 실행
        const optionValueInput = event.target; // 이벤트가 발생한 요소를 직접 참조
        const value = optionValueInput.value.trim();
        let parent = event.target.parentElement;
        let adjacent_name = parent.parentElement.previousElementSibling.querySelector('input').value.trim();

        if (adjacent_name === '') {
            alert('옵션명을 입력해주세요')
            optionValueInput.disable();
        }

        if (value !== '') {
            // 옵션값 박스 생성
            const valueBox = document.createElement('div');
            valueBox.classList.add('option-value-box');

            const valueText = document.createElement('span');
            valueText.textContent = value;

            // 삭제 버튼 (X자)
            const deleteButton = document.createElement('span');
            deleteButton.classList.add('delete-value');
            deleteButton.textContent = ' X';

            // 삭제 버튼 클릭 시 옵션값 삭제
            deleteButton.addEventListener('click', function () {
                parent.removeChild(valueBox);
            });

            // 옵션값 박스에 텍스트와 삭제 버튼 추가
            valueBox.appendChild(valueText);
            valueBox.appendChild(deleteButton);
            parent.appendChild(valueBox);

            // 입력창 초기화
            optionValueInput.value = '';
        }
    }
})

// 옵션 추가 버튼
const addOptionButton = document.getElementById('add-option');
addOptionButton.addEventListener('click', function () {
    const option = document.querySelector('#option');

    const option_innerHTML = `
                                        <div>
                                            <span>옵션명 :</span>
                                            <input type="text" class="${oldName + 1}" placeholder="옵션명을 입력하세요" required>
                                        </div>
                                        <div class="option-values">
                                            <span>옵션값 :</span>
                                            <div class="${oldFake + 1}">
                                                <input type="text" class="option-value-input"
                                                       placeholder="옵션값 입력 후 엔터" required>
                                            </div>
                                        </div>
                                        `;
    const option_name = document.querySelectorAll('.option-name');
    if (option_name.length < 5) {
        option.insertAdjacentHTML("beforeend", option_innerHTML);
    } else {
        alert('옵션을 더 이상 추가할 수 없습니다.')
        addOptionButton.remove();
    }
});


// 데이터 전송 파트 --------------------------------------------------------------------------------


const product_submit = document.querySelector('#product_submit');

product_submit.addEventListener('onclick', function (e) {
    e.preventDefault();

    const cateId1 = category1.value;
    const cateId2 = category2.value;
    const cateId3 = category3.value;

    // 상품 정보
    const name = document.querySelector('#name').value;
    const description = document.querySelector('#description').value;
    const company = document.querySelector('#company').value;
    const price = document.querySelector('#price').value;
    const discount = document.querySelector('#discount').value;
    const point = document.querySelector('#point').value;
    const stock = document.querySelector('#stock').value;
    const deliveryFee = document.querySelector('#deliveryFee').value;
    const prod_img1 = document.querySelector('#prod_img1').value;
    const prod_img2 = document.querySelector('#prod_img2').value;
    const prod_img3 = document.querySelector('#prod_img3').value;
    const prod_detail = document.querySelector('#prod_detail').value;

    // 옵션 리스트
    const option_names = document.querySelectorAll('.option-name');
    const option_value_inputs = document.querySelectorAll('.option-value-input');

    const optionMap = new Map();
    let optionValueList = {};

    for (const option_name of option_names) {
        let common = option_name.classList.value.split(" ")[1]
        for (const option_value_input of option_value_inputs) {
            if (common.eq(option_value_input.classList.value.split(" ")[1])) {
                optionValueList.add(option_value_input);
            }
        }
        optionMap.set(option_name, optionValueList);
    }

    console.log("optionMap : " + optionMap)

    const data2 = {
        category1: cateId1,
        category2: cateId2,
        category3: cateId3,
        name: name,
        description: description,
        company: company,
        price: price,
        discount: discount,
        point: point,
        stock: stock,
        deliveryFee: deliveryFee,
        prod_img1: prod_img1,
        prod_img2: prod_img2,
        prod_img3: prod_img3,
        prod_detail: prod_detail,
        options: optionMap
    };

    fetch('lotteon/admin/product/register', {
        method: 'POST',
        body: JSON.stringify(data2)
    })
        .then(resp => resp.json())
        .then(data => console.log(data))
        .catch(err => console.log(err));


    // 상품정보 제공고시

    const condition = document.querySelector('#condition').value;
    const duty = document.querySelector('#duty').value;
    const receipt = document.querySelector('#receipt').value;
    const sellerType = document.querySelector('#sellerType').value;
    const brand = document.querySelector('#brand').value;
    const COA = document.querySelector('#COA').value;
    const creator = document.querySelector('#creator').value;
    const country = document.querySelector('#country').value;
    const warning = document.querySelector('#warning').value;
    const createDate = document.querySelector('#createDate').value;
    const quality = document.querySelector('#quality').value;
    const as = document.querySelector('#as').value;
    const asPhone = document.querySelector('#asPhone').value;

    const data3 = {
        condition: condition,
        duty: duty,
        receipt: receipt,
        sellerType: sellerType,
        brand: brand,
        COA: COA,
        creator: creator,
        country: country,
        warning: warning,
        createDate: createDate,
        quality: quality,
        as: as,
        asPhone: asPhone
    }

});



