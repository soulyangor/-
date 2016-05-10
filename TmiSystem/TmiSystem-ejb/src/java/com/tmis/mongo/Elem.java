/*
 * Класс Elem для связи записей mongo с интерфейсом
 * пользователя, используется для отображения
 * массивов и таблиц
 */
package com.tmis.mongo;

import com.tmis.entities.ValueType;
import java.util.ArrayList;

/**
 * Вспомогательный элемент для работы с MongoDb
 * @author Sokolov Slava
 */
public class Elem {
    
    private ValueType valueType;
    private Object param;
    private ArrayList<Double[]> table = new ArrayList<Double[]>();
    private Double x;
    private Double y;

    public ValueType getValueType() { return valueType; }
    public void setValueType(ValueType valueType) { this.valueType = valueType; }

    public Object getParam() { return param; }
    public void setParam(Object param) { this.param = param; }

    public ArrayList<Double[]> getTable() {
        if( table == null ){ table = new ArrayList<Double[]>(); }
        return table; 
    }
    public void setTable(ArrayList<Double[]> table) { this.table = table; }

    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }

    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }

    public Elem( ValueType valueType ){
        this.valueType = valueType;
    }
    
    public void toTable(){ 
        System.out.println( "Вывод параметра "+this.param );
        this.table = ( ArrayList<Double[]> )this.param;
        System.out.println( "Вывод таблицы "+this.table );
    }
}
