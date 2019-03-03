package app.view;

public class MenuView implements View {

    @Override
    public String render(String parameter) {
        return "Pomoc - wpisz 'pomoc' |" +
            " Infomracje o kursie - wpisz 'kurs {krótka nazwa kursu}' |" +
            " Informacje na temat student - wpisz 'student {nr indeksu}'";
    }
}
