
$('#push').click(function() {
	console.log('pushed');
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
	})
});
