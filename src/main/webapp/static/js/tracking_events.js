
document.addEventListener('DOMContentLoaded', function() {
    var loadMoreButton = document.getElementById('loadMore');

    if (loadMoreButton) {
        loadMoreButton.addEventListener('click', function() {
            var page_number = document.getElementById('productList')
                .getAttribute('data-page-number');

            gtag('event', 'click_loadMore', {
                'page_number_loadMore': page_number
            });


        });
    }
});
