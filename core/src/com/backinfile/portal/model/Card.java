package com.backinfile.portal.model;

import com.backinfile.portal.LocalString;

public class Card {
    public long id;
    public int cost, finalCost;
    public int health, finalHealth;
    public LocalString.LocalCardString localCardString;

    public Card(LocalString.LocalCardString localCardString) {
        this.localCardString = localCardString;
        this.cost = this.finalCost = localCardString.cost;
        this.health = this.finalHealth = localCardString.health;
    }

}
