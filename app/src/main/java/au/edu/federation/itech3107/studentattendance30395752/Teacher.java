package au.edu.federation.itech3107.studentattendance30395752;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "teachers")
public class Teacher {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey
    @ColumnInfo(name = "_id")
    private int id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;


}
