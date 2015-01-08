/**
 * Contains custom JavaScript code
 */
var urlHolder = new Object();

function register() {
	$.post(urlHolder.register, {
			username: $('#newUsername').val(),
			firstName: $('#newFirstName').val(),
			lastName: $('#newLastName').val(),
			email: $('#newEmail').val(),
			password: $('#newPassword').val(),
			repeatPassword: $('#repeatPassword').val()
		}, 
		function(response) {
			if (response != null) {
				if (response) {
					window.location.replace(response);
				}
			} else {
				alert('Failure! An error has occurred!');
			}
		}
	);	
}
function signIn() {
	$.post(urlHolder.signin, {
			email: $('#email').val(),
			password: $('#password').val(),
		}, 
		function(response) {
			if (response != null) {
				if (response) {
					window.location.replace(response);
				}
			} else {
				alert('Failure! An error has occurred!');
			}
		}
	);	
}

function  selectFromList(id) {
	if (id == 'generalLink') {
		$('#general').css('display','block');
		$('#photo').css('display','none');
		$('#password').css('display','none');
		$('#interests').css('display','none');
		$("#generalLink").addClass("active");
		$("#passwordLink").removeClass("active");
		$("#interestsLink").removeClass("active");
		$("#photoLink").removeClass("active");
	} else if (id == 'passwordLink') {
		$('#general').css('display','none');
		$('#photo').css('display','none');
		$('#password').css('display','block');
		$('#interests').css('display','none');
		$("#generalLink").removeClass("active");
		$("#passwordLink").addClass("active");
		$("#interestsLink").removeClass("active");
		$("#photoLink").removeClass("active");
	} else if (id == 'interestsLink') {
		$('#general').css('display','none');
		$('#photo').css('display','none');
		$('#password').css('display','none');
		$('#interests').css('display','block');
		$("#generalLink").removeClass("active");
		$("#passwordLink").removeClass("active");
		$("#interestsLink").addClass("active");
		$("#photoLink").removeClass("active");
	} else if (id == 'photoLink') {
		$('#general').css('display','none');
		$('#password').css('display','none');
		$('#interests').css('display','none');
		$('#photo').css('display','block');
		$("#generalLink").removeClass("active");
		$("#photoLink").addClass("active");
		$("#passwordLink").removeClass("active");
		$("#interestsLink").removeClass("active");
	}
}
function editProfile() {
	$.post(urlHolder.edit, {
		firstName: $('#newFirstName').val(),
		middleName: $('#newMiddleName').val(),
		lastName: $('#newLastName').val(),
		country: $('#newCountry').val(),
		dateOfBirth: $('#newDateOfBirth').val(),
		email: $('#newEmail').val()
	}, 
	function(response) {
		if (response != null) {
			if (response) {
				window.location.replace(response);
			}
		} else {
			alert('Failure! An error has occurred!');
		}
	}
);
}
function changePassword() {
	$.post(urlHolder.password, {
		password: $('#newPassword').val(),
		repeatPassword: $('#repeatPassword').val()
	}, 
	function(response) {
		if (response != null) {
			if (response) {
				window.location.replace(response);
			}
		} else {
			alert('Failure! An error has occurred!');
		}
	}
);
}
function addInterest() {
	$.post(urlHolder.interests, {
		newInterest: $('#newInterest').val()
	}, 
	function(response) {
		if (response != null) {
			if (response) {
				window.location.replace(response);
			}
		} else {
			alert('Failure! An error has occurred!');
		}
	}
);
}
function removeInterest(interest) {
	$.post(urlHolder.removeInterest, {
		removedInterest: interest
	}, 
	function(response) {
		if (response != null) {
			if (response) {
				window.location.replace(response);
			}
		} else {
			alert('Failure! An error has occurred!');
		}
	}
);
}
function searchForRecords() {
	$.post(urlHolder.search, {
		searchFor: $('#searchFor').val()
	}, 
	function(response) {
		if (response != null) {
			if (response) {
				window.location.replace(response);
			}
		} else {
			alert('Failure! An error has occurred!');
		}
	}
);
}
function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#image_preview')
                .attr('src', e.target.result)
                .css("display", "block")
                .width(450)
                .height(200);
        };

        reader.readAsDataURL(input.files[0]);
    }
}
