/*
 * Sigleton бин для работы с MongoDB 
 * создаётся при вызове методов из класса
 */
package com.tmis.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.tmis.entities.Record;
import com.tmis.entities.TmUnit;
import com.tmis.entities.UnitType;
import com.tmis.entities.ValueType;
import com.tmis.facade.RecordFacade;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Класс бизнесс-логики для MongoDB
 * @author Sokolov Slava
 */
@Singleton
@Startup
@DeclareRoles({"ADMIN", "EUSER", "CUSER", "USER"})
@RolesAllowed({"ADMIN", "EUSER", "CUSER", "USER"})
public class MongoDB {
    
    @EJB
    RecordFacade recordFacade;

    private DB db;
    private MongoClient client;
    private DBCollection collection;

    @PostConstruct
    public void start() {
        try {
            if( client == null ){ client = new MongoClient(); }            
            db = client.getDB("admin");
            boolean auth = db.authenticate("root", "secret".toCharArray());
            db = client.getDB("passportDB");
            collection = db.getCollection("passports");
        } catch (UnknownHostException e) {
            System.out.println("Ошибка mongo");
        }
    }

    @PreDestroy
    public void cloceConnection() {
        if (client != null) {
            client.close();
        }
    }

    public void add(Record record, List<Elem> elems) {
        BasicDBObject doc = new BasicDBObject();
        doc.put("record", record.getId());
        for (Elem elem : elems) {
            System.out.println("тип " + elem.getValueType());
            System.out.println("параметр " + elem.getParam());
            if (!elem.getTable().isEmpty()) {
                doc.put(elem.getValueType().getNameId(), elem.getTable());
            } else {
                doc.put(elem.getValueType().getNameId(), elem.getParam());
            }
        }
        System.out.println("Метка " + doc);
        collection.save(doc);
    }

    public void remove(Record record) {
        BasicDBObject query = new BasicDBObject();
        query.put("record", record.getId());
        collection.remove(query);
    }

    public List<Elem> getParams(Record record) {
        BasicDBObject query = new BasicDBObject();
        query.put("record", record.getId());
        DBObject rez = collection.findOne(query);
        if (rez == null) {
            return null;
        }
        List<Elem> params = new ArrayList<Elem>();
        UnitType unitType = record.getTmUnit().getUnitType();
        List<ValueType> valueTypes =
                new ArrayList<ValueType>(unitType.getValueTypes());
        for (ValueType valueType : valueTypes) {
            Elem param = new Elem(valueType);
            param.setParam(rez.get(valueType.getNameId()));
            if (valueType.getType().equals("таблица")) {
                param.toTable();
            }
            if (valueType.getType().equals("массив")) {
                param.toTable();
            }
            System.out.println("значение " + param.getParam());
            params.add(param);
        }
        return params;
    }

    public void edit(Record record, Elem elem) {
        BasicDBObject query = new BasicDBObject();
        query.put("record", record.getId());
        DBObject newData = collection.findOne(query);
        if (!elem.getTable().isEmpty()) {
            newData.put(elem.getValueType().getNameId(), elem.getTable());
        } else {
            newData.put(elem.getValueType().getNameId(), elem.getParam());
        }
        collection.update(query, newData);
    }
    
    public List<TmUnit> find( Elem elem ){
        List<TmUnit> result = new ArrayList<TmUnit>();
        DBObject query = new BasicDBObject();
        if (!elem.getTable().isEmpty()) {
            query.put(elem.getValueType().getNameId(), elem.getTable());
        } else {
            query.put(elem.getValueType().getNameId(), elem.getParam());
        }
        DBCursor cursor = collection.find( query );
        while (cursor.hasNext()) {
            DBObject dbo = cursor.next();
            Long recordId = (Long)dbo.get("record");
            Record record = recordFacade.find( recordId );
            result.add( record.getTmUnit() );
        }
        return result;
    }

    public void removeAll() {
        collection.drop();
    }
}
