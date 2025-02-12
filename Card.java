class Card {

    private String value;
    private String type;

    Card(String v, String t){
        value = v;
        type = t;
    }

    // возвращает значение карты
    public String getValue(){
        return this.value;
    }

    // проверка типа катры
    public int checkingСardType(){
        // проверяем является ли наше значение тузом, королем, дамой, джокером
        if ("AJQK".contains(value)){
            if (value == "A"){
                return  11; // для туза вернем 11
            }
            return 10; // для мастей 10
        }
        return Integer.parseInt(value); // ну а для чисел сами числа
    }

    // вернет строковое представление карты ЗНАЧЕНИЕ-ТИП
    @Override
    public String toString(){
        return value + "-" + type;
    }

    public String imagePath(){
        return "./card/"+toString()+".png";
    }
}