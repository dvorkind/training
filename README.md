# Training `<epam>` - Java Web Development

[Task 1](https://github.com/dvorkind/training/tree/main/recording-task1-OOP/src/by/dvorkin/recording)

Создать консольное приложение, удовлетворяющее следующим требованиям:
<UL><li>Использовать возможности ООП: классы, наследование, полиморфизм, инкапсуляция.</li>
<li>Каждый класс должен иметь исчерпывающее смысл название и информативный состав.</li>
<li>Наследование должно применяться только тогда, когда это имеет смысл.</li>
<li>При кодировании должны быть использованы соглашения об оформлении кода java code convention.</li>
<li>Классы должны быть грамотно разложены по пакетам.</li>
<li>Работа с консолью или консольное меню должно быть минимальным.</li>
<li>Для хранения параметров инициализации можно использовать файлы.</li></UL>

**Звукозапись. Определить иерархию музыкальных композиций. Записать на диск сборку. Рассчитать продолжительность. Провести перестановку композиций диска на основе принадлежности к стилю. Найти композицию, соответствующую заданному диапазону длины треков.**

[Task 2](https://github.com/dvorkind/training/tree/main/strings-task2/src/by/dvorkin/strings)

Создать программу обработки текста учебника по программированию с использованием классов (по необходимости) для представления: символа, слова, предложения, знака препинания и др. Во всех задачах с формированием текста заменять табуляции и последовательности пробелов одним пробелом. Программа должна обрабатывать адреса электронной почты и номера телефонов в формате +XXX(XX)XXX-XX-XX как отдельные слова.

**В каждом предложении текста поменять местами первое слово с последним, не изменяя длины предложения.**

[Task 3](https://github.com/dvorkind/training/tree/main/xml-task3/src/by/dvorkin/xml)

<UL><li>Разработать для своего варианта структуру XML документа, описать её с помощью XSD. Создать файл XML, соответствующий разработанной XSD схеме.</li>
<li>При разработке XSD использовать простые и комплексные типы, перечисления, шаблоны и предельные значения, обязательно использование атрибутов и типа ID.</li>
<li>Создать Java-класс(классы), соответствующие разработанной схеме.</li>
<li>Создать Java-приложение для разбора XML-документа и инициализации коллекции объектов информацией из XML-файла. Для разбора использовать SAX, DOM или StAX парсер. Для сортировки объектов использовать интерфейс Comparator.</li>
<li>Произвести проверку корректности и валидности XML-документа с привлечением XSD.</li></UL>

**Компьютеры.**
Компьютерные комплектующие имеют следующие характеристики:
<UL><li>Name – название комплектующего.</li>
<li>Origin – страна производства.</li>
<li>Price – цена (0 – n рублей).</li>
<li>Type (должно быть несколько) – периферийное либо нет, энергопотребление (ватт), наличие кулера (есть либо нет), группа комплектующих (устройства ввода-вывода, мультимедийные), порты (COM, USB, LPT).</li>
<li>Critical – критично ли наличие комплектующего для работы компьютера.</li></UL>

[Task 4-5](https://github.com/dvorkind/training/tree/main/web-task4-5)

Разработать подсистему для работы с базой данных предложенной предметной области:
<UL><li>Разработать схему базы данных в соответствии с предметной областью вашего варианта. Создать sql-скрипты создания БД, пользователя БД, создание таблиц, заполнение таблиц, удаление данных, удаление таблиц, удаление БД, обновление данных, запросы на выборку данных.</li>
<li>Информацию о предметной области хранить в БД, для доступа использовать API JDBC с использованием пула соединений, разработанного самостоятельно. В качестве СУБД используется MySQL.</li>
<li>На основе сущностей предметной области создать классы их описывающие.</li>
<li>Классы и методы должны иметь отражающую их функциональность названия и должны быть грамотно структурированы по пакетам.</li>
<li>Оформление кода должно соответствовать Java Code Convention.</li>
<li>Приложение должно поддерживать работу с кириллицей (быть многоязычной), в том числе и при хранении информации в БД.</li>
<li>Выполнить журналирование событий, то есть информацию о возникающих исключениях и событиях в системе обрабатывать с помощью Log4j 2.</li>
<li>Код должен содержать комментарии.</li></UL>

**Система Телефонная станция.** 

**Администратор** осуществляет подключение **Абонентов**. **Абонент**  может выбрать одну или несколько из предоставляемых **Услуг**. **Абонент**  оплачивает **Счет** за разговоры и **Услуги**. **Администратор** может просмотреть список неоплаченных **Счетов** и заблокировать **Абонента**.

_Построить веб-систему (для предметной области в соответствии с вариантом task 4), поддерживающую заданную функциональность:_
<UL><li>Интерфейс приложения должен поддерживать работу с кириллицей (быть многоязычной).</li>
<li>Архитектура приложения должна соответствовать шаблону Model-View-Controller.</li>
<li>При реализации алгоритмов бизнес-логики использовать шаблоны GoF: Factory Method, Command, Builder, Strategy, State, Observer etc.</li>
<li>Используя сервлеты и JSP, реализовать функциональности, предложенные в постановке конкретной задачи.</li>
<li>В страницах JSP применять библиотеку JSTL и разработать собственные теги.</li>
<li>При разработке бизнес логики использовать сессии и фильтры.</li>
<li>Код должен содержать комментарии.</li></UL>