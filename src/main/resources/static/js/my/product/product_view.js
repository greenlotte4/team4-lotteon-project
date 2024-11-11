document.addEventListener("DOMContentLoaded", () => {
    // 모달 기능 설정
    const couponButton = document.getElementById("couponButton");
    const couponModal = document.getElementById("couponModal");
    const closeCouponModal = document.getElementById("closeCouponModal");

    couponButton?.addEventListener("click", () => {
        couponModal.style.display = "block";
    });

    closeCouponModal?.addEventListener("click", () => {
        couponModal.style.display = "none";
    });

    window.addEventListener("click", (event) => {
        if (event.target === couponModal) {
            couponModal.style.display = "none";
        }
    });

    // 리뷰 로드
    let currentPage = 0;
    const pageSize = 8;
    const prodId = document.getElementById("additional-product-info")?.dataset.productId;

    async function loadReviews(page = 0) {
        try {
            const response = await fetch(`/lotteon/review/${prodId}?page=${page}&size=${pageSize}`);
            const data = await response.json();

            if (response.ok) {
                renderReviews(data.content);
                updatePagination(data.totalPages, page);
                currentPage = page;
            } else {
                console.error("리뷰를 불러오는 중 오류 발생:", data);
            }
        } catch (error) {
            console.error("리뷰를 불러오는 중 오류 발생:", error);
        }
    }

    function renderReviews(reviews) {
        const reviewList = document.getElementById("review-list");
        reviewList.innerHTML = "";

        if (!reviews || reviews.length === 0) {
            reviewList.innerHTML = "<p>리뷰가 없습니다.</p>";
            return;
        }

        reviews.forEach(review => {
            const reviewItem = document.createElement("div");
            reviewItem.classList.add("review-item");

            let starsHTML = '';
            for (let i = 0; i < Math.floor(review.rating / 2); i++) {
                starsHTML += '<img src="/lotteon/images/review/star.png" alt="Full Star" class="star">';
            }
            if (review.rating % 2 === 1) {
                starsHTML += '<img src="/lotteon/images/review/star_half.png" alt="Half Star" class="star">';
            }
            for (let i = 0; i < 5 - Math.floor(review.rating / 2) - (review.rating % 2 === 1 ? 1 : 0); i++) {
                starsHTML += '<img src="/lotteon/images/review/star_gray.png" alt="Gray Star" class="star">';
            }

            reviewItem.innerHTML = `
                <div class="review-header">
                    <span class="review-author">${review.uid}</span>
                    <span class="review-date">${review.regDate ? review.regDate : '등록일 없음'}</span>
                </div>
                <div class="review-rating">${starsHTML}</div>
                <div>
                    <p class="review_content">${review.content}</p>
                </div>
                <div>
                    ${review.img1 ? `<img class="review_img" src="/lotteon/uploads/review/${review.img1}" alt="리뷰 이미지1" class="review-image"/>` : ""}
                    ${review.img2 ? `<img class="review_img" src="/lotteon/uploads/review/${review.img2}" alt="리뷰 이미지2" class="review-image"/>` : ""}
                </div>
            `;
            reviewList.appendChild(reviewItem);
        });
    }

    function updatePagination(totalPages, currentPage) {
        const paginationDiv = document.querySelector('.pagination');
        paginationDiv.innerHTML = '';

        const prevButton = document.createElement('button');
        prevButton.classList.add('page-btn');
        prevButton.disabled = currentPage === 0;
        prevButton.textContent = '이전';
        prevButton.onclick = () => loadReviews(currentPage - 1);
        paginationDiv.appendChild(prevButton);

        for (let i = 0; i < totalPages; i++) {
            const pageButton = document.createElement('button');
            pageButton.classList.add('page-num');
            if (i === currentPage) pageButton.classList.add('active');
            pageButton.textContent = i + 1;
            pageButton.onclick = () => loadReviews(i);
            paginationDiv.appendChild(pageButton);
        }

        const nextButton = document.createElement('button');
        nextButton.classList.add('page-btn');
        nextButton.disabled = currentPage + 1 === totalPages;
        nextButton.textContent = '다음';
        nextButton.onclick = () => loadReviews(currentPage + 1);
        paginationDiv.appendChild(nextButton);
    }

    loadReviews(currentPage);

    // 쿠폰 발급
    const buttons = document.querySelectorAll(".download-btn");

    // 각 버튼에 클릭 이벤트 리스너 추가
    buttons.forEach(button => {
        button.addEventListener("click", function () {
            issueCoupon(button);
        });
    });

    function issueCoupon(button) {
        console.log("버튼눌림");
        const couponId = button.getAttribute("data-coupon-id");
        console.log(couponId);

        fetch(`/lotteon/api/coupons/issue`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ couponId: couponId })
        })
            .then(response => {
                if (response.ok) {
                    alert("쿠폰이 성공적으로 발급되었습니다.");
                } else if (response.status === 409) {
                    alert("이미 발급된 쿠폰입니다.");
                } else if (response.status === 401) {
                    alert("로그인이 필요합니다.");
                    window.location.href = "/lotteon/member/login"; // 로그인 페이지로 리다이렉트
                } else {
                    alert("쿠폰 발급 중 오류가 발생했습니다.");
                }
            })
            .catch(error => console.error("Error issuing coupon:", error));
    }

    // 할인가격 계산 및 옵션 선택 관련
    function roundToTens(num) {
        return Math.round(num / 10) * 10;
    }

    const discountRateNode = document.querySelector('.discount-rate');
    const originalPriceNode = document.querySelector('.original-price');
    const discountedPriceNode = document.querySelector('.discount-price');
    const originalPrice = parseInt(originalPriceNode.getAttribute('data-originalPrice'));
    const discountRate = parseInt(discountRateNode.getAttribute('data-discountRate'));
    const discountedPrice = originalPrice * (1 - discountRate / 100);
    discountedPriceNode.textContent = roundToTens(discountedPrice).toLocaleString() + "원";

    function discounted_price(price) {
        return roundToTens(price * (1 - discountRate / 100));
    }

    const selects = document.querySelectorAll('#variant-options select');

    function findMatchingVariant(e) {
        const selectedOptions = Array.from(selects).map(select => select.value);
        if (selectedOptions.every(value => value)) {
            const selectedOptionsKey = `[${Array.from(selects).map(select => select.previousElementSibling.innerText.replace(':', '').trim()).join(', ')}]`;
            let matchingVariant = variantDTO.productVariants.find(variant => {
                const variantOptions = variant.options[selectedOptionsKey];
                return JSON.stringify(variantOptions) === JSON.stringify(selectedOptions);
            });

            if (matchingVariant) {
                const total_price = document.querySelector('.total-price');
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
                if (document.getElementById(`${matchingVariant.variant_id}`) === null) {
                    total_price.insertAdjacentHTML('beforebegin', innerHtml);
                }
            }
        }
    }

    selects.forEach(select => {
        select.addEventListener('change', findMatchingVariant);
    });

    // 장바구니 기능
    const btnCart = document.querySelector('.cart-button');
    const btnBuy = document.querySelector('.buy-button');

    btnCart.addEventListener('click', function (event) {
        event.preventDefault();

        const variantsIds = Array.from(document.querySelectorAll('.variantsIds')).map(node => parseInt(node.value.trim()));
        const counts = Array.from(document.querySelectorAll('.inputNum')).map(node => parseInt(node.value.trim()));

        fetch('/lotteon/product/cart', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ productVariants: variantsIds, counts: counts })
        })
            .then(response => response.text())
            .then(data => {
                if (data === "success") {
                    if (confirm("장바구니에 상품이 담겼습니다. 장바구니로 이동하시겠습니까?")) {
                        window.location.href = "/lotteon/product/cart";
                    }
                } else if (data === "failed") {
                    alert("장바구니 등록에 실패했습니다.");
                } else if (data === "noUser") {
                    alert("로그인이 필요합니다.");
                    window.location.href = "/lotteon/member/login";
                }
            })
            .catch(error => console.error('Error:', error));
    });

        // 바로구매 기능
        btnBuy.addEventListener('click', function (event) {
            event.preventDefault();

            const variantsIds = Array.from(document.querySelectorAll('.variantsIds')).map(node => parseInt(node.value.trim()));
            const counts = Array.from(document.querySelectorAll('.inputNum')).map(node => parseInt(node.value.trim()));

            fetch('/lotteon/product/order', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ productVariants: variantsIds, counts: counts })
            })
                .then(response => response.text())
                .then(data => {
                    if (data === "success") {
                        alert("주문 페이지로 이동합니다.");
                        window.location.href = "/lotteon/product/order";
                    } else if (data === "failed") {
                        alert("상품 구매 중 문제가 발생하였습니다.");
                    } else if (data === "noUser") {
                        alert("로그인이 필요합니다.");
                        window.location.href = "/lotteon/member/login";
                    }
                })
                .catch(error => console.error('Error:', error));
        });

        // 수량 증가/감소 기능 및 수량 입력 검증
        document.querySelectorAll('.btnPlusMinusDiv').forEach(div => {
            const btn_minus = div.querySelector('.btn_minus');
            const btn_plus = div.querySelector('.btn_plus');
            const inputNum = div.querySelector('.inputNum');
            let previousNum = parseInt(inputNum.value);

            btn_minus.addEventListener('click', function (e) {
                e.preventDefault();
                let currentNum = parseInt(inputNum.value);
                if (currentNum > parseInt(inputNum.min)) {
                    inputNum.value = currentNum - 1;
                    previousNum = currentNum - 1;
                }
            });

            btn_plus.addEventListener('click', function (e) {
                e.preventDefault();
                let currentNum = parseInt(inputNum.value);
                if (currentNum < parseInt(inputNum.max)) {
                    inputNum.value = currentNum + 1;
                    previousNum = currentNum + 1;
                }
            });

            inputNum.addEventListener('change', function (e) {
                let currentNum = parseInt(e.target.value);
                if (currentNum > parseInt(e.target.max) || currentNum <= 0 || isNaN(currentNum)) {
                    alert('수량은 1 이상 최대 재고 수량 이내로 입력해야 합니다.');
                    e.target.value = previousNum;
                } else {
                    previousNum = currentNum;
                }
            });
        });
    });
       