package com.backinfile.portal.model;

import com.backinfile.support.Random;
import com.backinfile.support.StreamUtils;
import com.backinfile.support.Tuple2;
import com.backinfile.support.Utils;
import com.backinfile.support.func.Predicate;

import java.util.*;

public class CardPile implements Iterable<Card> {
    private final LinkedList<Card> cards = new LinkedList<>();

    public CardPile() {
    }

    public CardPile(Iterable<Card> cards) {
        this.addAll(cards);
    }

    public CardPile(Iterator<Card> cards) {
        while (cards.hasNext()) {
            add(cards.next());
        }
    }

    public CardPile(Card... cards) {
        for (Card card : cards) {
            this.add(card);
        }
    }

    public void add(Card card) {
        if (card != null) {
            if (!cards.contains(card)) {
                cards.add(card);
            }
        }
    }

    public void add(int index, Card card) {
        if (card != null) {
            cards.add(index, card);
        }
    }

    public void addAll(Iterable<Card> cards) {
        for (Card card : cards) {
            add(card);
        }
    }

    public List<Long> getCardIdList() {
        return StreamUtils.map(cards, card -> card.id);
    }

    public boolean remove(Card card) {
        if (card != null) {
            return cards.remove(card);
        }
        return false;
    }

    public boolean removeAll(Predicate<Card> predicate) {
        boolean removed = false;
        for (Card card : new ArrayList<>(cards)) {
            if (predicate.test(card)) {
                removed |= remove(card);
            }
        }
        return removed;
    }

    public void removeAll(CardPile cardPile) {
        for (Card card : cardPile) {
            remove(card);
        }
    }

    public void clear() {
        cards.clear();
    }

    public boolean contains(Card card) {
        return cards.contains(card);
    }

    public boolean contains(long id) {
        return StreamUtils.any(cards, card -> card.id == id);
    }

    public Card get(int index) {
        return cards.get(index);
    }

    public Card getAny() {
        if (cards.isEmpty()) {
            return null;
        }
        return get(0);
    }

    public Card getCard(long id) {
        for (Card card : cards) {
            if (card.id == id) {
                return card;
            }
        }
        return null;
    }

    public CardPile getTop(int n) {
        CardPile cardPile = new CardPile();
        for (int i = 0; i < n; i++) {
            cardPile.add(this.get(size() - i - 1));
        }
        return cardPile;
    }

    public Card getTop() {
        return cards.getLast();
    }

    public Card pollTop() {
        return cards.pollLast();
    }

    public CardPile getBottom(int n) {
        CardPile cardPile = new CardPile();
        for (int i = 0; i < n; i++) {
            cardPile.add(this.get(i));
        }
        return cardPile;
    }

    public CardPile pollTop(int n) {
        CardPile cardPile = getTop(n);
        for (Card card : cardPile) {
            remove(card);
        }
        return cardPile;
    }

    public CardPile getRandom(int n) {
        CardPile copy = new CardPile(this);
        CardPile polls = new CardPile();
        for (int i = 0; i < n; i++) {
            if (copy.isEmpty()) {
                break;
            }
            Card card = copy.get(Utils.nextInt(copy.size()));
            copy.remove(card);
            polls.add(card);
        }
        return polls;
    }

    public CardPile pollRandom(Random random, int n) {
        CardPile polls = new CardPile();
        for (int i = 0; i < n; i++) {
            if (isEmpty()) {
                break;
            }
            Card card = get(random.next(size()));
            remove(card);
            polls.add(card);
        }
        return polls;
    }

    public CardPile reverse() {
        Collections.reverse(cards);
        return this;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int size() {
        return cards.size();
    }

    @Override
    public Iterator<Card> iterator() {
        return new ArrayList<>(cards).iterator();
    }

    public CardPile filter(Predicate<Card> predicate) {
        return new CardPile(StreamUtils.filter(cards, predicate));
    }

    public CardPile filter(Class<? extends Card> clazz) {
        return new CardPile(StreamUtils.filter(cards, clazz::isInstance));
    }

    public Set<Tuple2<Integer, Card>> cardsWithIndex() {
        Set<Tuple2<Integer, Card>> cardsIndex = new HashSet<Tuple2<Integer, Card>>();
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            cardsIndex.add(new Tuple2<Integer, Card>(i, card));
        }
        return cardsIndex;
    }

    public void shuffle(Random random) {
        if (isEmpty()) {
            return;
        }
        CardPile newCardPile = new CardPile(this);
        this.clear();
        while (!newCardPile.isEmpty()) {
            int rnd = random.next(0, newCardPile.size());
            Card card = newCardPile.get(rnd);
            newCardPile.remove(card);
            this.add(card);
        }
    }

    public CardPile copy() {
        return new CardPile(this);
    }

}

