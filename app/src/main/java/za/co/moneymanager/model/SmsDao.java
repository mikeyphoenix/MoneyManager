package za.co.moneymanager.model;

/**
 * Created by michaeljacobs on 9/4/2015.
 */
public class SmsDao {
    String id;
    String address;
    String person;
    String date;
    String body;

    public SmsDao(String id, String address, String person, String date, String body) {
        this.id = id;
        this.address = address;
        this.person = person;
        this.date = date;
        this.body = body;
    }

    public SmsDao() {
        this.id = null;
        this.address = null;
        this.person = null;
        this.date = null;
        this.body = null;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
