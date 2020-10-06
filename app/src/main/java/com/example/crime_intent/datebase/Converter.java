package com.example.crime_intent.datebase;

import androidx.room.TypeConverter;

import java.util.Date;
import java.util.UUID;

public class Converter {
    @TypeConverter
    public static Date longToDate(long timestamp){
        return new Date(timestamp);
    }
    @TypeConverter
    public static long DateToLong(Date date){
        return date.getTime();
    }
    @TypeConverter
    public static UUID StringToUUID(String uuid){
        return UUID.fromString(uuid);
    }
    @TypeConverter
    public static String UUIDToString(UUID uuid){
        return uuid.toString();
    }

}
