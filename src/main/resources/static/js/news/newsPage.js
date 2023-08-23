$(document).ready(function() {
    $('#searchButton').click(function() {
        var query = $('#searchInput').val();
        if (query !== "") {
            searchBlog(query);
        }
    });

    function searchBlog(query) {
        var client_id = 'JeoZb70w2v35nvvPqzLN';
        var client_secret = 'e6EN3rbnqs';
        var api_url = 'https://openapi.naver.com/v1/search/news?query=' + encodeURI(query);

        $.ajax({
            url: api_url,
            headers: {
                'X-Naver-Client-Id': client_id,
                'X-Naver-Client-Secret': client_secret
            },
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                displayResults(data.items);
            },
            error: function(xhr, status, error) {
                console.error('Error:', status, error);
            }
        });
    }

    function displayResults(items) {
        var resultsDiv = $('#results');
        resultsDiv.empty();

        if (items.length === 0) {
            resultsDiv.append('<p>No results found.</p>');
            return;
        }

        for (var i = 0; i < items.length; i++) {
            var item = items[i];
            var title = item.title;
            var link = item.link;
            var description = item.description;

            var resultHtml = `
                        <div class="result">
                            <h3><a href="${link}" target="_blank">${title}</a></h3>
                            <p>${description}</p>
                        </div>
                    `;
            resultsDiv.append(resultHtml);
        }
    }
});
