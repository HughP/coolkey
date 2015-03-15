### I. Założenia projektowe ###
CoolKey jest wieloplatformowym programem do nauki bezwzrokowego pisania na klawiaturze (przy użyciu wszystkich dziesięciu palców). Zawiera kurs składający się z serii lekcji, pozwalających na opanowanie kolejnych klawiszy na klawiaturze, zaczynając od "asdf", "jkl;" itd. CoolKey śledzi postępy użytkownika i w zależności od jego wyników dobiera poziom trudności ćwiczeń (wprowadzając nowe klawisze lub sugerując powtórzenie lekcji). Istnieje możliwość wyboru pomiędzy dwoma rodzajami ćwiczeń: standardowym przepisywaniem podanego tekstu, lub graniem w polegającą na szybkim pisaniu grę. Po opanowaniu całej klawiatury użytkownik może trenować prędkość pisania poprzez przepisywanie tekstów literackich o różnym poziomie trudności, lub tekstów wygenerowanych z użyciem łańcucha Markowa.

Możliwości programu:
  * Obsługa wielu użytkowników (możliwość zabezpieczenia profilu hasłem).
  * Dostosowanie kursu do układu klawiatury QWERTY lub Dvorak.
  * Możliwość ćwiczenia z użyciem dowolnych ładowanych z dysku plików tekstowych.
  * Wykresy pokazujące jak zmieniała się prędkość w trakcie wykonywania ćwiczenia, aktualizowane na bieżąco.
  * Statystyki dla użytkownika, pokazujące postępy w prędkości i bezbłędności (również dla poszczególnych klawiszy).
  * Wyspecjalizowane ćwiczenia, dotyczące znaków specjalnych, liczb, obszarów klawiatury, palców.
  * Wizualizacja klawiatury na ekranie, aby oduczyć użytkownika spoglądania w dół na swoją klawiaturę.
  * Efekty dźwiękowe (stukanie klawiszy, sygnalizowanie błędu).
  * Możliwość generowania treści ćwiczeń specjalnie dostosowanej do użytkownika (z uwzględnieniem tego, które klawisze sprawiają mu trudność).


### II. Podział pracy ###
Michał Dettlaff:
  * moduł przepisywania
  * treść ćwiczeń
  * instrukcja użytkowania

Karol Domagała:
  * interfejs użytkownika
  * rysowanie wykresów

Łukasz Draba:
  * gra edukacyjna


### III. Technologie potrzebne do realizacji projektu ###
  * Język programowania Java, z użyciem Java API w wersji 1.6
  * Biblioteka Standard Widget Toolkit 3.4
  * Zintegrowane Środowisko Programistyczne Eclipse 3.4 Ganymede
  * System kontroli wersji Subversion


### IV. Platformy docelowe ###
Program przeznaczony jest dla systemów Windows i GNU/Linux, ze Środowiskiem Uruchomieniowym Javy (JRE) w wersji 1.5 lub nowszej.


### V. Uwagi końcowe ###
Program jest dostępny na licencji GNU General Public License v3.

Do programu załączona jest dokumentacja wygenerowana za pomocą narzędzia Javadoc.