package com.jjmgab.notifier.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.time.LocalDateTime;
import java.util.List;

@Dao
public interface NotificationDao {
    @Query("SELECT * FROM Notification ORDER BY date")
    List<Notification> getAll();

    @Query("SELECT * FROM Notification WHERE id IN (:ids)")
    List<Notification> loadAllByIds(int[] ids);

    /*
    @Query("SELECT * FROM Notification WHERE date > :date")
    List<Notification> getAfterDate(LocalDateTime date);
/*
    /*
    @Query("SELECT * FROM Notification WHERE date = :date")
    List<Notification> findByDate(LocalDateTime date);

    @Query("SELECT * FROM Notification WHERE date >= :date")
    List<Notification> findAfterDate(LocalDate date, LocalTime time);

    @Query("SELECT * FROM Notification WHERE date < :date")
    List<Notification> findBeforeDate(LocalDate date, LocalTime time);
*/
    @Insert
    void insertAll(Notification... notifications);

    @Delete
    void delete(Notification notifications);
}
