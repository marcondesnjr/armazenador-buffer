package io.github.marcondesnjr.armazenadorbuffer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author José Marcondes do Nascimento Junior
 */
public class ArmazenadorBuffer<T extends Ilike, R> {

    private final List<T> list;
    private final List<Integer> vitima;
    private final int tamanho;
    private final int INITIAL_VIC = 0;
    private final CRUD<T, R> crud;
    private final Equivalente eq;

    public ArmazenadorBuffer(int tamanho, CRUD<T, R> crud, Equivalente<T, R> eq) {
        list = new ArrayList<>();
        vitima = new ArrayList<>();
        this.tamanho = tamanho;
        this.crud = crud;
        this.eq = eq;
    }

    public boolean isFull() {
        return tamanho == list.size();
    }

    public void add(T element) throws PersistenceException {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).ilike(element)) {
                crud.update(element);
                list.set(i, element);
                addVit();
                vitima.set(i, vitima.get(i)-1);
                return;
            }
        }
        if (!isFull()) {
            crud.create(element);
            list.add(element);
            vitima.add(INITIAL_VIC);           
        } else {
            int prox = proxVitimaIndex();
            list.remove(prox);
            vitima.remove(prox);
            addVit();
            list.add(element);
            vitima.add(INITIAL_VIC);
            crud.create(element);
        }
    }

    public T search(R arg) throws PersistenceException {
        for (int i = 0; i < list.size(); i++) {
            if (eq.equivalente(list.get(i), arg)) {
                    addVit();
                    vitima.set(i, vitima.get(i) - 1);
                return list.get(i);
            }
        }
        T val = crud.read(arg);
        if (val != null) {
            add(val);
        }
        return val;
    }

    public void remove(R arg) throws PersistenceException {
        crud.delete(arg);
        for (int i = 0; i < list.size(); i++) {
            if (eq.equivalente(list.get(i), arg)) {
                list.remove(i);
                vitima.remove(i);
            }
        }
    }

    private int proxVitimaIndex() {
        int proxVitima = 0;
        for (int i = 0; i < vitima.size(); i++) {
            if (vitima.get(proxVitima) <= vitima.get(i)) {
                proxVitima = i;
            }
        }
        return proxVitima;
    }

    private void addVit() {
        for (int i = 0; i < vitima.size(); i++) {
            vitima.set(i, vitima.get(i) + 1);
        }
    }

}
