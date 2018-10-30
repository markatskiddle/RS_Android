package com.example.markprime.rs_android.scan.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;

public class SharedPrefs {

    public static final String PREFS_PROMOTER = "PREFS_PROMOTER";
    public static final String PROMOTER_ID = "promoter_id";
    public static final String PREFS_PACCESS = "PREFS_PACCESS";
    public static final String PACCESS_TOKEN = "paccess_token";

    public SharedPrefs() {
        super();
    }

    ///////Saved Events ///////////////////////
    public void saveEvents(Context context, List<EventObject> save) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_PROMOTER,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonSaved = gson.toJson(save);

        editor.putString(PROMOTER_ID, jsonSaved);

        editor.apply();
    }

    public void addPromoter(Context context, EventObject eventObject) {
        List<EventObject> save = getEvents(context);
        if (save == null)
            save = new ArrayList<EventObject>();
        save.add(eventObject);
        saveEvents(context, save);
    }

    public void removeEvents(Context context, EventObject eventObject) {
        ArrayList<EventObject> save = getEvents(context);
        if (save != null) {
            save.remove(eventObject);
            saveEvents(context, save);
        }

    }

    public ArrayList<EventObject> getEvents(Context context) {
        SharedPreferences settings;
        List<EventObject> save;

        settings = context.getSharedPreferences(PREFS_PROMOTER,
                Context.MODE_PRIVATE);

        if (settings.contains(PROMOTER_ID)) {
            String jsonSaved = settings.getString(PROMOTER_ID, null);
            Gson gson = new Gson();
            EventObject[] savedItems = gson.fromJson(jsonSaved,
                    EventObject[].class);

            save = Arrays.asList(savedItems);
            save = new ArrayList<EventObject>(save);
        } else
            return null;

        return (ArrayList<EventObject>) save;
    }



    /////////////////Purchased Tickets/////////////////



    // This four methods are used for maintaining favorites.
    public void saveTicket(Context context, List<EventObject> ticket) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_PACCESS,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonTickets = gson.toJson(ticket);

        editor.putString(PACCESS_TOKEN, jsonTickets);

        editor.apply();
    }

    public void addTicket(Context context, EventObject eventObject) {
        List<EventObject> ticket = getTickets(context);
        if (ticket == null)
            ticket = new ArrayList<EventObject>();
        ticket.add(eventObject);
        saveTicket(context, ticket);
    }

    public void removeTicket(Context context, EventObject eventObject) {
        ArrayList<EventObject> ticket = getTickets(context);
        if (ticket != null) {
            ticket.remove(eventObject);
            saveTicket(context, ticket);
        }
    }

    public ArrayList<EventObject> getTickets(Context context) {
        SharedPreferences settings;
        List<EventObject> ticket;

        settings = context.getSharedPreferences(PREFS_PACCESS,
                Context.MODE_PRIVATE);

        if (settings.contains(PACCESS_TOKEN)) {
            String jsonFavorites = settings.getString(PACCESS_TOKEN, null);
            Gson gson = new Gson();
            EventObject[] ticketItems = gson.fromJson(jsonFavorites,
                    EventObject[].class);

            ticket = Arrays.asList(ticketItems);
            ticket = new ArrayList<EventObject>(ticket);
        } else
            return null;

        return (ArrayList<EventObject>) ticket;
    }
}
