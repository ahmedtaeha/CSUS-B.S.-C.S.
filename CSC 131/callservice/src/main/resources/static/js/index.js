let filterToggled = false;
let lastFilter = "";
let entityStats = $(".entityStats");
let availableStats = $(".availableStats");
let busyStats = $(".busyStats");
let previewStats = $(".previewStats");
let loggedOutStats = $(".loggedOutStats");
let afterStats = $(".afterStats");
let gridElement

// Hanlder for events being emitted
$(document).ready(() => {
    var source = new EventSource("/api/v1/init");
    gridElement = $("#dots-grid");

    source.addEventListener("updateAgent", EventHandler);
    source.addEventListener("deleteAgent", deleteHandler);

    $(".agent-dot").hover(function (e) {
        $(".agent_name").html($(this).attr("data-user-name"));
        $("#status-information").html('[' + $(this).attr("data-user-status") + ']');
        $("#status-information").removeClass();
        $("#status-information").addClass($(this).attr("data-user-status"));
        $(".agent-info-div").css("visibility", "visible");
    }, function () {
        // mouseout event codes...
    });
});


/**
 * Method to handle the event listener. Awaits events and parses data to be pushed onto frontend.
 * @param {Object} event Event from emitter that handles the incoming events
 */
function EventHandler(event) {
    // parse through the event
    let agentData = JSON.parse(event.data);

    // check in all the displayed i elements if the agent is already present
    let present = false;

    // check if the user is present
    let agent = $(`i[data-user-id='${agentData.id}']`);
    if (agent.length) {
        present = true;
        updateAgent(agent, agentData);
    } else {
        entityStats.html(parseInt(entityStats.html()) + 1);
        let element = createElement(agentData);
        updateHomeStats(agentData.status);
        gridElement.prepend(element);
    }
}

/**
 * Method to update statistics and information of entities on web page
 */
function deleteHandler(event) {
    let data = JSON.parse(event.data);

    let agent = $(`i[data-user-id='${data.id}']`);
    if (agent.length) {
        present = true;
        if (agent.hasClass("available")) {
            availableStats.html(parseInt(availableStats.html()) - 1);
        } else if (agent.hasClass("busy")) {
            busyStats.html(parseInt(busyStats.html()) - 1);
        } else if (agent.hasClass("preview")) {
            previewStats.html(parseInt(previewStats.html()) - 1);
        } else if (agent.hasClass("after")) {
            afterStats.html(parseInt(afterStats.html()) - 1);
        } else if (
            agent.hasClass("loggedout") ||
            agent.hasClass("logged-out")
        ) {
            loggedOutStats.html(parseInt(loggedOutStats.html()) - 1);
        }
        agent.remove();
    }
}

/**
 * Method to update a user's attributes and class values when agent already present.
 * @param {jQuery Obj} element jQuery node element.
 * @param {JSON} agentData Object containing information pertaining to the user we are updating
 * @param {jQuery Obj} childElemtn jQuery node element.
 */
function updateAgent(element, agentData) {
    // let childElement = $(element.children()[0]);
    // check if the element has any present colors if so remove then update
    if (element.hasClass("available")) {
        element.toggleClass("available", false);
        availableStats.html(parseInt(availableStats.html()) - 1);
    } else if (element.hasClass("busy")) {
        element.toggleClass("busy", false);
        busyStats.html(parseInt(availableStats.html()) - 1);
    } else if (
        element.hasClass("loggedout" || element.hasClass("logged-out"))
    ) {
        element.toggleClass("loggedout", false);
        element.toggleClass("logged-out", false);
        loggedOutStats.html(parseInt(availableStats.html()) - 1);
    } else if (element.hasClass("preview")) {
        element.toggleClass("preview", false);
        previewStats.html(parseInt(availableStats.html()) - 1);
    } else if (element.hasClass("after")) {
        element.toggleClass("after", false);
        afterStats.html(parseInt(availableStats.html()) - 1);
    }

    // update the new color
    element.toggleClass(agentData.status); // TODO: add error handling incase they pass an invalid name
    updateHomeStats(agentData.status);

    $(element.children()[0]).html(`${agentData.name}<br>${agentData.status}`);
}

function createElement(agentData) {
    const nodeElement = document.createElement("i");
    const element = $(nodeElement);

    const spanElement = document.createElement("span");
    spanElement.classList.add("tooltiptext");
    spanElement.innerHTML = `${agentData.name} <br>${agentData.status}`;

    element.addClass(
        `fa-solid fa-circle agent-dot tooltipType ${agentData.status}`
    );

    // attributes to be used when hovering
    element.attr("data-user-id", agentData.id);

    element.append(spanElement);

    return element;
}

function updateHomeStats(status) {
    // update stats
    if (status.toLowerCase() == "available") {
        availableStats.html(parseInt(availableStats.html()) + 1);
    } else if (status.toLowerCase() == "busy") {
        busyStats.html(parseInt(busyStats.html()) + 1);
    } else if (status.toLowerCase() == "preview") {
        previewStats.html(parseInt(previewStats.html()) + 1);
    } else if (
        status.toLowerCase() == "logged-out" ||
        status.toLowerCase() == "loggedout"
    ) {
        loggedOutStats.html(parseInt(loggedOutStats.html()) + 1);
    } else if (status.toLowerCase() == "after") {
        afterStats.html(parseInt(afterStats.html()) + 1);
    }
}

function filter(status) {
    if (
        !(
            status.toLowerCase() == "available" ||
            status.toLowerCase() == "busy" ||
            status.toLowerCase() == "preview" ||
            status.toLowerCase() == "loggedout" ||
            status.toLowerCase() == "logged-out" ||
            status.toLowerCase() == "after"
        )
    ) {
        resetFilters();
        return;
    }

    let agents = [];
    agents = document.querySelectorAll(".agent-dot");
    filterToggled = true;
    resetFilters();
    if (status == lastFilter) {
        lastFilter = "";
        return;
    }

    // check if filter valid
    agents.forEach((dot) => {
        if (!$(dot).hasClass(status.toLowerCase())) {
            $(dot).toggleClass("d-none", true);
        }
        
    });
    lastFilter = status;
}

function resetFilters() {
    document.querySelectorAll(".agent-dot").forEach( (dot) => {
        if ($(dot).hasClass("d-none")) {
            $(dot).toggleClass("d-none", false);
        }
    })
}
