"use strict";

export function navigateMain() {

    const urlSearchParams = new URLSearchParams(location.search);

    if (!urlSearchParams.has('current-page')) {
        urlSearchParams.append('current-page', '1');
    }
    if (!urlSearchParams.has('page-limit')) {
        urlSearchParams.append('page-limit', '10');
    }

    history.replaceState({}, '', [location.pathname, urlSearchParams].join('?'));

    if (document.querySelector('#searchForm') == null) {
        buildSearch();
    }

    fetch('http://localhost:8080/api/contacts' + location.search,
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            mode: 'cors',
            cache: 'default'
        })
        .then(response => response.json())
        .then(result => {
            createContactsTable(result);
            createPagination(result);
        });
}


function buildSearch() {
    const searchForm = '<form id="searchForm" action="#">' +
        '<label for="searchFirstNameInput">Имя</label><br>' +
        '<input type="text" id="searchFirstNameInput" name="first-name"><br>' +
        '<label for="searchMiddleNameInput">Отчество</label><br>' +
        '<input type="text" id="searchMiddleNameInput" name="middle-name"><br>' +
        '<label for="searchLastNameInput">Фамилия</label><br>' +
        '<input type="text" id="searchLastNameInput" name="last-name"><br>' +
        '<label>Дата рождения</label><br>' +
        '<input type="date" id="searchDateOfBirthAfter" name="date-of-birth-after"><br>' +
        '<input type="date" id="searchDateOfBirthBefore" name="date-of-birth-before"><br>' +
        '<label for="searchGender">Пол</label>' +
        '<select id="searchGender" name="gender">' +
        '<option value=""></option>' +
        '<option value="male">мужской</option>' +
        '<option value="female">женский</option>' +
        '</select><br>' +
        '<label for="searchMaritalStatus">Семейное положение</label>' +
        '<select id="searchMaritalStatus" name="marital-status">' +
        '<option value=""></option>' +
        '<option value="single">холост/не замужем</option>' +
        '<option value="married">женат/замужем</option>' +
        '<option value="divorced">разведён/разведена</option>' +
        '<option value="widowed">вдова/вдовец</option>' +
        '</select><br>' +
        '<label for="searchCitizenship">Гражданство</label><br>' +
        '<input type="text" id="searchCitizenship" name="citizenship"><br>' +
        '<label for="searchCountry">Страна</label><br>' +
        '<input type="text" id="searchCountry" name="country"><br>' +
        '<label for="searchCity">Город</label><br>' +
        '<input type="text" id="searchCity" name="city"><br>' +
        '<label for="searchStreet">Улица</label><br>' +
        '<input type="text" id="searchStreet" name="street"><br>' +
        '<label for="searchHouseNumber">Дом</label><br>' +
        '<input type="number" id="searchHouseNumber" name="house-number"><br>' +
        '<label for="searchApartmentNumber">Квартира</label><br>' +
        '<input type="number" id="searchApartmentNumber" name="apartment-number"><br>' +
        '<label for="searchPostcode">Индекс</label><br>' +
        '<input type="number" id="searchPostcode" name="postcode"><br>' +
        '<input type="submit" value="Поиск">' +
        '</form>';

    document.querySelector('#app').appendChild(document.createRange().createContextualFragment(searchForm));
    document.querySelector('#searchForm').addEventListener('submit', search);

}

function search(event) {
    event.preventDefault();
    const urlSearchParams = new URLSearchParams(location.search);
    [...document.querySelector("#searchForm").elements]
        .filter(element => element.type !== 'submit')
        .forEach(element => {
            if (element.value === '') {
                urlSearchParams.delete(element.name);
            } else {
                urlSearchParams.set(element.name, element.value);
            }
        });

    history.replaceState({}, '', [location.pathname, urlSearchParams].join('?'));

    removeNode('#contactTable');
    removeNode('#pagination');
    navigateMain();
}

function removeNode(selector) {
    cleanNode(selector);
    document.querySelector(selector).remove();
}

function cleanNode(selector) {
    const myNode = document.querySelector(selector);
    while (myNode.lastElementChild) {
        myNode.removeChild(myNode.lastElementChild);
    }
}

function createContactsTable(result) {

    let contactTable =
        '<table id="contactTable"><thead><tr><th>Выбрать</th>></th><th>ФИО</th><th>Дата рождения</th><th>Место работы</th><th>Адрес</th><th>Изменить</th><th>Удалить</th></thead><tbody>';
    for (let contact of result.shortContacts) {
        const address = contact.address;
        contactTable += '<tr>'+
`<td><input type="checkbox" class="contactCheckbox" id="contactCheckbox${contact.id}" value="${contact.id}"></td>`+
`<td><a href="/edit/${contact.id}">${contact.firstName} ${contact.lastName} ${contact.middleName}</a></td>`+
`<td> ${contact.dateOfBirth}</td>`+
`<td>${contact.currentPlaceOfWork}</td>`+
`<td>${address.country}, ${address.city}, ${address.street}, ${address.houseNumber},
${address.apartmentNumber}, ${address.postcode}</td>`+
'<td><button>Изменить</button></td>'+
'<td><button>Удалить</button></td></tr>';}
    contactTable += '</tbody></table>';
    document.querySelector('#app').appendChild(document.createRange().createContextualFragment(contactTable));
}

function createPagination(result) {

    const div=document.createElement('div');
    div.setAttribute('id','pagination');
    const label=document.createElement('label');
    label.setAttribute('for','pageLimitSelect');
    label.setAttribute('value','Записей на странице');
    const br=document.createElement('br');
    const select=document.createElement('select');
    select.setAttribute('id','pageLimitSelect');

    const option1=document.createElement('option');
    option1.setAttribute('value','10');
    option1.setAttribute('label','10');

    const option2=document.createElement('option');
    option2.setAttribute('value','20');
    option2.setAttribute('label','20');
    select.appendChild(option1);
    select.appendChild(option2);

    select.addEventListener('change', changePageLimit);

     const urlSearchParams = new URLSearchParams(location.search);
     if(urlSearchParams.get('page-limit')==='20'){
         select.selectedIndex=1;
     }else {
         select.selectedIndex=0;
         urlSearchParams.set('page-limit','10');
         history.replaceState({}, '', [location.pathname, urlSearchParams].join('?'));
     }

    div.appendChild(label);
    div.appendChild(select);
    div.appendChild(br);

    for (let i = 1; i <= result.totalElements / result.pageLimit + 1; i++) {
        if (result.currentPage === i) {
          const p=document.createElement('p');
            p.textContent=`${i}`;
          div.appendChild(p);
        } else {
            const a=document.createElement('a');
            a.setAttribute('href','#');
            a.textContent=`${i}`;
            div.appendChild(a);
            a.addEventListener('click', loadPage);
        }
    }

    document.querySelector('#app').appendChild(div);
}

function loadPage(event){
    event.preventDefault();

    const urlSearchParams = new URLSearchParams(location.search);
    urlSearchParams.set('current-page', this.text);

    history.replaceState({}, '', [location.pathname, urlSearchParams].join('?'));
    removeNode('#contactTable');
    removeNode('#pagination');
    navigateMain();
}

function changePageLimit(){
    const urlSearchParams = new URLSearchParams(location.search);
    urlSearchParams.set('page-limit', this.value);
    urlSearchParams.delete('current-page');
    history.replaceState({}, '', [location.pathname, urlSearchParams].join('?'));
    removeNode('#contactTable');
    removeNode('#pagination');
    navigateMain();
}
