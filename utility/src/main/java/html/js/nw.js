/**
 * Each object in sessionStorage corresponds to a page that displays that object (entity) including
 * any collections of UUIDs contained in that object.
 * When opening a new page, for example, when on an address and the user drills down to a resident,
 * the "id" of the resident is stored in "resident". When the resident page is opened, the 
 * resident id is used to that resident,
 * replacing the small object just containing the id.
 * The entity also contains a subset of child objects suitable for table displays. For example,
 * A neighborhood will contain a list of addresses and administrators for that neighborhood.
 * 
 */
 	  function detailFormatter(index, row, tableObject) {
		    var $table = $("#" + tableObject.context.id);
		    var entityType = $table.data('entity-type');
		    var entityDescription = $table.data('entity-description');
		    var $templateWrapper = $("#" + entityType);
		  	var $clone = $templateWrapper.find("#template").clone(true);
		    
		  	if (row.saved===undefined) {
		  		// Deep copy the row to row.saved where we keep the modified data, if any.
		  		// Closing the form (not just collapsing) deletes the saved data
		  		row.saved = JSON.parse(JSON.stringify(row));
		  		delete row.saved.rowtype;
		  		delete row.saved.rowstatus;
		  	}
	    	if (row.rowtype===undefined) {
				row.rowtype = "PUT";
			}

			// In addition to cloning our form, we also take a snapshot of the row data
			// And edit from there. This way we preserve the original row.
			var $onopen = $clone.find(".onopen");				// Button that opens the entity
			var $onclose = $clone.find(".onclose");				// Button that closes, cancels detail
			var $ondelete = $clone.find(".ondelete");				// Button that deletes an entity
			var $oninvite = $clone.find(".oninvite");				// Button that invites someone to administer the entity
			var $onannounce = $clone.find(".onannounce");				// Button that invites someone to administer the entity
			var $displayonupdate = $clone.find(".displayonupdate");		// Buttons displayed on an update
			var $displayonadd = $clone.find(".displayonadd");	// Buttons displayed on an add
			var $enableonchange = $clone.find(".enableonchange");		// Buttons enabled on changed content
			var $enablenotonchange = $clone.find(".enablenotonchange");	// Buttons enabled when no change has occurred
			var $activateonaddenter = $clone.find(".displayonadd .activateonenter");		// Button clicked on enter key (on add)
			var $activateonupdateenter = $clone.find(".displayonupdate .activateonenter");	// Button clicked on enter key (on update)
			var $enable = $clone.find(".enable");
			$clone.find(".btn").addClass("d-none").prop("disabled",true);	// First, hide and disable all buttons
		    var $messages = $clone.find("#messages");
		    var $form = $clone.find("form");
		    
			$enable.prop("disabled",false);
			// Rowtype is known so adjust visibility and set enter activation now
			if (row.rowtype=='POST') {
				$displayonadd.removeClass("d-none");
				$activateonaddenter.keyup(function (event){
					if ( event.which == 13 ) {
						event.preventDefault();
						$activateonaddenter.click();
					}
				});
			    // Close button
		    	$onclose.click(function() {
		    		$table.bootstrapTable('remove', {field: '$index', values: [index]});
		    	});
			} else {	// Must be a put (update)
				$displayonupdate.removeClass("d-none");
				$activateonupdateenter.keyup(function (event){
					if ( event.which == 13 ) {
						event.preventDefault();
						$activateonupdateenter.click();
					}
				});
			   	$onclose.click(function() {
			   		$table.bootstrapTable('collapseRowByUniqueId', row.id);
			   		delete row.saved;
			   	});
			}
			$onopen.click(function() {
				window.location.assign(entityType + ".html?id=" + row.id);
			});

			$oninvite.click(function() {
				var $modal = $("#inviteAdministratorModal");
				$modal.find('input[data-invite="entityType"]').val(entityType);
				$modal.find('span[data-invite="entityType"]').text(entityType);
				$modal.find('[data-invite="entityId"]').val(row.id);
				var $description = $form.find(entityDescription);
				var description = [];
				$description.each(function(index) {
					description.push($description[index].value);
				});
				$modal.find('input[data-invite="entityDescription"]').val(description.join(" "));
				$modal.find('span[data-invite="entityDescription"]').text(description.join(" "));
				$modal.find("#invite").prop("disabled",false);
				var $inviteForm = $modal.find("form");
				$modal.find("#invite").click(function(){
					if (validateForm($inviteForm)) {
						if ($inviteForm.find("#email").val().length==0 &&
							$inviteForm.find("#phoneNumber").val().length==0	) {
							$modal.find("#messages").css("color", "red");
							$modal.find("#messages").html("Enter a phone number or an email or both");
							return;
						}
						$modal.find("#messages").css("color", "black");
						$modal.find("#messages").html("Sending Invitation");
						$inviteForm.find("#invite").prop("disabled",true);
						formData = {};
						formData.type="invitation";
						formData.invitationType="ADMINISTRATOR";
						$inviteForm.find("input").each(function () {
							formData[this.id] = this.value;
					});
					$modal.find("#invite span").removeClass("d-none");
					  $.ajax({
						    url: "api/invitation", 
							method: "POST",
							contentType: "application/json",
							data: JSON.stringify(formData)})
						.done(function(data, status) {
							$inviteForm.find("#invite").prop("disabled",false);
							displayMessages(data,$modal.find("#messages"));
							$modal.find("#invite span").addClass("d-none");
							if (data.success) {
								$modal.modal('hide');
							}
				  		});
					}
				});
				$modal.modal('show');
			});

			$onannounce.click(function() {
				var $modal = $("#announceModal");
				$modal.find('input[data-invite="entityType"]').val(entityType);
				$modal.find('span[data-invite="entityType"]').text(entityType);
				$modal.find('[data-invite="entityId"]').val(row.id);
				var $description = $form.find(entityDescription);
				var description = [];
				$description.each(function(index) {
					description.push($description[index].value);
				});
				$modal.find('input[data-invite="entityDescription"]').val(description.join(" "));
				$modal.find('span[data-invite="entityDescription"]').text(description.join(" "));
				$modal.find("#invite").prop("disabled",false);
				var $inviteForm = $modal.find("form");
				$modal.find("#invite").click(function(){
					if (validateForm($inviteForm)) {
						$modal.find("#messages").css("color", "black");
						$modal.find("#messages").html("Sending Invitation");
						$inviteForm.find("#invite").prop("disabled",true);
						formData = {};
						formData.type="invitation";
						formData.invitationType="RESIDENT";
						$inviteForm.find("input").each(function () {
							formData[this.id] = this.value;
					});
					$modal.find("#invite span").removeClass("d-none");
					  $.ajax({
						    url: "api/invitation", 
							method: "POST",
							contentType: "application/json",
							data: JSON.stringify(formData)})
						.done(function(data, status) {
							$inviteForm.find("#invite").prop("disabled",false);
							displayMessages(data,$modal.find("#messages"));
							$modal.find("#invite span").addClass("d-none");
							if (data.success) {
								$modal.modal('hide');
							}
				  		});
					}
				});
				$modal.modal('show');
			});

		    $ondelete.click(function() {
			    $.ajax({
		    		url: "api/" + entityType + "/" + row.id, 
					method: "DELETE"})
					 .done(function(data, status) {
						displayMessages(data,$messages);
						if (data.success) {
							$table.bootstrapTable('remove', {field: 'id', values: [row.id]});
						}
					});
			});
		    if (row.rowstatus=='pending') {
				$enablenotonchange.prop("disabled",true);
				$enableonchange.prop("disabled",false);
			} else {
				$enablenotonchange.prop("disabled",false);
				$enableonchange.prop("disabled",true);
			}
		    //As a field value changes, immediately save the value in a saved location in the row
			$form.change(row,function(event) {
				event.data.saved[event.target.id] = event.target.value;
				event.data.rowstatus="pending";
				$enablenotonchange.prop("disabled",true);
				$enableonchange.prop("disabled",false);
			});
			$messages.text("");
		    $form.submit(function( event ) {
		    	event.preventDefault();
		    	var $form = $(event.target);
		  		if (validateForm($form)) {
					$messages.text("success");
			    	// All is good at this point, so we can send the message to the server
					// Don't send our junky temporary id to the API (for a new object)
					if (row.rowtype=='POST') {
						delete row.saved.id;
					}
			    	var json = JSON.stringify(row.saved);
			    	$.ajax({
		    			url: "api/" + entityType, 
						method: row.rowtype,
						contentType: "application/json",
						data: json})
					  .done(function(data, status) {
						  displayMessages(data,$messages);
		 				  $enableonchange.prop("disabled",true);
						  if (data.success) {
//					    	delete row.saved;	// And saved is no longer valid
					    	delete row.rowstatus;	// And saved is no longer valid
//					    	delete row.rowtype;
							$(data.entities).each(function() {
								if (this.type==entityType){
									$table.bootstrapTable('remove', {field: 'id', values: [row.id]});
									this.rowtype="PUT";	// The new guy is now existing, hence, update.
									$table.bootstrapTable('append', this);
								} 
							});
						  }
				  	  });
				} else {
					$messages.text("fail");
				}
		    });
			
		    var $fields = $clone.find("input,select");
			$fields.each( function(index) {
				$this = $(this);
				// We load up these fields from saved data, not the "original"
				$this.val(row.saved[this.id]);
			});
			return $clone;
	  }
var $initialTab;
function initialize(next){
	$(".nav-link").each(function(index, element) {
		if (index==0) {
			$initialTab = $(element);
			location.hash = this.id;
		}
		element.addEventListener('click', function (event) {
		    event.preventDefault()
			location.hash = this.id;
		    $(this).show();
		  });
	});
	window.onhashchange = function(event) {
		event.preventDefault();
		$hash = $(location.hash);
		if ($hash.length>0) {
			$hash.click();
		} else {
			window.history.back();
//			$initialTab.click();
		}
	};
	// Initial tab based on hash
	$hash = $(location.hash);
	if ($hash) {
		$hash.click();
	}
	  $("body .container").prepend("<div id='heading'></div>");
	  $('#heading').load("heading.html", function() {
		  setupOptions();
		  setupUser(next);
		  $(".phone-us").keyup(function(event) {
			phoneEvent(event);
		  });
	  });
};

function phoneEvent(event) {
	var $field = $(event.target);
	var key = event.which;
	
	if (key==8) return;	// Let a backspace just remove the char
	  var cleaned = ('' + $field.val()).replace(/\D/g, '');
	  var match = cleaned.match(/^(\d{3})(\d{3})(\d{4})$/);
	  if (match) {
		  $field.val('(' + match[1] + ') ' + match[2] + '-' + match[3]);
	  }
	  match = cleaned.match(/^(\d{3})(\d{3})$/);
	  if (match) {
		  $field.val('(' + match[1] + ') ' + match[2] + '-');
	  }
	  match = cleaned.match(/^(\d{3})$/);
	  if (match) {
		  $field.val('(' + match[1] + ') ');
	  }
};

function isValid(field) {
	field.removeClass('is-invalid').addClass('is-valid');
}	
function isInvalid(field) {
	field.removeClass('is-valid').addClass('is-invalid');
}	

/**
 * To validate a field, we start off assuming it succeeds and look for reasons to fail
 */
function validateInputField(field) {
	 field.val(field.val().trim());	// Always trim input fields
	var success = true;
	$type = field.prop("type");
	$required = field.attr("required");
	var $fieldValue = field.val();
	if ($required && $fieldValue.length==0) {
		success = false;
	}
	$max = field.attr("maxlength");
	if ($max) {
		if ($fieldValue.length > $max) {
			success = false;
		}
	}
	$min = field.attr("minlength");
	if ($min) {
		if ($fieldValue.length < $min) {
			success = false;
		}
	}
//	console.info("id: " + field.attr("id") + " Type: " + $type);
	if ($type=="checkbox" && $required && !field.prop('checked')) {
		success = false;
	} else if ($type=="tel") {
		var cleaned = ('' + field.val()).replace(/\D/g, '');
		// Can be empty (if not required) but if not, must be 10 digits
		if (($required || cleaned.length > 0) && cleaned.length!=10) {
			success = false;
		}
	} else if ($type=="email") {
		if ($fieldValue) {
			pattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,16})+$/;
			if (!pattern.test($fieldValue)) {
				success = false;
			}
		}
	}
	$pattern = field.attr("pattern");
	if (success && $fieldValue.length > 0 && $pattern) {
		var regexp = new RegExp($pattern);
		if (!regexp.test($fieldValue)) {
			success = false;
		}
	}
	if (success) {
		isValid(field);
	} else {
		isInvalid(field);
	}
	return success;
}

function validateSelectField(field) {
	var success = true;
	$fieldValue = field.find(":selected").val();
	if (!$fieldValue) {
		success = false;
	}
	if (success) {
		isValid(field);
	} else {
		isInvalid(field);
	}
	return success;

}
function validateForm(formId) {
	var success = true;
	var $form;
	// If formId is a string, then find the form by name, otherwise, it is assumed to be a selector alredy referencing the form
	if (typeof formId === "string") {
		$form = $( "#" + formId );
	} else {
		$form = formId;
	}
	$form.find("input").each(function(index, element) {
		var sts = validateInputField($(element));
		success = (success && sts);
	});
	$form.find("select").each(function(index, element) {
		var sts = validateSelectField($(element));
		success = (success && sts);
	});
	// If a form has password and confirm password fields, make sure they match
	$password = $form.find("#password");
	$confirmPassword = $form.find("#confirmPassword");
	if ($password.size()==1 && $confirmPassword.size()==1 && $password[0].value!=$confirmPassword[0].value) {
		isInvalid($confirmPassword);
		success = false;
	}
	return success;
}

function requestEntity(url,onSuccess) {
 	$.ajax({
	    url: url, 
		method: "GET"})
		.done(function(data, status) {
			  displayMessages(data,"#messages");
			  if (data.success) {
				displayEntities(data);
				if (onSuccess) {
					onSuccess();
				}
			  }
	  	});
}
/**
 * Display the messages that we got back from the server
 */
function displayMessages(response,selector) {
	$messages = $(selector);
	if (response.success) {
		$messages.css("color", "black");
	} else {
		$messages.css("color", "red");
		$("#errorModal").modal('show');
		$(".errorMessages").html(response.messages.join("<br/>"));
	}
	$messages.html(response.messages.join("<br/>"));
	sessionStorage.setItem("lastMessage",$messages.html());
}
/**
 * Display errors, if any.
 * Return true if success
 * The data will always be a json object which is described by the Entity classes
 * we iterate the collection of enties in the response and populate the appropriate
 * elements. Collectins, Regions, Neighborhoods, etc are assumed to populate bootstrap tables
 * with the same name as the entityType. Other types populate elements or form elements
 */
function displayEntities(response) {
	$(response.entities).each(function() {
		if (this.type=="address") populateFields("address",this); else
		if (this.type=="addresses") {
			$("#addresses").bootstrapTable('load', this.addresses); 
			$("#roster").bootstrapTable('load', this.addresses); 
		} else
		if (this.type=="administrator") populateFields("administrator",this); else
		if (this.type=="administrators") $("#administrators").bootstrapTable('load', this.administrators); else
		if (this.type=="alert") populateFields("alert",this); else
		if (this.type=="alerts") $("#alerts").bootstrapTable('load', this.alerts); else
		if (this.type=="candidate") populateFields("candidate",this); else
		if (this.type=="candidates") $("#candidates").bootstrapTable('load', this.candidates); else
		if (this.type=="contact") populateFields("contact",this); else
		if (this.type=="contacts") $("#contacts").bootstrapTable('load', this.contacts); else
		if (this.type=="entities") $("#entities").bootstrapTable('load', this.entities); else
		if (this.type=="invitation") populateFields("invitation",this); else
		if (this.type=="invitations") $("#invitations").bootstrapTable('load', this.invitations); else
		if (this.type=="neighborhood") populateFields("neighborhood",this); else
		if (this.type=="neighborhoods") $("#neighborhoods").bootstrapTable('load', this.neighborhoods); else
		if (this.type=="supress") populateFields("supress",this); else
		if (this.type=="supresses") $("#supresses").bootstrapTable('load', this.supresses); else
		if (this.type=="pet") populateFields("pet",this); else
		if (this.type=="pets") $("#pets").bootstrapTable('load', this.pets); else
		if (this.type=="principal") populateFields("principal",this); else
		if (this.type=="principals") $("#principals").bootstrapTable('load', this.principals); else
		if (this.type=="region") populateFields("region",this); else
		if (this.type=="regions") $("#regions").bootstrapTable('load', this.regions); else
		if (this.type=="resident") populateFields("resident",this); else
		if (this.type=="residents") $("#residents").bootstrapTable('load', this.residents); else
		if (this.type=="user") populateFields("user",this); else
		if (this.type=="vehicle") populateFields("vehicle",this); else
		if (this.type=="vehicles") $("#vehicles").bootstrapTable('load', this.vehicles);
	});
}
function populateFields(eType, record) {
	// Look for elements with data-nw-"eType"
	// store the matching field name from the record into either the value or text, depending on element type.
	// Make a selector
	sel = "[data-nw-" + eType + "]";
	$(sel).each(function() {
		fieldName = $(this).attr("data-nw-" + eType);
		if ($(this).is("input")) {
			if (this.type=="checkbox") {
				this.checked=record[fieldName];
			} else {
				$(this).val(record[fieldName]);
			}
			$(this).removeClass("is-valid").removeClass("is-invalid");
		} else if ($(this).is("ul")) {
			
		} else if ($(this).is("select")) {
				$(this).val(record[fieldName]);
		} else {
			this.innerHTML = record[fieldName];
		}
	});
}
function clearPrincipal() {
	sessionStorage.setItem("principal",null);
}

function getPrincipalId() {
	return sessionStorage.getItem("principal");
}
function setPrincipalId( id ) {
	sessionStorage.setItem("principal",id);
}
function clearAfterLogin() {
	sessionStorage.setItem("afterLogin","");
}
function pushAfterLogin() {
	sessionStorage.setItem("afterLogin",window.location.href);
}
// If AfterLogin is set, go to that page, otherwise, go to specified page
function popAfterLogin(page) {
	var next = sessionStorage.getItem("afterLogin");
	if (!next) {
		next = page;
	}
	pushAfterLogin("");
	window.location.assign(next);
}

function clearRegion() {
	sessionStorage.setItem("region",null);
	sessionStorage.setItem("administrator",null);	
	clearNeighborhood();
}
function getRegionId() {
	return sessionStorage.getItem("region");
}
function setRegionId( id ) {
	sessionStorage.setItem("region",id);
}

function getNeighborhoodId() {
	return sessionStorage.getItem("neighborhood");
}
function setNeighborhoodId( id ) {
	sessionStorage.setItem("neighborhood",id);
}
function getNeighborhood() {
	return sessionStorage.getItem("neighborhood");
}


function setAddressId( id ) {
	sessionStorage.setItem("address",id);
}

function getAddressId() {
	return sessionStorage.getItem("address");
}

function clearAddress() {
	sessionStorage.setItem("address",null);
	clearResident();
	clearContact();
	clearVehicle();
	clearPet();
}
function setResidentId( id ) {
	sessionStorage.setItem("resident",id);
}


function getResidentId() {
	return sessionStorage.getItem("resident");
}

function setContactId( id ) {
	sessionStorage.setItem("contact",{id: id});
}

function getContactId() {
	return sessionStorage.getItem("contact");
}

function setVehicleId( id ) {
	sessionStorage.setItem("vehicle",id);
}

function getVehicleId() {
	return sessionStorage.getItem("vehicle");
}

function setPetId( id ) {
	sessionStorage.setItem("pet",id);
}


function getPetId() {
	return sessionStorage.getItem("pet");
}

function setUserId( id ) {
	sessionStorage.setItem("user",id);
}


function getUser() {
	return sessionStorage.getItem("user");
}

function clearUser() {
	sessionStorage.setItem("user",null);	
}


function getMode( ) {
	return sessionStorage.getItem("mode");
}
function isNew( ) {
	return getMode()=='new';
}
function isUpdate() {
	return getMode()!='new';
}
function setMode(mode) {
	sessionStorage.setItem("mode", mode);
}

function toggleDisplayFields() {
		$("[data-display='new']").toggle(isNew());
		$("[data-display='update']").toggle(!isNew());
		console.log("new=" + isNew());
}


function updateFormFields(top,pre) {
	for (let [key, value] of Object.entries(top)) {
		if (typeof value == "object") {
			if (typeof pre !== 'undefined') {
	            updateFormFields(value,pre+"."+ key);
			} else {
	            updateFormFields(value, key, "");
			}
		} else {
		    	if (typeof pre !== 'undefined') {
			    	newKey = pre + "." + key;
		    	} else {
			    	newKey = key;
		    	}
		  	  $("[data-nw='" + newKey+ "']").val(value);
		    	console.log(newKey, "==", value);
		}
	}
}
function updateDisplayFields(top,pre) {
	for (let [key, value] of Object.entries(top)) {
		if (typeof value == "object") {
			if (typeof pre !== 'undefined') {
	            updateDisplayFields(value,pre+"."+ key);
			} else {
	            updateDisplayFields(value, key, "");
			}
		} else {
		    	if (typeof pre !== 'undefined') {
			    	newKey = pre + "." + key;
		    	} else {
			    	newKey = key;
		    	}
		  	  $("[data-nw='" + newKey+ "']").text(value);
		    	console.log(newKey, "==", value);
		}
	}
}

// Add a <li> with embedded <a> to <selector> pointing to <entityType>.html?id=<entityId and named <name>
function addMenuItem(selector, name, entityId, entityType) {
	$menu = $(selector);
	$item = $('<li class="dropdown-item"><a href="' + entityType + '.html?id=' + entityId + '">' + name + '</a></li>');
	$menu.append($item);
}
/**
 * get system-wite settings such as title from config yaml file
 */
function setupOptions() {
	$.ajax({
	    url: "/api/utility/options", 
		method: "GET"})
		.done(function(data, status) {
			populateFields("options",data);
		});
}
/**
 * classes used to enable-disable display by toggling d_none class: isLoggedIn, isNotLoggedIn, isSystemUser, hasRegions, hasNeighborhoods, hasAddresses
 * data-nw-XXX="field" names: "...region", "...neighborhood", "...address", "...user", 
 */
function setupUser(onSuccess) {
	$(".isSystemUser").addClass("d-none");
	$(".hasRegions").addClass("d-none");
	$(".hasNeighborhoods").addClass("d-none");
	$(".hasAddresses").addClass("d-none");
	$(".isNotLoggedIn").addClass("d-none");
	$(".isLoggedIn").addClass("d-none");
	$(".editableBySystem").attr("disabled",true);
	$(".isAdministrator").addClass("d-none");
	$.ajax({
	    url: "/api/user", 
		method: "GET"})
		.done(function(data, status) {
			  if (data.success) {
			    displayMessages(data,"#messages");
				displayEntities(data);
				$(".isLoggedIn").removeClass("d-none");
				setupEntities();
				if (onSuccess) {
					onSuccess();
				}
			  } else {
				$(".isNotLoggedIn").removeClass("d-none");
			}
	  	});
}
// What the user can do (called from setup user)
function setupEntities() {
	$.ajax({
	    url: "/api/entities", 
		method: "GET"})
		.done(function(data, status) {
			displayMessages(data,"#messages");
			if (data.success) {
				const urlParams = new URLSearchParams(window.location.search);
				const id = urlParams.get('id');

				$(data.entities).each(function() {
						if (this.id==id) {
							$(".isAdministrator").removeClass("d-none");
						}
					if (this.type=="regions") {
						$(".editableBySystem").attr("disabled",false);
						$(".isSystemUser").removeClass("d-none");
					} else if (this.type=="principals") {
						$(".isSystemUser").removeClass("d-none");
					} else if (this.type=="address") {
						$(".hasAddresses").removeClass("d-none");
						// Add menu item to myAddresses
						addMenuItem("#myAddressesMenu",this.line1,this.id,this.type);
					} else if (this.type=="neighborhood") {
						$(".hasNeighborhoods").removeClass("d-none");
						// Add menu item to myNighborhoods
						addMenuItem("#myNeighborhoodsMenu",this.name,this.id,this.type);
					} else if (this.type=="region") {
						$(".hasRegions").removeClass("d-none");
						// Add menu item to myRegions
						addMenuItem("#myRegionsMenu",this.name,this.id,this.type);
					}
				});
			}
	  	});
}
