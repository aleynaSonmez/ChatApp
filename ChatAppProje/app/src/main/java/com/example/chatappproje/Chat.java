package com.example.chatappproje;

public class Chat {

    String mesaj;
    String gonderenID;
    String aliciID;

    public Chat()
    {

    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getGonderenID() {
        return gonderenID;
    }

    public void setGonderenID(String gonderenID) {
        this.gonderenID = gonderenID;
    }

    public String getAliciID() {
        return aliciID;
    }

    public void setAliciID(String aliciID) {
        this.aliciID = aliciID;
    }

    public Chat(String mesaj, String gonderenID, String aliciID) {
        this.mesaj = mesaj;
        this.gonderenID = gonderenID;
        this.aliciID = aliciID;
    }
}
