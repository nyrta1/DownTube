// VERY BAD JS CODE :(

$(document).ready(function () {
    $("#youtubeAllTag").on('click', function(e) {
        e.preventDefault();
        $("#youtubeAllTag").attr('class', 'nav-link active')
        $("#youtubePlaylistTag").attr('class', 'nav-link')
        $("#youtubeChannelTag").attr('class', 'nav-link')
    });

    $("#youtubePlaylistTag").on('click', function(e) {
        e.preventDefault();
        $("#youtubeAllTag").attr('class', 'nav-link')
        $("#youtubePlaylistTag").attr('class', 'nav-link active')
        $("#youtubeChannelTag").attr('class', 'nav-link')
    });

    $("#youtubeChannelTag").on('click', function(e) {
        e.preventDefault();
        $("#youtubeAllTag").attr('class', 'nav-link')
        $("#youtubePlaylistTag").attr('class', 'nav-link')
        $("#youtubeChannelTag").attr('class', 'nav-link active')
    });

    $("#submitButton").on('click', function() {
        $("#tableResult").show();
    })

    $("#nav-link-audio").on('click', function(e) {
        e.preventDefault();
        $("#table-video").hide();
        $("#table-video-no-audio").hide();
        $("#nav-link-audio").attr('class', 'nav-link active')
        $("#nav-link-video").attr('class', 'nav-link')
        $("#nav-link-video-no-audio").attr('class', 'nav-link')

        $("#table-audio").show();
    });

    $("#nav-link-video").on('click', function(e) {
        e.preventDefault();
        $("#table-video-no-audio").hide();
        $("#table-audio").hide();
        $("#nav-link-video").attr('class', 'nav-link active')
        $("#nav-link-audio").attr('class', 'nav-link')
        $("#nav-link-video-no-audio").attr('class', 'nav-link')
        $("#table-video").show();
    });

    $("#nav-link-video-no-audio").on('click', function(e) {
        e.preventDefault();
        $("#table-audio").hide();
        $("#table-video").hide();
        $("#nav-link-video-no-audio").attr('class', 'nav-link active')
        $("#nav-link-video").attr('class', 'nav-link')
        $("#nav-link-audio").attr('class', 'nav-link')
        $("#table-video-no-audio").show();
    });
});
