package com.backinfile.portal.model;

public class Source {
    public CardPile link = new CardPile();
    public CardPile memory = new CardPile();
    public int star = 1, starMax = 5;

    public static class VirusSource extends Source {

    }

    public static class ServerSource extends Source {

    }
}
