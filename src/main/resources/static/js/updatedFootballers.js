'use strict';

var footballersData = document.querySelector('#footballers-data');

var stompClient = null;

console.log('Проверка из updatedFootballers.js');

connect();

function connect(event) {
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);

    event.preventDefault();
}

function onConnected() {
    // Подписываемся на тему
    console.log('Соединение устанавливается');
    stompClient.subscribe('/topic/public', onMessageReceived);
    console.log('Соединение установлено');
}

function onError(error) {
    console.error('Ошибка во время соединения:' + error);
}

function onMessageReceived(payload) {
    console.log('Сообщение получено в onMessageReceived в updatedFootballers.js');
    let footballers = JSON.parse(payload.body);

    // Перед обновлением таблицы, проверим наличие элемента с id footballers-data
    if (!footballersData) {
        console.error('Элемент с id "footballers-data" не найден на странице');
        return;
    }

    // Очищаем содержимое таблицы
    footballersData.innerHTML = '';

    // Перебираем массив футболистов и добавляем их в таблицу
    footballers.forEach(function(footballer) {
        let row = document.createElement('tr');

        let firstNameCell = document.createElement('td');
        firstNameCell.textContent = footballer.firstName;
        row.appendChild(firstNameCell);

        let lastNameCell = document.createElement('td');
        lastNameCell.textContent = footballer.lastName;
        row.appendChild(lastNameCell);

        let birthDateCell = document.createElement('td');
        birthDateCell.textContent = footballer.birthDate;
        row.appendChild(birthDateCell);

        let genderCell = document.createElement('td');
        genderCell.textContent = footballer.gender;
        row.appendChild(genderCell);

        let countryCell = document.createElement('td');
        countryCell.textContent = footballer.country;
        row.appendChild(countryCell);

        let teamCell = document.createElement('td');
        teamCell.textContent = footballer.team.name;
        row.appendChild(teamCell);

        let editCell = document.createElement('td');
        let editLink = document.createElement('a');
        editLink.href = '/footballers/edit/' + footballer.id;
        editLink.textContent = 'Редактировать данные';
        editCell.appendChild(editLink);
        row.appendChild(editCell);

        footballersData.appendChild(row);
    });
}