"use strict";

export function home(){
    let init = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        mode: 'cors',
        cache: 'default'
    };
    fetch('http://localhost:8080/api/contacts?current-page=1&page-limit=2', init)
        .then(response => {
            return response.json()
        })
        .then(result => {
            createContactsTable(result);
            createPagination(result);
        });
}


function createContactsTable(result){

    let contactTable =
        '<table><thead><tr><th>Выбрать</th>></th><th>ФИО</th><th>Дата рождения</th><th>Место работы</th><th>Адрес</th><th>Изменить</th><th>Удалить</th></thead><tbody>';
    for (let contact of result.shortContacts) {
        let address = contact.address;
        contactTable += `<tr>
<td><input type="checkbox" class="contactCheckbox" id="contactCheckbox${contact.id}" value="${contact.id}"></td>
<td><a href="/#/edit/${contact.id}">${contact.firstName} ${contact.lastName} ${contact.middleName}</a></td>
<td> ${contact.dateOfBirth}</td>
<td>${contact.currentPlaceOfWork}</td>
<td>${address.country}, ${address.city}, ${address.street}, ${address.houseNumber},
${address.apartmentNumber}, ${address.postcode}</td>
<td><button onclick="location.href='#/edit/${contact.id}';">Изменить</button></td>
<td><button onclick=deleteContact(${contact.id})>Удалить</button></td></tr>`;
    }
    contactTable += '</tbody></table>' +
        '<button onclick=deleteContacts()>Удалить выбранные</button>';
    document.getElementById('app').innerHTML = contactTable;
}

function createPagination(result) {
    let pagination="";
    for (let i = 1; i <= result.totalElements / result.pageLimit + 1; i++) {
        if(result.currentPage==1){

        }else {
            pagination += `<a href="http://localhost:8080/api/contacts?current-page=${i}&page-limit=${result.pageLimit}">${i}</a> `;
        }
    }
    document.getElementById('app').innerHTML=document.getElementById('app').innerHTML+pagination;
}

function deleteContacts(){
    let deleteQuery = '[';
   for(let checkbox of document.querySelectorAll('contactCheckbox')){
       if(checkbox.checked){
           deleteQuery+=checkbox.value;
       }
   }
   deleteQuery+=']';
    let init = {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        mode: 'cors',
        cache: 'default',
        body: JSON.stringify(deleteQuery)
    };
   fetch('http://localhost:8080/api/contacts',init);
}

function createContact(){
    let createContact =
        '<form action="">' +
        '<label for="createFirstNameInput">Имя</label><br>' +
        '        <input type="text" id="createFirstNameInput"><br>' +
        '        <label for="createMiddleNameInput">Отчество</label><br>' +
        '        <input type="text" id="createMiddleNameInput"><br>' +
        '        <label for="createLastNameInput">Фамилия</label><br>' +
        '        <input type="text" id="createLastNameInput"><br>' +
        '        <label for="createDateOfBirth">Дата рождения</label><br>' +
        '        <input type="date" id="createDateOfBirth"><br>' +
        '        <label for="createGender">Пол</label>' +
        '        <select id="createGender">' +
        '        <option value="male">мужской</option>' +
        '        <option value="female">женский</option>' +
        '        </select><br>' +
        '        <label for="createMaritalStatus">Семейное положение</label>' +
        '    <select id="createMaritalStatus">' +
        '        <option value="single">холост/не замужем</option>' +
        '    <option value="married">женат/замужем</option>' +
        '        <option value="divorced">разведён/разведена</option>' +
        '        <option value="widowed">вдова/вдовец</option>' +
        '        </select><br>' +
        '        <label for="createCitizenship">Гражданство</label><br>' +
        '        <input type="text" id="createCitizenship"><br>' +
        '        <label for="createWebsite">Гражданство</label><br>' +
        '        <input type="text" id="createWebsite"><br>' +
        '        <label for="createEmail">Гражданство</label><br>' +
        '        <input type="email" id="createEmail"><br>' +
        '        <label for="createCurrentPlaceOfWork">Гражданство</label><br>' +
        '        <input type="text" id="createCurrentPlaceOfWork"><br>' +
        '        <label for="createPhoto">Гражданство</label><br>' +
        '        <input type="text" id="createPhoto"><br>' +

        '        <label for="createCountry">Страна</label><br>' +
        '        <input type="text" id="createCountry"><br>' +
        '        <label for="createCity">Город</label><br>' +
        '        <input type="text" id="createCity"><br>' +
        '        <label for="createStreet">Улица</label><br>' +
        '        <input type="text" id="createStreet"><br>' +
        '        <label for="createHouseNumber">Дом</label><br>' +
        '        <input type="number" id="createHouseNumber"><br>' +
        '        <label for="createApartmentNumber">Квартира</label><br>' +
        '        <input type="number" id="createApartmentNumber"><br>' +
        '        <label for="createPostcode">Индекс</label><br>' +
        '        <input type="number" id="createPostcode"><br>' +



        '        <input text="Создать" type="submit"></form>'
}

function sendCreate() {

}