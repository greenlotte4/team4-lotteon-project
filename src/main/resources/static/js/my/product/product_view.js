'use strict';

document.addEventListener("DOMContentLoaded", () => {

    // --- Global Variables ---
    let currentPage = 0;
    const pageSize = 8;
    const prodId = document.getElementById("additional-product-info").dataset.productId;

    // --- Discounted Price Calculation ---
    function roundToTens(num) {
        return Math.round(num / 10) * 10;
    }

    const discountRateNode = document.querySelector('.discount-rate');
    const originalPriceNode = document.querySelector('.original-price');
    const discountedPriceNode = document.querySelector('.discount-price');
    const originalPrice = parseInt(originalPriceNode.getAttribute('data-originalPrice'));
    const discountRate = parseInt(discountRateNode.getAttribute('data-discountRate'));
    const discountedPrice = originalPrice * (1 - discountRate / 100);
    discountedPriceNode.textContent = `${roundToTens(discountedPrice).toLocaleString()}원`;

    function discounted_price(price) {
        return roundToTens(price * (1 - discountRate / 100));
    }

    // --- Product Options Selection ---
    const variantDTO = JSON.parse(document.getElementById('product-dto').textContent);
    const selects = document.querySelectorAll('#variant-options select');

    selects.forEach(select => select.addEventListener('change', findMatchingVariant));

    function findMatchingVariant() {
        // 선택된 옵션을 배열에 담음
        const selectedOptions = Array.from(selects).map(select => select.value);
        if (selectedOptions.every(value => value)) {
            const selectedOptionsKey = `[${Array.from(selects).map(select => select.previousElementSibling.innerText.replace(':', '').trim()).join(', ')}]`;

            const matchingVariant = variantDTO.productVariants.find(variant =>
                JSON.stringify(variant.options[selectedOptionsKey]) === JSON.stringify(selectedOptions)
            );

            if (matchingVariant) {
                displayVariant(matchingVariant);
            } else {
                document.querySelector('.selected-variant-price').textContent = "제품 가격 : 0원";
            }
        }
    }

    function displayVariant(variant) {
        // Display the variant's HTML and add the price calculations
        console.log("Matched Variant Price:", variant.price);
    }

    // --- Load Reviews with Pagination ---
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
        reviewList.innerHTML = reviews.length === 0 ? "<p>리뷰가 없습니다.</p>" : "";

        reviews.forEach(review => {
            const reviewItem = document.createElement("div");
            reviewItem.classList.add("review-item");
            reviewItem.innerHTML = `
                <div class="review-header">
                    <span class="review-author">${review.uid}</span>
                    <span class="review-date">${review.regDate || '등록일 없음'}</span>
                </div>
                <div class="review-rating">${renderStars(review.rating)}</div>
                <div><p class="review_content">${review.content}</p></div>
                <div>
                    ${review.img1 ? `<img class= "review_img" src="/lotteon/uploads/review/${review.img1}" alt="리뷰 이미지1"/>` : ""}
                    ${review.img2 ? `<img class= "review_img" src="/lotteon/uploads/review/${review.img2}" alt="리뷰 이미지2"/>` : ""}
                </div>`;
            reviewList.appendChild(reviewItem);
        });
    }

    function renderStars(rating) {
        let starsHTML = '';
        for (let i = 0; i < Math.floor(rating / 2); i++) starsHTML += '<img src="/lotteon/images/review/star.png" alt="Full Star">';
        if (rating % 2 === 1) starsHTML += '<img src="/lotteon/images/review/star_half.png" alt="Half Star">';
        for (let i = 0; i < 5 - Math.ceil(rating / 2); i++) starsHTML += '<img src="/lotteon/images/review/star_gray.png" alt="Gray Star">';
        return starsHTML;
    }

    function updatePagination(totalPages, currentPage) {
        const paginationDiv = document.querySelector('.pagination');
        paginationDiv.innerHTML = '';

        const prevButton = createPageButton("이전", currentPage === 0, () => loadReviews(currentPage - 1));
        paginationDiv.appendChild(prevButton);

        for (let i = 0; i < totalPages; i++) {
            const pageButton = createPageButton(i + 1, i === currentPage, () => loadReviews(i));
            paginationDiv.appendChild(pageButton);
        }

        const nextButton = createPageButton("다음", currentPage + 1 === totalPages, () => loadReviews(currentPage + 1));
        paginationDiv.appendChild(nextButton);
    }

    function createPageButton(text, disabled, onClick) {
        const button = document.createElement('button');
        button.textContent = text;
        button.disabled = disabled;
        button.onclick = onClick;
        return button;
    }

    loadReviews();

    // --- Modal and Coupon Issue ---
    const couponModal = document.getElementById("couponModal");

    document.getElementById("couponButton")?.addEventListener("click", () => {
        couponModal.style.display = "block";
    });

    document.getElementById("closeCouponModal")?.addEventListener("click", () => {
        couponModal.style.display = "none";
    });

    window.addEventListener("click", (event) => {
        if (event.target === couponModal) couponModal.style.display = "none";
    });

    document.querySelectorAll(".download-btn").forEach(button =>
        button.addEventListener("click", () => issueCoupon(button))
    );

    function issueCoupon(button) {
        const couponId = button.getAttribute("data-coupon-id");
        fetch(`/lotteon/api/coupons/issue`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ couponId })
        })
            .then(response => response.ok ? alert("쿠폰이 성공적으로 발급되었습니다.") : handleCouponError(response.status))
            .catch(error => console.error("Error issuing coupon:", error));
    }

    function handleCouponError(status) {
        if (status === 409) alert("이미 발급된 쿠폰입니다.");
        else if (status === 401) {
            alert("로그인이 필요합니다.");
            window.location.href = "/lotteon/member/login";
        } else alert("쿠폰 발급 중 오류가 발생했습니다.");
    }
});