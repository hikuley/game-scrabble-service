package com.sahibinden.repository;

import com.sahibinden.domain.Board;
import com.sahibinden.domain.Word;

import java.util.List;

public class WordRepository extends BaseRepositoryImpl<Word> {


    public List<Word> getWords(Long id) {

        getEntityManager().getTransaction().begin();

        Board board = new Board();

        board.setId(id);

        List<Word> words = getEntityManager()
                .createQuery("select w from Word w where w.board=:board", Word.class)
                .setParameter("board", board)
                .getResultList();

        getEntityManager().getTransaction().commit();

        return words;

    }

}
