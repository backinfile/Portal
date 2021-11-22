package com.backinfile.portal.model;

import com.backinfile.portal.LocalString;

public class Card {
    public long id;
    public CardType cardType;

    public int cost, finalCost;
    public int health, healthMax, finalHealth;

    public LocalString.LocalCardString localCardString;

}
