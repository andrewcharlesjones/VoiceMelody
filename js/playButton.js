
$('#push').click(function() {
	$.ajax({
		url: 'https://api.spotify.com/v1/tracks/3BxWKCI06eQ5Od8TY2JBeA',
		id: '3BxWKCI06eQ5Od8TY2JBeA',
		success: function(response) {
			console.log(response);
			var track = response.preview_url;
			var audio = new Audio();
			audio.src = track;
			audio.play();
			setTimeout(audio.pause(), 2);
		}
	});
});

$(document).ready(function() {
	$('#push').click(function() {
		$('#push').animate({
			left: "200px"
		});
	});
});

function randomDir() {
	var height = $(window).height();
	var width = $(window).width();

	var randDecVert = Math.random();
	var randDecHorz = Math.random();
	randVert = randDecVert * height;
	randHorz = randDecHorz * width;

	return [randVert, randHorz];
}