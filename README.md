# ContactDirectory

<h1>Общее описание проекта</h1>
Разработка web приложения Справочник контактов.
<h2>Основные функциональные требования</h2>
<ul>
    <li>Возможность создания, просмотра,  редактирование и удаления контакта</li>
<li>Возможность подсоединения документов к  контакту</li>
    <li>Поиск по различным полям контакта</li>
    <li>Возможность отправлять email на основании шаблона всем выбранным контактам</li>
    <li>Автоматическая отправка email контактам подходящим по заданным критериям</li>
</ul>
<h2>Основные формы приложения</h2>
<ul>
    <li>Главная Форма - Список контактов</li>
    <li>Форма Поиска</li>
    <li>Форма Создания \ Редактирования контакта</li>
    <li>Форма отправки email выбранным контактам.</li>

</ul>

<h2>Описание формы Список контактов</h2>
<p>Форма Список контактов содержит табличное представление контактов.
Список контактов разбит постранично (10 или 20 контактов на страницу). Поддерживается постраничная навигация.
На форме Список контактов присутствуют кнопки позволяющие Создать новый контакт, редактировать или удалить существующий. Также на форме Список контактов присутствует кнопка перехода к форме поиска и кнопка отправки email.
    Каждая строка таблицы контактов содержит краткую информацию о контакте:</p>
<ul>
    <li>Полное имя</li>
    <li>Дата рождения</li>
    <li>Адрес</li>
    <li>Компания в которой сейчас работает контакт</li>
</ul>

<p>Полное имя контакта представлено как гиперссылка. По нажатию на гиперссылку открывается форма редактирования выбранного Контакта.
    Первая колонка таблицы содержит checkbox для каждой записи. При нажатии на кнопку удаления контактов должны быть удалены все контакты для которых checkbox в состоянии “выбран”.</p>
<h2>Описание Формы Создания \ Редактирования контакта</h2>

<p>Форма Создания \ Редактирования контакта содержит следующие поля, таблицы и подформы:</p>
<p>Основные поля</p>
<ul>
    <li>Имя*</li>
    <li>Фамилия*</li>
    <li>Отчество</li>
    <li>Дата рождения</li>
    <li>Пол (Выбор из двух опций)</li>
    <li>Гражданство</li>
    <li>Семейное положение</li>
    <li>Web Site</li>
    <li>Email</li>
    <li>Текущее место работы</li>
    <li>Адрес
    <ul>
        <li>Страна</li>
        <li>Город</li>
        <li>Улица/Дом/Квартира</li>
        <li>Индекс</li>
    </ul>
    </li>
</ul>

<h2>Список контактных телефонов</h2>
<p>Форма контакт содержит таблицу со списком контактных телефонов.
Каждая строка таблицы содержит следующие колонки</p>
<ul>
    <li>Checkbox для выбора строки</li>
    <li>Полный телефонный номер</li>
    <li>Описание номера (Домашний / Мобильный)</li>
    <li>Комментарий (если есть)</li>
</ul>

<p>Рядом  с таблицей телефонов  (над таблицей с выравниванием по правому краю таблицы) расположены кнопки для удаления/редактирования/создание нового телефона.
    Создание/редактирование телефонов происходит в отдельной Подформе Редактирования Телефонов представленной в виде Pop-up окна.</p>
<h2>Подформа  Редактирования Телефонов</h2>
<p>Подформа  Редактирования Телефонов содержит следующие поля:</p>
<ul>
    <li>Код страны</li>
    <li>Код оператора</li>
    <li>Телефонный номер</li>
    <li>Тип телефона (Домашний/Мобильный)</li>
    <li>Комментарий</li>
</ul>

<p>На подформе Редактирования Телефонов присутствуют кнопки Сохранить  / Отменить
    При нажатии на кнопку сохранить форма закрывается и телефон появляется в таблице контактных телефонов. Реальное сохранение в базу данных происходит только при сохранении всего контакта.</p>
<h2>Список Присоединений (attachments)</h2>
<p>Форма контакт содержит таблицу со списком присоединений (attachments).
    Каждая строка таблицы содержит следующие колонки</p>
<ul>
    <li>Имя файла присоединения</li>
    <li>Дата загрузки</li>
    <li>Комментарий</li>
</ul>

<p>Рядом  с таблицей присоединений  (над таблицей с выравниванием по правому краю таблицы) расположены кнопки для удаления/редактирования/создание нового присоединения.
    Создание/редактирование присоединений происходит в отдельной Подформе Редактирования присоединений представленной в виде Pop-up окна.</p>
<h2>Подформа  Редактирования Присоединений</h2>
<p>Подформа  Редактирования Присоединений содержит следующие поля:</p>
<ul>
    <li>Имя файла присоединения</li>
    <li>Комментарий</li>
</ul>
<p>На подформе Редактирования Присоединений присутствуют кнопки Сохранить  / Отменить
    При нажатии на кнопку сохранить форма закрывается и Присоединение появляется в таблице Присоединений. Реальное сохранение в базу данных происходит только при сохранении всего контакта.</p>
<h2>Фото и форма выбора фото</h2>
<p>В Левом верхнем углу Формы Редактирования Контакта присутствует поле для фото. По умолчанию в поле помещена стандартная обезличенная картинка с силуэтом человека. При клике на картинку открывается форма выбора фото. Форма содержит поле – путь к картинке на диске, кнопку “Найти” и кнопки “Сохранить”/ “Отменить” .  Реальное сохранение в Базу данных происходит при сохранении всего контакта.</p>
<h2>Форма  поиска</h2>
<p>Форма поиска содержит поля и позволяет искать контакты по Фамилии, Имени, Отчеству, Дате рождения (больше/меньше), полу, семейному положению, гражданству, полям относящимся к адресу.
    После заполнения полей на Форме поиска пользователь нажимает кнопку “Поиск” . Найденные результаты отображаются на стандартной форме Список Контактов</p>
<h2>Форма отправки email выбранным контактам</h2>
<p>Выбрав в Списке Контактов несколько контактов и нажав на кнопку отправить email пользователь переходит на форму отправки email. Форма отправки email содержит поля:</p>
<ul>
    <li>Кому (где перечислены emails всех выбранных контактов)</li>
    <li>Тема</li>
    <li>Шаблон</li>
    <li>Текст</li>

</ul>
<p>На форме присутствуют кнопки Отправить и Отменить.
Поле Шаблон позволяет выбрать один из предопределенных шаблонов (используемых с библиотекой StringTemplates). В этом случае текст шаблона отображается в поле Текст.
    Если шаблон не выбран, пользователь может ввести любой текст письма вручную.</p>
<h2>Автоматическая отправка email контактам подходящим по заданным критериям</h2>
<p>Ежедневно программа автоматически (без каких то действий пользователя) должна проверять есть ли контакты у которых сегодня день рождения. И если такие контакты найдены отправлять администратору системы письмо со списком контактов у которых сегодня день рождения.</p>
 
<h2>Технические требования</h2>
<h3>Общие требования</h3>
<ul>
    <li>В общем случае для backend и frontend частей приложения не допускается использование сторонних библиотек и фреймворков, если явным образом не указано иное.</li>
    <li>Программа должна производить валидацию вводимых пользователем значений и не допускать данных которые не соответствуют формату поля или могут привести к ошибкам в работе системы.</li>
    <li>Форматирование кода должно быть консистентным для каждого используемого в приложении языка программирования. Java код должен соответствовать стандарту Java Code Convention.</li>

</ul>

<h3>Backend</h3>
<ul>
    <li>Сборка приложения должна производиться с использованием maven или gradle и быть модульной, например, содержать модуль с логикой (бизнес логикой, взаимодействием с базой и т.д.) и web модуль.</li>
    <li>Приложение должно иметь REST API, предоставляющие данные для frontend. Также backed должен поставлять статические файлы (html, css, js). Для реализации допустимо использование только Java EE стека (Servlet и т.д).</li>
    <li>При необходимости допускается использование JSP вместо html страниц, например для генерации head страницы. Но с минимально возможной логикой вынесенной в JSP.</li>
    <li>Для доступа к базе данных должно использоваться JDBC API.</li>
    <li>Необходимо логировать основные действия пользователя, а также все ошибки, возникающие в системе, например, с использованием log4j или slf4j.</li>
    <li>Допускается использование Open Source библиотек для реализации отдельных функций системы, таких как Quartz, StringTemplate, Commons Upload, другие библиотеки из Apache Commons.</li>
    <li>Для написания тестов допускается использование любых необходимых библиотек</li>

</ul>

<h3>Frontend</h3>
<ul>
    <li>Frontend часть представляет собой набор статических страничек возвращаемых сервером, каждая страничка реализует свою логику.</li>
    <li>Приложение должно быть написано с использованием чистого JS стандарта ES5, HTML5 и CSS3. При желании допускается использование template engine, например, mustache.</li>
    <li>Каждая страница приложения должна содержать только необходимый лично ей код, т.е. страница поиска не должна содержать стили или скрипты от страницы со списком контактов.
        Но можно иметь отдельные файлы с переиспользуемым между страницами кодом.</li>
    <li>Web-приложение должно корректно работать в последних версиях всех основных браузеров (включая IE11).</li>
    <li>Для отправки запросов в браузерах, не поддерживающих fetch API, можно использовать fetch polyfill.</li>

</ul>
