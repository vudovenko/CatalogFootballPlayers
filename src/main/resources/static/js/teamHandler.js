'use strict';

function handleTeamSelection() {
    let teamSelect = document.getElementById("teamSelect");
    let teamInput = document.getElementById("teamInput");

    teamInput.disabled = teamSelect.value !== "";
    if (teamInput.disabled) {
        teamInput.value = "";
    }
}

function handleTeamInput() {
    let teamSelect = document.getElementById("teamSelect");
    let teamInput = document.getElementById("teamInput");

    teamSelect.disabled = teamInput.value !== "";
    if (teamSelect.disabled) {
        teamSelect.value = "";
    }
}