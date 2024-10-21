'use strict';

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
        console.log(adjacent_name)

        if (adjacent_name === ''){
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

const product_submit = document.querySelector('#product_submit');

product_submit.addEventListener('onclick', function (e) {
    e.preventDefault();

    // 카테고리 1, 2차 값
    const category1 = document.querySelector('#category1').value;
    const category2 = document.querySelector('#category2').value;

    const data1 = {
        category1 : category1,
        category2 : category2
    };

    // 상품 정보
    const prod_name = document.querySelector('#prod_name').value;
    const prod_description = document.querySelector('#prod_description').value;
    const price = document.querySelector('#price').value;
    const discount = document.querySelector('#discount').value;
    const point = document.querySelector('#point').value;
    const stock = document.querySelector('#stock').value;
    const deliveryfee = document.querySelector('#deliveryfee').value;
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

    const date2 = {
        prod_name : prod_name,
        prod_description : prod_description,
        price : price,
        discount : discount,
        point : point,
        stock : stock,
        deliveryfee : deliveryfee,
        prod_img1 : prod_img1,
        prod_img2 : prod_img2,
        prod_img3 : prod_img3,
        prod_detail : prod_detail,
        options : optionMap
    };


    // 상품정보 제공고시

    const prod_Detail_Condition = document.querySelector('#prod_Detail_Condition').value;
    const prod_Detail_duty = document.querySelector('#prod_Detail_duty').value;
    const prod_Detail_receipt = document.querySelector('#prod_Detail_receipt').value;
    const prod_Detail_gubun = document.querySelector('#prod_Detail_gubun').value;
    const prod_Detail_brand = document.querySelector('#prod_Detail_brand').value;
    const prod_Detail_COA = document.querySelector('#prod_Detail_COA').value;
    const prod_Detail_creator = document.querySelector('#prod_Detail_creator').value;
    const prod_Detail_country = document.querySelector('#prod_Detail_country').value;
    const prod_Detail_warning = document.querySelector('#prod_Detail_warning').value;
    const prod_Detail_create_date = document.querySelector('#prod_Detail_create_date').value;
    const prod_Detail_quality = document.querySelector('#prod_Detail_quality').value;
    const prod_Detail_as = document.querySelector('#prod_Detail_as').value;
    const prod_Detail_asPhone = document.querySelector('#prod_Detail_asPhone').value;

    const data3 = {
        prod_Detail_Condition : prod_Detail_Condition,
        prod_Detail_duty : prod_Detail_duty,
        prod_Detail_receipt : prod_Detail_receipt,
        prod_Detail_gubun : prod_Detail_gubun,
        prod_Detail_brand : prod_Detail_brand,
        prod_Detail_COA : prod_Detail_COA,
        prod_Detail_creator : prod_Detail_creator,
        prod_Detail_country : prod_Detail_country,
        prod_Detail_warning : prod_Detail_warning,
        prod_Detail_create_date : prod_Detail_create_date,
        prod_Detail_quality : prod_Detail_quality,
        prod_Detail_as : prod_Detail_as,
        prod_Detail_asPhone : prod_Detail_asPhone
    }



});



