package ru.mavesoft.cornofficeapp.office_entrance;

public class EntranceRequest {

    String email;
    int key;

    public EntranceRequest(String email, int key) {
        this.email = email;
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
