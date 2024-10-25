'use strict';

// 일괄 등록 버튼
const btnProdNameInputAll = document.querySelector('#btnProdNameInputAll');
const btnProdPriceInputAll = document.querySelector('#btnProdPriceInputAll');
const btnProdStockInputAll = document.querySelector('#btnProdStockInputAll');

// 옵션 저장은 controller와 service 단에서 함. 순서대로 출력되었기 때문에 나머지 값만 받고 다시 순서대로 넣으면 됨.

// 등록 버튼 이벤트 설정
const btnSubmit = document.querySelector('#product_submit');

btnSubmit.addEventListener('click', function (e) {
    e.preventDefault();

    // 제품고유명, 가격, 수량 값 받아오기
    const prodONameNodes = document.querySelectorAll('.prodOName');
    const prodPriceNodes = document.querySelectorAll('.prodPrice');
    const prodStockNodes = document.querySelectorAll('.prodStock');
    const mixedValuesNodes = document.querySelector('#mixedValuesList');

    const prodONames = Array.from(prodONameNodes).map(node => node.value.trim());
    const prodPrices = Array.from(prodPriceNodes).map(node => node.value.trim());
    const prodStocks = Array.from(prodStockNodes).map(node => node.value.trim());
    const mixedValuesList = mixedValuesNodes.value;

    const formData = new FormData();
    formData.append('prodONames', JSON.stringify(prodONames))
    formData.append('prodPrices', JSON.stringify(prodPrices))
    formData.append('prodStocks', JSON.stringify(prodStocks))
    formData.append('mixedValuesList', JSON.stringify(mixedValuesList))

    fetch('/lotteon/admin/product/register/more', {
        method: 'POST',
        body: formData
    })
        .catch(err => console.log(err));

    alert('상세 등록이 완료되었습니다!')
});

