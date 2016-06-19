var MOVING = true;


$('#push').click(function() {
	console.log('done got pushed')
	MOVING = false;
	$('#toptext').text('YAY! all of my buttons hath been pushed. tysm.');
	$('#push').stop();
	clearInterval(moveInterval);
	console.log(MOVING)
	$.ajax({
		url: 'https://api.spotify.com/v1/tracks/3BxWKCI06eQ5Od8TY2JBeA',
		id: '3BxWKCI06eQ5Od8TY2JBeA',
		success: function(response) {
			console.log(response);
			var track = response.preview_url;
			var audio = new Audio();
			audio.src = track;
			audio.play();
			// setTimeout(audio.pause(), 2000);
		}
	});
});

// $(document).ready(function() {
	// $('#push').click(function() {
	// 	MOVING = false;
	// 	$('#push').animate({
	// 		left: "200px"
	// 	});
	// });
// });

function randomDir() {
	var height = $(window).height() - 50;
	var width = $(window).width() - 50;

	var randDecVert = Math.random() - .2;
	var randDecHorz = Math.random() - .2;
	randVert = randDecVert * height;
	randHorz = randDecHorz * width;

	// console.log([randVert, randHorz]);
	return [randVert, randHorz];
}

function moveAround() {
	if (MOVING) {
		var [v, h] = randomDir();
		$('#push').animate({
			top: v,
			left: h
		});
		// moveAround();
	}
	else {
		$('#toptext').innerHTML('YAY');
		$('#push').stop();
	}
}

// var moveInterval = setInterval(moveAround, 500);

// window.setInterval(
// 	moveAround(), 2000
// );
